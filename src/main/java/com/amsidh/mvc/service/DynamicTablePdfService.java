package com.amsidh.mvc.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.UnitValue;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class DynamicTablePdfService {

    private final ObjectMapper objectMapper;
    private static final float MARGIN = 20;
    private static final float FONT_SIZE = 10;
    private static final float CELL_PADDING = 5;
    private static final float BORDER_WIDTH = 1f;

    public DynamicTablePdfService() {
        this.objectMapper = new ObjectMapper();
    }

    public byte[] generateDynamicTablePdf(String jsonString) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(baos);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);
        document.setMargins(MARGIN, MARGIN, MARGIN, MARGIN);

        PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA);
        PdfFont boldFont = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);

        // Parse JSON
        LinkedHashMap<String, Object> jsonMap = objectMapper.readValue(
                jsonString, new TypeReference<>() {
                });

        // Create main table with 2 columns
        Table mainTable = new Table(UnitValue.createPercentArray(new float[]{1, 2}))
                .useAllAvailableWidth();
        mainTable.setBorder(new SolidBorder(ColorConstants.BLACK, BORDER_WIDTH));

        // Process JSON content
        processJsonToTable(mainTable, jsonMap, boldFont, font, 0);

        document.add(mainTable);
        document.close();
        return baos.toByteArray();
    }

    private void processJsonToTable(Table parentTable, Map<String, Object> data,
                                    PdfFont boldFont, PdfFont normalFont,
                                    int level) {
        data.forEach((key, value) -> {
            Cell keyCell = new Cell()
                    .add(new Paragraph(key)
                            .setFont(boldFont)
                            .setFontSize(FONT_SIZE))
                    .setPadding(CELL_PADDING)
                    .setBorder(new SolidBorder(ColorConstants.BLACK, BORDER_WIDTH));

            Cell valueCell = new Cell()
                    .setPadding(CELL_PADDING)
                    .setBorder(new SolidBorder(ColorConstants.BLACK, BORDER_WIDTH));

            if (value instanceof Map) {
                // If value is nested JSON, create a nested table
                Table nestedTable = new Table(UnitValue.createPercentArray(new float[]{1, 2}))
                        .useAllAvailableWidth();
                nestedTable.setBorder(new SolidBorder(ColorConstants.GRAY, BORDER_WIDTH)); // Nested table border

                // Recursive call for nested object
                processJsonToTable(nestedTable, (Map<String, Object>) value, boldFont, normalFont, level + 1);

                valueCell.add(nestedTable);
            } else if (value instanceof List) {
                // If value is a list, show each item
                List<?> list = (List<?>) value;
                for (Object item : list) {
                    if (item instanceof Map) {
                        Table nestedTable = new Table(UnitValue.createPercentArray(new float[]{1, 2}))
                                .useAllAvailableWidth();
                        nestedTable.setBorder(new SolidBorder(ColorConstants.GRAY, BORDER_WIDTH)); // Nested table border
                        processJsonToTable(nestedTable, (Map<String, Object>) item, boldFont, normalFont, level + 1);
                        valueCell.add(nestedTable);
                    } else {
                        valueCell.add(new Paragraph(String.valueOf(item))
                                .setFont(normalFont)
                                .setFontSize(FONT_SIZE));
                    }
                }
            } else {
                valueCell.add(new Paragraph(String.valueOf(value))
                        .setFont(normalFont)
                        .setFontSize(FONT_SIZE));
            }

            parentTable.addCell(keyCell);
            parentTable.addCell(valueCell);
        });
    }
}
