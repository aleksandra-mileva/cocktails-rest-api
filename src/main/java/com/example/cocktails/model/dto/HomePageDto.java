package com.example.cocktails.model.dto;

import com.example.cocktails.model.dto.picture.PictureHomePageViewModel;

import java.util.List;

public record HomePageDto(
    List<PictureHomePageViewModel> pictures,
    String message
) {
}
