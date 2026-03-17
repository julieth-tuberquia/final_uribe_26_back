package com.example.API.Analitica.usuario.dto;

import com.example.API.Analitica.usuario.modelo.RolUsuario;

public class UsuarioDTO {
	private Long id;
	private String nombre;
	private String correo;
	private RolUsuario rol;

	public UsuarioDTO(Long id, String nombre, String correo, RolUsuario rol) {
		this.id = id;
		this.nombre = nombre;
		this.correo = correo;
		this.rol = rol;
	}

	public Long getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public String getCorreo() {
		return correo;
	}

	public RolUsuario getRol() {
		return rol;
	}
}

