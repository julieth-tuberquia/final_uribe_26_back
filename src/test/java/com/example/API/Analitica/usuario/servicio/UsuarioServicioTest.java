package com.example.API.Analitica.usuario.servicio;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import com.example.API.Analitica.usuario.dto.UsuarioCrearDTO;
import com.example.API.Analitica.usuario.modelo.RolUsuario;
import com.example.API.Analitica.usuario.modelo.UsuarioModelo;
import com.example.API.Analitica.usuario.repositorio.UsuarioRepositorio;

@ExtendWith(MockitoExtension.class)
class UsuarioServicioTest {

	@Mock
	private UsuarioRepositorio usuarioRepositorio;

	@InjectMocks
	private UsuarioServicio usuarioServicio;

	@Test
	void crearUsuario_DeberiaGuardarCuandoCorreoNoExiste() {
		UsuarioCrearDTO solicitud = new UsuarioCrearDTO();
		solicitud.setNombre("Laura");
		solicitud.setCorreo("laura@correo.com");
		solicitud.setContrasena("1234");
		solicitud.setRol(RolUsuario.VENDEDOR);

		UsuarioModelo guardado = new UsuarioModelo();
		guardado.setId(10L);
		guardado.setNombre("Laura");
		guardado.setCorreo("laura@correo.com");
		guardado.setContrasena("1234");
		guardado.setRol(RolUsuario.VENDEDOR);

		when(usuarioRepositorio.existsByCorreoIgnoreCase("laura@correo.com")).thenReturn(false);
		when(usuarioRepositorio.save(org.mockito.ArgumentMatchers.any(UsuarioModelo.class))).thenReturn(guardado);

		var respuesta = usuarioServicio.crear(solicitud);

		assertEquals(10L, respuesta.getId());
		assertEquals("Laura", respuesta.getNombre());
		verify(usuarioRepositorio).save(org.mockito.ArgumentMatchers.any(UsuarioModelo.class));
	}

	@Test
	void obtenerUsuario_DeberiaFallarCuandoNoExiste() {
		when(usuarioRepositorio.findById(99L)).thenReturn(Optional.empty());
		assertThrows(ResponseStatusException.class, () -> usuarioServicio.obtener(99L));
	}
}

