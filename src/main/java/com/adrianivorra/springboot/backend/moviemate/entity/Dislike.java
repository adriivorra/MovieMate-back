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
@Table(name = "dislikes")
public class Dislike {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "usuario_id")
	private Usuario usuario;

	@ManyToOne
	@JoinColumn(name = "usuario_id_dislike")
	private Usuario usuarioDislike;

	@Column(name = "fecha_dislike")
	private Timestamp fechaDislike;

	// Constructor vacío
	public Dislike() {
	}

	public Dislike(Integer id, Usuario usuario, Usuario usuarioDislike, Timestamp fechaDislike) {
		super();
		this.id = id;
		this.usuario = usuario;
		this.usuarioDislike = usuarioDislike;
		this.fechaDislike = fechaDislike;
	}

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

	public Usuario getUsuarioDislike() {
		return usuarioDislike;
	}

	public void setUsuarioDislike(Usuario usuarioDislike) {
		this.usuarioDislike = usuarioDislike;
	}

	public Timestamp getFechaDislike() {
		return fechaDislike;
	}

	public void setFechaDislike(Timestamp fechaDislike) {
		this.fechaDislike = fechaDislike;
	}

}
