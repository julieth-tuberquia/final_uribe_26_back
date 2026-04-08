package com.example.API.Analitica.producto.dto;

import java.math.BigDecimal;

public class ProductoDTO {
	private Long id;
	private String nombre;
	private BigDecimal precioUnitario;
	private Integer cantidadInventario;
	private Long tiendaId;
	private String tiendaNombre;

	public ProductoDTO(Long id, String nombre, BigDecimal precioUnitario, Integer cantidadInventario, Long tiendaId,
			String tiendaNombre) {
		this.id = id;
		this.nombre = nombre;
		this.precioUnitario = precioUnitario;
		this.cantidadInventario = cantidadInventario;
		this.tiendaId = tiendaId;
		this.tiendaNombre = tiendaNombre;
	}

	public Long getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public BigDecimal getPrecioUnitario() {
		return precioUnitario;
	}

	public Integer getCantidadInventario() {
		return cantidadInventario;
	}

	public Long getTiendaId() {
		return tiendaId;
	}

	public String getTiendaNombre() {
		return tiendaNombre;
	}
}

