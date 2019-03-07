package com.uno.demo.service.impl;

import com.uno.demo.constants.Constants;
import com.uno.demo.exception.EmptyDeckException;
import com.uno.demo.model.Card.Card;
import com.uno.demo.model.player.Player;
import com.uno.demo.service.ICardService;
import com.uno.demo.service.IDealerService;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

@Service
public class DealerServiceImpl implements IDealerService {

    private Stack<Card> cardStack;
    private ICardService cardService;

    public DealerServiceImpl(ICardService cardService){
        this.cardService = cardService;
    }

    @Override
    public Stack<Card> shuffle(){
        List<Card> deckOfCards = cardService.getCards();
        List<Card> shuffledCards = new LinkedList<Card>();

        while(!deckOfCards.isEmpty()){
            int totalCards = deckOfCards.size();

            Random random = new Random();
            int position = random.nextInt(totalCards);

            Card randomCard = deckOfCards.get(position);
            deckOfCards.remove(position);
            shuffledCards.add(randomCard);
        }

        cardStack = new Stack<Card>();
        for(Card card : shuffledCards){
            cardStack.add(card);
        }

        return cardStack;
    }

    @Override
    public void distributeCards(List<Player> playerList){
        for (int i = 0 ; i < Constants.CARDS_PER_PLAYER; i ++){
            playerList.forEach(player -> {
               player.addCard(cardStack.pop());
            });
        }

        System.out.println("Cards distributed by dealer");
    }

    public Card getTopCard() throws EmptyDeckException {
        if (cardStack.isEmpty()){
            throw new EmptyDeckException(" Card Stack Over");
        }
        return cardStack.pop();
    }
}
