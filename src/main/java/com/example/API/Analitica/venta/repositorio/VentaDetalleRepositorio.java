package com.example.API.Analitica.venta.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.API.Analitica.venta.modelo.VentaDetalleModelo;

public interface VentaDetalleRepositorio extends JpaRepository<VentaDetalleModelo, Long> {
}

