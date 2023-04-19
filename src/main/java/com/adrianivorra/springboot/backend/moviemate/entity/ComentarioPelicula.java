package com.adrianivorra.springboot.backend.moviemate.entity;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "comentarios_peliculas")
public class ComentarioPelicula implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "texto")
	private String texto;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_usuario")
	private Usuario usuario;

	@Column(name = "id_pelicula")
	private Integer idPelicula;

	@Column(name = "fecha_creacion")
	private Timestamp fechaCreacion;

	public ComentarioPelicula(Integer id, String texto, Usuario usuario, Integer idPelicula, Timestamp fechaCreacion) {
		super();
		this.id = id;
		this.texto = texto;
		this.usuario = usuario;
		this.idPelicula = idPelicula;
		this.fechaCreacion = fechaCreacion;
	}

	public ComentarioPelicula() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Integer getIdPelicula() {
		return idPelicula;
	}

	public void setIdPelicula(Integer idPelicula) {
		this.idPelicula = idPelicula;
	}

	public Timestamp getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Timestamp fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	@Override
	public String toString() {
		return "ComentarioPelicula [id=" + id + ", texto=" + texto + ", usuario=" + usuario + ", idPelicula="
				+ idPelicula + ", fechaCreacion=" + fechaCreacion + "]";
	}

}
