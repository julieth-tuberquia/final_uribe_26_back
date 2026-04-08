package com.example.API.Analitica.usuario.controlador;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.API.Analitica.usuario.dto.UsuarioActualizarDTO;
import com.example.API.Analitica.usuario.dto.UsuarioCrearDTO;
import com.example.API.Analitica.usuario.dto.UsuarioDTO;
import com.example.API.Analitica.usuario.servicio.UsuarioServicio;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioControlador {

	private final UsuarioServicio usuarioServicio;

	public UsuarioControlador(UsuarioServicio usuarioServicio) {
		this.usuarioServicio = usuarioServicio;
	}

	@PostMapping
	public ResponseEntity<UsuarioDTO> crear(@Valid @RequestBody UsuarioCrearDTO solicitud) {
		return ResponseEntity.status(HttpStatus.CREATED).body(usuarioServicio.crear(solicitud));
	}

	@GetMapping
	public List<UsuarioDTO> listar() {
		return usuarioServicio.listar();
	}

	@GetMapping("/{id}")
	public UsuarioDTO obtener(@PathVariable Long id) {
		return usuarioServicio.obtener(id);
	}

	@PutMapping("/{id}")
	public UsuarioDTO actualizar(@PathVariable Long id, @Valid @RequestBody UsuarioActualizarDTO solicitud) {
		return usuarioServicio.actualizar(id, solicitud);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminar(@PathVariable Long id) {
		usuarioServicio.eliminar(id);
		return ResponseEntity.noContent().build();
	}
}

