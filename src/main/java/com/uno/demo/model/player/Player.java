package com.uno.demo.model.player;

import com.uno.demo.model.Card.Card;
import lombok.Data;

import java.util.LinkedList;
import java.util.List;

@Data
public class Player extends AbstractPlayer {

    private List<Card> myCards = new LinkedList<>();
    private int remainingCards;
    private boolean saidUNO;
    private boolean isMyTurn;

    public Player(String name, int age) {
        super(name, age);
    }

    public boolean hasCards(){
        return (myCards.isEmpty()) ? false : true;
    }

    public void addCard(Card card){
        myCards.add(card);
        remainingCards++;
    }

    public boolean hasCard(Card thisCard){
        return myCards.contains(thisCard);
    }

    public void removeCard(Card thisCard){
        myCards.remove(thisCard);
        remainingCards--;
        System.out.println("remainig cards for Player " + getName() + " is " + remainingCards);
    }

    public void saysUNO(){
        saidUNO = true;
    }

    public void setSaidUNOFalse(){
        saidUNO = false;
    }

    public int getRemainingCards(){
        return remainingCards;
    }

    public void toggleTurn(){
        isMyTurn = (isMyTurn) ? false : true;
    }

    public String toString(){
        return super.getName();
    }

}
