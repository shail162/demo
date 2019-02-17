package com.uno.demo.model.Card;

import com.uno.demo.constants.CardType;
import com.uno.demo.constants.Color;
import lombok.Data;

@Data
public class ActionCard extends Card {

    public ActionCard(Color color, CardType cardType, String cardValue){
        super(color, cardType, cardValue);
    }
}
