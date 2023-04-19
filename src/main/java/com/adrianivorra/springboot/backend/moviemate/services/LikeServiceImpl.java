package com.adrianivorra.springboot.backend.moviemate.services;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adrianivorra.springboot.backend.moviemate.dao.ILikeDao;
import com.adrianivorra.springboot.backend.moviemate.dao.IUsuarioDao;
import com.adrianivorra.springboot.backend.moviemate.entity.Like;
import com.adrianivorra.springboot.backend.moviemate.entity.Usuario;

@Service
public class LikeServiceImpl {

	@Autowired
	private ILikeDao likeRepository;

	@Autowired
	private IUsuarioDao usuarioRepository;

	public Like darLike(Usuario user, Usuario userLike) {

		List<Like> likes = likeRepository.findUsuarioLikeToUsuario(user.getUid(), userLike.getUid());
		List<Like> likesUsuario = likeRepository.findUsuarioLikeToUsuario(userLike.getUid(), user.getUid());
		Like like = new Like();

		if (likes != null && !likes.isEmpty()) {
			like.setId(likes.get(0).getId());
			like.setUsuario(user);
			like.setUsuarioLike(userLike);
			like.setMutuo(true);
			like.setFechaLikeUsuario1(likes.get(0).getFechaLikeUsuario1());
			like.setFechaLikeUsuario2(Timestamp.valueOf(LocalDateTime.now()));
			like.setFechaMatch(Timestamp.valueOf(LocalDateTime.now()));
			likeRepository.save(like);
			return like;
		} else if (likesUsuario == null || likesUsuario.isEmpty()) {
			like.setUsuario(user);
			like.setUsuarioLike(userLike);
			like.setMutuo(false);
			like.setFechaLikeUsuario1(Timestamp.valueOf(LocalDateTime.now()));
			likeRepository.save(like);
			return like;
		} else {
			return null;
		}
	}

	public List<String> getUserMatches(String userId) {
		return likeRepository.findUserMatches(userId);
	}

}
