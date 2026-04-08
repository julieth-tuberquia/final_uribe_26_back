package com.example.API.Analitica.venta.dto;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class VentaCrearDTO {

	@NotNull
	private Long vendedorId;

	@NotNull
	private Long tiendaId;

	@Valid
	@NotEmpty
	private List<VentaCrearItemDTO> productos;

	public Long getVendedorId() {
		return vendedorId;
	}

	public void setVendedorId(Long vendedorId) {
		this.vendedorId = vendedorId;
	}

	public Long getTiendaId() {
		return tiendaId;
	}

	public void setTiendaId(Long tiendaId) {
		this.tiendaId = tiendaId;
	}

	public List<VentaCrearItemDTO> getProductos() {
		return productos;
	}

	public void setProductos(List<VentaCrearItemDTO> productos) {
		this.productos = productos;
	}
}

