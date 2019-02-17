package com.uno.demo;

import com.uno.demo.constants.Constants;
import com.uno.demo.service.ICardService;
import com.uno.demo.service.impl.CardServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Random;

@RunWith(MockitoJUnitRunner.class)
public class CardServiceTest {

    @InjectMocks
    ICardService cardService = new CardServiceImpl();


    @Test
    public void getCardsMatch() {
        int totalUnoCards = cardService.getCards().size();
        int expected = Constants.TOTAL_CARDS;
        Assert.assertTrue(totalUnoCards == expected);
    }


    @Test
    public void getCardsDoNotMatch() {
        int totalUnoCards = cardService.getCards().size();
        int expected = Constants.TOTAL_CARDS + new Random().nextInt(10);
        Assert.assertFalse(totalUnoCards == expected);
    }
}
