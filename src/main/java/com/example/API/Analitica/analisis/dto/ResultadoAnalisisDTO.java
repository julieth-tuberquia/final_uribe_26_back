package com.example.API.Analitica.analisis.dto;

import java.time.Instant;

public class ResultadoAnalisisDTO {
	private Long id;
	private Long usuarioId;
	private Double score;
	private String etiqueta;
	private String comentario;
	private Instant creadoEn;

	public ResultadoAnalisisDTO(Long id, Long usuarioId, Double score, String etiqueta, String comentario,
			Instant creadoEn) {
		this.id = id;
		this.usuarioId = usuarioId;
		this.score = score;
		this.etiqueta = etiqueta;
		this.comentario = comentario;
		this.creadoEn = creadoEn;
	}

	public Long getId() {
		return id;
	}

	public Long getUsuarioId() {
		return usuarioId;
	}

	public Double getScore() {
		return score;
	}

	public String getEtiqueta() {
		return etiqueta;
	}

	public String getComentario() {
		return comentario;
	}

	public Instant getCreadoEn() {
		return creadoEn;
	}
}

