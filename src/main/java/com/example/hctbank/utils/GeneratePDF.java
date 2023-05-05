package com.example.hctbank.utils;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.io.source.ByteArrayOutputStream;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class GeneratePDF {
    public static void generatePdfFromHtml(String html) {
        String outputFolder = System.getProperty("user.home") + File.separator + "thymeleaf.pdf";

        ByteArrayOutputStream target = new ByteArrayOutputStream();

        ConverterProperties converterProperties = new ConverterProperties();
        converterProperties.setBaseUri("http://localhost:8080");
        OutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream("string-output.pdf");
        } catch (FileNotFoundException e) {
            System.out.println("pdf not found");
            throw new RuntimeException(e);
        }
        HtmlConverter.convertToPdf(html, fileOutputStream);
        HtmlConverter.convertToPdf(html, target, converterProperties);

    }


}
