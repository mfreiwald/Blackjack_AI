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

// $Id: StandardDeck.java,v 1.4 2005/06/04 08:50:51 gsmith Exp $

package garrettsmith.playingcards;

import java.util.*;

import garrettsmith.playingcards.Card.Color;
import garrettsmith.playingcards.Card.Suit;
import garrettsmith.playingcards.Card.Value;

/**
 * <p>
 * Represents a standard deck of 52 playing cards.
 * </p><p>
 * This class is not thread safe.
 * </p><p>
 * This deck contains 13 cards (two through ten, Jack, Queen, King, and Ace)
 * in 4 suits (diamonds, hearts, spades, and clubs).  It has no jokers.
 * </p>
 * @author Garrett Smith, gsmith at northwestern dot edu
 * @version Blackjack v1.0
 * @since Blackjack v1.0
 */
public class StandardDeck
    implements Deck {

    final List _cards;

    /**
	 * Singleton instance of this immutable deck.
	 */
    public static final StandardDeck DECK = new StandardDeck();

    /**
     * Returns a nonrandom list of all 52 cards contained in a fresh deck.
     *
     * @return a nonrandom list of all 52 cards contained in a fresh deck.
     */
    public
    List getAllCards() {
        return _cards;
    }

    /**
     * Returns the number of cards in this deck: 52 for this specific deck.
     *
     * @return the number of cards in this deck.
     */
    public
    int size() {
        return 52;
    }

    /**
     * Returns a string representation of this deck.
     *
     * @return a string representing the deck
     */
    public
    String toString() {
        return getClass().getName();
    }

    private StandardDeck() {
        ArrayList cards = new ArrayList( 52 );
        cards.add( new Card( Card.Value.TWO, Card.Suit.SPADE ) );
        cards.add( new Card( Card.Value.THREE, Card.Suit.SPADE ) );
        cards.add( new Card( Card.Value.FOUR, Card.Suit.SPADE ) );
        cards.add( new Card( Card.Value.FIVE, Card.Suit.SPADE  ) );
        cards.add( new Card( Card.Value.SIX, Card.Suit.SPADE ) );
        cards.add( new Card( Card.Value.SEVEN, Card.Suit.SPADE ) );
        cards.add( new Card( Card.Value.EIGHT, Card.Suit.SPADE ) );
        cards.add( new Card( Card.Value.NINE, Card.Suit.SPADE ) );
        cards.add( new Card( Card.Value.TEN, Card.Suit.SPADE ) );
        cards.add( new Card( Card.Value.JACK, Card.Suit.SPADE ) );
        cards.add( new Card( Card.Value.QUEEN, Card.Suit.SPADE ) );
        cards.add( new Card( Card.Value.KING, Card.Suit.SPADE  ) );
        cards.add( new Card( Card.Value.ACE, Card.Suit.SPADE ) );

        cards.add( new Card( Card.Value.TWO, Card.Suit.CLUB ) );
        cards.add( new Card( Card.Value.THREE, Card.Suit.CLUB ) );
        cards.add( new Card( Card.Value.FOUR, Card.Suit.CLUB ) );
        cards.add( new Card( Card.Value.FIVE, Card.Suit.CLUB ) );
        cards.add( new Card( Card.Value.SIX, Card.Suit.CLUB ) );
        cards.add( new Card( Card.Value.SEVEN, Card.Suit.CLUB ) );
        cards.add( new Card( Card.Value.EIGHT, Card.Suit.CLUB ) );
        cards.add( new Card( Card.Value.NINE, Card.Suit.CLUB ) );
        cards.add( new Card( Card.Value.TEN, Card.Suit.CLUB ) );
        cards.add( new Card( Card.Value.JACK, Card.Suit.CLUB ) );
        cards.add( new Card( Card.Value.QUEEN, Card.Suit.CLUB ) );
        cards.add( new Card( Card.Value.KING, Card.Suit.CLUB ) );
        cards.add( new Card( Card.Value.ACE, Card.Suit.CLUB ) );

        cards.add( new Card( Card.Value.TWO, Card.Suit.HEART ) );
        cards.add( new Card( Card.Value.THREE, Card.Suit.HEART  ) );
        cards.add( new Card( Card.Value.FOUR, Card.Suit.HEART ) );
        cards.add( new Card( Card.Value.FIVE, Card.Suit.HEART ) );
        cards.add( new Card( Card.Value.SIX, Card.Suit.HEART ) );
        cards.add( new Card( Card.Value.SEVEN , Card.Suit.HEART ) );
        cards.add( new Card( Card.Value.EIGHT, Card.Suit.HEART ) );
        cards.add( new Card( Card.Value.NINE, Card.Suit.HEART ) );
        cards.add( new Card( Card.Value.TEN, Card.Suit.HEART ) );
        cards.add( new Card( Card.Value.JACK, Card.Suit.HEART ) );
        cards.add( new Card( Card.Value.QUEEN, Card.Suit.HEART ) );
        cards.add( new Card( Card.Value.KING, Card.Suit.HEART ) );
        cards.add( new Card( Card.Value.ACE, Card.Suit.HEART ) );

        cards.add( new Card( Card.Value.TWO, Card.Suit.DIAMOND ) );
        cards.add( new Card( Card.Value.THREE, Card.Suit.DIAMOND ) );
        cards.add( new Card( Card.Value.FOUR, Card.Suit.DIAMOND ) );
        cards.add( new Card( Card.Value.FIVE, Card.Suit.DIAMOND ) );
        cards.add( new Card( Card.Value.SIX, Card.Suit.DIAMOND ) );
        cards.add( new Card( Card.Value.SEVEN, Card.Suit.DIAMOND ) );
        cards.add( new Card( Card.Value.EIGHT, Card.Suit.DIAMOND ) );
        cards.add( new Card( Card.Value.NINE, Card.Suit.DIAMOND ) );
        cards.add( new Card( Card.Value.TEN, Card.Suit.DIAMOND ) );
        cards.add( new Card( Card.Value.JACK, Card.Suit.DIAMOND ) );
        cards.add( new Card( Card.Value.QUEEN, Card.Suit.DIAMOND ) );
        cards.add( new Card( Card.Value.KING,  Card.Suit.DIAMOND  ) );
        cards.add( new Card( Card.Value.ACE, Card.Suit.DIAMOND ) );
        _cards = Collections.unmodifiableList( cards );
    }

    /**
     * {@inheritDoc}
     */
    public int getNumCardsOfType( final Color color ) {
        if ( color == null ) {
            throw new NullPointerException( "color" );
        }
        if ( Color.NONE.equals( color ) ) {
            return 0;
        }
        else {
            return 26;
        }
    }

    /**
     * {@inheritDoc}
     */
    public int getNumCardsOfType( final Suit suit ) {
        if ( suit == null ) {
            throw new NullPointerException( "suit" );
        }
        if ( Suit.NONE.equals( suit ) ) {
            return 0;
        }
        else {
            return 13;
        }
    }

    /**
     * {@inheritDoc}
     */
    public int getNumCardsOfType( final Value value ) {
        if ( value == null ) {
            throw new NullPointerException( "value" );
        }
        if ( Value.JOKER.equals( value ) ) {
            return 0;
        }
        else {
            return 4;
        }
    }

} // class StandardDeck