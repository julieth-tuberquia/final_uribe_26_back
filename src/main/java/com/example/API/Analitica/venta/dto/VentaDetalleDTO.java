package com.example.API.Analitica.venta.dto;

import java.math.BigDecimal;

public class VentaDetalleDTO {
	private Long productoId;
	private String producto;
	private Integer cantidad;
	private BigDecimal precioUnitario;
	private BigDecimal subtotal;
	private Integer inventarioRestante;

	public VentaDetalleDTO(Long productoId, String producto, Integer cantidad, BigDecimal precioUnitario,
			BigDecimal subtotal, Integer inventarioRestante) {
		this.productoId = productoId;
		this.producto = producto;
		this.cantidad = cantidad;
		this.precioUnitario = precioUnitario;
		this.subtotal = subtotal;
		this.inventarioRestante = inventarioRestante;
	}

	public Long getProductoId() {
		return productoId;
	}

	public String getProducto() {
		return producto;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public BigDecimal getPrecioUnitario() {
		return precioUnitario;
	}

	public BigDecimal getSubtotal() {
		return subtotal;
	}

	public Integer getInventarioRestante() {
		return inventarioRestante;
	}
}

