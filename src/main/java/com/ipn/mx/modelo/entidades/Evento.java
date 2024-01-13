package com.ipn.mx.modelo.entidades;

import java.io.Serializable;
import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name = "Evento")
public class Evento implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idEvento;
	
	@NotBlank(message = "La descripcion del Evento no puede estar en blanco")
	@Size(max = 250, message = "La descripción del evento no puede tener más de 250 caracteres")
	@Column(name = "descripccionEvento", nullable = false, length = 250)
	private String descripccionEvento;
	
	@NotNull(message = "La fecha de creación no puede ser nula")
	@Temporal(TemporalType.DATE)
	@Column(name = "fechaCreacion", nullable = false)
	private Date fechaCreacion;
	
	@NotBlank(message = "El nombre de Evento no puede estar en blanco")
	@Size(max = 250, message = "El nombre del evento no puede tener más de 250 caracteres")
	@Column(name = "nombreEvento", nullable = false, length = 250)
	private String nombreEvento;
}
