package com.example.API.Analitica.producto.servicio;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import com.example.API.Analitica.producto.dto.ProductoCrearDTO;
import com.example.API.Analitica.producto.modelo.ProductoModelo;
import com.example.API.Analitica.producto.repositorio.ProductoRepositorio;
import com.example.API.Analitica.tienda.modelo.TiendaRopaModelo;
import com.example.API.Analitica.tienda.servicio.TiendaRopaServicio;

@ExtendWith(MockitoExtension.class)
class ProductoServicioTest {

	@Mock
	private ProductoRepositorio productoRepositorio;

	@Mock
	private TiendaRopaServicio tiendaRopaServicio;

	@InjectMocks
	private ProductoServicio productoServicio;

	@Test
	void crearProducto_DeberiaGuardar() {
		ProductoCrearDTO solicitud = new ProductoCrearDTO();
		solicitud.setNombre("Camisa");
		solicitud.setPrecioUnitario(new BigDecimal("95000"));
		solicitud.setCantidadInventario(20);
		solicitud.setTiendaId(3L);

		TiendaRopaModelo tienda = new TiendaRopaModelo();
		tienda.setId(3L);
		tienda.setNombre("Moda Joven");

		ProductoModelo guardado = new ProductoModelo();
		guardado.setId(1L);
		guardado.setNombre("Camisa");
		guardado.setPrecioUnitario(new BigDecimal("95000"));
		guardado.setCantidadInventario(20);
		guardado.setTienda(tienda);

		when(tiendaRopaServicio.buscarModelo(3L)).thenReturn(tienda);
		when(productoRepositorio.existsByNombreIgnoreCaseAndTiendaId("Camisa", 3L)).thenReturn(false);
		when(productoRepositorio.save(org.mockito.ArgumentMatchers.any(ProductoModelo.class))).thenReturn(guardado);

		var respuesta = productoServicio.crear(solicitud);

		assertEquals(1L, respuesta.getId());
		assertEquals(20, respuesta.getCantidadInventario());
	}

	@Test
	void crearProducto_DeberiaFallarSiNombreExiste() {
		ProductoCrearDTO solicitud = new ProductoCrearDTO();
		solicitud.setNombre("Camisa");
		solicitud.setPrecioUnitario(new BigDecimal("95000"));
		solicitud.setCantidadInventario(20);
		solicitud.setTiendaId(3L);

		TiendaRopaModelo tienda = new TiendaRopaModelo();
		tienda.setId(3L);
		when(tiendaRopaServicio.buscarModelo(3L)).thenReturn(tienda);
		when(productoRepositorio.existsByNombreIgnoreCaseAndTiendaId("Camisa", 3L)).thenReturn(true);

		assertThrows(ResponseStatusException.class, () -> productoServicio.crear(solicitud));
	}
}

