package com.adrianivorra.springboot.backend.moviemate.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.adrianivorra.springboot.backend.moviemate.entity.ComentarioPelicula;
import com.adrianivorra.springboot.backend.moviemate.entity.ComentarioSerie;
import com.adrianivorra.springboot.backend.moviemate.entity.Usuario;
import com.adrianivorra.springboot.backend.moviemate.services.ComentariosServiceImpl;
import com.adrianivorra.springboot.backend.moviemate.services.UsuarioServiceImpl;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class UsuariosRestController {

	@Autowired
	private UsuarioServiceImpl usuarioService;

	@GetMapping("/usuarios")
	public ResponseEntity<?> getAllUsers() {
		List<Usuario> listaUsuarios = new ArrayList<>();
		Map<String, Object> response = new HashMap<>();

		try {
			listaUsuarios = usuarioService.getAllUsers();
		} catch (DataAccessException e) { // La base de datos esta apagada
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<List<Usuario>>(listaUsuarios, HttpStatus.OK);
	}

	@GetMapping("/usuarios/{uid}")
	public ResponseEntity<?> findUserById(@PathVariable String uid) {
		Usuario usuario = null;
		Map<String, Object> response = new HashMap<>();

		try {
			usuario = this.usuarioService.findUserById(uid);
		} catch (DataAccessException e) { // La base de datos esta apagada
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (usuario == null) { // No se encuentra el usuario
			response.put("mensaje", "El usuario con UID: ".concat(uid.concat(" no existe en la base de datos")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);
	}

	@PostMapping("/usuarios")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> createUser(@Valid @RequestBody Usuario user, BindingResult result) {
		Usuario nuevoUsuario = null;
		Map<String, Object> response = new HashMap<>();

		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream()
					.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());

			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}

		try {
			nuevoUsuario = usuarioService.save(user);
		} catch (DataAccessException e) { // La base de datos esta apagada
			response.put("mensaje", "Error al realizar el insert en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (user.getUid() == null || user.getEmail() == null) {
			response.put("error", "Comprueba que los campos uid o email no esten vacios");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}

		response.put("mensaje", "El usuario se ha insertado con exito!");
		response.put("usuario", nuevoUsuario);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@PutMapping("/usuarios/{uid}")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> updateUser(@Valid @RequestBody Usuario user, BindingResult result,
			@PathVariable String uid) {
		Usuario currentUser = usuarioService.findUserById(uid);
		Usuario updateUserUsuario = null;

		Map<String, Object> response = new HashMap<>();

		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream()
					.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());

			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}

		if (currentUser == null) {
			response.put("mensaje", "Error: no se pudo editar el chiste con uid: "
					.concat(uid.concat(". No existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		try {
			updateUserUsuario = usuarioService.save(user);
		} catch (DataAccessException e) { // La base de datos esta apagada
			response.put("mensaje", "Error al actualizar el usuario en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "El usuario ha sido actualizado con exito!");
		response.put("usuario", updateUserUsuario);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@DeleteMapping("/usuarios/{uid}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<?> deleteUser(@PathVariable String uid) {
		Map<String, Object> response = new HashMap<>();

		try {
			Usuario currentUser = usuarioService.findUserById(uid);
			usuarioService.deleteUser(currentUser);
		} catch (DataAccessException e) { // La base de datos esta apagada
			response.put("mensaje", "Error al eliminar el usuario de la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "Usuario eliminado con éxito!");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	// COMENTARIOS
	@Autowired
	private ComentariosServiceImpl comentarioRepository;

	@GetMapping("/usuarios/{uid}/movie/comentarios")
	public ResponseEntity<?> findMovieComentsByUserId(@PathVariable String uid) {
		List<ComentarioPelicula> listaComentariosPelicula = new ArrayList<>();
		Map<String, Object> response = new HashMap<>();

		try {
			listaComentariosPelicula = this.comentarioRepository.findMovieComentsByUserUid(uid);
		} catch (DataAccessException e) { // La base de datos esta apagada
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (listaComentariosPelicula.size() == 0) { // No se encuentran comentarios
			response.put("mensaje", "El usuario con UID: ".concat(uid.concat(" no tiene comentarios")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<ComentarioPelicula>>(listaComentariosPelicula, HttpStatus.OK);
	}

	@GetMapping("/usuarios/{uid}/serie/comentarios")
	public ResponseEntity<?> findSerieComentsByUserId(@PathVariable String uid) {
		List<ComentarioSerie> listaComentariosSerie = new ArrayList<>();
		Map<String, Object> response = new HashMap<>();

		try {
			listaComentariosSerie = this.comentarioRepository.findSerieComentsByUserUid(uid);
		} catch (DataAccessException e) { // La base de datos esta apagada
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (listaComentariosSerie.size() == 0) { // No se encuentran comentarios
			response.put("mensaje", "El usuario con UID: ".concat(uid.concat(" no tiene comentarios")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<ComentarioSerie>>(listaComentariosSerie, HttpStatus.OK);
	}

	@GetMapping("/usuarios/{uid}/find")
	public ResponseEntity<?> getUsersByUserPreference(@PathVariable String uid, @RequestParam("page") int page) {
		Usuario user = null;
		Page<Usuario> pageUusarios = null;
		Map<String, Object> response = new HashMap<>();

		try {
			user = usuarioService.findUserById(uid);
			if (user != null) {
				Pageable pageable = PageRequest.of(page, 10);
				pageUusarios = usuarioService.findByDistance(user.getLatitude(), user.getLongitude(),
						user.getDistance_preference(), user.getUid(), user.getAge_min_preference(),
						user.getAge_max_preference(), pageable);
				for (Usuario usuario : pageUusarios) {
					usuario.setAge(usuario.getAge());
					usuario.setDistance(calcularDistancia(user.getLatitude(), user.getLongitude(),
							usuario.getLatitude(), usuario.getLongitude()));
				}

			} else {
				response.put("error", "Comprueba que los campos uid o email no esten vacios");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
			}

		} catch (DataAccessException e) { // La base de datos esta apagada
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Page<Usuario>>(pageUusarios, HttpStatus.OK);
	}

	public static final double RADIO_TIERRA = 6371; // en kilómetros

	public static double calcularDistancia(double latitud1, double longitud1, double latitud2, double longitud2) {
		double dLat = Math.toRadians(latitud2 - latitud1);
		double dLon = Math.toRadians(longitud2 - longitud1);

		double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(Math.toRadians(latitud1))
				* Math.cos(Math.toRadians(latitud2)) * Math.sin(dLon / 2) * Math.sin(dLon / 2);

		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

		double distancia = RADIO_TIERRA * c;

		return distancia;
	}

}
