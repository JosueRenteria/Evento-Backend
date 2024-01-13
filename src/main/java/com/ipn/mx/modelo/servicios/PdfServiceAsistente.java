package com.ipn.mx.modelo.servicios;

import java.io.ByteArrayOutputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ipn.mx.modelo.entidades.Asistente;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

@Service
public class PdfServiceAsistente {
    @Autowired
    private AsistenteService asistenteService;  // Asegúrate de tener el servicio de Asistente

    public byte[] generateAsistenciasPdfReport() throws DocumentException {
        List<Asistente> asistentes = asistenteService.findAll();

        // Crear un documento PDF con OpenPDF
        Document document = new Document();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, outputStream);

        document.open();

        // Agregar contenido al documento
        document.add(new Paragraph("Lista de Asistentes:"));
        for (Asistente asistente : asistentes) {
            document.add(new Paragraph(asistente.toString()));
        }

        document.close();

        return outputStream.toByteArray();
    }
}
