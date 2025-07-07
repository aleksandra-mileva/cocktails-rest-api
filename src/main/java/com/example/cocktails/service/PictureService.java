package com.example.cocktails.service;

import com.example.cocktails.model.cloudinary.CloudinaryImage;
import com.example.cocktails.model.dto.picture.PictureHomePageViewModel;
import com.example.cocktails.model.entity.PictureEntity;
import com.example.cocktails.model.entity.enums.TypeNameEnum;
import com.example.cocktails.model.mapper.PictureMapper;
import com.example.cocktails.repository.CocktailRepository;
import com.example.cocktails.repository.PictureRepository;
import com.example.cocktails.repository.UserRepository;
import com.example.cocktails.web.exception.InvalidFileException;
import com.example.cocktails.web.exception.ObjectNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

  //
//  public Page<PictureViewModel> findAllPictureViewModelsByUsername(String principalName,
//      Pageable pageable) {
//    Page<PictureEntity> pictures = this.pictureRepository
//        .findAllByAuthor_Username(principalName, pageable);
//
//    return pictures.map(picture -> this.mapToPictureViewModel(picture, principalName));
//  }
//
  public PictureEntity createAndSavePictureEntity(Long userId, MultipartFile file,
      Long cocktailId) {

    try {
      final CloudinaryImage upload = cloudinaryService
          .uploadImage(file);
      PictureEntity newPicture = new PictureEntity()
          .setAuthor(userRepository.findById(userId)
              .orElseThrow(
                  () -> new ObjectNotFoundException("User with id " + userId + " not found!")))
          .setUrl(upload.url())
          .setPublicId(upload.publicId())
          .setTitle(file.getOriginalFilename())
          .setCocktail(cocktailRepository.findById(cocktailId).orElse(null));

      return pictureRepository.save(newPicture);
    } catch (RuntimeException | IOException e) {
      throw new InvalidFileException(
          "File with name " + file.getOriginalFilename() + " can not be uploaded.");
    }
  }

  public PictureHomePageViewModel mapToPictureHomePageViewModel(PictureEntity picture) {
    PictureHomePageViewModel pictureHomePageViewModel = pictureMapper.pictureEntityToPictureHomePageViewModel(picture);

    pictureHomePageViewModel.setAuthorFullName(picture.getAuthor().getFirstName()
        + " " + picture.getAuthor().getLastName());

    return pictureHomePageViewModel;
  }

  public List<PictureHomePageViewModel> getThreeRandomPicturesByCocktailType(TypeNameEnum typeNameEnum) {
    List<PictureEntity> allPictures = pictureRepository.findAllByCocktailType(typeNameEnum);

    List<PictureHomePageViewModel> resultPictures = new ArrayList<>();
    Set<Long> selectedCocktailIds = new HashSet<>();

    Collections.shuffle(allPictures);

    for (PictureEntity picture : allPictures) {
      if (!selectedCocktailIds.contains(picture.getCocktail().getId())) {
        resultPictures.add(this.mapToPictureHomePageViewModel(picture));
        selectedCocktailIds.add(picture.getCocktail().getId());
      }

      if (resultPictures.size() == 3) {
        break;
      }
    }
    return resultPictures;
  }
}




