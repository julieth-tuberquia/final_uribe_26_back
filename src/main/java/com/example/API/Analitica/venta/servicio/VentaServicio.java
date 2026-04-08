package com.example.API.Analitica.venta.servicio;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.example.API.Analitica.producto.modelo.ProductoModelo;
import com.example.API.Analitica.producto.repositorio.ProductoRepositorio;
import com.example.API.Analitica.tienda.modelo.TiendaRopaModelo;
import com.example.API.Analitica.tienda.servicio.TiendaRopaServicio;
import com.example.API.Analitica.usuario.modelo.RolUsuario;
import com.example.API.Analitica.usuario.modelo.UsuarioModelo;
import com.example.API.Analitica.usuario.servicio.UsuarioServicio;
import com.example.API.Analitica.venta.dto.VentaCrearDTO;
import com.example.API.Analitica.venta.dto.VentaDTO;
import com.example.API.Analitica.venta.dto.VentaDetalleDTO;
import com.example.API.Analitica.venta.modelo.VentaDetalleModelo;
import com.example.API.Analitica.venta.modelo.VentaModelo;
import com.example.API.Analitica.venta.repositorio.VentaRepositorio;

@Service
public class VentaServicio {

	private final VentaRepositorio ventaRepositorio;
	private final ProductoRepositorio productoRepositorio;
	private final UsuarioServicio usuarioServicio;
	private final TiendaRopaServicio tiendaRopaServicio;

	public VentaServicio(VentaRepositorio ventaRepositorio, ProductoRepositorio productoRepositorio,
			UsuarioServicio usuarioServicio, TiendaRopaServicio tiendaRopaServicio) {
		this.ventaRepositorio = ventaRepositorio;
		this.productoRepositorio = productoRepositorio;
		this.usuarioServicio = usuarioServicio;
		this.tiendaRopaServicio = tiendaRopaServicio;
	}

	@Transactional
	public VentaDTO crear(VentaCrearDTO solicitud) {
		UsuarioModelo vendedor = usuarioServicio.buscarModelo(solicitud.getVendedorId());
		TiendaRopaModelo tienda = tiendaRopaServicio.buscarModelo(solicitud.getTiendaId());
		if (vendedor.getRol() != RolUsuario.VENDEDOR) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El usuario seleccionado no tiene rol VENDEDOR");
		}

		VentaModelo venta = new VentaModelo();
		venta.setVendedor(vendedor);
		venta.setTienda(tienda);
		BigDecimal total = BigDecimal.ZERO;

		for (var productoVenta : solicitud.getProductos()) {
			ProductoModelo producto = productoRepositorio.findById(productoVenta.getProductoId())
					.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
							"Producto no encontrado: " + productoVenta.getProductoId()));
			if (!producto.getTienda().getId().equals(tienda.getId())) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"El producto " + producto.getNombre() + " no pertenece a la tienda seleccionada");
			}

			if (producto.getCantidadInventario() < productoVenta.getCantidad()) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"Inventario insuficiente para " + producto.getNombre() + ". Disponibles: "
								+ producto.getCantidadInventario());
			}

			BigDecimal subtotal = producto.getPrecioUnitario()
					.multiply(BigDecimal.valueOf(productoVenta.getCantidad()));
			Integer inventarioRestante = producto.getCantidadInventario() - productoVenta.getCantidad();
			producto.setCantidadInventario(inventarioRestante);

			VentaDetalleModelo detalle = new VentaDetalleModelo();
			detalle.setProducto(producto);
			detalle.setCantidad(productoVenta.getCantidad());
			detalle.setPrecioUnitario(producto.getPrecioUnitario());
			detalle.setSubtotal(subtotal);
			detalle.setInventarioRestante(inventarioRestante);
			venta.agregarDetalle(detalle);

			total = total.add(subtotal);
		}

		venta.setTotalVenta(total);
		return toDTO(ventaRepositorio.save(venta));
	}

	public List<VentaDTO> listar() {
		return ventaRepositorio.findAll().stream().map(this::toDTO).toList();
	}

	public VentaDTO obtener(Long id) {
		VentaModelo venta = ventaRepositorio.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Venta no encontrada"));
		return toDTO(venta);
	}

	private VentaDTO toDTO(VentaModelo venta) {
		List<VentaDetalleDTO> detalles = venta.getDetalles().stream()
				.map(d -> new VentaDetalleDTO(d.getProducto().getId(), d.getProducto().getNombre(), d.getCantidad(),
						d.getPrecioUnitario(), d.getSubtotal(), d.getInventarioRestante()))
				.toList();
		return new VentaDTO(venta.getId(), venta.getFecha(), venta.getVendedor().getId(), venta.getVendedor().getNombre(),
				venta.getTienda().getId(), venta.getTienda().getNombre(), venta.getTotalVenta(), detalles);
	}
}

