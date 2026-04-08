package com.example.API.Analitica.producto.controlador;

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

import com.example.API.Analitica.producto.dto.ProductoActualizarDTO;
import com.example.API.Analitica.producto.dto.ProductoCrearDTO;
import com.example.API.Analitica.producto.dto.ProductoDTO;
import com.example.API.Analitica.producto.servicio.ProductoServicio;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/productos")
public class ProductoControlador {

	private final ProductoServicio productoServicio;

	public ProductoControlador(ProductoServicio productoServicio) {
		this.productoServicio = productoServicio;
	}

	@PostMapping
	public ResponseEntity<ProductoDTO> crear(@Valid @RequestBody ProductoCrearDTO solicitud) {
		return ResponseEntity.status(HttpStatus.CREATED).body(productoServicio.crear(solicitud));
	}

	@GetMapping
	public List<ProductoDTO> listar() {
		return productoServicio.listar();
	}

	@GetMapping("/{id}")
	public ProductoDTO obtener(@PathVariable Long id) {
		return productoServicio.obtener(id);
	}

	@PutMapping("/{id}")
	public ProductoDTO actualizar(@PathVariable Long id, @Valid @RequestBody ProductoActualizarDTO solicitud) {
		return productoServicio.actualizar(id, solicitud);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminar(@PathVariable Long id) {
		productoServicio.eliminar(id);
		return ResponseEntity.noContent().build();
	}
}

