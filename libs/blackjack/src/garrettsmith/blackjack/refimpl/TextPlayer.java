/*
Copyright (c) 2005 Garrett Smith
The MIT License

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
 */

// $Id: TextPlayer.java,v 1.16 2005/06/04 08:50:53 gsmith Exp $

package garrettsmith.blackjack.refimpl;

// Java packages
import garrettsmith.blackjack.Blackjack;
// garrett_smith packages
import garrettsmith.blackjack.EventHandler;
import garrettsmith.blackjack.Hand;
import garrettsmith.blackjack.Move;
import garrettsmith.blackjack.Result;
import garrettsmith.blackjack.Rules;
import garrettsmith.playingcards.Card;
import garrettsmith.playingcards.CardList;
import gnu.getopt.Getopt;
// third-party packages
import gnu.getopt.LongOpt;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * Provides for playing Blackjack via the console. Use argument --help for
 * command line parameter use.
 *
 * @author Garrett Smith, gsmith at northwestern dot edu
 * @version Blackjack v1.0
 * @since Blackjack v1.0
 */
public class TextPlayer implements EventHandler {

	private static final String[] blanks = new String[] { "", "\r", "\r\n",
			"\n" };
	private static final int NO = 0;
	private static final int YES = 1;
	private static final int NEITHER = -1;

	private static final String _APP_NAME = EventHandler.class.getName();

	private static TextPlayer _player = new TextPlayer();

	private boolean _hasDealerCardBeenPrinted = false;
	private Blackjack _blackjack;
	private double _purse = 0.0;
	private Rules _rules = null;

	/**
	 * <p>
	 * Plays blackjack via the console. Use argument --help for command line
	 * parameter usage.
	 * </p>
	 */
	public static void main(String[] arguments) {

		Rules rules = null;
		Options options = new Options(arguments);
		if (!options.argsCorrect) {

			printHelp();
			System.exit(1);
		} else if (options.helpSelected) {

			printHelp();
			System.exit(0);
		} else if (options.rulesSpecified) {

			try {

				Properties props = new Properties();
				InputStream input = new BufferedInputStream(
						new FileInputStream(options.rulesPath));
				props.load(input);
				rules = new Rules(props);
				input.close();
			} catch (IOException ioE) {

				System.err.println(_APP_NAME + ": trouble opening file "
						+ options.rulesPath);
				System.err.println(_APP_NAME + ": more information: "
						+ ioE.getMessage());
				printHelp();
				return;
			}
		}

		try {

			_player.setRules(rules);
			while (_player.playGame()) {

				/* no operation */
			}
		} catch (Exception e) {

			System.err.println("an unknown error occurred; detail below");
			e.printStackTrace();
			return;
		}
	}

	/**
	 * Invoked when insurance if available; always returns false.
	 */
	public boolean offerInsurance(Hand hand) {

		return false;
	}

	/**
	 * Invoked when early surrender is offered; this method then prompts the
	 * user.
	 */
	@Override
	public boolean offerEarlySurrender(Hand hand) {

		try {

			printDealerCardIfNeeded(hand);
			return promptForSurrender();
		} catch (Exception e) {

			fatalErrorOccurred(e);
			return false;
		}
	}

	@Override
	public Move offerRegularTurn(Hand hand) {
		try {
			printDealerCardIfNeeded(hand);
			System.out.print("For this hand you have ");
			printCards(hand.getCards());
			System.out.println(" (" + (hand.isSoft() ? "soft " : "")
					+ Integer.toString(hand.getBestValue()) + ").");
			return getMoveFromUser(hand);
		} catch (Exception e) {
			fatalErrorOccurred(e);
			return null;
		}
	}

	@Override
	public void fatalErrorOccurred(Exception e) {

		System.err.println("a fatal error occurred: " + e.getMessage());
		e.printStackTrace();
		System.exit(1);
	}

	@Override
	public void handFinished(Hand hand, double gainOrLoss, Result result,
			CardList dealerCards) {

		System.out.print("game over: you ");
		if (Result.PUSH.equals(result)) {
			System.out.println("pushed: 0.0");
		} else if (Result.WIN.equals(result)) {
			System.out.println("won: " + Double.toString(gainOrLoss));
		} else if (Result.LOSE.equals(result)) {
			System.out.println("lost: " + Double.toString(gainOrLoss));
		} else if (Result.LATE_SURRENDER.equals(result)) {
			System.out.println("surrendered: " + Double.toString(gainOrLoss));
		} else if (Result.DEALER_BLACKJACK.equals(result)) {
			System.out.println("lost: " + Double.toString(gainOrLoss)
					+ "; the dealer had a blackjack");
		} else if (Result.BUSTED.equals(result)) {
			System.out.println("BUSTED: " + Double.toString(gainOrLoss));
		} else if (Result.DEALER_BUSTED.equals(result)) {
			System.out.println("won; the dealer BUSTED: "
					+ Double.toString(gainOrLoss));
		} else if (Result.BLACKJACK.equals(result)) {
			System.out
					.println("got BLACKJACK!: " + Double.toString(gainOrLoss));
		} else if (Result.BLACKJACK_PUSH.equals(result)) {
			System.out.println("pushed, you BOTH had blackjack: "
					+ Double.toString(gainOrLoss));
		} else {
			throw new IllegalArgumentException("unknown value of parameter "
					+ "result: " + result.value());
		}
		System.out.print("In the end, the dealer had ");
		printCards(dealerCards);
		System.out.println(" ("
				+ Integer.toString(Blackjack.calculateBestValue(dealerCards))
				+ "),");
		System.out.print("and you had ");
		printCards(hand.getCards());
		System.out.println(" (" + Blackjack.calculateBestValue(hand.getCards())
				+ ").");
		_purse += gainOrLoss;
		System.out.println("You have " + Double.toString(_purse)
				+ " in your purse.");
	}

	private static String formatCard(Card card) {

		if (Card.Value.ACE.equals(card.getValue())) {

			return "an ace";
		} else if (Card.Value.KING.equals(card.getValue())) {

			return "a king";
		} else if (Card.Value.QUEEN.equals(card.getValue())) {

			return "a queen";
		} else if (Card.Value.JACK.equals(card.getValue())) {

			return "a jack";
		} else if (Card.Value.TEN.equals(card.getValue())) {

			return "a ten";
		} else if (Card.Value.NINE.equals(card.getValue())) {

			return "a nine";
		} else if (Card.Value.EIGHT.equals(card.getValue())) {

			return "an eight";
		} else if (Card.Value.SEVEN.equals(card.getValue())) {

			return "a seven";
		} else if (Card.Value.SIX.equals(card.getValue())) {

			return "a six";
		} else if (Card.Value.FIVE.equals(card.getValue())) {

			return "a five";
		} else if (Card.Value.FOUR.equals(card.getValue())) {

			return "a four";
		} else if (Card.Value.THREE.equals(card.getValue())) {

			return "a three";
		} else if (Card.Value.TWO.equals(card.getValue())) {

			return "a two";
		}
		throw new IllegalArgumentException("unknown card type: "
				+ card.toString());
	}

	private static int interpretYesNo(String input) {

		input = input.trim().toLowerCase();

		if (input.length() <= 0) {

			return NEITHER;
		}
		if (input.startsWith("y")) {

			return YES;
		} else if (input.startsWith("n")) {

			return NO;
		}
		return NEITHER;
	}

	private boolean playGame() throws IOException {

		if (_blackjack == null) {

			if (_rules == null) {

				_blackjack = new Blackjack();
			} else {

				_blackjack = new Blackjack(_rules);
			}
		}

		System.out.println();
		_hasDealerCardBeenPrinted = false;
		_blackjack.playGame(Arrays.asList(new EventHandler[]{this}));

		return promptForAnotherGame();
	}

	private void printDealerCard(Hand hand) {

		System.out.println("For this hand the dealer is showing "
				+ formatCard(hand.getDealerCard()) + ".");
	}

	/**
	 * Prints the dealer's card in human-readable format to standard output if
	 * and only if it has not already been printed for this game.
	 */
	private void printDealerCardIfNeeded(Hand hand) {

		if (!_hasDealerCardBeenPrinted) {

			printDealerCard(hand);
			_hasDealerCardBeenPrinted = true;
		}
	}

	private static void printCards(List<Card> cards) {

		Card card;
		for (int i = 0; i < cards.size(); i++) {

			card = (Card) cards.get(i);

			if (i < cards.size() - 2) {

				System.out.print(formatCard(card) + ", ");
			} else if (i == cards.size() - 2) {

				System.out.print(formatCard(card) + " ");
			} else {

				System.out.print("and " + formatCard(card));
			}
		}
	}

	private static boolean promptForAnotherGame() throws IOException {

		String input;
		int result;

		while (true) {

			System.out
					.print("Would you like to play another game (YES or no)? ");
			input = getInputFromUser();
			result = interpretYesNo(input);
			if (result == YES) {

				return true;
			} else if (result == NO) {

				return false;
			} else if (isBlank(input)) {

				// then it was the default answer
				return true;
			} else {

				System.out.println("Unknown answer \"" + input
						+ "\"; please try again.");
			}
		}
	}

	static boolean isBlank(String input) {
		for (int i = 0; i < blanks.length; i++) {
			if (blanks[i].equals(input)) {
				return true;
			}
		}
		return false;
	}

	private static void promptForMove(Hand hand) {

		String prompt = "What would you like to do? ( Hit | STand ";
		if (hand.isSurrenderAllowed()) {

			prompt += "| SUrrender ";
		}
		if (hand.isDoubleDownAllowed()) {

			prompt += "| Double ";
		}
		if (hand.isSplitAllowed()) {

			prompt += "| SPlit ";
		}
		prompt += "): ";
		System.out.print(prompt);
	}

	private static String getInputFromUser() throws IOException {

		byte[] input = new byte[2048];
		int length;
		length = System.in.read(input);
		return new String(input, 0, length - 1);
	}

	private static Move getMoveFromUser(Hand hand) throws IOException {

		String input;
		Move response;

		while (true) {

			promptForMove(hand);
			input = getInputFromUser();
			response = interpretMove(input);
			if (response == null) {

				System.out.println("I'm sorry, I didn't understand \"" + input
						+ "\"; please try again.");
			} else if (!hand.isMoveAllowed(response)) {

				System.out.println("I'm sorry, but that move is not allowed.");
			} else {

				break;
			}
		}
		return response;
	}

	private static Move interpretMove(String input) {

		input = input.trim().toLowerCase();
		if (input.startsWith("h")) {

			return Move.HIT;
		} else if (input.startsWith("st")) {

			return Move.STAND;
		} else if (input.startsWith("su")) {

			return Move.SURRENDER;
		} else if (input.startsWith("sp")) {

			return Move.SPLIT;
		} else if (input.startsWith("d")) {

			return Move.DOUBLE;
		}
		return null;
	}

	private static void printHelp() {

		System.out
				.println("usage: "
						+ _APP_NAME
						+ " [options...]\n"
						+ "no options will run the application with the default rules\n"
						+ "-h/--help     Print this help information\n"
						+ "-r/--rules <properties-file>\n"
						+ "              Run the application with the rules specified in "
						+ "<properties-file>");
	}

	private static boolean promptForSurrender() throws IOException {

		String input;
		int result;

		while (true) {

			System.out.print("Would you like to surrender (yes or NO)? ");
			input = getInputFromUser();
			result = interpretYesNo(input);
			if (result == YES) {

				return true;
			} else if (result == NO) {

				return false;
			} else if (isBlank(input)) {

				// default value
				return false;
			} else {

				System.out.println("Unknown answer \"" + input
						+ "\"; please try again.");
			}
		}
	}

	private void setRules(Rules rules) {
		_rules = rules;
	}

	public TextPlayer() {
	}

	private static class Options {

		private Options(String[] arguments) {

			LongOpt[] longOpts = new LongOpt[3];
			longOpts[0] = new LongOpt("help", LongOpt.NO_ARGUMENT, null, 'h');
			longOpts[1] = new LongOpt("rules", LongOpt.REQUIRED_ARGUMENT, null,
					'r');
			Getopt getopt = new Getopt(_APP_NAME, arguments, ":hr:", longOpts);
			int value;
			while ((value = getopt.getopt()) != -1) {

				switch (value) {

				case 'h':
					helpSelected = true;
					break;

				case 'r':
					rulesPath = getopt.getOptarg();
					rulesSpecified = true;
					break;

				case '?':
				case ':':
					argsCorrect = false;
					break;

				default:
					throw new IllegalArgumentException("getopt() returned '"
							+ (char) value + "'");
				}
			}
		}

		private boolean helpSelected = false;
		private boolean rulesSpecified = false;
		private String rulesPath = null;
		private boolean argsCorrect = true;

	}

	@Override
	public double getWager() {
		// TODO Auto-generated method stub
		return 0;
	}
}