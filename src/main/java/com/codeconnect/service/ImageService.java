package com.codeconnect.service;

import com.codeconnect.entity.Image;
import com.codeconnect.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ImageService {
    private final ImageRepository imageRepository;

    public String[] save(MultipartFile[] images) throws IOException {
        String[] filePaths = new String[images.length];
        String extension = "";
        for (int i = 0; i < images.length; i++) {
            MultipartFile image = images[i];
            // 파일 이름 추출
            String fileName = image.getOriginalFilename();

            if (fileName != null && fileName.lastIndexOf('.') > 0) {
                // 파일 확장자 추출
                extension = fileName.substring(fileName.lastIndexOf('.'));
            }

            String fullPathName = "C:\\Users\\jse\\Desktop\\dev\\workspace-springboot\\codeconvo-project\\uploads\\images\\"
                    + UUID.randomUUID().toString() + extension;
            
            // 서버에 파일 저장
            image.transferTo(new File(fullPathName));

            Image imageEntity = Image.builder()
                    .filePath(fullPathName)
                    .build();
            
            filePaths[i] = fullPathName;
            // DB에 이미지 경로 저장
            imageRepository.save(imageEntity);
        }

        return filePaths;
    }
}
