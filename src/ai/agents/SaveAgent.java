package ai.agents;

import garrettsmith.blackjack.Hand;
import garrettsmith.blackjack.Move;

/**
 * This agent won't take one more card if he theoretically can reach more than 21.
 *
 * @author amayer
 */
public class SaveAgent extends BaseAgent {

	public SaveAgent() {
		super("SaveAgent");
	}

	@Override
	public Move offerRegularTurn(Hand hand) {
		printDealerCardIfNeeded(hand);
		printPlayerCards(hand);

		if (evaluateHand(hand)) {
			return Move.HIT;
		} else {
			return Move.STAND;
		}
	}

	private boolean evaluateHand(Hand hand) {
		if (hand.getValue() <= 11) {
			return true;
		} else {
			return false;
		}
	}

}
