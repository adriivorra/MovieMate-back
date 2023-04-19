package com.adrianivorra.springboot.backend.moviemate.services;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import org.aspectj.weaver.NewConstructorTypeMunger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.adrianivorra.springboot.backend.moviemate.dao.IDislikeDao;
import com.adrianivorra.springboot.backend.moviemate.dao.IUsuarioDao;
import com.adrianivorra.springboot.backend.moviemate.entity.Dislike;
import com.adrianivorra.springboot.backend.moviemate.entity.Usuario;

@Service
public class DislikeServiceImpl {

	@Autowired
	private IDislikeDao dislikeDao;

	@Autowired
	private IUsuarioDao usuarioRepository;

	@Transactional(readOnly = true)
	public List<Dislike> getAllDislikes() {
		return (List<Dislike>) dislikeDao.findAll();
	}

	@Transactional
	public Dislike addDislike(Usuario user, Usuario userDislike) {

		List<Dislike> dislikesUsuario = dislikeDao.findUusarioDislikes(user.getUid(), userDislike.getUid());

		Dislike dislike = new Dislike();

		if (dislikesUsuario == null || dislikesUsuario.isEmpty()) {
			dislike = new Dislike(null, user, userDislike, Timestamp.valueOf(LocalDateTime.now()));
			dislikeDao.save(dislike);
			return dislike;
		} else {
			return null;
		}
	}

	@Transactional
	public void deleteDislike(Dislike dislike) {
		dislikeDao.delete(dislike);
	}
}
