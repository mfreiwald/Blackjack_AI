package ai.agents;

import garrettsmith.blackjack.Hand;
import garrettsmith.blackjack.Move;
import garrettsmith.blackjack.Result;
import garrettsmith.playingcards.Card;
import garrettsmith.playingcards.CardList;

public class WallHackAgent extends BaseAgent {

	public WallHackAgent() {
		super("WallHackAgent");
	}
	
	@Override
	public Move playTurn(Hand hand) {
		//System.out.println("Next Card:");
		Card next = this.getGame().hintNextCard();
		//System.out.println(next);
		
		int nextValue = 0;
		try {
			nextValue = Integer.parseInt(formatCard(next));
		} catch (NumberFormatException e) {
			if(formatCard(next).equals("A")) {
				nextValue = 1;
			} else {
				nextValue = 10;
			}
		}
		int value = hand.getValue();
		
		if( (value+nextValue) > 21) {
			return Move.STAND;
		} else {
			return Move.HIT;
		}
	}

}
