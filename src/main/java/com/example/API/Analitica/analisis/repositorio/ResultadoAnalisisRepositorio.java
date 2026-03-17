package com.example.API.Analitica.analisis.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.API.Analitica.analisis.modelo.ResultadoAnalisisModelo;

public interface ResultadoAnalisisRepositorio extends JpaRepository<ResultadoAnalisisModelo, Long> {
	List<ResultadoAnalisisModelo> findByUsuarioIdOrderByCreadoEnDesc(Long usuarioId);
}

