package com.ipn.mx.modelo.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailServices {
	@Autowired
    private JavaMailSender javaMailSender;

    public void enviarCorreo(String destinatario, String asunto, String cuerpo) throws MessagingException {
        MimeMessage mensaje = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mensaje, true);

        helper.setTo(destinatario);
        helper.setSubject(asunto);
        helper.setText(cuerpo, true);

        javaMailSender.send(mensaje);
    }

}
