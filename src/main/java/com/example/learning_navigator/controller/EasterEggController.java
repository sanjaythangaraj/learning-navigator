package com.example.learning_navigator.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

@RestController
@RequestMapping("/hidden-feature")
public class EasterEggController {

  RestClient restClient = RestClient.create();

  @GetMapping("/{number}")
  String fetchNumberFact(@PathVariable Long number) {
    return restClient.get().uri("http://numbersapi.com/" + number).retrieve().body(String.class);
  }
}
