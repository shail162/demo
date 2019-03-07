package com.uno.demo.service.impl;

import com.uno.demo.constants.*;
import com.uno.demo.model.Card.ActionCard;
import com.uno.demo.model.Card.Card;
import com.uno.demo.model.Card.NumberCard;
import com.uno.demo.model.Card.WildCard;
import com.uno.demo.service.ICardService;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class CardServiceImpl implements ICardService {

    private final LinkedList<NumberCard> numberCards;
    private final LinkedList<ActionCard> actionCards;
    private final LinkedList<WildCard> wildCards;

    private LinkedList<Card> UNOcards;

    public CardServiceImpl(){

        numberCards = new LinkedList<NumberCard>();
        actionCards = new LinkedList<ActionCard>();
        wildCards = new LinkedList<WildCard>();

        UNOcards = new LinkedList<Card>();

        addCards();

    }

    /**
     * Initialize cards
     *
     * Total  Cards  108
     * Number Cards   76  [0-9]
     * Action Cards   24
     * Wild Cards      8
     */
    private void addCards() {
        for (Color color : Color.values()) {
            for (int i = 1; i <= 9; i++) {
                int n =0;
                do {
                    Card card = new NumberCard(color, CardType.NUMBERS, String.valueOf(i));
                    UNOcards.add(card);
                    n++;
                } while (n<2);
            }
            addActionCards(color);
        }
        /**
         * Add 4 zeros to make total number cards to make 76
         */
        for (Color color : Color.values()) {
            Card card = new NumberCard(color, CardType.NUMBERS, String.valueOf(0));
            UNOcards.add(card);
        }

        addWildCards();

        System.out.println("Cards inialized.."  + UNOcards.size());
    }

    /**
     * Create 24 Action Cards
     */
    private void addActionCards(Color color){
        for (ActionType actionType : ActionType.values()){
            for(int i=0; i<2; i++)
                UNOcards.add(new ActionCard(color, CardType.ACTION, String.valueOf(i)));
        }
    }

    /**
     * Add 8 wild cards
     */
    private void addWildCards(){
        for(WildType wildType : WildType.values()){
            for(int i=0;i<4;i++){
                UNOcards.add(new WildCard(Color.GREEN, CardType.WILD, String.valueOf(i)));
            }
        }
    }

    @Override
    public List<Card> getCards(){
        return UNOcards;
    }
}
