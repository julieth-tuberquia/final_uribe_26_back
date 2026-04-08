package com.example.API.Analitica.producto.servicio;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.API.Analitica.producto.dto.ProductoActualizarDTO;
import com.example.API.Analitica.producto.dto.ProductoCrearDTO;
import com.example.API.Analitica.producto.dto.ProductoDTO;
import com.example.API.Analitica.producto.modelo.ProductoModelo;
import com.example.API.Analitica.producto.repositorio.ProductoRepositorio;
import com.example.API.Analitica.tienda.modelo.TiendaRopaModelo;
import com.example.API.Analitica.tienda.servicio.TiendaRopaServicio;

@Service
public class ProductoServicio {

	private final ProductoRepositorio productoRepositorio;
	private final TiendaRopaServicio tiendaRopaServicio;

	public ProductoServicio(ProductoRepositorio productoRepositorio, TiendaRopaServicio tiendaRopaServicio) {
		this.productoRepositorio = productoRepositorio;
		this.tiendaRopaServicio = tiendaRopaServicio;
	}

	public ProductoDTO crear(ProductoCrearDTO solicitud) {
		TiendaRopaModelo tienda = tiendaRopaServicio.buscarModelo(solicitud.getTiendaId());
		if (productoRepositorio.existsByNombreIgnoreCaseAndTiendaId(solicitud.getNombre(), solicitud.getTiendaId())) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe un producto con ese nombre");
		}
		ProductoModelo producto = new ProductoModelo();
		producto.setNombre(solicitud.getNombre());
		producto.setPrecioUnitario(solicitud.getPrecioUnitario());
		producto.setCantidadInventario(solicitud.getCantidadInventario());
		producto.setTienda(tienda);
		return toDTO(productoRepositorio.save(producto));
	}

	public List<ProductoDTO> listar() {
		return productoRepositorio.findAll().stream().map(this::toDTO).toList();
	}

	public ProductoDTO obtener(Long id) {
		return toDTO(buscarModelo(id));
	}

	public ProductoDTO actualizar(Long id, ProductoActualizarDTO solicitud) {
		ProductoModelo producto = buscarModelo(id);
		TiendaRopaModelo tienda = tiendaRopaServicio.buscarModelo(solicitud.getTiendaId());
		if (productoRepositorio.existsByNombreIgnoreCaseAndTiendaIdAndIdNot(solicitud.getNombre(), solicitud.getTiendaId(),
				id)) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe un producto con ese nombre");
		}
		producto.setNombre(solicitud.getNombre());
		producto.setPrecioUnitario(solicitud.getPrecioUnitario());
		producto.setCantidadInventario(solicitud.getCantidadInventario());
		producto.setTienda(tienda);
		return toDTO(productoRepositorio.save(producto));
	}

	public void eliminar(Long id) {
		if (!productoRepositorio.existsById(id)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado");
		}
		productoRepositorio.deleteById(id);
	}

	public ProductoModelo buscarModelo(Long id) {
		return productoRepositorio.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado"));
	}

	private ProductoDTO toDTO(ProductoModelo p) {
		return new ProductoDTO(p.getId(), p.getNombre(), p.getPrecioUnitario(), p.getCantidadInventario(),
				p.getTienda().getId(), p.getTienda().getNombre());
	}
}

