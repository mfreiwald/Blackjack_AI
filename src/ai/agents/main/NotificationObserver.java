package ai.agents.main;

import ai.agents.BaseAgent;
import garrettsmith.playingcards.Card;

public abstract class NotificationObserver {

	/**
	 * Is called, if the CardContainer is going to be empty
	 * @param cards Number of cards in the CardContainer
	 */
	public void dealerCreateNewDecks(int cards) {}
	
	/**
	 * Is called, if a player or dealer getting a new card.
	 * @param player The Player who getting the card.
	 * @param card The Card the player get, null if no one nows the card value
	 */
	public void playerGetCard(BaseAgent player, Card card) {}
	
	/**
	 * Is called, if the dealer getting a new card.
	 */
	public void dealerGetCard() {}
	
	
}
