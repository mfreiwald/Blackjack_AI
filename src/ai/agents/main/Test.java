package ai.agents.main;
import garrettsmith.blackjack.Blackjack;
import garrettsmith.blackjack.EventHandler;
import ai.agents.ReflexAgent;

public class Test {

	public static void main(String[] args) {
		double wager = 1000; // you're a high roller, right?
		EventHandler eventHandler = new ReflexAgent();

		Blackjack blackjack = new Blackjack();
		blackjack.playGame(eventHandler, wager);
	}
}
