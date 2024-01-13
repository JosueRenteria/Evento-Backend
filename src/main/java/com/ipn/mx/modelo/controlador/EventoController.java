package com.ipn.mx.modelo.controlador;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ipn.mx.modelo.entidades.Evento;
import com.ipn.mx.modelo.servicios.CorreoRequest;
import com.ipn.mx.modelo.servicios.EmailServices;
import com.ipn.mx.modelo.servicios.EventoService;
import com.ipn.mx.modelo.servicios.PdfService;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/apiEvento") 
public class EventoController {
	@Autowired
	private EventoService service;
	
	@Autowired
	private PdfService pdfService;
	
	@GetMapping("/evento")
	public List<Evento> readAll(){
		return service.findAll();
	}
	
	//Regresa una Categoria no una lista
	@GetMapping("/evento/{id}")
	public Evento read(@PathVariable Long id){
		return service.findById(id);
	}
	
	@DeleteMapping("/evento/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		service.delete(id);
	}
	
	@PostMapping("/evento")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> save(@Valid @RequestBody Evento evento, BindingResult result) {
		if (result.hasErrors()) {
			return ResponseEntity.badRequest().body("Error de validacion");
		}
		Evento savedEvento = service.save(evento);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedEvento);
	}
	
    @PutMapping("/evento/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> update(@Valid @RequestBody Evento evento, BindingResult result, @PathVariable Long id) {
        if (result.hasErrors()) {
            // Manejar errores de validación aquí, si es necesario
            return ResponseEntity.badRequest().body("Errores de validación");
        }

        // Si no hay errores de validación, procede a actualizar el evento
        Evento e = service.findById(id);
        e.setDescripccionEvento(evento.getDescripccionEvento());
        e.setFechaCreacion(evento.getFechaCreacion());
        e.setNombreEvento(evento.getNombreEvento());
        Evento updatedEvento = service.save(e);

        return ResponseEntity.status(HttpStatus.OK).body(updatedEvento);
    }
    
    @GetMapping("/evento/generar_reporte")
    public ResponseEntity<byte[]> generarEventoReporte() {
        try {
            List<Evento> eventos = service.findAll();
            ByteArrayOutputStream reportStream = pdfService.generarEventoReporte(eventos);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "evento-report.pdf");

            return new ResponseEntity<>(reportStream.toByteArray(), headers, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    
    @Autowired
    private EmailServices emailService;

    @PostMapping("/enviar-correo")
    public String enviarCorreo(@RequestBody CorreoRequest request) {
        try {
            emailService.enviarCorreo(request.getDestinatario(), request.getAsunto(), request.getCuerpo());
            return "Correo enviado con éxito";
        } catch (MessagingException e) {
            return "Error al enviar el correo: " + e.getMessage();
        }
    }
}
