package com.uno.demo.game;

import com.uno.demo.constants.ActionType;
import com.uno.demo.constants.CardType;
import com.uno.demo.constants.Color;
import com.uno.demo.constants.WildType;
import com.uno.demo.exception.EmptyDeckException;
import com.uno.demo.exception.InvalidMoveException;
import com.uno.demo.model.Card.Card;
import com.uno.demo.model.player.Player;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Stack;

@Service
@Data
public class Server {

    @Autowired
    UnoGame game;

    private Stack<Card> playedCards;
    public boolean canPlay;

    @PostConstruct
    public void init() throws EmptyDeckException {

        System.out.println("Server initialized..");

        playedCards = new Stack<Card>();

        // First Card
        Card firstCard = game.getCard();

        playedCards.add(firstCard);
        game.whoseTurn();
        canPlay = true;
    }

    /**
     *
     * @param choseCard
     */
    public void playThisCard(Card choseCard) throws InvalidMoveException,  EmptyDeckException{

        if (!isHisTurn(choseCard)) {
            System.out.println("It's not your turn");
        } else {
            if (isValidMove(choseCard)) {

                playedCards.add(choseCard);
                game.removePlayedCard(choseCard);

                switch (choseCard.getCardType()) {
                    case ACTION:
                        performAction(choseCard);
                        break;
                    case WILD:
                        performWild(choseCard);
                        break;
                    default:
                        break;
                }

                game.switchTurn();
                checkResult();
            } else {
                throw new InvalidMoveException("invalid move");
            }
        }
    }

    //check if it is a valid card
    public boolean isValidMove(Card playedCard) {
        Card topCard = playedCards.peek();

        if (playedCard.getCardColor().equals(topCard.getCardColor())
                || playedCard.getCardValue().equals(topCard.getCardValue())) {
            return true;
        } else if (playedCard.getCardType() == CardType.WILD) {
            return true;
        } else if (topCard.getCardType() == CardType.WILD) {
            Color color = topCard.getCardColor();
            if (color.equals(playedCard.getCardColor()))
                return true;
        }
        return false;
    }

    private void performAction(Card actionCard) throws EmptyDeckException {

        if (actionCard.getCardValue().equals(ActionType.DRAW2PLUS.toString()))
            game.drawPlus(2);
        else if (actionCard.getCardValue().equals(ActionType.REVERSE.toString()))
            game.switchTurn();
        else if (actionCard.getCardValue().equals(ActionType.SKIP.toString()))
            game.switchTurn();
    }

    private void performWild(Card wildCard)  throws EmptyDeckException{
        if (wildCard.getCardValue().equals(WildType.DRAW4PLUS.toString()))
            game.drawPlus(4);
    }

    public void requestCard() throws EmptyDeckException {
        game.drawCard(playedCards.peek());
    }

    public boolean isHisTurn(Card clickedCard) {
        for (Player p : game.getPlayers()) {
            if (p.hasCard(clickedCard) && p.isMyTurn())
                return true;
        }
        return false;
    }

    private void checkResult() {
        if (game.isOver()) {
            canPlay = false;
            System.out.println("GAME OVER");
        }
    }
}
