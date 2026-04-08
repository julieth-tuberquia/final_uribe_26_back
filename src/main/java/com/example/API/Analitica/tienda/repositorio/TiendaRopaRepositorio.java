package com.example.API.Analitica.tienda.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.API.Analitica.tienda.modelo.TiendaRopaModelo;

public interface TiendaRopaRepositorio extends JpaRepository<TiendaRopaModelo, Long> {
}

