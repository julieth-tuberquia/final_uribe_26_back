package com.example.API.Analitica.usuario.repositorio;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.API.Analitica.usuario.modelo.UsuarioModelo;

public interface UsuarioRepositorio extends JpaRepository<UsuarioModelo, Long> {
	boolean existsByCorreoIgnoreCase(String correo);

	Optional<UsuarioModelo> findByCorreoIgnoreCase(String correo);
}

