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

package garrettsmith.playingcards;

import java.util.*;

import garrettsmith.playingcards.Card.Value;
import garrettsmith.playingcards.Card.Color;
import garrettsmith.playingcards.Card.Suit;

import junit.framework.TestCase;

public class StandardDeckTest extends TestCase {

    public StandardDeckTest(String arg0) {
        super(arg0);
    }

    public void testGetAllCards() {
        assertEquals( 26, TestingUtilities.countNumColor( _deck.getAllCards(), Color.RED ) );
        assertEquals( 26, TestingUtilities.countNumColor( _deck.getAllCards(), Color.BLACK ) );
        assertEquals( 0, TestingUtilities.countNumColor( _deck.getAllCards(), Color.NONE ) );
        assertEquals( 13, TestingUtilities.countNumSuit( _deck.getAllCards(), Suit.CLUB ) );
        assertEquals( 13, TestingUtilities.countNumSuit( _deck.getAllCards(), Suit.DIAMOND ) );
        assertEquals( 13, TestingUtilities.countNumSuit( _deck.getAllCards(), Suit.HEART ) );
        assertEquals( 13, TestingUtilities.countNumSuit( _deck.getAllCards(), Suit.SPADE ) );
        assertEquals( 0, TestingUtilities.countNumSuit( _deck.getAllCards(), Suit.NONE ) );
        assertEquals( 4, TestingUtilities.countNumValue( _deck.getAllCards(), Value.TWO ) );
        assertEquals( 4, TestingUtilities.countNumValue( _deck.getAllCards(), Value.THREE) );
        assertEquals( 4, TestingUtilities.countNumValue( _deck.getAllCards(), Value.FOUR ) );
        assertEquals( 4, TestingUtilities.countNumValue( _deck.getAllCards(), Value.FIVE ) );
        assertEquals( 4, TestingUtilities.countNumValue( _deck.getAllCards(), Value.SIX ) );
        assertEquals( 4, TestingUtilities.countNumValue( _deck.getAllCards(), Value.SEVEN ) );
        assertEquals( 4, TestingUtilities.countNumValue( _deck.getAllCards(), Value.EIGHT ) );
        assertEquals( 4, TestingUtilities.countNumValue( _deck.getAllCards(), Value.NINE ) );
        assertEquals( 4, TestingUtilities.countNumValue( _deck.getAllCards(), Value.TEN ) );
        assertEquals( 4, TestingUtilities.countNumValue( _deck.getAllCards(), Value.JACK ) );
        assertEquals( 4, TestingUtilities.countNumValue( _deck.getAllCards(), Value.QUEEN ) );
        assertEquals( 4, TestingUtilities.countNumValue( _deck.getAllCards(), Value.KING ) );
        assertEquals( 4, TestingUtilities.countNumValue( _deck.getAllCards(), Value.ACE ) );
    }

    public void testSize() {
        assertEquals( _expectedSize, _deck.size() );
    }
    
    public
    void testGetNumCardsOfTypeForSuit() {
        assertEquals( 13, _deck.getNumCardsOfType( Suit.CLUB ) );
        assertEquals( 13, _deck.getNumCardsOfType( Suit.HEART ) );
        assertEquals( 13, _deck.getNumCardsOfType( Suit.DIAMOND ) );
        assertEquals( 13, _deck.getNumCardsOfType( Suit.SPADE ) );
        assertEquals( 0, _deck.getNumCardsOfType( Suit.NONE ) );
    }
    
    public
    void testGetNumCardsOfTypeForColor() {
        assertEquals( 26, _deck.getNumCardsOfType( Color.RED ) );
        assertEquals( 26, _deck.getNumCardsOfType( Color.BLACK ) );
        assertEquals( 0, _deck.getNumCardsOfType( Color.NONE ) );
    }
    public
    void testGetNumCardsOfTypeForValue() {
        assertEquals( 4, _deck.getNumCardsOfType( Value.ACE ) );
        assertEquals( 4, _deck.getNumCardsOfType( Value.KING ) );
        assertEquals( 4, _deck.getNumCardsOfType( Value.QUEEN ) );
        assertEquals( 4, _deck.getNumCardsOfType( Value.JACK ) );
        assertEquals( 4, _deck.getNumCardsOfType( Value.TEN ) );
        assertEquals( 4, _deck.getNumCardsOfType( Value.NINE ) );
        assertEquals( 4, _deck.getNumCardsOfType( Value.EIGHT ) );
        assertEquals( 4, _deck.getNumCardsOfType( Value.SEVEN ) );
        assertEquals( 4, _deck.getNumCardsOfType( Value.SIX ) );
        assertEquals( 4, _deck.getNumCardsOfType( Value.FIVE ) );
        assertEquals( 4, _deck.getNumCardsOfType( Value.FOUR ) );
        assertEquals( 4, _deck.getNumCardsOfType( Value.THREE ) );
        assertEquals( 4, _deck.getNumCardsOfType( Value.TWO ) );
        assertEquals( 0, _deck.getNumCardsOfType( Value.JOKER ) );
    }

    private Deck _deck = StandardDeck.DECK;
    
    private static final int _expectedSize = 52;


    private int countNumSuit(List cards, Card.Suit suit) {
        Card card = null;
        int numType = 0;
        for ( Iterator i = cards.iterator(); i.hasNext(); ) {
            if ( ((Card)i.next()).getSuit().equals( suit ) ) {
                numType++;
            }
        }
        return numType;
    }

    private int countNumValue( List cards, Card.Value value ) {
        Card card = null;
        int numValue = 0;
        for ( Iterator i = cards.iterator(); i.hasNext(); ) {
            if ( ((Card)i.next()).getValue().equals( value ) ) {
                numValue++;
            }
        }
        return numValue;
    }
    
} // class StandardDeckTest