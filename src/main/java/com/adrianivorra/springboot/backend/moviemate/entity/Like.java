package com.adrianivorra.springboot.backend.moviemate.entity;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "likes")
public class Like {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "usuario_id")
	private Usuario usuario;

	@ManyToOne
	@JoinColumn(name = "usuario_id_like")
	private Usuario usuarioLike;

	@Column(name = "mutuo")
	private Boolean mutuo;

	@Column(name = "fecha_like_usuario1")
	private Timestamp fechaLikeUsuario1;

	@Column(name = "fecha_like_usuario2")
	private Timestamp fechaLikeUsuario2;

	@Column(name = "fecha_match")
	private Timestamp fechaMatch;

	// Constructor vacío
	public Like() {
	}

	// Constructor con todos los campos
	public Like(Integer id, Usuario usuario, Usuario usuarioLike, Boolean mutuo, Timestamp fechaLikeUsuario1,
			Timestamp fechaLikeUsuario2, Timestamp fechaMatch) {
		this.id = id;
		this.usuario = usuario;
		this.usuarioLike = usuarioLike;
		this.mutuo = mutuo;
		this.fechaLikeUsuario1 = fechaLikeUsuario1;
		this.fechaLikeUsuario2 = fechaLikeUsuario2;
		this.fechaMatch = fechaMatch;
	}

	// Getters y setters para todos los campos
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Usuario getUsuarioLike() {
		return usuarioLike;
	}

	public void setUsuarioLike(Usuario usuarioLike) {
		this.usuarioLike = usuarioLike;
	}

	public Boolean getMutuo() {
		return mutuo;
	}

	public void setMutuo(Boolean mutuo) {
		this.mutuo = mutuo;
	}

	public Timestamp getFechaLikeUsuario1() {
		return fechaLikeUsuario1;
	}

	public void setFechaLikeUsuario1(Timestamp fechaLikeUsuario1) {
		this.fechaLikeUsuario1 = fechaLikeUsuario1;
	}

	public Timestamp getFechaLikeUsuario2() {
		return fechaLikeUsuario2;
	}

	public void setFechaLikeUsuario2(Timestamp fechaLikeUsuario2) {
		this.fechaLikeUsuario2 = fechaLikeUsuario2;
	}

	public Timestamp getFechaMatch() {
		return fechaMatch;
	}

	public void setFechaMatch(Timestamp fechaMatch) {
		this.fechaMatch = fechaMatch;
	}

}
