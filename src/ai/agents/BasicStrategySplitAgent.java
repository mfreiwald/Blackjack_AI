package ai.agents;

import garrettsmith.blackjack.Hand;
import garrettsmith.blackjack.Move;

public class BasicStrategySplitAgent extends BaseAgent {

	public BasicStrategySplitAgent() {
		super("BasicStrategySplitAgent");
	}

	@Override
	public Move playTurn(Hand hand) {
		// split allowed
		int playerValue = hand.getValue();
		int dealerValue = hand.getDealerValue();

		switch (playerValue) {
			case 2:
				return Move.SPLIT;

			case 4:
			case 6:
				return makeMove(hand, 4, 7, Move.HIT, Move.SPLIT);

			case 8:
				return Move.HIT;

				// case 10: not handled here -> perform default action

			case 12:
				return makeMove(hand, 3, 6, Move.HIT, Move.SPLIT);

			case 14:
				return makeMove(hand, 2, 7, Move.HIT, Move.SPLIT);

			case 16:
				return Move.SPLIT;

			case 18:
				if (dealerValue == 1 || dealerValue == 7 || dealerValue == 10) {
					return Move.STAND;
				} else {
					return Move.SPLIT;
				}

			default:
				throw new IllegalArgumentException("undefined next move, current playerValue: " + playerValue + " - dealerValue: " + hand.getDealerValue());
		}
	}

}
