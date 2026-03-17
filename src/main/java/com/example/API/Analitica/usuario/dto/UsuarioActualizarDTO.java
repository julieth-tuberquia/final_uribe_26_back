package com.example.API.Analitica.usuario.dto;

import com.example.API.Analitica.usuario.modelo.RolUsuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UsuarioActualizarDTO {

	@NotBlank
	@Size(max = 120)
	private String nombre;

	@NotBlank
	@Email
	@Size(max = 160)
	private String correo;

	@NotBlank
	@Size(min = 4, max = 255)
	private String contrasena;

	@NotNull
	private RolUsuario rol;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public RolUsuario getRol() {
		return rol;
	}

	public void setRol(RolUsuario rol) {
		this.rol = rol;
	}
}

