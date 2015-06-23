package ai.agents;

import garrettsmith.blackjack.Hand;
import garrettsmith.blackjack.Move;

/**
 * Implementation for the moves of BasicStrategyAgent if he has a soft hand (Ace)<br>
 * Do not use this agent as a standalone variant.
 *
 * @author amayer
 */
public class BasicStrategyStandSoftAgent extends BaseAgent {

	public BasicStrategyStandSoftAgent() {
		super("BasicStrategyStandSoftAgent");
	}

	@Override
	public Move playTurn(Hand hand) {
		int playerValue = hand.getValue();

		switch (playerValue) {
			case 3:
			case 4:
				return makeMove(hand, 5, 6, Move.HIT, Move.DOUBLE);

			case 5:
			case 6:
				return makeMove(hand, 4, 6, Move.HIT, Move.DOUBLE);

			case 7:
				return makeMove(hand, 3, 6, Move.HIT, Move.DOUBLE);

			case 8:
				int dealerValue = hand.getDealerValue();
				if (dealerValue == 1 || dealerValue > 8) {
					return Move.HIT;
				} else {
					return makeMove(hand, 3, 6, Move.STAND, Move.DOUBLE);
				}

			case 9:
			case 10:
				return Move.STAND;
		}
		throw new IllegalArgumentException("undefined next move, current playerValue: " + playerValue + " - dealerValue: " + hand.getDealerValue());
	}

	@Override
	public void dealerCreateNewDecks(int cards) {
		wager = 1.0;
	}

}
