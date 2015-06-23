package ai.agents;

import garrettsmith.blackjack.Hand;
import garrettsmith.blackjack.Move;

/**
 * Implementation for the moves of BasicStrategyAgent if he has a hard hand (no Ace).<br>
 * Do not use this agent as a standalone variant.
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
				return makeMove(hand, 4, 6, Move.HIT, Move.STAND);
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

	@Override
	public void dealerCreateNewDecks(int cards) {
		wager = 1.0;
	}

}
