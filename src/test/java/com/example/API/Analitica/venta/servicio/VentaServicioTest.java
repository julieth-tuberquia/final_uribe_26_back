package com.example.API.Analitica.venta.servicio;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import com.example.API.Analitica.producto.modelo.ProductoModelo;
import com.example.API.Analitica.producto.repositorio.ProductoRepositorio;
import com.example.API.Analitica.tienda.modelo.TiendaRopaModelo;
import com.example.API.Analitica.tienda.servicio.TiendaRopaServicio;
import com.example.API.Analitica.usuario.modelo.RolUsuario;
import com.example.API.Analitica.usuario.modelo.UsuarioModelo;
import com.example.API.Analitica.usuario.servicio.UsuarioServicio;
import com.example.API.Analitica.venta.dto.VentaCrearDTO;
import com.example.API.Analitica.venta.dto.VentaCrearItemDTO;
import com.example.API.Analitica.venta.modelo.VentaModelo;
import com.example.API.Analitica.venta.repositorio.VentaRepositorio;

@ExtendWith(MockitoExtension.class)
class VentaServicioTest {

	@Mock
	private VentaRepositorio ventaRepositorio;

	@Mock
	private ProductoRepositorio productoRepositorio;

	@Mock
	private UsuarioServicio usuarioServicio;

	@Mock
	private TiendaRopaServicio tiendaRopaServicio;

	@InjectMocks
	private VentaServicio ventaServicio;

	@Test
	void crearVenta_DeberiaCalcularTotalYDescontarInventario() {
		UsuarioModelo vendedor = new UsuarioModelo();
		vendedor.setId(1L);
		vendedor.setNombre("Carlos");
		vendedor.setRol(RolUsuario.VENDEDOR);

		TiendaRopaModelo tienda = new TiendaRopaModelo();
		tienda.setId(9L);
		tienda.setNombre("Moda Joven");

		ProductoModelo camisa = new ProductoModelo();
		camisa.setId(5L);
		camisa.setNombre("Camisa");
		camisa.setPrecioUnitario(new BigDecimal("100000"));
		camisa.setCantidadInventario(10);
		camisa.setTienda(tienda);

		VentaCrearItemDTO productoVenta = new VentaCrearItemDTO();
		productoVenta.setProductoId(5L);
		productoVenta.setCantidad(2);

		VentaCrearDTO solicitud = new VentaCrearDTO();
		solicitud.setVendedorId(1L);
		solicitud.setTiendaId(9L);
		solicitud.setProductos(List.of(productoVenta));

		when(usuarioServicio.buscarModelo(1L)).thenReturn(vendedor);
		when(tiendaRopaServicio.buscarModelo(9L)).thenReturn(tienda);
		when(productoRepositorio.findById(5L)).thenReturn(Optional.of(camisa));
		when(ventaRepositorio.save(org.mockito.ArgumentMatchers.any(VentaModelo.class)))
				.thenAnswer(invocacion -> invocacion.getArgument(0));

		var respuesta = ventaServicio.crear(solicitud);

		assertEquals(new BigDecimal("200000"), respuesta.getTotalVenta());
		assertEquals(8, camisa.getCantidadInventario());
		assertEquals(1, respuesta.getProductos().size());
		assertEquals(9L, respuesta.getTiendaId());
	}

	@Test
	void crearVenta_DeberiaFallarSiNoHayInventario() {
		UsuarioModelo vendedor = new UsuarioModelo();
		vendedor.setId(1L);
		vendedor.setNombre("Carlos");
		vendedor.setRol(RolUsuario.VENDEDOR);

		TiendaRopaModelo tienda = new TiendaRopaModelo();
		tienda.setId(9L);
		tienda.setNombre("Moda Joven");

		ProductoModelo jean = new ProductoModelo();
		jean.setId(6L);
		jean.setNombre("Jean");
		jean.setPrecioUnitario(new BigDecimal("120000"));
		jean.setCantidadInventario(1);
		jean.setTienda(tienda);

		VentaCrearItemDTO productoVenta = new VentaCrearItemDTO();
		productoVenta.setProductoId(6L);
		productoVenta.setCantidad(3);

		VentaCrearDTO solicitud = new VentaCrearDTO();
		solicitud.setVendedorId(1L);
		solicitud.setTiendaId(9L);
		solicitud.setProductos(List.of(productoVenta));

		when(usuarioServicio.buscarModelo(1L)).thenReturn(vendedor);
		when(tiendaRopaServicio.buscarModelo(9L)).thenReturn(tienda);
		when(productoRepositorio.findById(6L)).thenReturn(Optional.of(jean));

		assertThrows(ResponseStatusException.class, () -> ventaServicio.crear(solicitud));
	}
}

