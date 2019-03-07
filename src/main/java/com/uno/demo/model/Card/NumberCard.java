package com.uno.demo.model.Card;

import com.uno.demo.constants.CardType;
import com.uno.demo.constants.Color;
import lombok.Data;

@Data
public class NumberCard extends Card {

    public NumberCard(Color color, CardType cardType, String cardValue){
        super(color, cardType, cardValue);
    }

    @Override
    public String toString(){
        return super.toString();
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final NumberCard card = (NumberCard) obj;

        if (card.getCardType().equals(this.getCardValue()) &&
                card.getCardValue().equals(this.getCardValue()) &&
                card.getCardColor().equals(this.getCardColor())){
            return true;
        }
        return false;
    }
}
