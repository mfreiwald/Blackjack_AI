package ai.agents;

import garrettsmith.blackjack.Hand;
import garrettsmith.blackjack.Move;

/**
 * This agent acts like a table agent. <br>
 * He uses the a basic blackjack strategy. The agent will stand hard.
 *
 * @author amayer
 */
public class BasicStrategyStandHardAgent extends BaseAgent {

	public BasicStrategyStandHardAgent() {
		super("BasicStrategyStandHardAgent");
	}

	@Override
	public Move playTurn(Hand hand) {
		int playerValue = hand.getValue();
		int dealerValue = hand.getDealerValue();

		if (playerValue < 9) {
			return Move.HIT;
		} else if (playerValue > 16) {
			return Move.STAND;
		}

		// some special cases
		switch (playerValue) {
		// hit or try DoubleDown
			case 9:
				return makeMove(hand, 3, 6, Move.HIT, Move.DOUBLE);
			case 10:
				return makeMove(hand, 2, 9, Move.HIT, Move.DOUBLE);
			case 11:
				return makeMove(hand, 2, 10, Move.HIT, Move.DOUBLE);

				// hit or Stand
			case 12:
				return makeMove(hand, 5, 6, Move.HIT, Move.STAND);
			case 13:
			case 14:
				return makeMove(hand, 2, 6, Move.STAND, Move.DOUBLE);

				// hit, stand or try surrender
			case 15:
				if (dealerValue == 10) {
					if (hand.isMoveAllowed(Move.SURRENDER)) {
						return Move.SURRENDER;
					}
				}
				return makeMove(hand, 2, 6, Move.HIT, Move.STAND);
			case 16:
				if (dealerValue == 1 || dealerValue == 9 || dealerValue == 10) {
					if (hand.isMoveAllowed(Move.SURRENDER)) {
						return Move.SURRENDER;
					}
				}
				return makeMove(hand, 2, 6, Move.HIT, Move.STAND);

		}
		throw new IllegalArgumentException("undefined next move, current playerValue: " + playerValue + " - dealerValue: " + hand.getDealerValue());
	}

	/**
	 * Returns the Move passed by <code>'alternativeMove'</code> if the dealers' hand value is
	 * between lowerBound and upperBound, else he will return the Move passed by <code>'move'</code>
	 *
	 * @param hand
	 *            The dealers' hand
	 * @param lowerBound
	 *            The lower bound of dealers' hand value to perform the alternative move
	 * @param upperBound
	 *            The upper bound of dealers' hand value to perform the alternative move
	 * @param move
	 *            The move which shall be returned, this move should always be allowed!
	 * @param alternativeMove
	 *            The move which shall be returned if the dealers' hand value is between lower and
	 *            upper Bound
	 * @return Move <code>'alternativeMove'</code> if dealers' hand value is between lowerBound and
	 *         upperBound and allowed, else the Move passed by 'move'
	 */
	private Move makeMove(Hand hand, int lowerBound, int upperBound, Move move, Move alternativeMove) {
		int dealerValue = hand.getDealerValue();
		if (dealerValue < lowerBound) {
			return move;
		} else if (dealerValue > upperBound) {
			return move;
		} else {
			if (hand.isMoveAllowed(alternativeMove)) {
				return alternativeMove;
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
