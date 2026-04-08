package com.example.API.Analitica.producto.modelo;

import java.math.BigDecimal;

import com.example.API.Analitica.tienda.modelo.TiendaRopaModelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "productos", uniqueConstraints = @UniqueConstraint(columnNames = { "tienda_id", "nombre" }))
public class ProductoModelo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 140)
	private String nombre;

	@Column(nullable = false, precision = 12, scale = 2)
	private BigDecimal precioUnitario;

	@Column(nullable = false)
	private Integer cantidadInventario;

	@ManyToOne(optional = false)
	@JoinColumn(name = "tienda_id", nullable = false)
	private TiendaRopaModelo tienda;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public TiendaRopaModelo getTienda() {
		return tienda;
	}

	public void setTienda(TiendaRopaModelo tienda) {
		this.tienda = tienda;
	}
}

