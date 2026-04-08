package com.example.API.Analitica.producto.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.API.Analitica.producto.modelo.ProductoModelo;

public interface ProductoRepositorio extends JpaRepository<ProductoModelo, Long> {
	boolean existsByNombreIgnoreCaseAndTiendaId(String nombre, Long tiendaId);

	boolean existsByNombreIgnoreCaseAndTiendaIdAndIdNot(String nombre, Long tiendaId, Long id);
}

