package com.uno.demo.model.Card;

import com.uno.demo.constants.CardType;
import com.uno.demo.constants.Color;
import lombok.Data;
import lombok.EqualsAndHashCode;

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

    @Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final Card card = (Card) obj;

        if (card.getCardType().equals(this.getCardValue()) &&
                card.getCardValue().equals(this.getCardValue()) &&
                card.getCardColor().equals(this.getCardColor())){
            return true;
        }
        return false;
    }

}
