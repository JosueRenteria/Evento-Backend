package com.ipn.mx.modelo.servicios;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ipn.mx.modelo.entidades.Asistente;
import com.ipn.mx.modelo.entidades.Evento;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

@Service
public class PdfService {

    private static final Font TITLE_FONT = new Font(Font.HELVETICA, 16, Font.BOLD);
    private static final Font TABLE_FONT = new Font(Font.HELVETICA, 12, Font.NORMAL);

    public ByteArrayOutputStream generarEventoReporte(List<Evento> eventos) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            Document document = new Document(PageSize.A4.rotate());
            PdfWriter.getInstance(document, outputStream);

            document.open();

            Paragraph title = new Paragraph("Listado de Eventos Registrados", TITLE_FONT);
            title.setSpacingAfter(10f);
            document.add(title);

            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);

            addCell(table, "ID", Element.ALIGN_CENTER, Element.ALIGN_MIDDLE);
            addCell(table, "Nombre", Element.ALIGN_CENTER, Element.ALIGN_MIDDLE);
            addCell(table, "Descripción", Element.ALIGN_CENTER, Element.ALIGN_MIDDLE);
            addCell(table, "Fecha de Creación", Element.ALIGN_CENTER, Element.ALIGN_MIDDLE);

            for (Evento evento : eventos) {
                addCell(table, String.valueOf(evento.getIdEvento()), Element.ALIGN_CENTER, Element.ALIGN_MIDDLE);
                addCell(table, evento.getNombreEvento(), Element.ALIGN_CENTER, Element.ALIGN_MIDDLE);
                addCell(table, evento.getDescripccionEvento(), Element.ALIGN_CENTER, Element.ALIGN_MIDDLE);
                addCell(table, String.valueOf(evento.getFechaCreacion()), Element.ALIGN_CENTER, Element.ALIGN_MIDDLE);
            }

            document.add(table);

            document.close();
        } catch (Exception e) {
            throw new IOException("Error al generar el informe PDF", e);
        }

        return outputStream;
    }

    public ByteArrayOutputStream generarAsistenteReporte(List<Asistente> asistentes) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            Document document = new Document(PageSize.A4.rotate());
            PdfWriter.getInstance(document, outputStream);

            document.open();

            Paragraph title = new Paragraph("Listado de Asistentes Registrados", TITLE_FONT);
            title.setSpacingAfter(10f);
            document.add(title);

            PdfPTable table = new PdfPTable(7);
            table.setWidthPercentage(100);

            addCell(table, "ID", Element.ALIGN_CENTER, Element.ALIGN_MIDDLE);
            addCell(table, "Email", Element.ALIGN_CENTER, Element.ALIGN_MIDDLE);
            addCell(table, "Fecha de Registro", Element.ALIGN_CENTER, Element.ALIGN_MIDDLE);
            addCell(table, "Apellido Paterno", Element.ALIGN_CENTER, Element.ALIGN_MIDDLE);
            addCell(table, "Apellido Materno", Element.ALIGN_CENTER, Element.ALIGN_MIDDLE);
            addCell(table, "Nombre", Element.ALIGN_CENTER, Element.ALIGN_MIDDLE);
            addCell(table, "ID del Evento", Element.ALIGN_CENTER, Element.ALIGN_MIDDLE);

            for (Asistente asistente : asistentes) {
                addCell(table, String.valueOf(asistente.getIdAsistente()), Element.ALIGN_CENTER, Element.ALIGN_MIDDLE);
                addCell(table, asistente.getEmail(), Element.ALIGN_CENTER, Element.ALIGN_MIDDLE);
                addCell(table, String.valueOf(asistente.getFechaRegistro()), Element.ALIGN_CENTER, Element.ALIGN_MIDDLE);
                addCell(table, asistente.getPaterno(), Element.ALIGN_CENTER, Element.ALIGN_MIDDLE);
                addCell(table, asistente.getMaterno(), Element.ALIGN_CENTER, Element.ALIGN_MIDDLE);
                addCell(table, asistente.getNombre(), Element.ALIGN_CENTER, Element.ALIGN_MIDDLE);
                addCell(table, String.valueOf(asistente.getEvento().getIdEvento()), Element.ALIGN_CENTER, Element.ALIGN_MIDDLE);
            }

            document.add(table);

            document.close();
        } catch (Exception e) {
            throw new IOException("Error al generar el informe PDF", e);
        }

        return outputStream;
    }

    private void addCell(PdfPTable table, String text, int horizontalAlignment, int verticalAlignment) {
        PdfPCell cell = new PdfPCell(new Paragraph(text, TABLE_FONT));
        cell.setHorizontalAlignment(horizontalAlignment);
        cell.setVerticalAlignment(verticalAlignment);
        table.addCell(cell);
    }
}
