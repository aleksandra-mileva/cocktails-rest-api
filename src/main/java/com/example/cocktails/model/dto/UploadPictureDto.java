package com.example.cocktails.model.dto;

import org.springframework.web.multipart.MultipartFile;

public record UploadPictureDto(
    Long cocktailId,
    MultipartFile picture
) {
}


