package com.example.API.Analitica.venta.modelo;

import java.math.BigDecimal;

import com.example.API.Analitica.producto.modelo.ProductoModelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "venta_detalles")
public class VentaDetalleModelo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "venta_id", nullable = false)
	private VentaModelo venta;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "producto_id", nullable = false)
	private ProductoModelo producto;

	@Column(nullable = false)
	private Integer cantidad;

	@Column(nullable = false, precision = 12, scale = 2)
	private BigDecimal precioUnitario;

	@Column(nullable = false, precision = 14, scale = 2)
	private BigDecimal subtotal;

	@Column(nullable = false)
	private Integer inventarioRestante;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public VentaModelo getVenta() {
		return venta;
	}

	public void setVenta(VentaModelo venta) {
		this.venta = venta;
	}

	public ProductoModelo getProducto() {
		return producto;
	}

	public void setProducto(ProductoModelo producto) {
		this.producto = producto;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public BigDecimal getPrecioUnitario() {
		return precioUnitario;
	}

	public void setPrecioUnitario(BigDecimal precioUnitario) {
		this.precioUnitario = precioUnitario;
	}

	public BigDecimal getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(BigDecimal subtotal) {
		this.subtotal = subtotal;
	}

	public Integer getInventarioRestante() {
		return inventarioRestante;
	}

	public void setInventarioRestante(Integer inventarioRestante) {
		this.inventarioRestante = inventarioRestante;
	}
}

