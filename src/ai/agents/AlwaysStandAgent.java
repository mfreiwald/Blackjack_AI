package ai.agents;

import garrettsmith.blackjack.Blackjack;
import garrettsmith.blackjack.Hand;
import garrettsmith.blackjack.Move;

public class AlwaysStandAgent extends BaseAgent {

	public AlwaysStandAgent() {
		super("AlwaysStandAgent");
	}
	
	@Override
	public Move playTurn(Hand hand) {
		
		printDealerCardIfNeeded(hand);
		printPlayerCards(hand);
		
		return Move.STAND;

	}

}
