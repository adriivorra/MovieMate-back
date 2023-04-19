package com.adrianivorra.springboot.backend.moviemate.services;

import java.util.List;

import com.adrianivorra.springboot.backend.moviemate.entity.Usuario;

public interface IUsuarioService {

	public List<Usuario> getAllUsers();

	public Usuario save(Usuario user);

	public Usuario findUserById(String uid);

	public void deleteUser(Usuario user);
}
