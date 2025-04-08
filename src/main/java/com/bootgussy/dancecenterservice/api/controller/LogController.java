package com.bootgussy.dancecenterservice.api.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.GZIPInputStream;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/logs")
public class LogController {
    @GetMapping("/download")
    public byte[] downloadLogFile(@RequestParam String date) throws IOException {
        String logFilePath = "logs/app.log." + date + ".0.gz"; // Путь к сжатому файлу
        Path path = Paths.get(logFilePath);

        if (!Files.exists(path)) {
            throw new IOException("File not found: " + logFilePath);
        }

        try (InputStream fileInputStream = Files.newInputStream(path);
             GZIPInputStream gzipInputStream = new GZIPInputStream(fileInputStream);
             ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {

            byte[] buffer = new byte[1024];
            int len;
            while ((len = gzipInputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, len);
            }

            return byteArrayOutputStream.toByteArray();
        }
    }
}