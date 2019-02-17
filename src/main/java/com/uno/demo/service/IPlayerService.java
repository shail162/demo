package com.uno.demo.service;

import com.uno.demo.exception.InvalidAgeException;
import com.uno.demo.model.Card.Card;
import com.uno.demo.model.player.Player;

import java.util.List;

public interface IPlayerService {

    /**
     * Create Player
     * @param name
     * @param age
     * @return
     */
    Player createPlayer(String name, int age) throws InvalidAgeException;

    /**
     *
     * @return
     */
    List<Card> getCards(Player player);

    /**
     *
     * @param topCard
     * @param player
     * @return
     */
    boolean move(Card topCard, Player player);




}
