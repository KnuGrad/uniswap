package com.knu.uniswap.common.utils;

import com.knu.uniswap.common.exception.DirectoryCreateException;
import com.knu.uniswap.common.exception.ImageUploadException;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class ImageUploader {

    @Value("${file.path}")
    private String baseFilePath;

    public List<String> uploadImages(List<MultipartFile> images) {
        List<String> imageUrls = new ArrayList<>();

        for (MultipartFile image : images) {
            if (!image.isEmpty()) {
                imageUrls.add(uploadImage(image));
            }
        }

        return imageUrls;
    }

    public String uploadImage(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        String filename = UUID.randomUUID() + "_" + originalFilename;
        String filePath = getFullFilePath(filename);

        Path storeDirectoryPath = getStoreDirectoryPath();

        try {
            if (!Files.exists(storeDirectoryPath)) {
                Files.createDirectories(storeDirectoryPath);
            }
        } catch (Exception e) {
            throw new DirectoryCreateException("파일을 저장할 디렉토리 생성에 실패했습니다.");
        }

        try {
            file.transferTo(new File(filePath));
        } catch (Exception e) {
            throw new ImageUploadException("파일 저장에 실패했습니다.");
        }

        return filePath;
    }

    private String getFullFilePath(String filename) {
        return Paths.get(getStoreDirectoryPath().toString(), filename).toString();
    }

    private Path getStoreDirectoryPath() {
        String year = String.valueOf(LocalDateTime.now().getYear());
        String month = formatAsTwoDigits(LocalDateTime.now().getMonthValue());
        String day = formatAsTwoDigits(LocalDateTime.now().getDayOfMonth());

        return Paths.get(baseFilePath, year, month, day);
    }

    /**
     * 파라미터가 1~9면 앞에 0을 붙여 두자리로 만들고, 스트링 타입으로 리턴
     * @param num
     * @return
     */
    private String formatAsTwoDigits(int num) {
        return num < 10 ? "0" + num : String.valueOf(num);
    }

}
