package com.adrianivorra.springboot.backend.moviemate.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.adrianivorra.springboot.backend.moviemate.entity.ComentarioPelicula;

@Repository
public interface IComentarioPeliculaDao extends JpaRepository<ComentarioPelicula, Integer> {
	List<ComentarioPelicula> findByIdPelicula(Integer idPelicula);

	List<ComentarioPelicula> findByUsuarioUid(String uidUsuario);
}
