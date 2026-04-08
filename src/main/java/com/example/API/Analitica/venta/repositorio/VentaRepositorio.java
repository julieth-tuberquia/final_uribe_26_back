package com.example.API.Analitica.venta.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.API.Analitica.venta.modelo.VentaModelo;

public interface VentaRepositorio extends JpaRepository<VentaModelo, Long> {
}

