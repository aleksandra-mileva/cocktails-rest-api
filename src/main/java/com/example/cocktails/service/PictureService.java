package com.example.cocktails.service;

import com.example.cocktails.model.cloudinary.CloudinaryImage;
import com.example.cocktails.model.entity.PictureEntity;
import com.example.cocktails.model.mapper.PictureMapper;
import com.example.cocktails.repository.CocktailRepository;
import com.example.cocktails.repository.PictureRepository;
import com.example.cocktails.repository.UserRepository;
import com.example.cocktails.web.exception.InvalidFileException;
import com.example.cocktails.web.exception.ObjectNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class PictureService {

  private final PictureRepository pictureRepository;
  private final UserRepository userRepository;
  private final CocktailRepository cocktailRepository;
  private final CloudinaryService cloudinaryService;
  private final PictureMapper pictureMapper;

  public PictureService(
      PictureRepository pictureRepository,
      UserRepository userRepository,
      CocktailRepository cocktailRepository,
      CloudinaryService cloudinaryService,
      PictureMapper pictureMapper
  ) {
    this.pictureRepository = pictureRepository;
    this.userRepository = userRepository;
    this.cocktailRepository = cocktailRepository;
    this.cloudinaryService = cloudinaryService;
    this.pictureMapper = pictureMapper;
  }

  public PictureEntity createAndSavePictureEntity(Long userId, MultipartFile file) {

    try {
      final CloudinaryImage upload = cloudinaryService.uploadImage(file);
      PictureEntity newPicture = new PictureEntity().setAuthor(userRepository.findById(userId)
              .orElseThrow(() -> new ObjectNotFoundException("User with id " + userId + " not found!")))
          .setUrl(upload.url())
          .setPublicId(upload.publicId())
          .setTitle(file.getOriginalFilename());
      return pictureRepository.save(newPicture);
    } catch (RuntimeException | IOException e) {
      throw new InvalidFileException(
          "File with name " + file.getOriginalFilename() + " can not be uploaded.");
    }
  }
}




