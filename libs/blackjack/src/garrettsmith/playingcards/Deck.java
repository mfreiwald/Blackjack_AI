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

// $Id: Deck.java,v 1.4 2005/06/04 08:50:51 gsmith Exp $

package garrettsmith.playingcards;

import java.util.List;

/**
 * <p>
 * Abstractly represents a deck of playing cards with a type distribution.
 * The exact number and type of cards contained in the deck is defined by the
 * concrete class that implements this interface.
 * </p><p>
 * Any class inmplementing this interface should have a public default no-arg
 * constructor.
 * </p>
 * @author Garrett Smith, gsmith at northwestern dot edu
 * @version Blackjack v1.0
 * @since Blackjack v1.0
 */
public interface Deck {

    /**
     * Returns a nonrandom list of all cards contained in a fresh deck.
     * Invoking this method does not change the state of this deck.
     *
     * @return a <code>CardList</code> of {@link Card}s
     */
    public abstract
    List<Card> getAllCards();

    /**
     * Returns the total number of cards in the deck.
     *
     * @return the total number of cards in the deck.
     */
    public abstract
    int size();

    /**
     * Returns the number of cards in the deck whose color is equal to <code>color</code>.
     */
    public abstract
    int getNumCardsOfType( Card.Color color );

    /**
     * Returns the number of cards in the deck whose suit is equal to <code>suite</code>.
     */
    public abstract
    int getNumCardsOfType( Card.Suit suit );

    /**
     * Returns the number of cards in the deck whose value is equal to <code>value</code>.
     */
    public abstract
    int getNumCardsOfType( Card.Value value );

} // interface Deck