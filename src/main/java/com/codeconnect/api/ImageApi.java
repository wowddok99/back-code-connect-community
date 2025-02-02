package com.codeconnect.api;

import com.codeconnect.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
public class ImageApi {
    private final ImageService imageService;

    @PostMapping("/api/images")
    public ResponseEntity<String[]> uploadImages(@RequestParam("files") MultipartFile[] files) throws IOException {
        String[] filePaths = imageService.save(files);

        return ResponseEntity.ok(filePaths);
    }
}
