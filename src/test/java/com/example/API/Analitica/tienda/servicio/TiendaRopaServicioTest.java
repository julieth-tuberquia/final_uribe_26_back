package com.example.API.Analitica.tienda.servicio;

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

import com.example.API.Analitica.tienda.dto.TiendaRopaActualizarDTO;
import com.example.API.Analitica.tienda.dto.TiendaRopaCrearDTO;
import com.example.API.Analitica.tienda.modelo.TiendaRopaModelo;
import com.example.API.Analitica.tienda.repositorio.TiendaRopaRepositorio;

@ExtendWith(MockitoExtension.class)
class TiendaRopaServicioTest {

	@Mock
	private TiendaRopaRepositorio tiendaRopaRepositorio;

	@InjectMocks
	private TiendaRopaServicio tiendaRopaServicio;

	@Test
	void crearTienda_DeberiaGuardarDatos() {
		TiendaRopaCrearDTO solicitud = new TiendaRopaCrearDTO();
		solicitud.setNombre("Moda Joven");
		solicitud.setNit("901234567");
		solicitud.setDireccion("Calle 10 # 20-30");
		solicitud.setTelefono("3001112233");
		solicitud.setCorreo("contacto@modajoven.com");

		TiendaRopaModelo guardada = new TiendaRopaModelo();
		guardada.setId(1L);
		guardada.setNombre("Moda Joven");
		guardada.setNit("901234567");
		guardada.setDireccion("Calle 10 # 20-30");
		guardada.setTelefono("3001112233");
		guardada.setCorreo("contacto@modajoven.com");

		when(tiendaRopaRepositorio.save(org.mockito.ArgumentMatchers.any(TiendaRopaModelo.class))).thenReturn(guardada);

		var respuesta = tiendaRopaServicio.crear(solicitud);

		assertEquals(1L, respuesta.getId());
		assertEquals("Moda Joven", respuesta.getNombre());
	}

	@Test
	void actualizarTienda_DeberiaAplicarCambios() {
		TiendaRopaModelo tienda = new TiendaRopaModelo();
		tienda.setId(2L);
		tienda.setNombre("Antigua");

		TiendaRopaActualizarDTO solicitud = new TiendaRopaActualizarDTO();
		solicitud.setNombre("Nueva Tienda");
		solicitud.setNit("900000001");
		solicitud.setDireccion("Cra 1 # 2-3");
		solicitud.setTelefono("3010000000");
		solicitud.setCorreo("nueva@tienda.com");

		when(tiendaRopaRepositorio.findById(2L)).thenReturn(Optional.of(tienda));
		when(tiendaRopaRepositorio.save(tienda)).thenReturn(tienda);

		var respuesta = tiendaRopaServicio.actualizar(2L, solicitud);

		assertEquals("Nueva Tienda", respuesta.getNombre());
		assertEquals("Cra 1 # 2-3", respuesta.getDireccion());
	}

	@Test
	void eliminarTienda_DeberiaFallarSiNoExiste() {
		when(tiendaRopaRepositorio.existsById(99L)).thenReturn(false);
		assertThrows(ResponseStatusException.class, () -> tiendaRopaServicio.eliminar(99L));
	}

	@Test
	void eliminarTienda_DeberiaEliminarSiExiste() {
		when(tiendaRopaRepositorio.existsById(3L)).thenReturn(true);
		tiendaRopaServicio.eliminar(3L);
		verify(tiendaRopaRepositorio).deleteById(3L);
	}
}

