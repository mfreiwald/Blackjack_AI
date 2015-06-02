package ai.agents.main;

import java.util.ArrayList;
import java.util.List;

public class GameNotifications {

	public static enum Notifications {
		NewDeck
	}

	private static List<NotificationObserver> observers = new ArrayList<>();

	public static void register(NotificationObserver observer) {
		synchronized (observers) {
			observers.add(observer);
		}
	}

	public static void unregister(NotificationObserver observer) {
		synchronized (observers) {
			observers.remove(observer);
		}
	}

	public static void dealerCreateNewDecks(int cards) {
		synchronized (observers) {
			for (NotificationObserver ob : observers) {
				ob.dealerCreateNewDecks(cards);
			}
		}
	}

	/*
	 * public static void playerGetCard(Card card) { for(NotificationObserver
	 * ob: observers) { //ob.playerGetCard(player, card); } }
	 */
	public static void dealerGetCard() {
		synchronized (observers) {
			for (NotificationObserver ob : observers) {
				ob.dealerGetCard();
			}
		}
	}
}
