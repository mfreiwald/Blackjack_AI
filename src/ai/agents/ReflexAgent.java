package ai.agents;

import garrettsmith.blackjack.Hand;
import garrettsmith.blackjack.Move;

import java.util.Random;

public class ReflexAgent extends BaseAgent {

	
	public ReflexAgent() {
		super("ReflexAgent");
	}
	
	@Override
	public Move offerRegularTurn(Hand hand) {
		
		printDealerCardIfNeeded(hand);
		printPlayerCards(hand);
		
		
		int possibilities = 4;
		Random r = new Random();
		while (true) {
			int move = r.nextInt(possibilities);
			switch (move) {
			case 3:
				if (hand.isDoubleDownAllowed()) {
					return Move.DOUBLE;
				}
				break;
			case 1:
				if (hand.isStandAllowed()) {
					return Move.STAND;
				}
				break;
			case 2:
				if (hand.isSplitAllowed()) {
					return Move.SPLIT;
				}
				break;
			case 0:
				if (hand.isHitAllowed()) {
					return Move.HIT;
				}
				break;
			}
		}

	}

}
