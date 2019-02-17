package com.uno.demo;

import com.uno.demo.constants.CardType;
import com.uno.demo.constants.Color;
import com.uno.demo.exception.InvalidAgeException;
import com.uno.demo.model.Card.Card;
import com.uno.demo.model.Card.NumberCard;
import com.uno.demo.model.player.Player;
import com.uno.demo.service.IPlayerService;
import com.uno.demo.service.impl.PlayerServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PlayerServiceTest {

    @InjectMocks
    IPlayerService playerService = new PlayerServiceImpl();


    @Test
    public void createPlayer() throws InvalidAgeException {
        Player expected = new Player("shailesh", 33);
        Player actual = playerService.createPlayer("shailesh", 33);

        Assert.assertEquals(expected, actual);
    }

    @Test(expected = InvalidAgeException.class)
    public void createPlayerWithInvalidAge() throws InvalidAgeException {
        playerService.createPlayer("shailesh", 6);
    }

    @Test
    public void move(){
        Card topCard = new NumberCard(Color.GREEN, CardType.NUMBERS, "1");
        Player player = new Player("shailesh", 33);
        player.addCard(new NumberCard(Color.GREEN, CardType.NUMBERS, "1"));

        boolean actual = playerService.move(topCard, player);

        Assert.assertTrue(actual);
    }


    @Test
    public void moveNotMatch(){
        Card topCard = new NumberCard(Color.GREEN, CardType.NUMBERS, "1");
        Player player = new Player("shailesh", 33);
        player.addCard(new NumberCard(Color.YELLOW, CardType.NUMBERS, "5"));

        boolean actual = playerService.move(topCard, player);

        Assert.assertFalse(actual);
    }
}

