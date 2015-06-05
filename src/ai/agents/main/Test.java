package ai.agents.main;
import garrettsmith.blackjack.Blackjack;
import garrettsmith.blackjack.EventHandler;
import garrettsmith.blackjack.refimpl.TextPlayer;

import java.util.Arrays;

public class Test {

	public static void main(String[] args) {
		double wager = 2; // you're a high roller, right?
		EventHandler eventHandler = new TextPlayer();

		Blackjack blackjack = new Blackjack();
		blackjack.playGame(Arrays.asList(new EventHandler[]{eventHandler}));
	}
	
	
	
}
