package ai.agents.main;

import garrettsmith.blackjack.Blackjack;
import garrettsmith.blackjack.EventHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ai.agents.AlwaysStandAgent;
import ai.agents.BaseAgent;
import ai.agents.BasicStrategyAgent;
import ai.agents.BasicStrategyStandHardAgent;
import ai.agents.BasicStrategyStandSoftAgent;
import ai.agents.DealerLearningAgent;
import ai.agents.HighLowAgent;
import ai.agents.HitUntilAgent;
import ai.agents.LearningAgent;
import ai.agents.PredicateAgent;
import ai.agents.ReflexAgent;
import ai.agents.SaveAgent;
import ai.agents.WallHackAgent;
import ai.agents.main.GameLog.Level;

public class Main extends Thread {

	public final int ROUNDS;
	private int roundsPlayed = 0;
	private List<BaseAgent> agents = null;
	private Blackjack blackjack = new Blackjack();

	public static void main(String[] args) throws InterruptedException {
		
		while(true) {
			System.out.println("Agents: ");
			System.out.println("AlwaysStandAgent\t= 0");
			System.out.println("BasicStrategyAgent\t= 1");
			System.out.println("HighLowAgent\t\t= 2");
			System.out.println("HitUntilAgent\t\t= 3 & upper bound");
			System.out.println("LearningAgent\t\t= 4");
			System.out.println("PredicateAgent\t\t= 5");
			System.out.println("ReflexAgent\t\t= 6");
			System.out.println("SaveAgent\t\t= 7");
			System.out.println("WallHackAgent\t\t= 8");
		
			final String input = System.console().readLine("Choose Agent: ");

			BaseAgent agent = null;
			// AlwaysStandAgent 	= 0
			// BasicStrategyAgent 	= 1
			// HighLowAgent			= 2
			// HitUntilAgent		= 3 (Param!)
			// LearningAgent		= 4
			// PredicateAgent		= 5
			// ReflexAgent			= 6
			// SaveAgent			= 7
			// WallHackAgent		= 8
			switch(input) {
			case "0": agent = new AlwaysStandAgent(); break;
			case "1": agent = new BasicStrategyAgent(); break;
			case "2": agent = new HighLowAgent(); break;
			case "3": 
				int x = 17;
				try {
					x = Integer.parseInt(System.console().readLine("Hit until? "));
				} catch (Exception e) {
					
				}
				agent = new HitUntilAgent(x); 
				break;
			case "4": agent = new LearningAgent(); break;
			case "5": agent = new PredicateAgent(); break;
			case "6": agent = new ReflexAgent(); break;
			case "7": agent = new SaveAgent(); break;
			case "8": agent = new WallHackAgent(); break;
			default: agent = new AlwaysStandAgent(); break;
			}
			
			testAgent(agent);
		
			System.console().readLine();
			System.out.print(String.format("\033[2J"));

		} 
		
	}


	private static void testAgent(BaseAgent agents) throws InterruptedException {
		Main cca = new Main(Arrays.asList(new BaseAgent[]{agents}), 3);
		cca.run();
		for(BaseAgent agent: cca.agents) {
			System.out.println(agent.name + " purse: " + agent.getPurse());
			agent.printStats();
		}
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
