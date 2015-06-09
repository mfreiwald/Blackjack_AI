package ai.agents.main;

import garrettsmith.blackjack.Blackjack;
import garrettsmith.blackjack.EventHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ai.agents.AlwaysStandAgent;
import ai.agents.BaseAgent;
import ai.agents.BasicStrategyStandHardAgent;
import ai.agents.BasicStrategyStandSoftAgent;
<<<<<<< HEAD
import ai.agents.HighLowAgent;
import ai.agents.HitUntilAgent;
import ai.agents.LearningAgent;
import ai.agents.ReflexAgent;
=======
>>>>>>> e0cfe3c3c6d050d86149319b521bd1f1c3503519
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

	private static void testAgent() throws InterruptedException {
		Main cca = new Main(Arrays.asList(new BaseAgent[]{new WallHackAgent()}), 1000000);
		cca.run();
		for(BaseAgent agent: cca.agents) {
			System.out.println(agent.name + " purse: " + agent.getPurse());
			agent.printStats();
		}
	}

	private static void testAgents() {
		final int ROUNDS = 100000;
<<<<<<< HEAD
		
		
		Main[] agents = {
				new Main(new SaveAgent(), ROUNDS, Level.ERROR),
				//new Main(new ReflexAgent(), ROUNDS, Level.ERROR),
				new Main(new AlwaysStandAgent(), ROUNDS, Level.ERROR),
				//new Main(new HighLowAgent(), ROUNDS, Level.ERROR),
				new Main(new BasicStrategyStandSoftAgent(), ROUNDS, Level.ERROR),
				new Main(new BasicStrategyStandHardAgent(), ROUNDS, Level.ERROR),
				new Main(new WallHackAgent(), ROUNDS, Level.ERROR) };
				//new Main(new LearningAgent(), ROUNDS, Level.ERROR) };
		
		/*
		Main[] agents = {
				new Main(new HitUntilAgent(13), ROUNDS, Level.ERROR),
				new Main(new HitUntilAgent(14), ROUNDS, Level.ERROR),
				new Main(new HitUntilAgent(15), ROUNDS, Level.ERROR),
				new Main(new HitUntilAgent(16), ROUNDS, Level.ERROR),
				new Main(new HitUntilAgent(17), ROUNDS, Level.ERROR),
				new Main(new HitUntilAgent(18), ROUNDS, Level.ERROR),
				new Main(new HitUntilAgent(19), ROUNDS, Level.ERROR),
				new Main(new HitUntilAgent(20), ROUNDS, Level.ERROR) };
			*/	 
		runAgentsInThread(agents);
=======
		BaseAgent[] agents = {
				new SaveAgent(), new AlwaysStandAgent(), new BasicStrategyStandSoftAgent(),
				new BasicStrategyStandHardAgent(), new WallHackAgent() };
		new Main(Arrays.asList(agents), ROUNDS).run();
>>>>>>> e0cfe3c3c6d050d86149319b521bd1f1c3503519
	}


	Main(List<BaseAgent> agents, int rounds, Level level) {
		this.agents = agents;
		this.ROUNDS = rounds;
		GameLog.level = level;
		for(BaseAgent agent : this.agents) {
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
		for(BaseAgent agent : agents) {
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
