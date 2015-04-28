package ai.agents.main;
import garrettsmith.blackjack.Blackjack;
import garrettsmith.blackjack.EventHandler;
import garrettsmith.blackjack.refimpl.TextPlayer;
import ai.agents.ReflexAgent;

public class Test {

	public static void main(String[] args) {
		double wager = 2; // you're a high roller, right?
		EventHandler eventHandler = new TextPlayer();

		Blackjack blackjack = new Blackjack();
		blackjack.playGame(eventHandler, wager);
	}
	
	
	
}
