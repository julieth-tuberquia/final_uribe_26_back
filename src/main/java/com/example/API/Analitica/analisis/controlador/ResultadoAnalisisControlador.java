package com.example.API.Analitica.analisis.controlador;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.API.Analitica.analisis.dto.ResultadoAnalisisCrearDTO;
import com.example.API.Analitica.analisis.dto.ResultadoAnalisisDTO;
import com.example.API.Analitica.analisis.modelo.ResultadoAnalisisModelo;
import com.example.API.Analitica.analisis.repositorio.ResultadoAnalisisRepositorio;
import com.example.API.Analitica.usuario.modelo.UsuarioModelo;
import com.example.API.Analitica.usuario.repositorio.UsuarioRepositorio;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class ResultadoAnalisisControlador {

	private final ResultadoAnalisisRepositorio resultadoAnalisisRepositorio;
	private final UsuarioRepositorio usuarioRepositorio;

	public ResultadoAnalisisControlador(ResultadoAnalisisRepositorio resultadoAnalisisRepositorio,
			UsuarioRepositorio usuarioRepositorio) {
		this.resultadoAnalisisRepositorio = resultadoAnalisisRepositorio;
		this.usuarioRepositorio = usuarioRepositorio;
	}

	@PostMapping("/usuarios/{usuarioId}/analisis")
	public ResponseEntity<ResultadoAnalisisDTO> crear(@PathVariable Long usuarioId,
			@Valid @RequestBody ResultadoAnalisisCrearDTO solicitud) {
		UsuarioModelo usuario = usuarioRepositorio.findById(usuarioId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

		ResultadoAnalisisModelo resultado = new ResultadoAnalisisModelo();
		resultado.setUsuario(usuario);
		resultado.setScore(solicitud.getScore());
		resultado.setEtiqueta(solicitud.getEtiqueta());
		resultado.setComentario(solicitud.getComentario());

		ResultadoAnalisisModelo resultadoGuardado = resultadoAnalisisRepositorio.save(resultado);
		return ResponseEntity.status(HttpStatus.CREATED).body(toDTO(resultadoGuardado));
	}

	@GetMapping("/usuarios/{usuarioId}/analisis")
	public List<ResultadoAnalisisDTO> listarPorUsuario(@PathVariable Long usuarioId) {
		if (!usuarioRepositorio.existsById(usuarioId)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado");
		}
		return resultadoAnalisisRepositorio.findByUsuarioIdOrderByCreadoEnDesc(usuarioId).stream().map(this::toDTO)
				.toList();
	}

	@GetMapping("/analisis")
	public List<ResultadoAnalisisDTO> listarTodos() {
		return resultadoAnalisisRepositorio.findAll().stream().map(this::toDTO).toList();
	}

	@DeleteMapping("/analisis/{id}")
	public ResponseEntity<Void> eliminar(@PathVariable Long id) {
		if (!resultadoAnalisisRepositorio.existsById(id)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Resultado no encontrado");
		}
		resultadoAnalisisRepositorio.deleteById(id);
		return ResponseEntity.noContent().build();
	}

	private ResultadoAnalisisDTO toDTO(ResultadoAnalisisModelo r) {
		return new ResultadoAnalisisDTO(r.getId(), r.getUsuario().getId(), r.getScore(), r.getEtiqueta(),
				r.getComentario(), r.getCreadoEn());
	}
}

