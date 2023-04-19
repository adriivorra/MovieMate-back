package com.adrianivorra.springboot.backend.moviemate.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.adrianivorra.springboot.backend.moviemate.dao.IUsuarioDao;
import com.adrianivorra.springboot.backend.moviemate.entity.Usuario;

@Service
public class UsuarioServiceImpl implements IUsuarioService {

	@Autowired
	private IUsuarioDao usuarioDao;

	@Override
	@Transactional(readOnly = true)
	public List<Usuario> getAllUsers() {
		return (List<Usuario>) usuarioDao.findAll();
	}

	@Override
	@Transactional
	public Usuario save(Usuario user) {
		return usuarioDao.save(user);
	}

	@Override
	@Transactional(readOnly = true)
	public Usuario findUserById(String uid) {
		return usuarioDao.findById(uid).orElse(null);
	}

	@Override
	@Transactional
	public void deleteUser(Usuario user) {
		usuarioDao.delete(user);
	}

	@Transactional(readOnly = true)
	public Page<Usuario> findByDistance(Double latitude, Double longitude, Integer distancia, String uid,
			Integer age_min, Integer age_max, Pageable pageable) {
		return usuarioDao.buscarPorDistancia(latitude, longitude, distancia, uid, age_min, age_max, pageable);
	}

	public List<Usuario> findUsersByIds(List<String> idsMatches) {
		return (List<Usuario>) usuarioDao.findAllById(idsMatches);
	}

}
