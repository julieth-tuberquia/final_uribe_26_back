package com.example.API.Analitica.tienda.controlador;

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

import com.example.API.Analitica.tienda.dto.TiendaRopaActualizarDTO;
import com.example.API.Analitica.tienda.dto.TiendaRopaCrearDTO;
import com.example.API.Analitica.tienda.dto.TiendaRopaDTO;
import com.example.API.Analitica.tienda.servicio.TiendaRopaServicio;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/tiendas")
public class TiendaRopaControlador {

	private final TiendaRopaServicio tiendaRopaServicio;

	public TiendaRopaControlador(TiendaRopaServicio tiendaRopaServicio) {
		this.tiendaRopaServicio = tiendaRopaServicio;
	}

	@PostMapping
	public ResponseEntity<TiendaRopaDTO> crear(@Valid @RequestBody TiendaRopaCrearDTO solicitud) {
		return ResponseEntity.status(HttpStatus.CREATED).body(tiendaRopaServicio.crear(solicitud));
	}

	@GetMapping
	public List<TiendaRopaDTO> listar() {
		return tiendaRopaServicio.listar();
	}

	@GetMapping("/{id}")
	public TiendaRopaDTO obtener(@PathVariable Long id) {
		return tiendaRopaServicio.obtener(id);
	}

	@PutMapping("/{id}")
	public TiendaRopaDTO actualizar(@PathVariable Long id, @Valid @RequestBody TiendaRopaActualizarDTO solicitud) {
		return tiendaRopaServicio.actualizar(id, solicitud);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminar(@PathVariable Long id) {
		tiendaRopaServicio.eliminar(id);
		return ResponseEntity.noContent().build();
	}
}

