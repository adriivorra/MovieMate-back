package com.adrianivorra.springboot.backend.moviemate.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.adrianivorra.springboot.backend.moviemate.entity.Like;
import com.adrianivorra.springboot.backend.moviemate.entity.Usuario;

@Repository
public interface ILikeDao extends JpaRepository<Like, Integer> {

	Optional<Like> findByUsuarioAndUsuarioLike(Usuario usuario, Usuario usuarioLike);

	List<Like> findByUsuario(Usuario usuario);

	List<Like> findByUsuarioLike(Usuario usuarioLike);

	@Query(value = "SELECT * FROM likes WHERE usuario_id like :usuarioIdLike and usuario_id_like like :usuarioId", nativeQuery = true)
	List<Like> findUsuarioLikeToUsuario(@Param("usuarioId") String usuarioId,
			@Param("usuarioIdLike") String usuarioIdLike);

	@Query(value = "SELECT CASE WHEN usuario_id like :usuarioId THEN usuario_id_like ELSE usuario_id END AS otro_usuario_id FROM likes WHERE mutuo = true AND (usuario_id like :usuarioId OR usuario_id_like like :usuarioId);", nativeQuery = true)
	List<String> findUserMatches(@Param("usuarioId") String usuarioId);

}
