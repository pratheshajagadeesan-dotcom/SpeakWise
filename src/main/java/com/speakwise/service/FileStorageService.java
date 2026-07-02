package com.speakwise.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileStorageService {

    private final String uploadDir = "uploads/audio/";

    public String uploadAudio(MultipartFile file) throws IOException {

        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String originalFilename = file.getOriginalFilename();

        if (originalFilename == null ||
                (!originalFilename.endsWith(".mp3")
                        && !originalFilename.endsWith(".wav"))) {

            throw new IllegalArgumentException(
                    "Only MP3 and WAV files are allowed.");
        }

        if (file.getSize() > 10 * 1024 * 1024) {
            throw new IllegalArgumentException(
                    "File size cannot exceed 10 MB.");
        }

        String fileName = UUID.randomUUID() + "_" + originalFilename;

        Path filePath = uploadPath.resolve(fileName);

        Files.copy(file.getInputStream(),
                filePath,
                StandardCopyOption.REPLACE_EXISTING);

        return filePath.toString();

    }

}