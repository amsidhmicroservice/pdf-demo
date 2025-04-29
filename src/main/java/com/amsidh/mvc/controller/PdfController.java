package com.amsidh.mvc.controller;

import com.amsidh.mvc.service.DynamicTablePdfService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/pdf")
public class PdfController {

    private final DynamicTablePdfService dynamicTablePdfService;

    public PdfController(DynamicTablePdfService dynamicTablePdfService) {
        this.dynamicTablePdfService = dynamicTablePdfService;
    }

    @PostMapping(value = "/generate-dynamic-pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> generateEnhancedPdf(@RequestBody String jsonString) throws IOException {
        byte[] pdfBytes = dynamicTablePdfService.generateDynamicTablePdf(jsonString);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=dynamicPdfData.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }
    @GetMapping(value = "/generate-static-pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> generateDynamicPdf() throws IOException {
        byte[] pdf = dynamicTablePdfService.generateDynamicTablePdf(getJsonData());
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=staticPdfData.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }
    private String getJsonData() {
        return "{\n" +
                "    \"name\": \"Amsidh Lokhande\",\n" +
                "    \"age\": 42,\n" +
                "    \"addresses\": [\n" +
                "        {\n" +
                "            \"city\": \"Shirnal\",\n" +
                "            \"taluk\": \"Bijapur\",\n" +
                "            \"dist\": \"Bijapur\",\n" +
                "            \"pincode\": 586119,\n" +
                "            \"state\": \"MH\",\n" +
                "            \"dscription\": \"This village is located in Bijapur district Karnataka. Population of this village is around 1400. Most common proffession of this village people is farming. THis village is on the border of Karnataka-Maharashtra states\",\n" +
                "            \"population\": 1400,\n" +
                "            \"border\": {\n" +
                "                \"east\": \"Makhnapur\",\n" +
                "                \"west\": \"MaharashtraBorder\",\n" +
                "                \"south\": \"Siddhapur\",\n" +
                "                \"north\": \"Kannur\"\n" +
                "            },\n" +
                "            \"longitude\": 43543.53434,\n" +
                "            \"latitude\": 54654.65565\n" +
                "        },\n" +
                "        {\n" +
                "            \"city\": \"Katepuram Chouk\",\n" +
                "            \"taluk\": \"Haveli\",\n" +
                "            \"dist\": \"Pune\",\n" +
                "            \"pincode\": 411048\n" +
                "        },\n" +
                "        {\n" +
                "            \"city\": \"Lohegaon\",\n" +
                "            \"taluk\": \"Haveli\",\n" +
                "            \"dist\": \"Pune\",\n" +
                "            \"pincode\": 411047\n" +
                "        },\n" +
                "        {\n" +
                "            \"city\": \"Shirnal1\",\n" +
                "            \"taluk\": \"Bijapur1\",\n" +
                "            \"dist\": \"Bijapur1\",\n" +
                "            \"pincode\": 5861191\n" +
                "        },\n" +
                "        {\n" +
                "            \"city\": \"Katepuram Chouk1\",\n" +
                "            \"taluk\": \"Haveli2\",\n" +
                "            \"dist\": \"Pune2\",\n" +
                "            \"pincode\": 4110482\n" +
                "        },\n" +
                "        {\n" +
                "            \"city\": \"Shirnal2\",\n" +
                "            \"taluk\": \"Bijapur2\",\n" +
                "            \"dist\": \"Bijapur2\",\n" +
                "            \"pincode\": 5861192\n" +
                "        },\n" +
                "        {\n" +
                "            \"city\": \"Katepuram Chouk3\",\n" +
                "            \"taluk\": \"Haveli3\",\n" +
                "            \"dist\": \"Pune3\",\n" +
                "            \"pincode\": 4110483\n" +
                "        },\n" +
                "        {\n" +
                "            \"city\": \"Shirnal4\",\n" +
                "            \"taluk\": \"Bijapur4\",\n" +
                "            \"dist\": \"Bijapur4\",\n" +
                "            \"pincode\": 5861194\n" +
                "        },\n" +
                "        {\n" +
                "            \"city\": \"Katepuram Chou5k\",\n" +
                "            \"taluk\": \"Haveli6\",\n" +
                "            \"dist\": \"Pune5\",\n" +
                "            \"pincode\": 4110485\n" +
                "        },\n" +
                "        {\n" +
                "            \"city\": \"Shirnal33\",\n" +
                "            \"taluk\": \"Bijapur254\",\n" +
                "            \"dist\": \"Bijapur534\",\n" +
                "            \"pincode\": 58611934\n" +
                "        },\n" +
                "        {\n" +
                "            \"city\": \"Katepuram Chouk43\",\n" +
                "            \"taluk\": \"Haveli54\",\n" +
                "            \"dist\": \"Pune64\",\n" +
                "            \"pincode\": 41104865\n" +
                "        },\n" +
                "        {\n" +
                "            \"city\": \"Shirnal534\",\n" +
                "            \"taluk\": \"Bijapur643\",\n" +
                "            \"dist\": \"Bijapur52\",\n" +
                "            \"pincode\": 58611953\n" +
                "        },\n" +
                "        {\n" +
                "            \"city\": \"Katepuram Chouk45\",\n" +
                "            \"taluk\": \"Haveli55\",\n" +
                "            \"dist\": \"Pune76\",\n" +
                "            \"pincode\": 41104834\n" +
                "        },\n" +
                "        {\n" +
                "            \"city\": \"Shirnal34543\",\n" +
                "            \"taluk\": \"Bijapur45\",\n" +
                "            \"dist\": \"Bijapur345\",\n" +
                "            \"pincode\": 5861193\n" +
                "        },\n" +
                "        {\n" +
                "            \"city\": \"Katepuram Chouk534\",\n" +
                "            \"taluk\": \"Haveli35\",\n" +
                "            \"dist\": \"Pune5\",\n" +
                "            \"pincode\": 41104852\n" +
                "        },\n" +
                "        {\n" +
                "            \"city\": \"Shirnal5232\",\n" +
                "            \"taluk\": \"Bijapur66\",\n" +
                "            \"dist\": \"Bijapur7e\",\n" +
                "            \"pincode\": 58611239\n" +
                "        },\n" +
                "        {\n" +
                "            \"city\": \"Katepuram Chouk23\",\n" +
                "            \"taluk\": \"Haveli124\",\n" +
                "            \"dist\": \"Pune542\",\n" +
                "            \"pincode\": 411048543\n" +
                "        },\n" +
                "        {\n" +
                "            \"city\": \"Shirnal23\",\n" +
                "            \"taluk\": \"Bijapur654\",\n" +
                "            \"dist\": \"Bijapur74\",\n" +
                "            \"pincode\": 5861195\n" +
                "        },\n" +
                "        {\n" +
                "            \"city\": \"Katepuram Chouk643\",\n" +
                "            \"taluk\": \"Haveli64\",\n" +
                "            \"dist\": \"Pune45\",\n" +
                "            \"pincode\": 41104875\n" +
                "        },\n" +
                "        {\n" +
                "            \"city\": \"Shirnal654\",\n" +
                "            \"taluk\": \"Bijapur754\",\n" +
                "            \"dist\": \"Bijapur56\",\n" +
                "            \"pincode\": 58611943\n" +
                "        },\n" +
                "        {\n" +
                "            \"city\": \"Katepuram Chouk435\",\n" +
                "            \"taluk\": \"Haveli43\",\n" +
                "            \"dist\": \"Pune36\",\n" +
                "            \"pincode\": 41104834\n" +
                "        },\n" +
                "        {\n" +
                "            \"city\": \"Shirnal323\",\n" +
                "            \"taluk\": \"Bijapur\",\n" +
                "            \"dist\": \"Bijapur523\",\n" +
                "            \"pincode\": 586119\n" +
                "        },\n" +
                "        {\n" +
                "            \"city\": \"Katepuram Chouk111\",\n" +
                "            \"taluk\": \"Haveli\",\n" +
                "            \"dist\": \"Pune\",\n" +
                "            \"pincode\": 411048\n" +
                "        },\n" +
                "        {\n" +
                "            \"city\": \"Shirnal111\",\n" +
                "            \"taluk\": \"Bijapur\",\n" +
                "            \"dist\": \"Bijapur\",\n" +
                "            \"pincode\": 586119\n" +
                "        },\n" +
                "        {\n" +
                "            \"city\": \"Katepuram Chouk444\",\n" +
                "            \"taluk\": \"Haveli\",\n" +
                "            \"dist\": \"Pune\",\n" +
                "            \"pincode\": 411048\n" +
                "        },\n" +
                "        {\n" +
                "            \"city\": \"Shirnal44\",\n" +
                "            \"taluk\": \"Bijapur\",\n" +
                "            \"dist\": \"Bijapur\",\n" +
                "            \"pincode\": 586119\n" +
                "        },\n" +
                "        {\n" +
                "            \"city\": \"KatepuramChouk44444\",\n" +
                "            \"taluk\": \"Haveli\",\n" +
                "            \"dist\": \"Pune\",\n" +
                "            \"pincode\": 411048\n" +
                "        },\n" +
                "        {\n" +
                "            \"city\": \"KatepuramChouk1\",\n" +
                "            \"taluk\": \"Haveli\",\n" +
                "            \"dist\": \"Pune\",\n" +
                "            \"pincode\": 411048\n" +
                "        },\n" +
                "        {\n" +
                "            \"city\": \"KatepuramChouk4442\",\n" +
                "            \"taluk\": \"Haveli\",\n" +
                "            \"dist\": \"Pune\",\n" +
                "            \"pincode\": 411048\n" +
                "        },\n" +
                "        {\n" +
                "            \"city\": \"KatepuramChouk4424\",\n" +
                "            \"taluk\": \"Haveli\",\n" +
                "            \"dist\": \"Pune\",\n" +
                "            \"pincode\": 411048\n" +
                "        },\n" +
                "        {\n" +
                "            \"city\": \"KatepuramChouk244\",\n" +
                "            \"taluk\": \"Haveli\",\n" +
                "            \"dist\": \"Pune\",\n" +
                "            \"pincode\": 411048\n" +
                "        },\n" +
                "        {\n" +
                "            \"city\": \"KatepuramChouk2444\",\n" +
                "            \"taluk\": \"Haveli\",\n" +
                "            \"dist\": \"Pune\",\n" +
                "            \"pincode\": 411048\n" +
                "        },\n" +
                "        {\n" +
                "            \"city\": \"KatepuramChouk24444\",\n" +
                "            \"taluk\": \"Haveli\",\n" +
                "            \"dist\": \"Pune\",\n" +
                "            \"pincode\": 411048\n" +
                "        },\n" +
                "        {\n" +
                "            \"city\": \"KatepuramChou4\",\n" +
                "            \"taluk\": \"Haveli\",\n" +
                "            \"dist\": \"Pune\",\n" +
                "            \"pincode\": 411048\n" +
                "        },\n" +
                "        {\n" +
                "            \"city\": \"Katepuramk44444\",\n" +
                "            \"taluk\": \"Haveli\",\n" +
                "            \"dist\": \"Pune\",\n" +
                "            \"pincode\": 411048\n" +
                "        }\n" +
                "    ],\n" +
                "    \"accounts\": [\n" +
                "        {\n" +
                "            \"bankName\": \"ICICI Bank555\",\n" +
                "            \"branchName\": \"Dhanori\",\n" +
                "            \"ifscCode\": \"ICICI548264\",\n" +
                "            \"accountNo\": \"45894654622456\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"bankName\": \"HDFC Bank7887\",\n" +
                "            \"branchName\": \"Lohegaon\",\n" +
                "            \"ifscCode\": \"HDFC89468\",\n" +
                "            \"accountNo\": \"8979794535134\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"bankName\": \"AXIS Bank675\",\n" +
                "            \"branchName\": \"Porwal Road\",\n" +
                "            \"ifscCode\": \"AXIS55466\",\n" +
                "            \"accountNo\": \"887941651616\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"bankName\": \"Canara Bankw2w2\",\n" +
                "            \"branchName\": \"Vimannagar\",\n" +
                "            \"ifscCode\": \"CANARA55784\",\n" +
                "            \"accountNo\": \"46816516516\"\n" +
                "        }\n" +
                "    ],\n" +
                "    \"aadhaarCardDetails\": {\n" +
                "        \"aadhaarNumber\": \"654646565464\",\n" +
                "        \"name\": \"Amsidh Babasha Lokhande\",\n" +
                "        \"address\": {\n" +
                "            \"city\": \"Shirnal\",\n" +
                "            \"taluk\": \"Bijapur\",\n" +
                "            \"dist\": \"Bijapur\",\n" +
                "            \"pincode\": 586119\n" +
                "        },\n" +
                "        \"dateOfBirth\": \"05/05/1983\"\n" +
                "    }\n" +
                "}";
    }

}

