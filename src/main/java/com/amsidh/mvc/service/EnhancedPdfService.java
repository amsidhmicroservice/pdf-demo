/*
package com.amsidh.mvc.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class EnhancedPdfService {

    private final ObjectMapper objectMapper;
    private static final float BORDER_MARGIN = 36; // 0.5 inch margin
    private static final float BORDER_WIDTH = 1.5f;
    private static final float DOUBLE_BORDER_GAP = 3f;

    public EnhancedPdfService() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.configure(
                com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
    }

    public byte[] generateEnhancedPdfFromJson(String jsonString) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(baos);
        PdfDocument pdf = new PdfDocument(writer);

        // Create document with margins to accommodate borders
        Document document = new Document(pdf);

        PdfFont headerFont = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
        PdfFont normalFont = PdfFontFactory.createFont(StandardFonts.HELVETICA);
        PdfFont pageNumberFont = PdfFontFactory.createFont(StandardFonts.HELVETICA_OBLIQUE);

        // Add title
        document.add(new Paragraph("Enhanced JSON to PDF")
                .setFont(headerFont)
                .setFontSize(16)
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginBottom(20));

        // Parse JSON with order preserved
        LinkedHashMap<String, Object> jsonMap = objectMapper.readValue(
                jsonString,
                new TypeReference<LinkedHashMap<String, Object>>() {});

        // Process JSON
        processOrderedJson(document, jsonMap, headerFont, normalFont, 0);

        // Add borders and page numbers to all pages
        int totalPages = pdf.getNumberOfPages();
        for (int i = 1; i <= totalPages; i++) {
            PdfPage page = pdf.getPage(i);
            addDoubleBorder(page);
            addPageNumber(document, page, i, totalPages, pageNumberFont);
        }

        document.close();
        return baos.toByteArray();
    }

    private void addDoubleBorder(PdfPage page) {
        Rectangle pageSize = page.getPageSize();
        PdfCanvas canvas = new PdfCanvas(page);

        // Outer border
        canvas.setLineWidth(BORDER_WIDTH)
                .setStrokeColor(ColorConstants.DARK_GRAY)
                .rectangle(BORDER_MARGIN, BORDER_MARGIN,
                        pageSize.getWidth() - 2 * BORDER_MARGIN,
                        pageSize.getHeight() - 2 * BORDER_MARGIN)
                .stroke();

        // Inner border
        canvas.setLineWidth(BORDER_WIDTH / 2)
                .setStrokeColor(ColorConstants.GRAY)
                .rectangle(BORDER_MARGIN + DOUBLE_BORDER_GAP, BORDER_MARGIN + DOUBLE_BORDER_GAP,
                        pageSize.getWidth() - 2 * (BORDER_MARGIN + DOUBLE_BORDER_GAP),
                        pageSize.getHeight() - 2 * (BORDER_MARGIN + DOUBLE_BORDER_GAP))
                .stroke();
    }

    private void addPageNumber(Document document, PdfPage page, int currentPage, int totalPages, PdfFont font) {
        Rectangle pageSize = page.getPageSize();
        float x = BORDER_MARGIN + 10; // Left position
        float y = BORDER_MARGIN + 20;  // Bottom position

        new Canvas(new PdfCanvas(page), pageSize)
                .showTextAligned(new Paragraph(String.format("Page %d of %d", currentPage, totalPages))
                                .setFont(font)
                                .setFontSize(10),
                        x, y, TextAlignment.LEFT);
    }

    private void processOrderedJson(Document document,
                                    LinkedHashMap<String, Object> jsonMap,
                                    PdfFont headerFont, PdfFont normalFont,
                                    int indentLevel) throws IOException {
        for (Map.Entry<String, Object> entry : jsonMap.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            if (value instanceof LinkedHashMap) {
                // Nested object
                document.add(createIndentedParagraph(key + ":", headerFont, indentLevel));
                processOrderedJson(document, (LinkedHashMap<String, Object>) value,
                        headerFont, normalFont, indentLevel + 1);
            }
            else if (value instanceof List) {
                // Array
                document.add(createIndentedParagraph(key + ":", headerFont, indentLevel));
                processOrderedArray(document, (List<?>) value, headerFont, normalFont, indentLevel + 1);
            }
            else {
                // Primitive value
                Paragraph p = createIndentedParagraph(key + ": " + value.toString(), normalFont, indentLevel);
                document.add(p);
            }
        }
    }

    private void processOrderedArray(Document document, List<?> array,
                                     PdfFont headerFont, PdfFont normalFont,
                                     int indentLevel) throws IOException {
        if (array.isEmpty()) return;

        Object firstElement = array.get(0);

        if (firstElement instanceof LinkedHashMap) {
            // Array of objects - create a table
            LinkedHashMap<String, Object> firstObj = (LinkedHashMap<String, Object>) firstElement;

            // Create table with columns in order
            float[] columnWidths = new float[firstObj.size()];
            for (int i = 0; i < columnWidths.length; i++) {
                columnWidths[i] = 100;
            }

            Table table = new Table(UnitValue.createPercentArray(columnWidths));

            // Add headers in order
            for (String header : firstObj.keySet()) {
                table.addHeaderCell(new Cell().add(new Paragraph(header).setFont(headerFont)));
            }

            // Add rows in order
            for (Object item : array) {
                LinkedHashMap<String, Object> obj = (LinkedHashMap<String, Object>) item;
                for (Object val : obj.values()) {
                    table.addCell(new Cell().add(new Paragraph(val.toString()).setFont(normalFont)));
                }
            }

            document.add(table);
        }
        else {
            // Array of primitives - list them
            for (Object item : array) {
                Paragraph p = createIndentedParagraph("- " + item.toString(), normalFont, indentLevel);
                document.add(p);
            }
        }
    }

    private Paragraph createIndentedParagraph(String text, PdfFont font, int indentLevel) {
        StringBuilder indent = new StringBuilder();
        for (int i = 0; i < indentLevel; i++) {
            indent.append("    "); // 4 spaces per indent level
        }
        return new Paragraph(indent.toString() + text).setFont(font);
    }
}*/
