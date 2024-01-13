package com.ipn.mx.modelo.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ipn.mx.modelo.dao.EventoDAO;
import com.ipn.mx.modelo.entidades.Evento;

@Service
public class EventoServiceImpl implements EventoService{
	@Autowired
	EventoDAO dao;
	
	@Override
	@Transactional (readOnly = false)
	public List<Evento> findAll() {
		return (List<Evento>) dao.findAll();
	}

	@Override
	@Transactional (readOnly = false)
	public Evento findById(Long id) {
		return dao.findById(id).orElse(null);
	}

	@Override
	@Transactional 
	public Evento save(Evento evento) {
		return dao.save(evento);
	}

	@Override
	@Transactional 
	public void delete(Long id) {
		dao.deleteById(id);
	}

}
