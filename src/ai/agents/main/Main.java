package ai.agents.main;

import garrettsmith.blackjack.Blackjack;
import ai.agents.*;
import ai.agents.main.GameLog.Level;

public class Main extends Thread {

	public final int ROUNDS;
	private int roundsPlayed = 0;
	private BaseAgent agent = null;
	private Blackjack blackjack = new Blackjack();

	public static void main(String[] args) throws InterruptedException {
		
		/*
		Main cca = new Main(new SaveAgent(), 50, Level.ALL);
		cca.start();
		cca.join();
		System.out.println(cca.agent.name + " purse: " + cca.agent.getPurse());
		cca.agent.printStats();
		*/
		
		final int ROUNDS = 5000;
		Main[] agents = {
			new Main(new SaveAgent(), ROUNDS, Level.ERROR),
			new Main(new ReflexAgent(), ROUNDS, Level.ERROR),
			new Main(new AlwaysStandAgent(), ROUNDS, Level.ERROR),
			new Main(new HighLowAgent(), ROUNDS, Level.ERROR)
		};
		runAgentsInThread(agents);
		
		
	}
	
	private static void runAgentsInThread(Main[] agents) {
		for(Main m: agents) {
			m.start();
		}
		
		
		for(Main m: agents) {
			try {
				m.join();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		for(Main m: agents) {
			System.out.println(m.agent.name + " purse: " + m.agent.getPurse());
			m.agent.printStats();
			System.out.println();
		}
	}
	
	Main(BaseAgent agent, int rounds, Level level) {
		this.agent = agent;
		this.ROUNDS = rounds;
		GameLog.level = level;
	}
	
	Main(BaseAgent agent, int rounds) {
		this(agent, rounds, Level.ALL);
	}
	
	public void run() {		
		while(playGame()) {
			/* nothing do to */
		}
	}
	
	private boolean playGame() {
		roundsPlayed++;
		GameLog.println("================ New Game ("+roundsPlayed+") ======================");
		
		agent.newGame();
		
		blackjack.playGame(agent, agent.wager);
				
		GameLog.println("====================================================");
		GameLog.println();
		
		if(this.roundsPlayed == this.ROUNDS) {
			return false;
		} else {
			return true;
		}
	}

}
