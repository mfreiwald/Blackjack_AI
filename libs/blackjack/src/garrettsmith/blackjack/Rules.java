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

// $Id: Rules.java,v 1.14 2005/06/04 08:50:50 gsmith Exp $

package garrettsmith.blackjack;

import java.util.*;

/**
 * Represents the rules that dictate how the game is played.
 *
 * @author Garrett Smith, gsmith at northwestern dot edu
 * @version Blackjack v1.0
 * @since Blackjack v1.0
 */
public class Rules {

	private boolean _dealerStandOnSoft17 = true;
	private boolean _canSurrenderEarly = false;
	private boolean _canDoubleAfterSplit = true;
	private boolean _isResplittingAcesAllowed = false;
	private boolean _isDoubleDownRestricted = false;
	private boolean _canSurrenderLate = true;
	private int _maxSplits = 3;
	private int _numDecks = 6;
	private double _blackjackPayoff = 1.5;
	private double _shuffleLimit = 0.2;

	/**
	 * Property key that sets what the blackjack multiple is; equal to
	 * <code>"blackJackPayoff"</code>.  The value should be a double.  
	 */
	static final String BLACKJACK_PAYOFF = "blackJackPayoff";

	/**
	 * Property key whose value is whether early surrender is allowed; equal to
	 * <code>"isEarlySurrenderAllowed"</code>.  The value should be a boolean.  
	 */
    static final String EARLY_SURRENDER = "isEarlySurrenderAllowed";

	/**
	 * Property key whose value is whether doubling after split is allowed; equal to
	 * <code>"isDoubleAfterSplitAllowed"</code>.  The value should be a boolean.  
	 */
    static final String DOUBLE_AFTER_SPLIT= "isDoubleAfterSplitAllowed";

	/**
	 * Property key whose value is whether late surrender is allowed; equal to
	 * <code>"isLateSurrenderAllowed"</code>.  The value should be a boolean.
	 */
    static final String LATE_SURRENDER = "isLateSurrenderAllowed";

	/**
	 * Property key whose value is the maximum number of splits allowed; equal to
	 * <code>"maxNumRegularSplits"</code>.  The value should be a int.
	 */
    static final String MAX_SPLITS = "maxNumRegularSplits";

	/**
	 * Property key whose value is the number of decks in the shoe; equal to
	 * <code>"numberOfDecks"</code>.  The value should be an int.
	 */
    static final String NUM_DECKS = "numberOfDecks";

	/**
	 * Property key whose value is whether splitting already-split aces is allowed; equal to
	 * <code>"isResplittingAcesAllowed"</code>.  The value should be a boolean.
	 */
    static final String RESPLIT_ACES = "isResplittingAcesAllowed";

	/**
	 * Property key whose value is whether double down is allowed on only 9s, 10s, and 11s; equal to
	 * <code>"isDoubleOnlyAllowedOn91011"</code>.  The value should be a boolean.
	 */
    static final String RESTRICTED_DOUBLE = "isDoubleOnlyAllowedOn91011";

	/**
	 * Property key whose value is whether dealer stands on a soft 17; equal to
	 * <code>"doesDealerStandOnSoft17"</code>.  The value should be a boolean.
	 */
    static final String STAND_ON_SOFT_17 = "doesDealerStandOnSoft17";

	/**
	 * Property key whose value is a what percent the deck is shuffled; equal to
	 * <code>"shufflePercentLimit"</code>.  The value should be a double 0 to 1 inclusive.
	 */
    static final String SHUFFLE_LIMIT = "shufflePercentLimit";

    /**
     * Creates an object with the default rules.
     */
	public Rules() {}

    /**
     * Creates an object with the rules specified by the properties passed in.
     */
	public Rules( Properties properties ) {
		this.load( properties );
	}

	/**
	 * Returns whether the dealer stands on a soft 17.
	 */
	public boolean doesDealerStandOnSoft17() {
		return _dealerStandOnSoft17;
	}

	/**
	 * Returns the multiple of the original wager that is paid when the player
	 * has blackjack.
	 */
	public double getBlackjackPayoff() {
		return _blackjackPayoff;
	}

	/**
	 * Returns whether the player can double down after splitting.
	 */
	public boolean canDoubleAfterSplit() {
		return _canDoubleAfterSplit;
	}

	/**
	 * Returns whether the player can surrender early.
	 */
	public boolean canSurrenderEarly() {
		return _canSurrenderEarly;
	}
	
	/**
	 * Returns whether the player can surrender late.
	 */
	public boolean canSurrenderLate() {
		return _canSurrenderLate;
	}

	/**
	 * Returns the maximum number of splits allowed in one hand.
	 */
	public int getMaxSplits() {
		return _maxSplits;
	}

	/**
	 * Returns the number of decks used in the shoe for this game.
	 */
	public int getNumberOfDecks() {
		return _numDecks;
	}

	/**
	 * Returns whether splitting already-split aces is allowed.
	 */
	public boolean isResplittingAcesAllowed() {
		return _isResplittingAcesAllowed;
	}

	/**
	 * Returns whether double down is only allowed on 9s, 10s, and 11s.
	 */
	public boolean isDoubleDownRestricted() {
		return _isDoubleDownRestricted;
	}

	/**
	 * Returns the percent use, as a double between 0 and 1, at which the shoe is reshuffled.
	 */
	public double getShuffleLimit() {
		return _shuffleLimit;
	}

	void setMaxSplits(Properties props) {
		String value = props.getProperty( MAX_SPLITS );
		if ( value != null ) {
			try {
				setMaxSplits( Integer.parseInt( value ) );
			}
			catch ( NumberFormatException nfe ) {}
		}
	}

	void setMaxSplits(int i) {
		_maxSplits = i;
	}

	private void setBlackjackPayoff(Properties props) {
		String value = props.getProperty( BLACKJACK_PAYOFF );
		if ( value != null ) {
			try {
				setBlackjackPayoff( java.lang.Double.parseDouble( value ) );
			}
			catch ( NumberFormatException nfe ) {}
		}
	}

	void setBlackjackPayoff( double payoff ) {
		_blackjackPayoff = payoff;
	}
	
	void setNumberOfDecks(Properties props) {
		String value = props.getProperty( NUM_DECKS );
		if ( value != null ) {
			try {
				_numDecks = Integer.parseInt( value );
			}
			catch ( NumberFormatException e ) {}
		}
	}

	void setShuffleLimit(Properties props) {
		String value = props.getProperty( SHUFFLE_LIMIT );
		if ( value != null ) {
			try {
				_shuffleLimit = java.lang.Double.parseDouble( value );
			}
			catch ( NumberFormatException nfe ) {}
		}
	}
	
	void setDealerStandOnSoft17( boolean value ) {
		_dealerStandOnSoft17 = value;
	}
    
	private void load(Properties props) {
        setBlackjackPayoff( props );
        setMaxSplits( props );
        setNumberOfDecks( props );
        setShuffleLimit( props );
        setDealerStandOnSoft17(
        	Boolean.valueOf( props.getProperty( STAND_ON_SOFT_17 ) ).booleanValue() );
        _canSurrenderEarly =
        	Boolean.valueOf( props.getProperty( EARLY_SURRENDER ) ).booleanValue();
        _canSurrenderLate =
        	Boolean.valueOf( props.getProperty( LATE_SURRENDER ) ).booleanValue();
        setCanDoubleAfterSplit( 
        		Boolean.valueOf( props.getProperty( DOUBLE_AFTER_SPLIT ) ).booleanValue() );
        setIsResplittingAcesAllowed( 
        	Boolean.valueOf( props.getProperty( RESPLIT_ACES ) ).booleanValue() );
        _isDoubleDownRestricted =
        	Boolean.valueOf( props.getProperty( RESTRICTED_DOUBLE ) ).booleanValue();
	}

	void setIsResplittingAcesAllowed(boolean b) {
		_isResplittingAcesAllowed = b;
	}

	void setCanDoubleAfterSplit(boolean b) {
		_canDoubleAfterSplit = b;
	}
	
	void setCanSurrenderLate( boolean canSurrenderLate ) {
		_canSurrenderLate = canSurrenderLate;
	}

	void setCanSurrenderEarly(boolean value) {
		_canSurrenderEarly = value;
	}
}