package com.example.API.Analitica.venta.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public class VentaDTO {
	private Long id;
	private Instant fecha;
	private Long vendedorId;
	private String vendedorNombre;
	private Long tiendaId;
	private String tiendaNombre;
	private BigDecimal totalVenta;
	private List<VentaDetalleDTO> productos;

	public VentaDTO(Long id, Instant fecha, Long vendedorId, String vendedorNombre, Long tiendaId, String tiendaNombre,
			BigDecimal totalVenta,
			List<VentaDetalleDTO> productos) {
		this.id = id;
		this.fecha = fecha;
		this.vendedorId = vendedorId;
		this.vendedorNombre = vendedorNombre;
		this.tiendaId = tiendaId;
		this.tiendaNombre = tiendaNombre;
		this.totalVenta = totalVenta;
		this.productos = productos;
	}

	public Long getId() {
		return id;
	}

	public Instant getFecha() {
		return fecha;
	}

	public Long getVendedorId() {
		return vendedorId;
	}

	public String getVendedorNombre() {
		return vendedorNombre;
	}

	public Long getTiendaId() {
		return tiendaId;
	}

	public String getTiendaNombre() {
		return tiendaNombre;
	}

	public BigDecimal getTotalVenta() {
		return totalVenta;
	}

	public List<VentaDetalleDTO> getProductos() {
		return productos;
	}
}

