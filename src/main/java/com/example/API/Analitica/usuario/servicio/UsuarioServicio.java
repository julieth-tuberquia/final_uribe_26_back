package com.example.API.Analitica.usuario.servicio;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.API.Analitica.usuario.dto.UsuarioActualizarDTO;
import com.example.API.Analitica.usuario.dto.UsuarioCrearDTO;
import com.example.API.Analitica.usuario.dto.UsuarioDTO;
import com.example.API.Analitica.usuario.modelo.UsuarioModelo;
import com.example.API.Analitica.usuario.repositorio.UsuarioRepositorio;

@Service
public class UsuarioServicio {

	private final UsuarioRepositorio usuarioRepositorio;

	public UsuarioServicio(UsuarioRepositorio usuarioRepositorio) {
		this.usuarioRepositorio = usuarioRepositorio;
	}

	public UsuarioDTO crear(UsuarioCrearDTO solicitud) {
		if (usuarioRepositorio.existsByCorreoIgnoreCase(solicitud.getCorreo())) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe un usuario con ese correo");
		}
		UsuarioModelo u = new UsuarioModelo();
		u.setNombre(solicitud.getNombre());
		u.setCorreo(solicitud.getCorreo());
		u.setContrasena(solicitud.getContrasena());
		u.setRol(solicitud.getRol());
		return toDTO(usuarioRepositorio.save(u));
	}

	public List<UsuarioDTO> listar() {
		return usuarioRepositorio.findAll().stream().map(this::toDTO).toList();
	}

	public UsuarioDTO obtener(Long id) {
		return toDTO(buscarModelo(id));
	}

	public UsuarioDTO actualizar(Long id, UsuarioActualizarDTO solicitud) {
		UsuarioModelo u = buscarModelo(id);
		if (!u.getCorreo().equalsIgnoreCase(solicitud.getCorreo())
				&& usuarioRepositorio.existsByCorreoIgnoreCase(solicitud.getCorreo())) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe un usuario con ese correo");
		}
		u.setNombre(solicitud.getNombre());
		u.setCorreo(solicitud.getCorreo());
		u.setContrasena(solicitud.getContrasena());
		u.setRol(solicitud.getRol());
		return toDTO(usuarioRepositorio.save(u));
	}

	public void eliminar(Long id) {
		if (!usuarioRepositorio.existsById(id)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado");
		}
		usuarioRepositorio.deleteById(id);
	}

	public UsuarioModelo buscarModelo(Long id) {
		return usuarioRepositorio.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
	}

	private UsuarioDTO toDTO(UsuarioModelo u) {
		return new UsuarioDTO(u.getId(), u.getNombre(), u.getCorreo(), u.getRol());
	}
}

