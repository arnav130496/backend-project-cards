package com.learning.cards.controller;

import com.learning.cards.model.CardsDto;
import com.learning.cards.service.CardsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cards")
@Slf4j
public class CardsController {

    @Autowired
    CardsService cardsService;

    @PostMapping
    public ResponseEntity<String> createNewCard(@RequestParam String mobileNumber){
        log.info("Create new card Controller layer {}", mobileNumber);
        cardsService.createNewCard(mobileNumber);
        return new ResponseEntity<>("Success", HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<CardsDto> getCard(@RequestParam String mobileNumber){
        log.info("Fetch card Controller layer {}", mobileNumber);
        return new ResponseEntity<>(cardsService.getCardInfo(mobileNumber), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<CardsDto> updateCard(@RequestBody CardsDto cardsDto){
        log.info("Update card controller layer");
        return new ResponseEntity<>(cardsService.updateCardDetails(cardsDto), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Boolean> deleteCard(@RequestParam String mobileNumber){
        log.info("Delete Card Controller Layer, mobile number {}", mobileNumber);
        return new ResponseEntity<>( cardsService.deleteCardInfo(mobileNumber), HttpStatus.OK);
    }

}
