package ai.agents.main;

import java.util.ArrayList;
import java.util.List;

public class GameNotifications {

	public static enum Notifications {
		NewDeck
	}
	
	private static List<NotificationObserver> observers = new ArrayList<>();
	
	public static void register(NotificationObserver observer) {
		observers.add(observer);
	}
	
	public static void unregister(NotificationObserver observer) {
		observers.remove(observer);
	}
	
	public static void dealerCreateNewDecks(int cards) {
		for(NotificationObserver ob: observers) {
			ob.dealerCreateNewDecks(cards);
		}
	}
	/*
	public static void playerGetCard(Card card) {
		for(NotificationObserver ob: observers) {
			//ob.playerGetCard(player, card);
		}
	}
	*/
	public static void dealerGetCard() {
		for(NotificationObserver ob: observers) {
			ob.dealerGetCard();
		}
	}
}
