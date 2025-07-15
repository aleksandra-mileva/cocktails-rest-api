package com.example.cocktails.web;

import com.example.cocktails.model.dto.fortune.AddFortuneDto;
import com.example.cocktails.model.dto.fortune.FortunelViewModel;
import com.example.cocktails.service.FortuneService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/fortunes")
public class FortuneController {

  private final FortuneService fortuneService;

  public FortuneController(FortuneService fortuneService) {
    this.fortuneService = fortuneService;
  }

  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  @GetMapping
  public List<FortunelViewModel> getAllFortunes() {
    return fortuneService.getAllFortunes();
  }

  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  @PostMapping
  public void addFortune(@Valid @RequestBody AddFortuneDto addFortuneDto) {
    fortuneService.addFortune(addFortuneDto);
  }

  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  @DeleteMapping("/{id}")
  public void deleteFortune(@PathVariable Long id) {
    fortuneService.deleteFortuneById(id);
  }

  @GetMapping("/fortune")
  public FortunelViewModel getFortuneBy() {
    return fortuneService.getRandomFortune();
  }
}
