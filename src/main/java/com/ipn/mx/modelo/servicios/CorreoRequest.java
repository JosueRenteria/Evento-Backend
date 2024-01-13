package com.ipn.mx.modelo.servicios;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CorreoRequest {
	    private String destinatario;
	    private String asunto;
	    private String cuerpo;
}
