package ai.agents;

import garrettsmith.blackjack.Hand;
import garrettsmith.blackjack.Move;

public class HitUntilAgent extends BaseAgent {

	private final int stand;
	
	public HitUntilAgent() {
		this(17);
	}
	
	public HitUntilAgent(int stand) {
		super("HitUntilAgent "+stand);
		this.stand = stand;
	}
	
	@Override
	public Move playTurn(Hand hand) {
		if(hand.getValue() < stand) {
			return Move.HIT;
		} else {
			return Move.STAND;
		}
	}

}
