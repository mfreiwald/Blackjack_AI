package ai.agents;

import ai.agents.main.GameLog;
import garrettsmith.blackjack.Blackjack;
import garrettsmith.blackjack.Hand;
import garrettsmith.blackjack.Move;
import garrettsmith.blackjack.Result;
import garrettsmith.playingcards.CardList;

/**
 * 
 * @author michael
 *
 */
public class DealerLearningAgent extends BaseAgent {

	int lowestDealerValue = 22;
	
	public DealerLearningAgent() {
		super("DealerLearningAgent");
	}

	@Override
	public Move playTurn(Hand hand) {
		return Move.HIT;
	}
	
	@Override
	public void gameEnd(Hand hand, double gain, Result result,
			CardList dealerCards) {

		int dealerValue = Blackjack.calculateBestValue(dealerCards);
		if(dealerValue <= 21 && !Result.BLACKJACK.equals(result)) {
			this.lowestDealerValue = dealerValue < this.lowestDealerValue ? dealerValue : this.lowestDealerValue;
		}
		
		GameLog.println("Dealer lowest Value: " + this.lowestDealerValue);
	}

}
