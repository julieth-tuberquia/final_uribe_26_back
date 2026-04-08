package com.example.API.Analitica.producto.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ProductoCrearDTO {

	@NotBlank
	@Size(max = 140)
	private String nombre;

	@NotNull
	@DecimalMin(value = "0.01")
	private BigDecimal precioUnitario;

	@NotNull
	@Min(0)
	private Integer cantidadInventario;

	@NotNull
	private Long tiendaId;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public BigDecimal getPrecioUnitario() {
		return precioUnitario;
	}

	public void setPrecioUnitario(BigDecimal precioUnitario) {
		this.precioUnitario = precioUnitario;
	}

	public Integer getCantidadInventario() {
		return cantidadInventario;
	}

	public void setCantidadInventario(Integer cantidadInventario) {
		this.cantidadInventario = cantidadInventario;
	}

	public Long getTiendaId() {
		return tiendaId;
	}

	public void setTiendaId(Long tiendaId) {
		this.tiendaId = tiendaId;
	}
}

