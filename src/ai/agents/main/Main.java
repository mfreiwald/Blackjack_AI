package ai.agents.main;

import garrettsmith.blackjack.Blackjack;
import ai.agents.AlwaysStandAgent;
import ai.agents.BaseAgent;
import ai.agents.BasicStrategyStandHardAgent;
import ai.agents.BasicStrategyStandSoftAgent;
import ai.agents.HighLowAgent;
import ai.agents.LearningAgent;
import ai.agents.ReflexAgent;
import ai.agents.SaveAgent;
import ai.agents.WallHackAgent;
import ai.agents.main.GameLog.Level;

public class Main extends Thread {

	public final int ROUNDS;
	private int roundsPlayed = 0;
	private BaseAgent agent = null;
	private Blackjack blackjack = new Blackjack();
	
	public static void main(String[] args) throws InterruptedException {
		//testAgent();
		testAgents();
	}

	private static void testAgent() throws InterruptedException {
		Main cca = new Main(new WallHackAgent(), 1000000, Level.ERROR);
		cca.start();
		cca.join();
		System.out.println(cca.agent.name + " purse: " + cca.agent.getPurse());
		cca.agent.printStats();
	}

	private static void testAgents() {
		final int ROUNDS = 100000;
		Main[] agents = {
				new Main(new SaveAgent(), ROUNDS, Level.ERROR),
				//new Main(new ReflexAgent(), ROUNDS, Level.ERROR),
				new Main(new AlwaysStandAgent(), ROUNDS, Level.ERROR),
				//new Main(new HighLowAgent(), ROUNDS, Level.ERROR),
				new Main(new BasicStrategyStandSoftAgent(), ROUNDS, Level.ERROR),
				new Main(new BasicStrategyStandHardAgent(), ROUNDS, Level.ERROR),
				new Main(new WallHackAgent(), ROUNDS, Level.ERROR) };
				//new Main(new LearningAgent(), ROUNDS, Level.ERROR) };
		runAgentsInThread(agents);
	}

	private static void runAgentsInThread(Main[] agents) {
		for (Main m : agents) {
			m.start();
		}

		for (Main m : agents) {
			try {
				m.join();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		for (Main m : agents) {
			System.out.println(m.agent.name + " purse: " + m.agent.getPurse());
			m.agent.printStats();
			System.out.println();
		}
	}

	Main(BaseAgent agent, int rounds, Level level) {
		this.agent = agent;
		this.ROUNDS = rounds;
		GameLog.level = level;
		this.agent.setGame(blackjack);
	}

	Main(BaseAgent agent, int rounds) {
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

		agent.newGame();

		blackjack.playGame(agent, agent.wager);

		GameLog.println("====================================================");
		GameLog.println();

		if (this.roundsPlayed == this.ROUNDS) {
			return false;
		} else {
			return true;
		}
	}

}
