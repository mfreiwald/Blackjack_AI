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

// $Id: CardContainer.java,v 1.9 2005/06/04 08:50:51 gsmith Exp $

package garrettsmith.playingcards;

import java.util.*;

/**
 * Represents a collection of cards, typically comprised of one or more decks.
 * 
 * @author Garrett Smith, gsmith at northwestern dot edu
 * @version Blackjack v1.0
 * @since Blackjack v1.0
 */
public class CardContainer {

    private static final 	Random			_RANDOM = new Random();

	private final	       	CardList	   _cards;
	private final          	CardList       _discardedCards;
	private 				boolean        _autoReset;
	private 				Deck           _deck;
	private           		Double         _resetPercent       = null;
	private final     		int            _originalSize;
	private           		Integer        _resetNumber        = null;

	/**
	 * Creates a shuffled container with its cards set to 
	 * those of one <code>deck</code>.
	 * @param deck
	 */
    public
    CardContainer( final Deck deck ) {
        this( deck, 1 );
    }

    /**
     * Creates a shuffled container with cards from <code>numDecks</code> 
     * number of <code>decks</code>.
     */
    public
    CardContainer( final Deck deck, final int numDecks ) {
        this( deck, numDecks, true );
    }


    /**
     * Creates a container with cards from <code>numDecks</code> 
     * number of <code>decks</code>; will be shuffled according to the value of
     * <code>shuffle</code>.
     */
    public
    CardContainer( final Deck deck, final int numDecks, final boolean shuffle ) {
        _deck = deck;
        _originalSize = _deck.size() * numDecks;
        _cards = new CardList( _originalSize );        
        _discardedCards = new CardList( _originalSize );
        for ( int i = 0; i < numDecks; i++ ) {
            _cards.addAll( _deck.getAllCards() );
        }
        if ( shuffle ) shuffle();
    }

    /**
     * Creates an unshuffled container with its cards equal to the contents of
     * <code>cards<code>.
     */
    public
	CardContainer( final CardList cards ) {
    	_originalSize = cards.size();
    	_cards = new CardList( cards );
    	_discardedCards = new CardList( _originalSize );
    }

    /**
     * Returns whether or not auto reset is on.
     *
     * @return <code>true</code> if auto reset is on, <code>false</code> if
     *          not.
     */
    public
    boolean isAutoResetOn() {
        return _autoReset;
    }

    /**
     * Removes and returns a single card from the container.  
     * If both auto reset is on and the reset limit is
     * reached then the container will be reset and shuffled.
     *
     * @return a card taken from the top of the container.
     * @throws NoMoreCardsException if there are no more cards in the container and
     *                              auto reset is not on.
     */
    public
    Card getCard() {
        if ( isResetShuffleOnAndNeeded() ) {
            resetShuffle();
        }
        if ( this.isEmpty() ) {
            throw new NoMoreCardsException( "container is out of cards" );
        }
        Card card = _cards.removeCard( 0 );
        _discardedCards.add( card );
        return card;
    }

    /**
     * Returns whether this container has any cards left in it.
     *
     * @return <code>true</code> if the container has at least one card in it,
     *         <code>false</code> if not.
     */
    public
    boolean hasMoreCards() {
        return ( _cards.size() > 0 );
    }

    /**
     * Returns whether this container is empty.
     *
     * @return <code>true</code> if the container is empty, <code>false</code>
     *         if not.
     */
    public
    boolean isEmpty() {
        return !hasMoreCards();
    }

    /**
     * Resets this container with the original number and distribution of cards;
     * does not re-shuffle (randomly re-order) the container and does not return
     * the cards to their original order.
     */
    public
    void reset() {
        _cards.addAll( _discardedCards );
        _discardedCards.clear();
    }

    /**
     * Resets this container with the original number and type distribution of
     * cards and re-shuffles (randomly re-orders) the cards.
     */
    public
    void resetShuffle() {
        reset();
        shuffle();
    }

    /**
     * Returns the original size of this container.
     */
    public
    int originalSize() {
        return _originalSize;
    }

    /**
     * Returns the number of undealt cards in the container
     * @return the number of undealt cards
     */
    public
    int size() {
        return _cards.size();
    }

    /**
     * <p>
     * Turns on auto reset, where the container is automatically reset AND
     * reshuffled (radomly re-ordered) whenever the number of undealt cards
     * falls below <code>percentage</code> percent the original number.
     * </p><p>
     * If this method is invoked after {@link #setAutoReset( int )},
     * auto reset will still be on but will use a percentage threshold rather
     * than threshold based on the absolute number of cards remaining.
     * </p>
     *
     * @param percentage the percent undealt cards left that must be reached
     *                   before the deck is reset and reshuffled.  Must be
     *                   greater than 0 and less than or equal to 1.0.
     */
    public
    void setAutoReset( double percentage ) {
        if ( percentage <= 0.0 || percentage > 1.0 ) {
            throw new IllegalArgumentException(
                                "percentage must be greater than 0.0 and less "
                                + "than or equal to 1.0: " + percentage );
        }
        _autoReset = true;
        _resetPercent = new Double( percentage );
        _resetNumber = null;
    }

    /**
     * <p>
     * Turns on auto reset, where the container is automatically reset AND
     * reshuffled (radomly re-ordered) whenever the number of undealt cards
     * falls below <code>limit</code>.
     * </p><p>
     * If this method is invoked after {@link #setAutoReset(double)}, auto reset will still be
     * on but will be triggered by the number of cards limit rather than a
     * percentage limit.
     * </p>
     *
     * @param limit the number of undealt cards that the container that must have
     *              less than before the container is reset and reshuffled.
     */
    public
    void setAutoReset( final int limit ) {
        if ( limit <= 0 || limit > _cards.size() + _discardedCards.size() ) {
            throw new IllegalArgumentException(
                "limit must be greater than zero and less than the original "
                + "size of the deck: " + limit );
        }
        _autoReset = true;
        _resetNumber = new Integer( limit );
        _resetPercent = null;
    }

    /**
     * Turns off auto reset.
     *
     * @see #setAutoReset( int )
     * @see #setAutoReset( double )
     */
    public
    void setAutoResetOff() {
        _autoReset = false;
    }

    /**
     * <p>
     * Shuffles (randomly orders) the contents of the container.
     * </p><p>
     * This will not reset the container; only the undealt cards will be shuffled.
     * Use {@link #resetShuffle()} to both reset and shuffle the container.
     * </p>
     */
    public
    void shuffle() {
        Collections.shuffle( _cards, _RANDOM );
    }

    /**
     * Returns a string representation of this container.
     *
     * @return a string representation of the container.
     */
    public
    String toString() {
        StringBuffer ret = new StringBuffer();
        ret.append( "CardContainer=[" );
        int stopPoint = _cards.size() - 1;
        for ( int i = 0; i < stopPoint; i++ ) {
            ret.append( _cards.getCard( i ).toString() + "," );
        }
        ret.append( _cards.getCard( _cards.size() - 1 ).toString() + "]" );
        return ret.toString();
    }  // end String toString()

    private
    boolean isResetShuffleOnAndNeeded() {
        return _autoReset
               && ( isResetShuffleNeededByInt()
                    || isResetShuffleNeededByDouble() );
    }
    
    private
    boolean isResetShuffleNeededByInt() {
        return _resetNumber != null
               && _cards.size() <= _resetNumber.intValue();
    }
    
    private
    boolean isResetShuffleNeededByDouble() {
        return _resetPercent != null
               && ( _resetPercent.doubleValue() * _originalSize )
                     > _cards.size();
    }

}
