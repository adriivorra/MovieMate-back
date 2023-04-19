package com.adrianivorra.springboot.backend.moviemate.controllers;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.adrianivorra.springboot.backend.moviemate.entity.ComentarioPelicula;
import com.adrianivorra.springboot.backend.moviemate.entity.ComentarioSerie;
import com.adrianivorra.springboot.backend.moviemate.services.ComentariosServiceImpl;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class ComentariosRestController {

	@Autowired
	private ComentariosServiceImpl comentarioRepository;

	// PELICULAS

	@GetMapping("/pelicula/{idPelicula}/comentarios")
	public ResponseEntity<?> getComentariosPorPelicula(@PathVariable Integer idPelicula) {
		List<ComentarioPelicula> listaComentariosPelicula = new ArrayList<>();
		Map<String, Object> response = new HashMap<>();

		try {
			listaComentariosPelicula = comentarioRepository.findComentsByMovieId(idPelicula);
		} catch (DataAccessException e) { // La base de datos esta apagada
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (listaComentariosPelicula.size() == 0) { // No se encuentra la pelicula
			response.put("mensaje",
					"La pelicula con: ".concat(idPelicula.toString().concat(" no tiene ningun comentario")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<ComentarioPelicula>>(listaComentariosPelicula, HttpStatus.OK);

	}

	@DeleteMapping("/pelicula/comentarios/{idComment}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<?> deleteMovieComent(@PathVariable Integer idComment) {
		Map<String, Object> response = new HashMap<>();

		try {
			ComentarioPelicula comentarioPelicula = comentarioRepository.findMovieCommentById(idComment);
			comentarioRepository.deleteMovieComment(comentarioPelicula);
		} catch (DataAccessException e) { // La base de datos esta apagada
			response.put("mensaje", "Error al eliminar el usuario de la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "Comentario eliminado con éxito!");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	@PostMapping("/pelicula/comentarios")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> createMovieComment(@Valid @RequestBody ComentarioPelicula comentarioPelicula,
			BindingResult result) {
		ComentarioPelicula nuevoComentarioPelicula = null;
		Map<String, Object> response = new HashMap<>();

		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream()
					.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());

			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}

		try {
			comentarioPelicula.setFechaCreacion(Timestamp.valueOf(LocalDateTime.now()));
			nuevoComentarioPelicula = comentarioRepository.saveMovieComment(comentarioPelicula);
		} catch (DataAccessException e) { // La base de datos esta apagada
			response.put("mensaje", "Error al realizar el insert en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (comentarioPelicula.getTexto() == null || comentarioPelicula.getUsuario() == null) {
			response.put("error", "Comprueba que los campos texto o usuario no esten vacios");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}

		response.put("mensaje", "El comentario se ha insertado con exito!");
		response.put("comentario", nuevoComentarioPelicula);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	// SERIES

	@GetMapping("/serie/{idSerie}/comentarios")
	public ResponseEntity<?> getComentariosPorSerie(@PathVariable Integer idSerie) {
		List<ComentarioSerie> listaComentariosSerie = new ArrayList<>();
		Map<String, Object> response = new HashMap<>();

		try {
			listaComentariosSerie = comentarioRepository.findComentsBySerieId(idSerie);
		} catch (DataAccessException e) { // La base de datos esta apagada
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (listaComentariosSerie.size() == 0) { // No se encuentra la serie
			response.put("mensaje", "La serie con: ".concat(idSerie.toString().concat(" no tiene ningun comentario")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<ComentarioSerie>>(listaComentariosSerie, HttpStatus.OK);

	}

	@DeleteMapping("/serie/comentarios/{idComment}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<?> deleteTvShowComment(@PathVariable Integer idComment) {
		Map<String, Object> response = new HashMap<>();

		try {
			ComentarioSerie comentarioSerie = comentarioRepository.findSerieCommentById(idComment);
			comentarioRepository.deleteTvShowComment(comentarioSerie);
		} catch (DataAccessException e) { // La base de datos esta apagada
			response.put("mensaje", "Error al eliminar el usuario de la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "Comentario eliminado con éxito!");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	@PostMapping("/serie/comentarios")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> createTvShowComment(@Valid @RequestBody ComentarioSerie comentarioSerie,
			BindingResult result) {
		ComentarioSerie nuevoComentarioSerie = null;
		Map<String, Object> response = new HashMap<>();

		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream()
					.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());

			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}

		try {
			comentarioSerie.setFechaCreacion(Timestamp.valueOf(LocalDateTime.now()));
			nuevoComentarioSerie = comentarioRepository.saveTvShowComment(comentarioSerie);
		} catch (DataAccessException e) { // La base de datos esta apagada
			response.put("mensaje", "Error al realizar el insert en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (comentarioSerie.getTexto() == null || comentarioSerie.getUsuario() == null) {
			response.put("error", "Comprueba que los campos texto o usuario no esten vacios");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}

		response.put("mensaje", "El comentario se ha insertado con exito!");
		response.put("comentario", nuevoComentarioSerie);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

}
