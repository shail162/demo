package com.uno.demo.model.Card;

import com.uno.demo.constants.CardType;
import com.uno.demo.constants.Color;
import lombok.Data;

@Data
public class WildCard extends Card {

    public WildCard(Color color, CardType cardType, String cardValue){
        super(color, cardType, cardValue);
    }
}