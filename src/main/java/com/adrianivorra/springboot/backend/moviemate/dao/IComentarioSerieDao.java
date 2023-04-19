package com.adrianivorra.springboot.backend.moviemate.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.adrianivorra.springboot.backend.moviemate.entity.ComentarioSerie;

@Repository
public interface IComentarioSerieDao extends JpaRepository<ComentarioSerie, Integer> {
	List<ComentarioSerie> findByIdSerie(Integer idSerie);

	List<ComentarioSerie> findByUsuarioUid(String uidUsuario);
}
