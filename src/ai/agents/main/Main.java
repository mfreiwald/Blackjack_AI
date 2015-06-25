package ai.agents.main;

import garrettsmith.blackjack.Blackjack;
import garrettsmith.blackjack.EventHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ai.agents.AlwaysStandAgent;
import ai.agents.BaseAgent;
import ai.agents.BasicStrategyAgent;
import ai.agents.PredicateAgent;
import ai.agents.SaveAgent;
import ai.agents.WallHackAgent;
import ai.agents.main.GameLog.Level;

public class Main extends Thread {

	public final int ROUNDS;
	private int roundsPlayed = 0;
	private List<BaseAgent> agents = null;
	private Blackjack blackjack = new Blackjack();

	public static void main(String[] args) throws InterruptedException {
		testAgents();
	}

	private static void testAgents() {
		final int ROUNDS = 100;

		BaseAgent[] agents = {
				new SaveAgent(), new AlwaysStandAgent(), new BasicStrategyAgent(),
				new WallHackAgent(), new PredicateAgent()};
		new Main(Arrays.asList(agents), ROUNDS).run();
	}

	Main(List<BaseAgent> agents, int rounds, Level level) {
		this.agents = agents;
		this.ROUNDS = rounds;
		GameLog.level = level;
		for (BaseAgent agent : this.agents) {
			agent.setGame(blackjack);
		}
	}

	Main(List<BaseAgent> agent, int rounds) {
		this(agent, rounds, Level.ALL);
	}

	public void run() {
		while (playGame()) {
			/* nothing do to */
		}
	}

	private boolean playGame() {
		roundsPlayed++;
		GameLog.println("================ New Game (" + roundsPlayed
				+ ") ======================");
		for (BaseAgent agent : agents) {
			agent.newGame();
		}
		List<EventHandler> handlers = new ArrayList<EventHandler>(agents);
		blackjack.playGame(handlers);

		GameLog.println("====================================================");
		GameLog.println();

		if (this.roundsPlayed == this.ROUNDS) {
			return false;
		} else {
			return true;
		}
	}

}
