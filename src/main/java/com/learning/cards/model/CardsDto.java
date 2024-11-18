package com.learning.cards.model;

import lombok.*;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CardsDto {

    private String mobileNumber;
    private String cardNumber; // generate random number
    private String cardType;
    private int totalLimit;
    private int amountUsed;
    private int availableAmount;
}
