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

		if (playerValue < 13) {
			return Move.HIT;
		} else if (playerValue > 18) {
			return Move.STAND;
		}

		// some special cases
		switch (playerValue) {
			case 13:
			case 14:
				return moveOrDD(hand, 5, 6, Move.HIT);

			case 15:
			case 16:
				return moveOrDD(hand, 4, 6, Move.HIT);

			case 17:
				return moveOrDD(hand, 3, 6, Move.HIT);

			case 18:
				int dealerValue = hand.getDealerValue();
				if (dealerValue == 1 || dealerValue > 8) {
					return Move.HIT;
				} else {
					return moveOrDD(hand, 3, 6, Move.STAND);
				}
		}
		throw new IllegalArgumentException("undefined next move, current playerValue: " + playerValue + " - dealerValue: " + hand.getDealerValue());
	}

	/**
	 * Returns the Move 'Double' if the dealers' hand value is between lowDD and highDD, else he
	 * will return the Move passed by <code>'move'</code>
	 *
	 * @param hand
	 *            The dealers' hand
	 * @param lowDD
	 *            The lower bound of dealers' hand value
	 * @param highDD
	 *            The upper bound of dealers' hand value
	 * @param move
	 *            The move which shall be returned alternatively
	 * @return Move 'Double' if dealers' hand value is between lowDD and highDD and allowed, else
	 *         the Move passed by 'move'
	 */
	private Move moveOrDD(Hand hand, int lowDD, int highDD, Move move) {
		int dealerValue = hand.getDealerValue();
		if (dealerValue < lowDD) {
			return move;
		} else if (dealerValue > highDD) {
			return move;
		} else {
			if (hand.isDoubleDownAllowed()) {
				return Move.DOUBLE;
			} else {
				return move;
			}
		}
	}

	@Override
	public void dealerCreateNewDecks(int cards) {
		wager = 1.0;
	}

}
