package com.ipn.mx.modelo.controlador;

import java.util.List;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

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

import com.ipn.mx.modelo.entidades.Asistente;
import com.ipn.mx.modelo.servicios.AsistenteService;
import com.ipn.mx.modelo.servicios.PdfService;

import jakarta.validation.Valid;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/apiAsistente")
public class AsistenteController {
	@Autowired
	private AsistenteService service;
	
	@Autowired
	private PdfService pdfService;
	
	@GetMapping("/asistente")
	public List<Asistente> readAll(){
		return service.findAll();
	}
	
	//Regresa una Categoria no una lista
	@GetMapping("/asistente/{id}")
	public Asistente read(@PathVariable Long id){
		return service.findById(id);
	}
	
	@DeleteMapping("/asistente/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		service.delete(id);
	}
	
    @PostMapping("/asistente")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> save(@Valid @RequestBody Asistente asistente, BindingResult result) {
        if (result.hasErrors()) {
            // Manejar errores de validación aquí, si es necesario
            return ResponseEntity.badRequest().body("Errores de validación");
        }

        // Si no hay errores de validación, procede a guardar el asistente
        Asistente savedAsistente = service.save(asistente);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAsistente);
    }

    @PutMapping("/asistente/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> update(@Valid @RequestBody Asistente asistente, BindingResult result, @PathVariable Long id) {
        if (result.hasErrors()) {
            // Manejar errores de validación aquí, si es necesario
            return ResponseEntity.badRequest().body("Errores de validación");
        }

        // Si no hay errores de validación, procede a actualizar el asistente
        Asistente a = service.findById(id);
        a.setEmail(asistente.getEmail());
        a.setFechaRegistro(asistente.getFechaRegistro());
        a.setNombre(asistente.getNombre());
        a.setMaterno(asistente.getMaterno());
        a.setPaterno(asistente.getPaterno());
        a.setEvento(asistente.getEvento());
        Asistente updatedAsistente = service.save(a);

        return ResponseEntity.status(HttpStatus.OK).body(updatedAsistente);
    }
    
    @GetMapping("/asistente/generar_reporte")
    public ResponseEntity<byte[]> generarAsistenteReporte() {
        try {
            List<Asistente> asistentes = service.findAll();
            ByteArrayOutputStream reportStream = pdfService.generarAsistenteReporte(asistentes);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "asistente-report.pdf");

            return new ResponseEntity<>(reportStream.toByteArray(), headers, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
