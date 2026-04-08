package com.example.API.Analitica.venta.controlador;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.API.Analitica.venta.dto.VentaCrearDTO;
import com.example.API.Analitica.venta.dto.VentaDTO;
import com.example.API.Analitica.venta.servicio.VentaServicio;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/ventas")
public class VentaControlador {

	private final VentaServicio ventaServicio;

	public VentaControlador(VentaServicio ventaServicio) {
		this.ventaServicio = ventaServicio;
	}

	@PostMapping
	public ResponseEntity<VentaDTO> crear(@Valid @RequestBody VentaCrearDTO solicitud) {
		return ResponseEntity.status(HttpStatus.CREATED).body(ventaServicio.crear(solicitud));
	}

	@GetMapping
	public List<VentaDTO> listar() {
		return ventaServicio.listar();
	}

	@GetMapping("/{id}")
	public VentaDTO obtener(@PathVariable Long id) {
		return ventaServicio.obtener(id);
	}
}

