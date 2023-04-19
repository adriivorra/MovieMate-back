package com.adrianivorra.springboot.backend.moviemate.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.adrianivorra.springboot.backend.moviemate.entity.Dislike;
import com.adrianivorra.springboot.backend.moviemate.entity.Like;

@Repository
public interface IDislikeDao extends JpaRepository<Dislike, Integer> {

	@Query(value = "SELECT * FROM dislikes WHERE usuario_id like :usuarioId and usuario_id_dislike like :usuarioIdDislike", nativeQuery = true)
	List<Dislike> findUusarioDislikes(@Param("usuarioId") String usuarioId,
			@Param("usuarioIdDislike") String usuarioIdLike);

}
