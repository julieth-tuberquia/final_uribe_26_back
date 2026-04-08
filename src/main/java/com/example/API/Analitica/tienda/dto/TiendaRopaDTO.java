package com.example.API.Analitica.tienda.dto;

public class TiendaRopaDTO {
	private Long id;
	private String nombre;
	private String nit;
	private String direccion;
	private String telefono;
	private String correo;

	public TiendaRopaDTO(Long id, String nombre, String nit, String direccion, String telefono, String correo) {
		this.id = id;
		this.nombre = nombre;
		this.nit = nit;
		this.direccion = direccion;
		this.telefono = telefono;
		this.correo = correo;
	}

	public Long getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public String getNit() {
		return nit;
	}

	public String getDireccion() {
		return direccion;
	}

	public String getTelefono() {
		return telefono;
	}

	public String getCorreo() {
		return correo;
	}
}

