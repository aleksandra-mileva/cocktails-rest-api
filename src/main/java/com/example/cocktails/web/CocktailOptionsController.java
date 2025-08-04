package com.example.cocktails.web;

import com.example.cocktails.model.dto.cocktail.CocktailFormOptionsDto;
import com.example.cocktails.model.entity.enums.FlavourEnum;
import com.example.cocktails.model.entity.enums.SpiritNameEnum;
import com.example.cocktails.model.entity.enums.TypeNameEnum;
import com.example.cocktails.util.EnumUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(
    origins = "http://localhost:4200",
    allowCredentials = "true"
)
@RestController
@RequestMapping("/api/cocktails/options")
public class CocktailOptionsController {

  @GetMapping
  public CocktailFormOptionsDto getCocktailFormOptions() {
    return new CocktailFormOptionsDto(
        EnumUtils.toEnumOptions(FlavourEnum.class),
        EnumUtils.toEnumOptions(SpiritNameEnum.class),
        EnumUtils.toEnumOptions(TypeNameEnum.class)
    );
  }
}
