package com.ipn.mx.modelo.entidades;

import java.io.Serializable;
import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name = "Asistente")
public class Asistente implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idAsistente;
	
	@NotBlank(message = "El email no puede estar en blanco")
	@Size(max = 200, message = "El email no puede tener más de 200 caracteres")
	@Email(message = "El formato del email no es válido")
	@Column(name = "email", nullable = false, length = 200)
	private String email;
	
	@NotNull(message = "La fecha de registro no puede ser nula")
	@Temporal(TemporalType.DATE)
	@Column(name = "fechaRegistro", nullable = false)
	private Date fechaRegistro;
	
	@NotBlank(message = "El nombre no puede estar en blanco")
	@Size(max = 100, message = "El nombre no puede tener más de 100 caracteres")
	@Column(name = "nombre", nullable = false, length = 100)
	private String nombre;
	
	@NotBlank(message = "El materno no puede estar en blanco")
	@Size(max = 100, message = "El materno no puede tener más de 100 caracteres")
	@Column(name = "materno", nullable = false, length = 100)
	private String materno;
	
	@NotBlank(message = "El paterno no puede estar en blanco")
	@Size(max = 100, message = "El paterno no puede tener más de 100 caracteres")
	@Column(name = "paterno", nullable = false, length = 100)
	private String paterno;
	
	@ManyToOne
	@JoinColumn(name = "idEvento", referencedColumnName = "idEvento")
	@NotNull(message = "El evento no puede ser nulo")
	private Evento evento;

}
