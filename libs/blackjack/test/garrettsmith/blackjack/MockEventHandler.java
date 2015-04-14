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

import garrettsmith.playingcards.CardList;

import java.util.*;

import junit.framework.*;

public class MockEventHandler implements EventHandler {

	private ArrayList _results = new ArrayList();
	private boolean _shouldSurrenderEarly;
	private ArrayList _moves = new ArrayList();
	private ArrayList _gains = new ArrayList();
	private Exception _fatalError;
	private boolean _ignoreGainOrLoss = false;
	private boolean _ignoreResult = false;
	private CardList _dealerCards;
	private Exception _expectedError;

	public MockEventHandler() {
		_ignoreResult = true;
	}

	public MockEventHandler( Exception e ) {
		setExpectedError( e );
		ignoreGainOrLoss();
		_ignoreResult = true;
	}

	public MockEventHandler( Result expectedResult ) {
		addExpectedResult( expectedResult );
	}
	
	public MockEventHandler( ArrayList expectedResults ) {
		_results = expectedResults;
	}
	
	public MockEventHandler( ArrayList expectedResults, ArrayList expectedGains ) {
		this( expectedResults );
		_gains = expectedGains;
	}
	
	public MockEventHandler( Result expectedResult, double expectedGain ) {
		this( expectedResult );
		addExpectedGain( expectedGain );
	}
	
	public void fatalErrorOccurred(Exception e) {
		_fatalError = e;
		if ( _expectedError == null ) {
			Assert.fail( "fatalErrorOccured: " + e );
		}
		else {
			Assert.assertEquals( _expectedError.getClass(), e.getClass() );
		}
	}

	public void handFinished(Hand hand, double gain, Result result, CardList dealerCards ) {
		if ( ! _ignoreResult ) {
			Assert.assertTrue( "no expected results in list", _gains.size() > 0 );
			Object value = _results.remove( 0 );
			Assert.assertNotNull( "no expected result in stack", value );
			Assert.assertEquals( 
				"expecting " + value + " but got " + result 
					+ ", player cards are " + hand.getCards()
					+ ", dealer cards are " + dealerCards,
				value, 
				result );
		}
		if ( ! _ignoreGainOrLoss ) {
			Assert.assertTrue( "no expected gains in list", _gains.size() > 0 );
			Object value = _gains.remove( 0 );
			Assert.assertNotNull( "no expected gain in stack", value);
			Assert.assertEquals( 
				((java.lang.Double) value).doubleValue(),
				 gain,
				 0 );
		}
		_dealerCards = dealerCards;
	}

	public boolean offerEarlySurrender(Hand hand) {
		return _shouldSurrenderEarly;
	}

	public Move offerRegularTurn( Hand hand ) {
		if ( _moves.isEmpty() ) {
			Assert.fail( "no moves in list" );
		}
		return (Move) _moves.remove( 0 );
	}
	
	public CardList getDealerCards() {
		return _dealerCards;
	}
	
	private void setExpectedError(Exception e) {
		_expectedError = e;
	}
	
	public void addExpectedResult( Result expectedResult ) {
		_results.add( expectedResult );
	}
	
	public void setEarlySurrenderReturn( boolean shouldSurrenderEarly ) {
		_shouldSurrenderEarly = shouldSurrenderEarly;
	}
	
	public void addExpectedGain( double expectedGain ) {
		_gains.add( new java.lang.Double( expectedGain ) );
	}
	
	public void addMove( Move move ) {
		_moves.add( move );
	}
	
	public boolean wasIllegalMoveInvoked() {
		return ( _fatalError != null );
	}

	public void ignoreGainOrLoss() {
		_ignoreGainOrLoss = true;
	}
}