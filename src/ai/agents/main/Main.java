package ai.agents.main;

import garrettsmith.blackjack.Blackjack;
import ai.agents.BaseAgent;
import ai.agents.ReflexAgent;

public class Main {

	public static final int ROUNDS = 1;
	
	
	
	
	
	private static int roundsPlayed = 0;
	private static double wager = 1.0;
	private static BaseAgent agent = null;
	private static Blackjack blackjack = new Blackjack();

	public static void main(String[] args) {

		agent = new ReflexAgent();
		
		while(playGame()) {
			/* nothing do to */
		}
		
	}
	
	private static boolean playGame() {
		roundsPlayed++;
		GameLog.println("New Game ("+roundsPlayed+")");
		
		
		blackjack.playGame(agent, wager);
		
		GameLog.println();
		if(Main.roundsPlayed == Main.ROUNDS) {
			return false;
		} else {
			return true;
		}
	}

}
