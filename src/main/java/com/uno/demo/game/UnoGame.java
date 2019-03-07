package com.uno.demo.game;

import com.uno.demo.constants.CardType;
import com.uno.demo.constants.Constants;
import com.uno.demo.exception.EmptyDeckException;
import com.uno.demo.exception.InvalidAgeException;
import com.uno.demo.model.Card.Card;
import com.uno.demo.model.player.Player;
import com.uno.demo.service.ICardService;
import com.uno.demo.service.IDealerService;
import com.uno.demo.service.IPlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

@Service
public class UnoGame {

    @Autowired
    IPlayerService playerService;

    @Autowired
    IDealerService dealerService;

    @Autowired
    ICardService cardService;

    List<Player> players = new LinkedList<>();
    Stack<Card> cardStack;

    Boolean isGameOver = Boolean.FALSE;

    @PostConstruct
    public void init() throws InvalidAgeException {

        for (int i = 1; i <= Constants.NO_OF_PLAYERS ; i ++){
            players.add(playerService.createPlayer("Player" + i , 18));
        }

        System.out.println("Players added sucessfully.." + players.size());
        cardStack = dealerService.shuffle();
        dealerService.distributeCards(players);

        /**
         * Player 1 starting game
         */
        players.stream().findFirst().get().setMyTurn(Boolean.TRUE);
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Card getCard() throws EmptyDeckException {
        return dealerService.getTopCard();
    }

    public void drawCard(Card topCard)  throws EmptyDeckException{
        boolean canPlay = false;

        for (Player p : players) {
            if (p.isMyTurn()) {
                Card newCard = getCard();
                p.addCard(newCard);
                canPlay = canPlay(topCard, newCard);
                break;
            }
        }

        if (!canPlay)
            switchTurn();
    }

    public void switchTurn() {
        for (Player p : players) {
            p.toggleTurn();
        }
        whoseTurn();
    }

    public void whoseTurn() {

        for (Player p : players) {
            if (p.isMyTurn()){
                System.out.println(p.getName() + "'s Turn");

            }
        }
    }

    public void removePlayedCard(Card playedCard) throws EmptyDeckException{
        for (Player p : players) {
            if (p.hasCard(playedCard)){
                p.removeCard(playedCard);

                if (p.getRemainingCards() == 1 && !p.isSaidUNO()) {
                    System.out.println(p.getName() + " Forgot to say UNO");
                    p.addCard(getCard());
                    p.addCard(getCard());
                } else if(p.getRemainingCards()>2){
                    p.setSaidUNOFalse();
                }
            }
        }
    }

    /**
     *  Check if this card can be played
     */
    private boolean canPlay(Card topCard, Card newCard) {
        if (topCard.getCardColor().equals(newCard.getCardColor())
                || topCard.getCardValue().equals(newCard.getCardValue()))
            return true;

        else if (topCard.getCardType() == CardType.WILD)
            return topCard.getCardColor().equals(newCard.getCardColor());

        else if (newCard.getCardType() == CardType.WILD)
            return true;

        return false;
    }

    public void drawPlus(int times) throws EmptyDeckException{
        for (Player p : players) {
            if (!p.isMyTurn()) {
                for (int i = 1; i <= times; i++)
                    p.addCard(getCard());
            }
        }
    }

    /**
     * check if game is over or not
     * @return
     */
    public boolean isOver() {

        if(cardStack.isEmpty()){
            System.out.println("GAME OVER!! Card stack empty ");
            return isGameOver= true;

        }

        for (Player p : players) {
            if (!p.hasCards()) {
                isGameOver = true;
                System.out.println("GAME OVER!! Player " + p + " finished all his cards");

                break;
            }
        }

        return isGameOver;
    }

    /**
     * Check whether the player said or forgot to say UNO
     */
    public void checkUNO() throws EmptyDeckException {
        for (Player p : players) {
            if (p.isMyTurn()) {
                if (p.getRemainingCards() == 1 && !p.isSaidUNO()) {
                    System.out.println(p.getName() + " Forgot to say UNO");
                    p.addCard(getCard());
                    p.addCard(getCard());
                }
            }
        }
    }
}
