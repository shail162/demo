package com.uno.demo.service;

import com.uno.demo.model.Card.Card;
import com.uno.demo.model.player.Player;

import java.util.List;
import java.util.Stack;

public interface IDealerService {

    void distributeCards(List<Player> playerList);
    Stack<Card> shuffle();
    Card getTopCard();
}
