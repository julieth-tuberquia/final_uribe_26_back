package com.example.API.Analitica.venta.modelo;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import com.example.API.Analitica.tienda.modelo.TiendaRopaModelo;
import com.example.API.Analitica.usuario.modelo.UsuarioModelo;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "ventas")
public class VentaModelo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private Instant fecha;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "vendedor_id", nullable = false)
	private UsuarioModelo vendedor;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "tienda_id", nullable = false)
	private TiendaRopaModelo tienda;

	@Column(nullable = false, precision = 14, scale = 2)
	private BigDecimal totalVenta;

	@OneToMany(mappedBy = "venta", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<VentaDetalleModelo> detalles = new ArrayList<>();

	@PrePersist
	void prePersist() {
		if (fecha == null) {
			fecha = Instant.now();
		}
	}

	public void agregarDetalle(VentaDetalleModelo detalle) {
		detalles.add(detalle);
		detalle.setVenta(this);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Instant getFecha() {
		return fecha;
	}

	public void setFecha(Instant fecha) {
		this.fecha = fecha;
	}

	public UsuarioModelo getVendedor() {
		return vendedor;
	}

	public void setVendedor(UsuarioModelo vendedor) {
		this.vendedor = vendedor;
	}

	public TiendaRopaModelo getTienda() {
		return tienda;
	}

	public void setTienda(TiendaRopaModelo tienda) {
		this.tienda = tienda;
	}

	public BigDecimal getTotalVenta() {
		return totalVenta;
	}

	public void setTotalVenta(BigDecimal totalVenta) {
		this.totalVenta = totalVenta;
	}

	public List<VentaDetalleModelo> getDetalles() {
		return detalles;
	}
}

