/*
package com.amsidh.mvc.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class CompletePdfService {

    private final ObjectMapper objectMapper;
    private static final float MARGIN = 50; // Increased margin
    private static final float FONT_SIZE = 12;
    private static final float LINE_HEIGHT = 15;

    public CompletePdfService() {
        this.objectMapper = new ObjectMapper();
    }

    public byte[] generateCompletePdfFromJson(String jsonString) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(baos);
        PdfDocument pdf = new PdfDocument(writer);

        // Create document with proper margins
        Document document = new Document(pdf);
        document.setMargins(MARGIN, MARGIN, MARGIN, MARGIN);

        PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA);

        // Add title
        document.add(new Paragraph("Complete JSON Data")
                .setFontSize(16)
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginBottom(20));

        // Parse JSON
        LinkedHashMap<String, Object> jsonMap = objectMapper.readValue(
                jsonString, new TypeReference<>() {
                });

        // Process content with automatic page breaks
        processJsonContent(document, jsonMap, font, 0);

        document.close();
        return baos.toByteArray();
    }

    private void processJsonContent(Document document,
                                    Map<String, Object> data,
                                    PdfFont font,
                                    int indentLevel) {
        data.forEach((key, value) -> {
            String indent = "    ".repeat(indentLevel);
            if (value instanceof Map) {
                // Nested object
                Paragraph p = new Paragraph(indent + key + ":")
                        .setFont(font)
                        .setFontSize(FONT_SIZE)
                        .setFixedLeading(LINE_HEIGHT);
                document.add(p);
                processJsonContent(document, (Map<String, Object>) value, font, indentLevel + 1);
            } else if (value instanceof Iterable) {
                // Array
                Paragraph p = new Paragraph(indent + key + ":")
                        .setFont(font)
                        .setFontSize(FONT_SIZE)
                        .setFixedLeading(LINE_HEIGHT);
                document.add(p);
                for (Object item : (Iterable<?>) value) {
                    if (item instanceof Map) {
                        processJsonContent(document, (Map<String, Object>) item, font, indentLevel + 1);
                    } else {
                        document.add(new Paragraph(indent + "    - " + item)
                                .setFont(font)
                                .setFontSize(FONT_SIZE)
                                .setFixedLeading(LINE_HEIGHT));
                    }
                }
            } else {
                // Primitive value
                document.add(new Paragraph(indent + key + ": " + value)
                        .setFont(font)
                        .setFontSize(FONT_SIZE)
                        .setFixedLeading(LINE_HEIGHT));
            }
        });
    }
}*/
