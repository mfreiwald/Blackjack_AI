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

public class RulesTest 
	extends TestCase {

	private Properties _props;
	private Rules _rules;

	public void setUp() {
		_rules = new Rules();
		_props = new Properties();
	}

	public void testDealerStandOnSoft17() {
		setProperty( Rules.STAND_ON_SOFT_17, Boolean.toString( true ) );
		
		assertTrue( _rules.doesDealerStandOnSoft17() );
	}

	public void testDealerNotStandOnSoft17() {
		setProperty( Rules.STAND_ON_SOFT_17, Boolean.toString( false ) );
		
		assertFalse( _rules.doesDealerStandOnSoft17() );
	}

	public void testDefaultStandOnSoft17() {
		assertTrue( _rules.doesDealerStandOnSoft17() );
	}

	public void testCanSurrenderEarly() {
		setProperty( Rules.EARLY_SURRENDER, Boolean.toString( true ) );

		assertTrue( _rules.canSurrenderEarly() );
	}
	
	public void testCanNotSurrenderEarly() {
		setProperty( Rules.EARLY_SURRENDER, Boolean.toString( false ) );
		
		assertFalse( _rules.canSurrenderEarly() );
	}
	
	public void testDefaultCanSurrenderEarly() {
		assertFalse( _rules.canSurrenderEarly() );
	}

	public void testLoadWithEmptyProperties() {
		new Rules( new Properties() );
	}
	
	public void testDefaultBlackjackPayoff() {
		assertEquals( 1.5, _rules.getBlackjackPayoff(), 0 );
	}
	
	public void testBlackjackPayoff() {
		setProperty( Rules.BLACKJACK_PAYOFF, "3.33" );
		
		assertEquals( 3.33, _rules.getBlackjackPayoff(), 0 );
	}
	
	public void testCanDoubleAfterSplit() {
		setProperty( Rules.DOUBLE_AFTER_SPLIT, Boolean.toString( true ) );
		
		assertTrue( _rules.canDoubleAfterSplit() );
	}
	
	public void testCanNotDoubleAfterSplit() {
		setProperty( Rules.DOUBLE_AFTER_SPLIT, Boolean.toString( false ) );
		
		assertFalse( _rules.canDoubleAfterSplit() );
	}
	
	public void testDefaultCanDoubleAfterSplit() {
		assertTrue( _rules.canDoubleAfterSplit() );
	}
	
	public void testCanSurrenderLate() {
		setProperty( Rules.LATE_SURRENDER, Boolean.toString( true ) );
		
		assertTrue( _rules.canSurrenderLate() );
	}
	
	public void testCanNotSurrenderLate() {
		setProperty( Rules.LATE_SURRENDER, Boolean.toString( false ) );
		
		assertFalse( _rules.canSurrenderLate() );
	}
	
	public void testDefaultCanSurrenderLate() {
		assertTrue( _rules.canSurrenderLate() );
	}
	
	public void testMaxSplits() {
		setProperty( Rules.MAX_SPLITS, "7" );
		
		assertEquals( 7, _rules.getMaxSplits() );
	}
	
	public void testDefaultMaxSplits() {
		assertEquals( 3, _rules.getMaxSplits() );
	}

	public void testNumberOfDecks() {
		setProperty( Rules.NUM_DECKS, "42" );
		
		assertEquals( 42, _rules.getNumberOfDecks() );
	}
	
	public void testDefaultNumberOfDecks() {
		assertEquals( 6, _rules.getNumberOfDecks() );
	}

	public void testIsResplittingAcesAllowed() {
		setProperty( Rules.RESPLIT_ACES, Boolean.toString( true ) );
		
		assertTrue( _rules.isResplittingAcesAllowed() );
	}

	public void testResplittingAcesNotAllowed() {
		setProperty( Rules.RESPLIT_ACES, Boolean.toString( false ) );
		
		assertFalse( _rules.isResplittingAcesAllowed() );
	}
	
	public void testDefaultResplittingAcesAllowed() {
		assertFalse( _rules.isResplittingAcesAllowed() );
	}
	
	public void testIsDoubleDownRestricted() {
		setProperty( Rules.RESTRICTED_DOUBLE, Boolean.toString( true ) );
		
		assertTrue( _rules.isDoubleDownRestricted() );
	}
	
	public void testDoubleDownIsNotRestricted() {
		setProperty( Rules.RESTRICTED_DOUBLE, Boolean.toString( false ) );
		
		assertFalse( _rules.isDoubleDownRestricted() );
	}

	public void testDefaultDoubleDownRestricted() {
		assertFalse( _rules.isDoubleDownRestricted() );
	}
	
	public void testShufflePercent() {
		setProperty( Rules.SHUFFLE_LIMIT, "0.77" );
		
		assertEquals( _rules.getShuffleLimit(), 0.77, 0 );
	}
	
	public void testDefaultShufflePercent() {
		assertEquals( _rules.getShuffleLimit(), 0.2, 0 );
	}
	
	private void setProperty( String name, String value ) {
		_props.setProperty( name, value );
		_rules = new Rules( _props );
	}
}