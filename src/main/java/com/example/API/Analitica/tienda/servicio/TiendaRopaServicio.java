package com.example.API.Analitica.tienda.servicio;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.API.Analitica.tienda.dto.TiendaRopaActualizarDTO;
import com.example.API.Analitica.tienda.dto.TiendaRopaCrearDTO;
import com.example.API.Analitica.tienda.dto.TiendaRopaDTO;
import com.example.API.Analitica.tienda.modelo.TiendaRopaModelo;
import com.example.API.Analitica.tienda.repositorio.TiendaRopaRepositorio;

@Service
public class TiendaRopaServicio {

	private final TiendaRopaRepositorio tiendaRopaRepositorio;

	public TiendaRopaServicio(TiendaRopaRepositorio tiendaRopaRepositorio) {
		this.tiendaRopaRepositorio = tiendaRopaRepositorio;
	}

	public TiendaRopaDTO crear(TiendaRopaCrearDTO solicitud) {
		TiendaRopaModelo tienda = new TiendaRopaModelo();
		mapearCampos(solicitud.getNombre(), solicitud.getNit(), solicitud.getDireccion(), solicitud.getTelefono(),
				solicitud.getCorreo(), tienda);
		return convertirADTO(tiendaRopaRepositorio.save(tienda));
	}

	public List<TiendaRopaDTO> listar() {
		return tiendaRopaRepositorio.findAll().stream().map(this::convertirADTO).toList();
	}

	public TiendaRopaDTO obtener(Long id) {
		return convertirADTO(buscarModelo(id));
	}

	public TiendaRopaDTO actualizar(Long id, TiendaRopaActualizarDTO solicitud) {
		TiendaRopaModelo tienda = buscarModelo(id);
		mapearCampos(solicitud.getNombre(), solicitud.getNit(), solicitud.getDireccion(), solicitud.getTelefono(),
				solicitud.getCorreo(), tienda);
		return convertirADTO(tiendaRopaRepositorio.save(tienda));
	}

	public void eliminar(Long id) {
		if (!tiendaRopaRepositorio.existsById(id)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tienda de ropa no encontrada");
		}
		tiendaRopaRepositorio.deleteById(id);
	}

	public TiendaRopaModelo buscarModelo(Long id) {
		return tiendaRopaRepositorio.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tienda de ropa no encontrada"));
	}

	private TiendaRopaDTO convertirADTO(TiendaRopaModelo tienda) {
		return new TiendaRopaDTO(tienda.getId(), tienda.getNombre(), tienda.getNit(), tienda.getDireccion(),
				tienda.getTelefono(), tienda.getCorreo());
	}

	private void mapearCampos(String nombre, String nit, String direccion, String telefono, String correo,
			TiendaRopaModelo tienda) {
		tienda.setNombre(nombre);
		tienda.setNit(nit);
		tienda.setDireccion(direccion);
		tienda.setTelefono(telefono);
		tienda.setCorreo(correo);
	}
}

