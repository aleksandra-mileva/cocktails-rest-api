package com.example.cocktails.service;

import com.example.cocktails.model.dto.fortune.AddFortuneDto;
import com.example.cocktails.model.dto.fortune.FortunelViewModel;
import com.example.cocktails.web.exception.ObjectNotFoundException;
import jakarta.validation.Valid;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;

@Service
public class FortuneService {

  private final WebClient webClient;

  public FortuneService(WebClient webClient) {
    this.webClient = webClient;
  }

  public List<FortunelViewModel> getAllFortunes() {
    return webClient.get()
        .uri("/fortunes")
        .retrieve()
        .bodyToFlux(new ParameterizedTypeReference<FortunelViewModel>() {
        })
        .collectList()
        .block();
  }

  public void deleteFortuneById(Long fortuneId) {
    try {
      webClient.delete()
          .uri("/fortunes/{id}", fortuneId)
          .retrieve()
          .bodyToMono(Void.class)
          .block();
    } catch (WebClientResponseException.NotFound e) {
      throw new ObjectNotFoundException("fortune", "Fortune with id=" + fortuneId + " does not exist.");
    } catch (WebClientResponseException e) {
      throw new RuntimeException("Failed to delete fortune: " + e.getResponseBodyAsString(), e);
    }
  }

  public void addFortune(@Valid AddFortuneDto addFortuneDto) {
    webClient.post()
        .uri("/fortunes")
        .bodyValue(addFortuneDto)
        .retrieve()
        .bodyToMono(Void.class)
        .block();
  }

  public FortunelViewModel getRandomFortune() {
    return webClient.get()
        .uri("/fortunes/random")
        .retrieve()
        .bodyToMono(FortunelViewModel.class)
        .block();
  }
}
