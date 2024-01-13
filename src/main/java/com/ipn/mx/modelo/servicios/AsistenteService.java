package com.ipn.mx.modelo.servicios;

import java.util.List;

import com.ipn.mx.modelo.entidades.Asistente;

public interface AsistenteService {
	public List<Asistente> findAll();
	public Asistente findById(Long id);
	
	public Asistente save(Asistente asistente);
	public void delete(Long id);

}
