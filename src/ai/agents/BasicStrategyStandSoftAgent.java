package ai.agents;

import garrettsmith.blackjack.Hand;
import garrettsmith.blackjack.Move;

/**
 * This agent acts like a table agent. <br>
 * He uses the a basic blackjack strategy. The agent will stand soft.
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

		if (hand.isSplitAllowed() && playerValue != 10 && playerValue != 20) {
			BasicStrategySplitAgent splitAgent = new BasicStrategySplitAgent();
			return splitAgent.playTurn(hand);
		}

		if (playerValue < 13) {
			return Move.HIT;
		} else if (playerValue > 18) {
			return Move.STAND;
		}

		// some special cases
		switch (playerValue) {
			case 13:
			case 14:
				return makeMove(hand, 5, 6, Move.HIT, Move.DOUBLE);

			case 15:
			case 16:
				return makeMove(hand, 4, 6, Move.HIT, Move.DOUBLE);

			case 17:
				return makeMove(hand, 3, 6, Move.HIT, Move.DOUBLE);

			case 18:
				int dealerValue = hand.getDealerValue();
				if (dealerValue == 1 || dealerValue > 8) {
					return Move.HIT;
				} else {
					return makeMove(hand, 3, 6, Move.STAND, Move.DOUBLE);
				}
		}
		throw new IllegalArgumentException("undefined next move, current playerValue: " + playerValue + " - dealerValue: " + hand.getDealerValue());
	}

	@Override
	public void dealerCreateNewDecks(int cards) {
		wager = 1.0;
	}

}
