package com.learning.cards.service;

import com.learning.cards.entity.Cards;
import com.learning.cards.model.CardsDto;
import com.learning.cards.repository.CardsRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class CardsService {

    private CardsRepository cardsRepository;

    public void createNewCard(String mobileNumber) {
        log.info("Creating Card for mobile number {} : ", mobileNumber);

        cardsRepository.findByMobileNumber(mobileNumber).ifPresentOrElse(
                cards -> {
                    log.error("Card already exists for mobile number : {}", mobileNumber);
                    throw new RuntimeException("Card already exists for mobile number : " + mobileNumber);
                },
                () -> {
                    log.info("Creating new card in DB for mobile number : {}", mobileNumber);
                    createNewCardInDb(mobileNumber);
                }
        );
    }

    private void createNewCardInDb(String mobileNumber) {
        log.info("Creating new card in DB for mobile number : {}", mobileNumber);
        Cards cards = new Cards();
        cards.setMobileNumber(mobileNumber);
        long randomNumber = 1000000L + (long) (Math.random()* 900000000);
        cards.setCardNumber(String.valueOf(randomNumber));
        cards.setCardType("CREDIT_CARD");
        cards.setTotalLimit(100000);
        cards.setAmountUsed(0);
        cards.setAvailableAmount(100000);
        cardsRepository.save(cards);
    }


    public CardsDto getCardInfo(String mobileNumber) {
        log.info("Fetch card for mobileNumber {} ", mobileNumber);
        Cards cards = getCardsFromDBFromMobileNumber(mobileNumber);
        CardsDto cardsDto =  new CardsDto();
        BeanUtils.copyProperties(cards, cardsDto);
        return cardsDto;
    }

    private Cards getCardsFromDBFromMobileNumber(String mobileNumber) {
        Cards cardsDb = cardsRepository.findByMobileNumber(mobileNumber).orElseThrow(() -> {
            log.error("No cards exist for mobile number {} ", mobileNumber);
            throw new RuntimeException("No Card exists for mobile number : " + mobileNumber);
        });
       return cardsDb;
    }


    public CardsDto updateCardDetails(CardsDto cardsDto) {
        log.info("Fetch card for card number {} ", cardsDto.getCardNumber());
        Cards cardInDb = cardsRepository.findByCardNumber(cardsDto.getCardNumber())
                .orElseThrow(() -> {
                    log.error("No cards exist for card number {} ", cardsDto.getCardNumber());
                    throw new RuntimeException("No Card exists for card number : " + cardsDto.getCardNumber());
                });

        BeanUtils.copyProperties(cardsDto, cardInDb);
        cardsRepository.save(cardInDb);
        return cardsDto;
    }


    public boolean deleteCardInfo(String mobileNumber) {
        Cards cards = getCardsFromDBFromMobileNumber(mobileNumber);
        cardsRepository.deleteById(cards.getCardId());
        return true;
    }
}
