package com.ipn.mx.modelo.servicios;

import java.util.List;

import com.ipn.mx.modelo.entidades.Evento;

public interface EventoService {
	public List<Evento> findAll();
	public Evento findById(Long id);
	
	public Evento save(Evento evento);
	public void delete(Long id);
}
