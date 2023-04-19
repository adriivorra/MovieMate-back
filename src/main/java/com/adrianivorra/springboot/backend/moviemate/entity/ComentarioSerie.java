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
@Table(name = "comentarios_series")
public class ComentarioSerie implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "texto")
	private String texto;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_usuario")
	private Usuario usuario;

	@Column(name = "id_serie")
	private Integer idSerie;

	@Column(name = "fecha_creacion")
	private Timestamp fechaCreacion;

	public ComentarioSerie(Integer id, String texto, Usuario usuario, Integer idSerie, Timestamp fechaCreacion) {
		super();
		this.id = id;
		this.texto = texto;
		this.usuario = usuario;
		this.idSerie = idSerie;
		this.fechaCreacion = fechaCreacion;
	}

	public ComentarioSerie() {
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

	public Integer getIdSerie() {
		return idSerie;
	}

	public void setIdSerie(Integer idSerie) {
		this.idSerie = idSerie;
	}

	public Timestamp getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Timestamp fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	@Override
	public String toString() {
		return "ComentarioSerie [id=" + id + ", texto=" + texto + ", usuario=" + usuario + ", idSerie=" + idSerie
				+ ", fechaCreacion=" + fechaCreacion + "]";
	}

}
