package com.jobtrack.jobtrack.service;

import java.io.IOException;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PdfService {

    public String extractText(MultipartFile file) {

        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Resume PDF cannot be empty.");
        }

        long maxFileSize = 5 * 1024 * 1024;

        if (file.getSize() > maxFileSize) {
            throw new IllegalArgumentException(
                    "Resume PDF must be smaller than 5 MB."
            );
        }

        if (!"application/pdf".equalsIgnoreCase(file.getContentType())) {
            throw new IllegalArgumentException("Only PDF files are allowed.");
        }

        try {
            byte[] pdfBytes = file.getBytes();

            try (PDDocument document = Loader.loadPDF(pdfBytes)) {

                PDFTextStripper stripper = new PDFTextStripper();

                String text = stripper.getText(document);

                if (text == null || text.isBlank()) {
                    throw new IllegalArgumentException(
                            "Could not extract text from the resume PDF."
                    );
                }

                return text;
            }

        } catch (IOException e) {

            throw new IllegalStateException(
                    "Failed to read the resume PDF.",
                    e
            );
        }
    }
}