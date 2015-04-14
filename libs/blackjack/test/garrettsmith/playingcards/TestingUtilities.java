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

// $Id: TestingUtilities.java,v 1.4 2005/06/04 08:50:51 gsmith Exp $

package garrettsmith.playingcards;

// Java packages
import java.lang.reflect.*;
import java.util.*;

// third-party packages
import junit.framework.*;

// garrettsmith.net packages
import garrettsmith.playingcards.Card.Suit;
import garrettsmith.playingcards.Card.Value;
import garrettsmith.playingcards.Card.Color;

/**
 *
 * @author Garrett Smith, gsmith at northwestern dot edu
 * @version [name of product], RCS $Revision: 1.4 $
 */
public class TestingUtilities {

    public static
    void checkTypeDistribution( CardContainer container, Deck deck, int numDecks ) {
        try {
            Assert.assertEquals( "deck size", deck.size() * numDecks, container.size() );
            Assert.assertEquals( "number of spades",
                          deck.getNumCardsOfType( Suit.SPADE ) * numDecks,
                          countNumSuit( getCards( container ), Suit.SPADE ) );
            Assert.assertEquals( "number of clube",
                          deck.getNumCardsOfType( Suit.CLUB ) * numDecks,
                          countNumSuit( getCards( container ), Suit.CLUB ) );
            Assert.assertEquals( "number of hearts",
                          deck.getNumCardsOfType( Suit.HEART ) * numDecks,
                          countNumSuit( getCards( container ), Suit.HEART ) );
            Assert.assertEquals( "number of diamonds",
                          deck.getNumCardsOfType( Suit.DIAMOND ) * numDecks,
                          countNumSuit( getCards( container ), Suit.DIAMOND ) );
            Assert.assertEquals( "number of twos",
                          deck.getNumCardsOfType( Value.TWO ) * numDecks,
                          countNumValue( getCards( container ), Value.TWO ) );
            Assert.assertEquals( "number of threes",
                          deck.getNumCardsOfType( Value.THREE ) * numDecks,
                          countNumValue( getCards( container ), Value.THREE ) );
            Assert.assertEquals( "number of fours",
                          deck.getNumCardsOfType( Value.FOUR ) * numDecks,
                          countNumValue( getCards( container ), Value.FOUR ) );
            Assert.assertEquals( "number of fives",
                          deck.getNumCardsOfType( Value.FIVE ) * numDecks,
                          countNumValue( getCards( container ), Value.FIVE ) );
            Assert.assertEquals( "number of sixes",
                          deck.getNumCardsOfType( Value.SIX ) * numDecks,
                          countNumValue( getCards( container ), Value.SIX ) );
            Assert.assertEquals( "number of sevens",
                          deck.getNumCardsOfType( Value.SEVEN ) * numDecks,
                          countNumValue( getCards( container ), Value.SEVEN) );
            Assert.assertEquals( "number of eights",
                          deck.getNumCardsOfType( Value.EIGHT ) * numDecks,
                          countNumValue( getCards( container ), Value.EIGHT ) );
            Assert.assertEquals( "number of nines",
                          deck.getNumCardsOfType( Value.NINE ) * numDecks,
                          countNumValue( getCards( container ), Value.NINE ) );
            Assert.assertEquals( "number of ten",
                          deck.getNumCardsOfType( Value.TEN ) * numDecks,
                          countNumValue( getCards( container ), Value.TEN ) );
            Assert.assertEquals( "number of jacks",
                          deck.getNumCardsOfType( Value.JACK ) * numDecks,
                          countNumValue( getCards( container ), Value.JACK ) );
            Assert.assertEquals( "number of queens",
                          deck.getNumCardsOfType( Value.QUEEN ) * numDecks,
                          countNumValue( getCards( container ), Value.QUEEN ) );
            Assert.assertEquals( "number of kings",
                          deck.getNumCardsOfType( Value.KING ) * numDecks,
                          countNumValue( getCards( container ), Value.KING) );
            Assert.assertEquals( "number of aces",
                          deck.getNumCardsOfType( Value.ACE ) * numDecks,
                          countNumValue( getCards( container ), Value.ACE ) );
            Assert.assertEquals( "number of blacks",
                          deck.getNumCardsOfType( Color.BLACK ) * numDecks,
                          countNumColor( getCards( container ), Color.BLACK ) );
            Assert.assertEquals( "number of reds",
                          deck.getNumCardsOfType( Color.RED ) * numDecks,
                          countNumColor( getCards( container ), Color.RED ) );
            Assert.assertEquals( "number of no-colored cards",
                          deck.getNumCardsOfType( Color.NONE ) * numDecks,
                          countNumColor( getCards( container ), Color.NONE ) );
        }
        catch ( IllegalAccessException e ) {
            throw new RuntimeException( e );
        }
        catch ( NoSuchFieldException e ) {
            throw new RuntimeException( e );
        }
    }

    public static
    List getCards( CardContainer container )
        throws NoSuchFieldException, IllegalAccessException {
        Field field = container.getClass().getDeclaredField( "_cards" );
        field.setAccessible( true );
        return ( List ) field.get( container );
    }

    public static
    int countNumSuit(List cards, Card.Suit suit) {
        Card card = null;
        int numSuit = 0;
        for ( Iterator i = cards.iterator(); i.hasNext(); ) {
            if ( ((Card)i.next()).getSuit().equals( suit ) ) {
                numSuit++;
            }
        }
        return numSuit;
    }

    public static
    int countNumValue( List cards, Value value ) {
        Card card = null;
        int numValue = 0;
        for ( Iterator i = cards.iterator(); i.hasNext(); ) {
            if ( ((Card)i.next()).getValue().equals( value ) ) {
                numValue++;
            }
        }
        return numValue;
    }

    public static
    int countNumColor( List cards, Color color ) {
        int numColor = 0;
        for ( Iterator i = cards.iterator(); i.hasNext(); ) {
            if ( ((Card)i.next()).getColor().equals( color ) ) {
                numColor++;
            }
        }
        return numColor;
    }

}
