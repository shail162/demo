package com.uno.demo;


import com.uno.demo.constants.CardType;
import com.uno.demo.constants.Color;
import com.uno.demo.constants.Constants;
import com.uno.demo.exception.EmptyDeckException;
import com.uno.demo.model.Card.Card;
import com.uno.demo.model.Card.NumberCard;
import com.uno.demo.model.player.Player;
import com.uno.demo.service.ICardService;
import com.uno.demo.service.IDealerService;
import com.uno.demo.service.impl.DealerServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

@RunWith(MockitoJUnitRunner.class)
public class DealerServiceTest {

    @Mock
    private ICardService cardService;

    @Mock
    Stack<Card> cardStack;

    @InjectMocks
    IDealerService dealerService = new DealerServiceImpl(cardService);



    private int initialCards;
    private int initialPlayers;
    @Before
    public void setUp(){
        initialCards = 10;
        initialPlayers = 3;
    }

    @Test
    public void testShuffle() {
        List<Card> cardList = addCards();
        Mockito.when(cardService.getCards()).thenReturn(cardList);
        Stack<Card> shuffledCards = dealerService.shuffle();

        Assert.assertTrue(shuffledCards.size() == initialCards);
    }

    @Test
    public void testShuffleMisMatch(){
        List<Card> cardList = addCards();
        Mockito.when(cardService.getCards()).thenReturn(cardList);
        Stack<Card> shuffledCards = dealerService.shuffle();

        Assert.assertFalse(shuffledCards.size() == initialCards + new Random().nextInt(10));
    }


    @Test
    public void testDistributeCards(){
        List<Player> players =  getPlayerList();
        Mockito.when(cardStack.pop()).thenReturn(new NumberCard(Color.GREEN, CardType.NUMBERS, "1"));
        dealerService.distributeCards(players);

        players.forEach(player -> {
            Assert.assertTrue(player.getMyCards().size() == Constants.CARDS_PER_PLAYER);
        });
    }

    @Test
    public void testDistributeCardsFail(){
        List<Player> players =  getPlayerList();
        Mockito.when(cardStack.pop()).thenReturn(new NumberCard(Color.GREEN, CardType.NUMBERS, "1"));
        dealerService.distributeCards(players);

        players.forEach(player -> {
            Assert.assertFalse(player.getMyCards().size() == (Constants.CARDS_PER_PLAYER + new Random().nextInt(10)));
        });
    }

    @Test
    public void testGetTopCard() throws EmptyDeckException {
        Mockito.when(cardStack.pop()).thenReturn(new NumberCard(Color.GREEN, CardType.NUMBERS, "1"));
        Card numberCard = dealerService.getTopCard();
        Assert.assertTrue(numberCard.getCardColor().name() == Color.GREEN.toString());
        Assert.assertTrue(numberCard.getCardValue() == "1");
        Assert.assertTrue(numberCard.getCardType().toString() == CardType.NUMBERS.toString());
    }


    private List<Card> addCards() {
        List<Card> cardList = new LinkedList<>();
        for (int i = 0; i < initialCards; i++){
            cardList.add(new NumberCard(Color.GREEN, CardType.NUMBERS, String.valueOf(i)));
        }
        return cardList;
    }


    private List<Player> getPlayerList(){
        List<Player> players = new LinkedList<>();

        for (int i =0 ; i < initialPlayers ; i++){
            players.add(new Player("player" + i, 10 ));
        }

        return players;
    }

}
