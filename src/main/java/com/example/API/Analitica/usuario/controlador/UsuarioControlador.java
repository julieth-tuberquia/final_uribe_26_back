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
import org.springframework.web.server.ResponseStatusException;

import com.example.API.Analitica.usuario.dto.UsuarioActualizarDTO;
import com.example.API.Analitica.usuario.dto.UsuarioCrearDTO;
import com.example.API.Analitica.usuario.dto.UsuarioDTO;
import com.example.API.Analitica.usuario.modelo.UsuarioModelo;
import com.example.API.Analitica.usuario.repositorio.UsuarioRepositorio;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioControlador {

	private final UsuarioRepositorio usuarioRepositorio;

	public UsuarioControlador(UsuarioRepositorio usuarioRepositorio) {
		this.usuarioRepositorio = usuarioRepositorio;
	}

	@PostMapping
	public ResponseEntity<UsuarioDTO> crear(@Valid @RequestBody UsuarioCrearDTO body) {
		if (usuarioRepositorio.existsByCorreoIgnoreCase(body.getCorreo())) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe un usuario con ese correo");
		}

		UsuarioModelo u = new UsuarioModelo();
		u.setNombre(body.getNombre());
		u.setCorreo(body.getCorreo());
		u.setContrasena(body.getContrasena());
		u.setRol(body.getRol());

		UsuarioModelo saved = usuarioRepositorio.save(u);
		return ResponseEntity.status(HttpStatus.CREATED).body(toDTO(saved));
	}

	@GetMapping
	public List<UsuarioDTO> listar() {
		return usuarioRepositorio.findAll().stream().map(this::toDTO).toList();
	}

	@GetMapping("/{id}")
	public UsuarioDTO obtener(@PathVariable Long id) {
		UsuarioModelo u = usuarioRepositorio.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
		return toDTO(u);
	}

	@PutMapping("/{id}")
	public UsuarioDTO actualizar(@PathVariable Long id, @Valid @RequestBody UsuarioActualizarDTO body) {
		UsuarioModelo u = usuarioRepositorio.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

		if (!u.getCorreo().equalsIgnoreCase(body.getCorreo())
				&& usuarioRepositorio.existsByCorreoIgnoreCase(body.getCorreo())) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe un usuario con ese correo");
		}

		u.setNombre(body.getNombre());
		u.setCorreo(body.getCorreo());
		u.setContrasena(body.getContrasena());
		u.setRol(body.getRol());

		UsuarioModelo saved = usuarioRepositorio.save(u);
		return toDTO(saved);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminar(@PathVariable Long id) {
		if (!usuarioRepositorio.existsById(id)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado");
		}
		usuarioRepositorio.deleteById(id);
		return ResponseEntity.noContent().build();
	}

	private UsuarioDTO toDTO(UsuarioModelo u) {
		return new UsuarioDTO(u.getId(), u.getNombre(), u.getCorreo(), u.getRol());
	}
}

