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

package garrettsmith.blackjack;

import java.util.*;
import junit.framework.*;
import garrettsmith.playingcards.*;
import garrettsmith.playingcards.Card.Value;

public class BlackjackTest extends TestCase {

	private Blackjack _blackjack;
	private CardList _cards;

	public void setUp() {
		_blackjack = new Blackjack();
		_cards = new CardList();
	}
	
	public void test3SplitsLoseWinBustPush() {
		addCard( Card.Value.FIVE );
		addCard( Card.Value.QUEEN );
		addCard( Card.Value.FIVE );
		addCard( Card.Value.NINE );
		addCard( Card.Value.FIVE );
		addCard( Card.Value.FIVE );
		addCard( Card.Value.FIVE );
		addCard( Card.Value.FIVE );
		addCard( Card.Value.FIVE );
		addCard( Card.Value.FIVE );
		addCard( Card.Value.EIGHT );
		addCard( Card.Value.TEN );
		addCard( Card.Value.TEN );
		addCard( Card.Value.TEN );
		addCard( Card.Value.NINE );
		
		setCards();
		ArrayList results = new ArrayList();
		results.add( Result.LOSE );
		results.add( Result.WIN );
		results.add( Result.BUSTED );
		results.add( Result.PUSH );
		ArrayList gains = new ArrayList();
		gains.add( new java.lang.Double( -1 ) );
		gains.add( new java.lang.Double( 1 ) );
		gains.add( new java.lang.Double( -1 ) );
		gains.add( new java.lang.Double( 0 ) );
		MockEventHandler handler = new MockEventHandler( results, gains );
		handler.addMove( Move.SPLIT );
		handler.addMove( Move.SPLIT );
		handler.addMove( Move.SPLIT );
		handler.addMove( Move.HIT );
		handler.addMove( Move.STAND );
		handler.addMove( Move.HIT );
		handler.addMove( Move.STAND );
		handler.addMove( Move.HIT );
		handler.addMove( Move.HIT );
		handler.addMove( Move.HIT );
		handler.addMove( Move.STAND );

		playGame(handler);
	}
	
	public void testCantSplitWithoutSameCards() {
		addCard( Card.Value.FOUR );
		addCard( Card.Value.QUEEN );
		addCard( Card.Value.FIVE );
		addCard( Card.Value.NINE );
		setCards();
		MockEventHandler handler = new MockEventHandler( new NotAllowedException() );
		handler.addMove( Move.SPLIT );

		playGame(handler);
	}
	
	public void testCanSplitWithDifferentFaceCards() {
		addCard( Card.Value.QUEEN );
		addCard( Card.Value.JACK );
		addCard( Card.Value.TEN );
		addCard( Card.Value.NINE );
		addCard( Card.Value.TEN );
		addCard( Card.Value.TEN );
		setCards();
		MockEventHandler handler = new MockEventHandler( new NotAllowedException() );
		handler.addMove( Move.SPLIT );
		handler.addMove( Move.STAND );
		handler.addMove( Move.STAND );

		playGame(handler);
	}

	public void testCantSplitWithMoreThanThreeCards() {
		addCard( Card.Value.FIVE );
		addCard( Card.Value.QUEEN );
		addCard( Card.Value.FIVE );
		addCard( Card.Value.NINE );
		addCard( Card.Value.TWO );
		addCard( Card.Value.TEN );
		addCard( Card.Value.EIGHT );
		setCards();
		MockEventHandler handler = new MockEventHandler( new NotAllowedException() );
		handler.addMove( Move.SPLIT );
		handler.addMove( Move.HIT );
		handler.addMove( Move.SPLIT );

		playGame(handler);
	}
	
	public void testCanDoubleDownOnNon91011() {
		addCard( Card.Value.FIVE );
		addCard( Card.Value.QUEEN );
		addCard( Card.Value.FIVE );
		addCard( Card.Value.NINE );
		addCard( Card.Value.TWO );
		setCards();
		MockEventHandler handler = new MockEventHandler( Result.LOSE, -2 );
		handler.addMove( Move.DOUBLE );
		handler.addMove( Move.STAND );

		playGame(handler);
	}

	public void testCanNotDoubleDownOnNon91011() {
		addCard( Card.Value.FIVE );
		addCard( Card.Value.QUEEN );
		addCard( Card.Value.FIVE );
		addCard( Card.Value.NINE );
		addCard( Card.Value.TWO );
		setCards();
		MockEventHandler handler = new MockEventHandler( new NotAllowedException() );
		handler.addMove( Move.DOUBLE );

		playGame(handler);
	}
	
	public void testRulesOnlyAllowSplittingOnce() {
		Rules rules = new Rules();
		rules.setMaxSplits( 1 );
		_blackjack.setRules( rules );
		addCard( Card.Value.TEN );
		addCard( Card.Value.SEVEN );
		addCard( Card.Value.TEN );
		addCard( Card.Value.TEN );
		addCard( Card.Value.TEN );
		addCard( Card.Value.EIGHT );
		setCards();
		MockEventHandler handler = new MockEventHandler( new NotAllowedException() );
		handler.addMove( Move.SPLIT );
		handler.addMove( Move.SPLIT );

		playGame(handler);
	}

	public void testCantResplitAces() {
		addCard( Card.Value.ACE );
		addCard( Card.Value.SEVEN );
		addCard( Card.Value.ACE );
		addCard( Card.Value.TEN );
		addCard( Card.Value.ACE );
		addCard( Card.Value.ACE );
		addCard( Card.Value.ACE );
		addCard( Card.Value.ACE );
		setCards();
		MockEventHandler handler = new MockEventHandler( new NotAllowedException() );
		handler.addMove( Move.SPLIT );
		handler.addMove( Move.SPLIT );

		playGame(handler);
	}
	
	public void testCanResplitAces() {
		Rules rules = new Rules();
		rules.setIsResplittingAcesAllowed( true );
		_blackjack.setRules( rules );
		addCard( Card.Value.ACE );
		addCard( Card.Value.SEVEN );
		addCard( Card.Value.ACE );
		addCard( Card.Value.TEN );
		addCard( Card.Value.ACE ); // split, first hand
		addCard( Card.Value.NINE ); // split, second hand
		addCard( Card.Value.TWO ); // second split, first hand
		addCard( Card.Value.SEVEN ); // second split, third hand
		setCards();
		MockEventHandler handler = new MockEventHandler();
		handler.ignoreGainOrLoss();
		handler.addMove( Move.SPLIT );
		handler.addMove( Move.SPLIT );
		handler.addMove( Move.STAND );
		handler.addMove( Move.STAND );
		handler.addMove( Move.STAND );

		playGame(handler);
	}

	public void testCantDoubleAfterSplit() {
		Rules rules = new Rules();
		rules.setCanDoubleAfterSplit( false );
		_blackjack.setRules( rules );
		addCard( Card.Value.THREE );
		addCard( Card.Value.SEVEN );
		addCard( Card.Value.THREE ); // player has two threes initially
		addCard( Card.Value.TEN ); // dealer has 7 + 10 = 19 initially
		addCard( Card.Value.ACE );
		addCard( Card.Value.ACE );
		addCard( Card.Value.ACE );
		setCards();
		MockEventHandler handler = new MockEventHandler( new NotAllowedException() );
		handler.addMove( Move.SPLIT );
		handler.addMove( Move.DOUBLE );

		playGame(handler);
	}
	
	public void testCantSplitMoreThan3Times() {
		addCard( Card.Value.THREE );
		addCard( Card.Value.SEVEN );
		addCard( Card.Value.THREE ); // player has two threes initially
		addCard( Card.Value.TEN ); // dealer has 7 + 10 = 19 initially
		addCard( Card.Value.THREE );
		addCard( Card.Value.THREE );
		addCard( Card.Value.THREE );
		addCard( Card.Value.THREE );
		addCard( Card.Value.THREE );
		addCard( Card.Value.THREE );
		setCards();
		MockEventHandler handler = new MockEventHandler( new NotAllowedException() );
		handler.addMove( Move.SPLIT );
		handler.addMove( Move.SPLIT );
		handler.addMove( Move.SPLIT );
		handler.addMove( Move.SPLIT );

		playGame(handler);
	}

	public void testSplitHandDoubleWinBust() {
		addCard( Card.Value.THREE );
		addCard( Card.Value.SEVEN );
		addCard( Card.Value.THREE ); // player has two threes initially
		addCard( Card.Value.TEN ); // dealer has 7 + 10 = 19 initially
		addCard( Card.Value.EIGHT ); // split first hand; 11
		addCard( Card.Value.TWO ); // split second hand; 5
		addCard( Card.Value.NINE ); // double first split; wins at 21
		addCard( Card.Value.TWO ); // hit; 7
		addCard( Card.Value.TWO ); // hit; 9
		addCard( Card.Value.FIVE ); // hit; 14
		addCard( Card.Value.JACK ); // hit; second split busts with 24
		setCards();
		ArrayList results = new ArrayList();
		results.add( Result.WIN );
		results.add( Result.BUSTED );
		ArrayList gains = new ArrayList();
		gains.add( new java.lang.Double( 2 ) );
		gains.add( new java.lang.Double( -1 ) );
		MockEventHandler handler = new MockEventHandler( results, gains );
		handler.addMove( Move.SPLIT );
		handler.addMove( Move.DOUBLE ); // first split
		handler.addMove( Move.HIT );
		handler.addMove( Move.HIT );
		handler.addMove( Move.HIT );
		handler.addMove( Move.HIT ); // second split

		playGame(handler);
	}

	public void testSplitHandLoseBust() {
		addCard( Card.Value.TWO );
		addCard( Card.Value.NINE );
		addCard( Card.Value.TWO ); // player has two twos initially
		addCard( Card.Value.TEN ); // dealer has 9 + 10 = 19 initially
		addCard( Card.Value.TEN ); // split first hand
		addCard( Card.Value.TEN ); // hit; first split busts at 22
		addCard( Card.Value.TWO ); // split second hand
		addCard( Card.Value.TWO ); // hit
		addCard( Card.Value.TWO ); // hit
		addCard( Card.Value.TWO ); // hit
		addCard( Card.Value.EIGHT ); // hit; second split loses with 18
		setCards();
		ArrayList results = new ArrayList();
		results.add( Result.BUSTED );
		results.add( Result.LOSE );
		ArrayList gains = new ArrayList();
		gains.add( new java.lang.Double( -1 ) );
		gains.add( new java.lang.Double( -1 ) );
		MockEventHandler handler = new MockEventHandler( results, gains );
		handler.addMove( Move.SPLIT );
		handler.addMove( Move.HIT ); // first split
		handler.addMove( Move.HIT );
		handler.addMove( Move.HIT );
		handler.addMove( Move.HIT );
		handler.addMove( Move.HIT );
		handler.addMove( Move.STAND ); // second split

		playGame(handler);
	}

	public void testSplitHandWinLose() {
		addCard( Card.Value.TEN );
		addCard( Card.Value.NINE );
		addCard( Card.Value.TEN ); // player has two tens initially
		addCard( Card.Value.TEN ); // dealer has 19 initially
		addCard( Card.Value.ACE ); // player gets 21 on first split; stand
		addCard( Card.Value.TWO ); // second split; 12
		addCard( Card.Value.TWO ); // hit, 14
		addCard( Card.Value.TWO ); // hit, 16
		addCard( Card.Value.TWO ); // hit, second split has 18, then stand
		setCards();
		ArrayList results = new ArrayList();
		results.add( Result.WIN );
		results.add( Result.LOSE );
		ArrayList gains = new ArrayList();
		gains.add( new java.lang.Double(  1 ) );
		gains.add( new java.lang.Double( -1 ) );
		MockEventHandler handler = new MockEventHandler( results, gains );
		handler.addMove( Move.SPLIT );
		handler.addMove( Move.STAND );
		handler.addMove( Move.HIT );
		handler.addMove( Move.HIT );
		handler.addMove( Move.HIT );
		handler.addMove( Move.STAND );

		playGame(handler);
	}

	public void testSplitHandLoseWin() {
		_cards.add( new Card( Card.Value.NINE, Card.Suit.DIAMOND ) );
		_cards.add( new Card( Card.Value.FIVE, Card.Suit.HEART ) );
		_cards.add( new Card( Card.Value.NINE, Card.Suit.HEART ) );
		_cards.add( new Card( Card.Value.FIVE, Card.Suit.HEART ) );
		_cards.add( new Card( Card.Value.TWO, Card.Suit.HEART ) );
		_cards.add( new Card( Card.Value.TEN, Card.Suit.HEART ) );
		_cards.add( new Card( Card.Value.SEVEN, Card.Suit.HEART ) );

		setCards();
		ArrayList results = new ArrayList();
		results.add( Result.LOSE );
		results.add( Result.WIN );
		ArrayList gains = new ArrayList();
		gains.add( new java.lang.Double( -1 ) );
		gains.add( new java.lang.Double( 1 ) );
		MockEventHandler handler = new MockEventHandler( results, gains );
		handler.addMove( Move.SPLIT );
		handler.addMove( Move.STAND );
		handler.addMove( Move.STAND );

		playGame(handler);
	}

	public void testSplitHandWinWin() {
		_cards.add( new Card( Card.Value.NINE, Card.Suit.DIAMOND ) );
		_cards.add( new Card( Card.Value.FIVE, Card.Suit.HEART ) );
		_cards.add( new Card( Card.Value.NINE, Card.Suit.HEART ) );
		_cards.add( new Card( Card.Value.FIVE, Card.Suit.HEART ) );
		_cards.add( new Card( Card.Value.TEN, Card.Suit.HEART ) );
		_cards.add( new Card( Card.Value.TEN, Card.Suit.HEART ) );
		_cards.add( new Card( Card.Value.SEVEN, Card.Suit.HEART ) );

		setCards();
		ArrayList results = new ArrayList();
		results.add( Result.WIN );
		results.add( Result.WIN );
		ArrayList gains = new ArrayList();
		gains.add( new java.lang.Double( 1 ) );
		gains.add( new java.lang.Double( 1 ) );
		MockEventHandler handler = new MockEventHandler( results, gains );
		handler.addMove( Move.SPLIT );
		handler.addMove( Move.STAND );
		handler.addMove( Move.STAND );

		playGame(handler);
	}

	public void testNonDefaultBlackjackPayoff() {
		final double payoff = 3.7;
		Rules rules = new Rules();
		rules.setBlackjackPayoff( payoff );
		_blackjack = new Blackjack( rules );
		_cards.add( new Card( Card.Value.TEN, Card.Suit.DIAMOND ) );
		_cards.add( new Card( Card.Value.FIVE, Card.Suit.HEART ) );
		_cards.add( new Card( Card.Value.ACE, Card.Suit.HEART ) );
		_cards.add( new Card( Card.Value.FIVE, Card.Suit.HEART ) );
		setCards();
		MockEventHandler handler = new MockEventHandler( Result.BLACKJACK, payoff );
		
        playGame(handler);
	}
	
	public void testPlayDealerHandStandAtHard17WithHitOnSoft17True() {
		setRulesHitOnSoft17();
		_cards.add( new Card( Card.Value.TWO, Card.Suit.HEART ) );
		_cards.add( new Card( Card.Value.FIVE, Card.Suit.HEART ) );
		_cards.add( new Card( Card.Value.TWO, Card.Suit.HEART ) );
		_cards.add( new Card( Card.Value.FIVE, Card.Suit.HEART ) );
		_cards.add( new Card( Card.Value.FIVE, Card.Suit.HEART ) );
		_cards.add( new Card( Card.Value.TWO, Card.Suit.HEART ) );
		setCards();
		MockEventHandler handler = new MockEventHandler();
		handler.addMove( Move.STAND );
		handler.ignoreGainOrLoss();
		
        playGame(handler);
        
        assertEquals( 17, Blackjack.calculateBestValue( handler.getDealerCards() ) );
	}
	
	public void testPlayDealerHandHitAtSoft17() {
		setRulesHitOnSoft17();
		_cards.add( new Card( Card.Value.TWO, Card.Suit.HEART ) );
		_cards.add( new Card( Card.Value.ACE, Card.Suit.HEART ) );
		_cards.add( new Card( Card.Value.TWO, Card.Suit.HEART ) );
		_cards.add( new Card( Card.Value.TWO, Card.Suit.HEART ) );
		_cards.add( new Card( Card.Value.FOUR, Card.Suit.HEART ) );
		_cards.add( new Card( Card.Value.THREE, Card.Suit.CLUB ) );
		setCards();
		MockEventHandler handler = new MockEventHandler();
		handler.addMove( Move.STAND );
		handler.ignoreGainOrLoss();
		
        playGame(handler);
        
        assertEquals( 20, Blackjack.calculateBestValue( handler.getDealerCards() ) );
	}

	public void testPlayDealerHandStandAt20() {
		_cards.add( new Card( Card.Value.TWO, Card.Suit.HEART ) );
		_cards.add( new Card( Card.Value.FIVE, Card.Suit.HEART ) );
		_cards.add( new Card( Card.Value.TWO, Card.Suit.HEART ) );
		_cards.add( new Card( Card.Value.FIVE, Card.Suit.HEART ) );
		_cards.add( new Card( Card.Value.FIVE, Card.Suit.HEART ) );
		_cards.add( new Card( Card.Value.FIVE, Card.Suit.HEART ) );
		setCards();
		MockEventHandler handler = new MockEventHandler();
		handler.addMove( Move.STAND );
		handler.ignoreGainOrLoss();
		
        playGame(handler);
        
        assertEquals( 20, Blackjack.calculateBestValue( handler.getDealerCards() ) );
	}
	
	public void testPlayDealerHandStandAtHard17() {
		_cards.add( new Card( Card.Value.TWO, Card.Suit.HEART ) );
		_cards.add( new Card( Card.Value.FIVE, Card.Suit.HEART ) );
		_cards.add( new Card( Card.Value.TWO, Card.Suit.HEART ) );
		_cards.add( new Card( Card.Value.FIVE, Card.Suit.HEART ) );
		_cards.add( new Card( Card.Value.FIVE, Card.Suit.HEART ) );
		_cards.add( new Card( Card.Value.TWO, Card.Suit.HEART ) );
		setCards();
		MockEventHandler handler = new MockEventHandler();
		handler.addMove( Move.STAND );
		handler.ignoreGainOrLoss();
		
        playGame(handler);
        
        assertEquals( 17, Blackjack.calculateBestValue( handler.getDealerCards() ) );
	}
	
	public void testPlayDealerHandStandAtSoft17() {
		_cards.add( new Card( Card.Value.TWO, Card.Suit.HEART ) );
		_cards.add( new Card( Card.Value.ACE, Card.Suit.HEART ) );
		_cards.add( new Card( Card.Value.TWO, Card.Suit.HEART ) );
		_cards.add( new Card( Card.Value.TWO, Card.Suit.HEART ) );
		_cards.add( new Card( Card.Value.FOUR, Card.Suit.HEART ) );
		setCards();
		MockEventHandler handler = new MockEventHandler();
		handler.addMove( Move.STAND );
		handler.ignoreGainOrLoss();
		
        playGame(handler);
        
        assertEquals( 17, Blackjack.calculateBestValue( handler.getDealerCards() ) );
	}

	public void testPlayerDoublesWins() {
		_cards.add( new Card( Card.Value.NINE, Card.Suit.HEART ) );
		_cards.add( new Card( Card.Value.TWO, Card.Suit.HEART ) );
		_cards.add( new Card( Card.Value.NINE, Card.Suit.HEART ) );
		_cards.add( new Card( Card.Value.TWO, Card.Suit.HEART ) );
		_cards.add( new Card( Card.Value.TWO, Card.Suit.HEART ) );
		_cards.add( new Card( Card.Value.KING, Card.Suit.HEART ) );
		_cards.add( new Card( Card.Value.THREE, Card.Suit.HEART ) );
		setCards();

		MockEventHandler handler = new MockEventHandler( Result.WIN );
		handler.addMove( Move.DOUBLE );
		handler.addExpectedGain( 2 );
		
        playGame(handler);
	}

	public void testPlayerDoublesLoses() {
		_cards.add( new Card( Card.Value.NINE, Card.Suit.HEART ) );
		_cards.add( new Card( Card.Value.TWO, Card.Suit.HEART ) );
		_cards.add( new Card( Card.Value.SEVEN, Card.Suit.HEART ) );
		_cards.add( new Card( Card.Value.TWO, Card.Suit.HEART ) );
		_cards.add( new Card( Card.Value.TWO, Card.Suit.HEART ) );
		_cards.add( new Card( Card.Value.KING, Card.Suit.HEART ) );
		_cards.add( new Card( Card.Value.SIX, Card.Suit.HEART ) );
		setCards();
		MockEventHandler handler = new MockEventHandler( Result.LOSE, -2 );
		handler.addMove( Move.DOUBLE );
		
        playGame(handler);
	}
	
	public void testPlayerSurrendersLate() {
		addFiveCards();
		setCards();
		MockEventHandler handler = new MockEventHandler( Result.LATE_SURRENDER, -0.5 );
		handler.addMove( Move.SURRENDER );
		
        playGame(handler);
	}
	
	public void testCanNotSurrenderLate() {
		Rules rules = new Rules();
		rules.setCanSurrenderLate( false );
		_blackjack.setRules( rules );
		addCard( Card.Value.TWO );
		addCard( Card.Value.TWO );
		addCard( Card.Value.TWO );
		addCard( Card.Value.TWO );
		setCards();
		MockEventHandler handler = new MockEventHandler( new NotAllowedException() );
		handler.addMove( Move.SURRENDER );
		
        playGame(handler);
	}
	
	public void testCantSurrenderAfterMovePlayed() {
		addCard( Card.Value.TWO );
		addCard( Card.Value.TWO );
		addCard( Card.Value.TWO ); // player starts with 4
		addCard( Card.Value.TWO ); // dealer starts with 4
		addCard( Card.Value.TWO ); // hit; 6
		addCard( Card.Value.TWO ); // hit, 8, then surrenders
		setCards();
		MockEventHandler handler = new MockEventHandler( new NotAllowedException());
		handler.addMove( Move.HIT );
		handler.addMove( Move.SURRENDER );
		
        playGame(handler);
	}

	public void testHitPlayerBusts() {
		_cards.add( new Card( Card.Value.TEN, Card.Suit.HEART ) );
		_cards.add( new Card( Card.Value.SIX, Card.Suit.HEART ) );
		_cards.add( new Card( Card.Value.TEN, Card.Suit.HEART ) );
		_cards.add( new Card( Card.Value.SIX, Card.Suit.HEART ) );
		_cards.add( new Card( Card.Value.TEN, Card.Suit.HEART ) );
		_cards.add( new Card( Card.Value.TEN, Card.Suit.HEART ) );
		setCards();
		MockEventHandler handler = new MockEventHandler( Result.BUSTED, -1 );
		handler.addMove( Move.HIT );
		
        playGame(handler);
	}

	public void testHitStandPlayerWins() {
		_cards.add( new Card( Card.Value.SIX, Card.Suit.HEART ) );
		_cards.add( new Card( Card.Value.SIX, Card.Suit.HEART ) );
		_cards.add( new Card( Card.Value.SIX, Card.Suit.HEART ) );
		_cards.add( new Card( Card.Value.SIX, Card.Suit.HEART ) );
		_cards.add( new Card( Card.Value.SIX, Card.Suit.HEART ) );
		_cards.add( new Card( Card.Value.TEN, Card.Suit.HEART ) );
		setCards();
		MockEventHandler handler = new MockEventHandler( Result.DEALER_BUSTED, 1 );
		handler.addMove( Move.HIT );
		handler.addMove( Move.STAND );
		
        playGame(handler);
	}
	
	public void testBothHaveBlackjack() {
		_cards.add( new Card( Card.Value.ACE, Card.Suit.HEART ) );
		_cards.add( new Card( Card.Value.ACE, Card.Suit.HEART ) );
		_cards.add( new Card( Card.Value.TEN, Card.Suit.HEART ) );
		_cards.add( new Card( Card.Value.TEN, Card.Suit.HEART ) );
		setCards();
		
		MockEventHandler handler = new MockEventHandler( Result.BLACKJACK_PUSH, 0 );
		
        playGame(handler);
	}
	
	public void testPlayerHasBlackjack() {
		_cards.add( new Card( Card.Value.ACE, Card.Suit.HEART ) );
		_cards.add( new Card( Card.Value.ACE, Card.Suit.HEART ) );
		_cards.add( new Card( Card.Value.TEN, Card.Suit.HEART ) );
		_cards.add( new Card( Card.Value.NINE, Card.Suit.HEART ) );
		setCards();
		MockEventHandler handler = new MockEventHandler( Result.BLACKJACK, 1.5 );
		
        playGame(handler);
	}
	
	public void testDealerHasBlackjack() {
		_cards.add( new Card( Card.Value.ACE, Card.Suit.HEART ) );
		_cards.add( new Card( Card.Value.ACE, Card.Suit.HEART ) );
		_cards.add( new Card( Card.Value.NINE, Card.Suit.HEART ) );
		_cards.add( new Card( Card.Value.TEN, Card.Suit.HEART ) );
		setCards();
		
		MockEventHandler handler = new MockEventHandler( Result.DEALER_BLACKJACK, -1 );
		
        playGame(handler);
	}
	
	public void testCalculateBestValueForHardHand() {
		_cards.add( new Card( Card.Value.TWO, Card.Suit.CLUB ) );
		_cards.add( new Card( Card.Value.FIVE, Card.Suit.DIAMOND ) );

		assertEquals( 7, Blackjack.calculateBestValue( _cards ) );
	}

	public void testCalculateBestValueForSoftHand() {
		_cards.add( new Card( Card.Value.FIVE, Card.Suit.CLUB ) );
		_cards.add( new Card( Card.Value.ACE, Card.Suit.HEART ) );

		assertEquals( 16, Blackjack.calculateBestValue( _cards ) );
	}

	public void testCalculateBestValueForSoftHandAboveAceValuedOne() {
		_cards.add( new Card( Card.Value.NINE, Card.Suit.CLUB ) );
		_cards.add( new Card( Card.Value.FIVE, Card.Suit.CLUB ) );
		_cards.add( new Card( Card.Value.ACE, Card.Suit.HEART ) );
		
		assertEquals( 15, Blackjack.calculateBestValue( _cards ) );
	}

	public void testCalculateBestValueForBustedSoftHand() {
		_cards.add( new Card( Card.Value.NINE, Card.Suit.CLUB ) );
		_cards.add( new Card( Card.Value.NINE, Card.Suit.CLUB ) );
		_cards.add( new Card( Card.Value.FIVE, Card.Suit.SPADE ) );
		_cards.add( new Card( Card.Value.ACE, Card.Suit.HEART ) );
		
		assertEquals( 24, Blackjack.calculateBestValue( _cards ) );
	}

	public void testCalculateBestValueForBustedHardHand() {
		_cards.add( new Card( Card.Value.NINE, Card.Suit.CLUB ) );
		_cards.add( new Card( Card.Value.NINE, Card.Suit.CLUB ) );
		_cards.add( new Card( Card.Value.FIVE, Card.Suit.CLUB ) );
		
		assertEquals( 23, Blackjack.calculateBestValue( _cards ) );
	}
	
	public void testCalculateValueForHardHand() {
		_cards.add( new Card( Card.Value.TWO, Card.Suit.CLUB ) );
		_cards.add( new Card( Card.Value.FIVE, Card.Suit.DIAMOND ) );

		assertEquals( 7, Blackjack.calculateValue( _cards ) );
	}

	public void testCalculateValueForSoftHand() {
		_cards.add( new Card( Card.Value.FIVE, Card.Suit.CLUB ) );
		_cards.add( new Card( Card.Value.ACE, Card.Suit.HEART ) );

		assertEquals( 6, Blackjack.calculateValue( _cards ) );
	}

	public void testCalculateValueForSoftHandAboveAceValuedOne() {
		_cards.add( new Card( Card.Value.NINE, Card.Suit.CLUB ) );
		_cards.add( new Card( Card.Value.FIVE, Card.Suit.CLUB ) );
		_cards.add( new Card( Card.Value.ACE, Card.Suit.HEART ) );
		
		assertEquals( 15, Blackjack.calculateValue( _cards ) );
	}

	public void testCalculateValueForBustedSoftHand() {
		_cards.add( new Card( Card.Value.NINE, Card.Suit.CLUB ) );
		_cards.add( new Card( Card.Value.NINE, Card.Suit.CLUB ) );
		_cards.add( new Card( Card.Value.FIVE, Card.Suit.SPADE ) );
		_cards.add( new Card( Card.Value.ACE, Card.Suit.HEART ) );
		
		assertEquals( 24, Blackjack.calculateValue( _cards ) );
	}

	public void testCalculateValueForBustedHardHand() {
		_cards.add( new Card( Card.Value.NINE, Card.Suit.CLUB ) );
		_cards.add( new Card( Card.Value.NINE, Card.Suit.CLUB ) );
		_cards.add( new Card( Card.Value.FIVE, Card.Suit.CLUB ) );
		
		assertEquals( 23, Blackjack.calculateValue( _cards ) );
	}
	
	public void testCalculateSingleCardValueNonFaceCard() {
		assertEquals( 7,
					  Blackjack.calculateValue(
					  		new Card( Card.Value.SEVEN, Card.Suit.SPADE ) ) );
	}
	
	public void testCalculateSingleCardValueFaceCard() {
		assertEquals( 10,
					  Blackjack.calculateValue(
					  		new Card( Card.Value.QUEEN, Card.Suit.SPADE ) ) );
	}
	
	public void testCalculateSingleCardValueAce() {
		assertEquals( 1,
					  Blackjack.calculateValue(
					  		new Card( Card.Value.ACE, Card.Suit.DIAMOND ) ) );
	}
	
	public void testIsFaceCard() {
		assertTrue( Blackjack.isNonAceFaceCard( new Card( Card.Value.QUEEN, Card.Suit.SPADE ) ) );
	}
	
	public void testIsNotFaceCard() {
		assertFalse( Blackjack.isNonAceFaceCard( new Card( Card.Value.EIGHT, Card.Suit.SPADE ) ) );
	}

	public void testIsFaceCardAce() {
		assertFalse( Blackjack.isNonAceFaceCard( new Card( Card.Value.ACE, Card.Suit.SPADE ) ) );
	}

	public void testIsBlackjack() {
		_cards.add( new Card( Card.Value.TEN, Card.Suit.CLUB ) );
		_cards.add( new Card( Card.Value.ACE, Card.Suit.CLUB ) );
		
		assertTrue( Blackjack.isBlackjack( _cards ) );
	}

	public void testIsNotBlackjack() {
		_cards.add( new Card( Card.Value.TEN, Card.Suit.CLUB ) );
		_cards.add( new Card( Card.Value.SIX, Card.Suit.CLUB ) );
		
		assertFalse( Blackjack.isBlackjack( _cards ) );
	}

	public void testIsNotBlackjack21ThreeCards() {
		_cards.add( new Card( Card.Value.EIGHT, Card.Suit.CLUB ) );
		_cards.add( new Card( Card.Value.TWO, Card.Suit.CLUB ) );
		_cards.add( new Card( Card.Value.ACE, Card.Suit.CLUB ) );
		
		assertFalse( Blackjack.isBlackjack( _cards ) );
	}
	
	public void testIsSoft() {
		_cards.add( new Card( Card.Value.EIGHT, Card.Suit.CLUB ) );
		_cards.add( new Card( Card.Value.ACE, Card.Suit.CLUB ) );

		assertTrue( Blackjack.isSoft( _cards ) );
	}
	
	public void testIsNotSoft() {
		_cards.add( new Card( Card.Value.EIGHT, Card.Suit.CLUB ) );
		_cards.add( new Card( Card.Value.FIVE, Card.Suit.CLUB ) );

		assertFalse( Blackjack.isSoft( _cards ) );
	}

	public void testIsSoftAbove21() {
		_cards.add( new Card( Card.Value.EIGHT, Card.Suit.CLUB ) );
		_cards.add( new Card( Card.Value.EIGHT, Card.Suit.DIAMOND ) );
		_cards.add( new Card( Card.Value.ACE, Card.Suit.CLUB ) );

		assertFalse( Blackjack.isSoft( _cards ) );
	}
	
	private CardContainer getFourCards() {
		CardList cards = new CardList();
		cards.add( new Card( Card.Value.EIGHT, Card.Suit.CLUB ) );
		cards.add( new Card( Card.Value.EIGHT, Card.Suit.DIAMOND ) );
		cards.add( new Card( Card.Value.ACE, Card.Suit.CLUB ) );
		cards.add( new Card( Card.Value.ACE, Card.Suit.CLUB ) );
		return new CardContainer( cards );
	}
	
	private void playGame(MockEventHandler handler) {
		_blackjack.playGame( handler, 1 );
	}

	private void addFiveCards() {
		_cards.add( new Card( Card.Value.TEN, Card.Suit.HEART ) );
		_cards.add( new Card( Card.Value.SIX, Card.Suit.HEART ) );
		_cards.add( new Card( Card.Value.TEN, Card.Suit.HEART ) );
		_cards.add( new Card( Card.Value.SIX, Card.Suit.HEART ) );
		_cards.add( new Card( Card.Value.SIX, Card.Suit.HEART ) );
	}
	
	private void setRulesHitOnSoft17() {
		Rules rules = new Rules();
		rules.setDealerStandOnSoft17( false );
		_blackjack = new Blackjack( rules );
	}

	private void setCards() {
		_blackjack.setCards( new CardContainer( _cards ) );
	}

	private void addCard(Value value) {
		_cards.add( new Card( value, Card.Suit.DIAMOND ) );
	}
}