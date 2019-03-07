package com.uno.demo;

import com.uno.demo.exception.EmptyDeckException;
import com.uno.demo.exception.InvalidMoveException;
import com.uno.demo.game.Server;
import com.uno.demo.model.Card.Card;
import com.uno.demo.model.player.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

import java.util.List;

@SpringBootApplication
@EnableRetry
public class UnoApplication implements CommandLineRunner {

	@Autowired
	Server server;

	public static void main(String[] args) {
		SpringApplication.run(UnoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		List<Player> playerList = server.getGame().getPlayers();
		System.out.println("Number of players : "  + server.getGame().getPlayers());
		System.out.println("top card " + server.getPlayedCards().peek().toString());

		/**
		 * round-robin turn for each player
		 */
		while (!server.getGame().isOver()){
			for (Player player : playerList){
				System.out.println( "Player " + player + " remaining cards " + player.getMyCards().size());
				try {
					play(player);
				}catch(EmptyDeckException e) {
					System.out.println("GAME OVER.. Card deck Empty!!");
				}
			}
		}
	}


	public void play(Player player)  throws EmptyDeckException {
		boolean validMoveMade = Boolean.FALSE;
		for (Card card : player.getMyCards()) {
			try {
				System.out.println("Playing " + card);
				server.playThisCard(card);
				System.out.println("Valid MOVE!!");
				validMoveMade = Boolean.TRUE;
				break;
			} catch (InvalidMoveException e){
				System.out.println("Invalid move");
			}
		}

		if (!validMoveMade) {
			server.getGame().drawCard(server.getPlayedCards().peek());
		}
	}
}

