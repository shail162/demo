package com.uno.demo.model.Card;

import com.uno.demo.constants.CardType;
import com.uno.demo.constants.Color;
import lombok.Data;

@Data
public abstract class Card {

    private Color cardColor;
    private CardType cardType;
    private String cardValue;


    public Card(Color color, CardType cardType, String cardValue){
        this.cardColor = color;
        this.cardType = cardType;
        this.cardValue = cardValue;
    }
}
