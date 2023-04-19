package com.adrianivorra.springboot.backend.moviemate.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.adrianivorra.springboot.backend.moviemate.dao.IComentarioPeliculaDao;
import com.adrianivorra.springboot.backend.moviemate.dao.IComentarioSerieDao;
import com.adrianivorra.springboot.backend.moviemate.entity.ComentarioPelicula;
import com.adrianivorra.springboot.backend.moviemate.entity.ComentarioSerie;

@Service
public class ComentariosServiceImpl {
	@Autowired
	private IComentarioPeliculaDao comentarioPeliculaDao;

	@Autowired
	private IComentarioSerieDao comentarioSerieDao;

	// PELICULAS

	@Transactional(readOnly = true)
	public List<ComentarioPelicula> findComentsByMovieId(Integer id) {
		return (List<ComentarioPelicula>) comentarioPeliculaDao.findByIdPelicula(id);
	}

	@Transactional(readOnly = true)
	public ComentarioPelicula findMovieCommentById(Integer id) {
		return comentarioPeliculaDao.findById(id).orElse(null);
	}

	@Transactional(readOnly = true)
	public List<ComentarioPelicula> findMovieComentsByUserUid(String uid) {
		return comentarioPeliculaDao.findByUsuarioUid(uid);
	}

	@Transactional
	public ComentarioPelicula saveMovieComment(ComentarioPelicula comentarioPelicula) {
		return comentarioPeliculaDao.save(comentarioPelicula);
	}

	@Transactional
	public void deleteMovieComment(ComentarioPelicula comentarioPelicula) {
		comentarioPeliculaDao.delete(comentarioPelicula);
	}

	// SERIES

	@Transactional(readOnly = true)
	public List<ComentarioSerie> findComentsBySerieId(Integer id) {
		return (List<ComentarioSerie>) comentarioSerieDao.findByIdSerie(id);
	}

	@Transactional(readOnly = true)
	public ComentarioSerie findSerieCommentById(Integer id) {
		return comentarioSerieDao.findById(id).orElse(null);
	}

	@Transactional(readOnly = true)
	public List<ComentarioSerie> findSerieComentsByUserUid(String uid) {
		return comentarioSerieDao.findByUsuarioUid(uid);
	}

	@Transactional
	public ComentarioSerie saveTvShowComment(ComentarioSerie comentarioSerie) {
		return comentarioSerieDao.save(comentarioSerie);
	}

	@Transactional
	public void deleteTvShowComment(ComentarioSerie comentarioSerie) {
		comentarioSerieDao.delete(comentarioSerie);
	}

}
