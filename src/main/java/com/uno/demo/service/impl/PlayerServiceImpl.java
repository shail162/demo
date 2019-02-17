package com.uno.demo.service.impl;

import com.uno.demo.constants.CardType;
import com.uno.demo.constants.Constants;
import com.uno.demo.exception.InvalidAgeException;
import com.uno.demo.model.Card.Card;
import com.uno.demo.model.player.Player;
import com.uno.demo.service.IPlayerService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class PlayerServiceImpl implements IPlayerService{


    @Override
    public Player createPlayer(String name, int age) throws InvalidAgeException{

        if (age < Constants.MINIMUM_AGE){
            throw new InvalidAgeException("Sorry. Kids below " + age + " years of age are not allowed to play UNO.");
        }
        return new Player(name,age);
    }

    @Override
    public List<Card> getCards(Player player) {
        return player.getMyCards();
    }

    @Override
    public boolean move(Card topCard, Player player){

        boolean match = Boolean.FALSE;
        for (Card playerCard : player.getMyCards()) {
            if(playerCard.getCardColor().equals(topCard.getCardColor()) ||
                    playerCard.getCardValue().equals(topCard.getCardValue())){
                match = Boolean.TRUE;
            }
        }

        if(!match){
            for (Card playerCard : player.getMyCards()) {
                if (playerCard.getCardType().equals(CardType.WILD)){
                    match = Boolean.TRUE;
                }
            }
        }

        if (player.getRemainingCards() == 1) {
            boolean randomFlag = new Random().nextBoolean();
            if(randomFlag)
                player.saysUNO();
        }

        return match;
    }

}
