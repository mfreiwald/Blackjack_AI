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

// $Id: Hand.java,v 1.19 2005/06/04 08:50:51 gsmith Exp $

package garrettsmith.blackjack;

import garrettsmith.playingcards.*;

/**
 * Represents a player's hand of cards.
 *
 * @author Garrett Smith, gsmith at northwestern dot edu
 * @version Blackjack v1.0
 * @since Blackjack v1.0
 */
public class Hand {

    private        CardList     _cards        = null;
    private final  Blackjack    _blackjack;
    private        boolean      _isPlayerDone = false;
    private        boolean      _isDealerPlayNeeded = true;
    private        double       _wager;
    private        SplitCounter _splitCounter = new SplitCounter();
    private        boolean      _isEvaluationNeeded = true;

    Hand( final double wager,
          final Blackjack blackjack,
          final CardList cards ) {
          _wager = wager;
          _blackjack = blackjack;
          _cards = cards;
    }

    /**
     * Returns the highest hand value without busting, if possible.  For example, a hand
     * with a nine and an ace is scored as 20, but a hand with a nine, five, and an
     * ace is worth 15.
     * 
     * @see #getValue()
     */
    public
    int getBestValue() {
        return Blackjack.calculateBestValue( _cards );
    }

    /**
     * Returns the value of the hand assuming all aces are worth 1.
     *
     * @return the  value of the hand assuming any aces are worth 1
     * @see #getBestValue()
     */
    public
    int getValue() {
        return Blackjack.calculateValue( _cards );
    }
    
    /**
     * Returns the player's cards that represent this hand.
     *
     * @return a <code>List</code> of <code>Card</code> objects
     * @see garrettsmith.playingcards.Card
     */
    public
    CardList getCards() {
        return _cards;
    }

    /**
     * Returns the card that the dealer displays to the players.
     *
     * @return corresponding to the card that the dealer displays to the
     *         players.
     */
    public
    Card getDealerCard() {
        return _blackjack.getDealerCard();
    }

    /**
     * Calculates the value of the dealer's card assuming an ace is worth 1.
     *
     * @return the value of the dealer's card assuming an ace is worth 1
     */
    public
    int getDealerValue() {
        return Blackjack.calculateValue( _blackjack.getDealerCard() );
    }

    /**
     * Returns whether this hand has gone over 21.
     */
    public
    boolean isBusted() {
        return ( Blackjack.calculateValue( _cards ) > 21 );
    }

    /**
     * Returns whether a double down is allowed at this point in the game.
     *
     * @return <code>true</code> if a double down is allowed, <code>false</code>
     *         if not
     */
    public
    boolean isDoubleDownAllowed() {
        int value = this.getValue();
        if ( _cards.size() != 2 ) return false;
        if ( hasSplit() && !_blackjack.getRules().canDoubleAfterSplit() ) return false;
        if ( ( value < 9 || value > 11 ) && _blackjack.getRules().isDoubleDownRestricted() )
            return false;
        return true;
    }

    /**
     * Returns whether standing is allowed at this point in the game.
     */
    public
    boolean isStandAllowed() {
        return true;
    }

    /**
     * Returns the amount that was bet on this hand.
     */
    public
    double getWager() {
        return _wager;
    }

    /**
     * Returns whether <code>move</code> is allowed at this point in the game.
     */
    public
    boolean isMoveAllowed( Move move ) {
        if ( Move.HIT.equals( move ) ) {
            return this.isHitAllowed();
        }
        else if ( Move.STAND.equals( move ) ) {
            return this.isStandAllowed();
        }
        else if ( Move.SURRENDER.equals( move ) ) {
            return this.isSurrenderAllowed();
        }
        else if ( Move.DOUBLE.equals( move ) ) {
            return this.isDoubleDownAllowed();
        }
        else if ( Move.SPLIT.equals( move ) ) {
            return this.isSplitAllowed();
        }
        else {
            throw new IllegalStateException( "unknown move: " + move.value() );
        }
    }

    /**
     * Returns whether a hit is allowed at this point in the hand.
     *
     * @return <code>true</code> if a hit is allowed, <code>false</code> if not
     */
    public
    boolean isHitAllowed() {
        return ( this.getValue() <= 21 );
    }

    /**
     * Tells whether the hand is soft.  A soft hand is where one of the cards
     * in the hand is an ace and where ace may take the value of 1 or 11
     * without causing the total value of the hand to exceed 21.
     *
     * @return <code>true</code> if the hand is soft, <code>false</code> if
     *          not
     */
    public
    boolean isSoft() {
        return Blackjack.isSoft( _cards );
    }

    /**
     * Returns whether a split is allowed at this point in the hand.
     *
     * @return <code>true</code> if a split is allowed, <code>false</code> if
     *         not
     */
    public
    boolean isSplitAllowed() {

        // you must have 2 cards and only 2 cards to split
        if ( _cards.size() != 2 ) return false;
        Card card1 = ( Card ) _cards.get( 0 );
        Card card2 = ( Card ) _cards.get( 1 );

        // you may only split two face cards or two identical non-face cards
        final boolean hasTwoFaceCards = Blackjack.isNonAceFaceCard( card1 ) && Blackjack.isNonAceFaceCard( card2 );
        final boolean hasIdenticalCards = card1.getValue().equals( card2.getValue() );
        if ( !( hasTwoFaceCards || hasIdenticalCards ) ) return false;

        // the rules may only allow one original hand to be split a few times;
        // check to make sure that the maximum hasn't been exceeded
        if ( this.getSplitCount() >= _blackjack.getRules().getMaxSplits() ) return false;

        // you may resplit aces depending on the rules
        if ( hasSplit() 
             && Card.Value.ACE.equals( card1.getValue() )
             && ! _blackjack.getRules().isResplittingAcesAllowed() ) return false;

        // if all of these conditions have been met, a split is allowed
        return true;
    }

    /**
     * Returns the state of this hand as a human-readable String.
     */
    public String toString() {
        return "Hand[_wager=" + _wager + ",_isPlayerDone=" 
            + _isPlayerDone + ",_cards=" + Blackjack.cardsToString( _cards ) + "]";
    }

    /**
     * Returns whether a surrender is allowed.
     *
     * @return <code>true</code> if a surrender is allowed, <code>false</code>
     *         if not
     */
    public
    boolean isSurrenderAllowed() {
        return ( _cards.size() == 2
                 && _blackjack.getRules().canSurrenderLate()
                 && !hasSplit() );
    }

    /**
     * Performs a double down.  A single, final card is issued and the
     * player's wager is doubled.  No additional action can be taken
     * after a double down.
     *
     * @returns a <code>List</code> of <code>Card</code>s that represent
     *          the player's final hand
     * @throws NotAllowedException if a double down is not allowed
     */
    void doubleDown() 
        throws NotAllowedException, NoMoreCardsException {
        if ( !this.isDoubleDownAllowed() ) {
            throw new NotAllowedException( "double down" );
        }
        _cards.add( _blackjack.getCard() );
        _isPlayerDone = true;
        _wager *= 2;
    }

    boolean isInPlay() {
        return ( !isPlayerDone() && !isBusted() );
    }

    boolean isEvaluationNeeded() {
        return _isEvaluationNeeded;
    }

    void surrender() {
        _isPlayerDone = true;
        _isDealerPlayNeeded = false;
        _isEvaluationNeeded = false;
    }

    boolean isDealerPlayNeeded() {
        return _isDealerPlayNeeded;
    }
    
    int getSplitCount() {
        return _splitCounter.getCount();
    }

    /**
     * Performs a hit.  A single card is issued.
     *
     * @returns a <code>List</code> of <code>Card</code> objects that
     *          represent the player's current hand
     * @throws NotAllowedException if a hit is not allowed
     */
    void hit()
        throws NotAllowedException, NoMoreCardsException {
        if ( !this.isHitAllowed() ) {
            throw new NotAllowedException( "a hit is not allowed at this time");
        }
        _cards.add( _blackjack.getCard() );
    }

    boolean isPlayerDone() {
        return _isPlayerDone;
    }

    /**
     * Performs a split.  A reference to a new, second
     * <code>Hand</code> will be returned; the 
     * <code>Hand</code> that this method was invoked on will
     * represent the other hand.
     *
     * @returns representing the new, second hand resulting from the split
     */
    Hand split()
        throws NotAllowedException, NoMoreCardsException {
        if ( !this.isSplitAllowed() ) {
            throw new NotAllowedException( "split is not allowed" );
        }
        CardList newCards = new CardList();
        newCards.add( _cards.remove( 0 ) );
        _cards.add( _blackjack.getCard() );
        newCards.add( _blackjack.getCard() );
        _splitCounter.increment();
        return new Hand( _wager, _blackjack, newCards, _splitCounter );
    }

    void stand()
        throws NotAllowedException {
        if ( !this.isStandAllowed() ) {
            throw new NotAllowedException( "stand is not allowed at this time");
        }
        _isPlayerDone = true;
    }

    void addCard() {
        _cards.add( _blackjack.getCard() );
    }

    private
    Hand( double wager,
          Blackjack blackjack,
          CardList newCards,
          SplitCounter splitCounter ) {
        this( wager, blackjack, newCards );
        _splitCounter = splitCounter;
    }

    private boolean hasSplit() {
        return _splitCounter._count > 0;
    }

    private class SplitCounter {

        private int _count = 0;

        private SplitCounter() {}

        private int getCount() {
            return _count;
        }

        private void increment() {
            _count++;
        }
    }
}