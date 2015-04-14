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

import junit.framework.TestCase;

public class CardTest extends TestCase {

    /**
     * Constructor for CardTest.
     * @param arg0
     */
    public CardTest(String arg0) {
        super(arg0);
    }

    public void testEquals() {
        Card card = new Card( Card.Value.TEN, Card.Suit.SPADE );
        assertEquals( card, _TEN_SPADE );
        assertFalse( card.equals( _JOKER ) );
    }
    
    public void testHashCode() {
        Card card = new Card( Card.Value.TEN, Card.Suit.SPADE );
        assertEquals( card.hashCode(), _TEN_SPADE.hashCode() );                
    }

    public void testGetColor() {
        assertFalse( Card.Color.NONE.equals( _TEN_SPADE.getColor() ) );
        assertEquals( Card.Color.RED, _TWO_HEART.getColor() );
        assertEquals( Card.Color.NONE, _JOKER.getColor() );
    }

    public void testGetSuit() {
        assertEquals( Card.Suit.SPADE, _TEN_SPADE.getSuit() );
        assertFalse( Card.Suit.HEART.equals( _TEN_SPADE.getSuit() ) );
    }

    public void testGetValue() {
        assertEquals( Card.Value.TWO, _TWO_HEART.getValue() );
        assertEquals( Card.Value.JOKER, _JOKER.getValue() );
        assertFalse( Card.Value.SIX.equals( _TWO_HEART.getValue() ) );
        assertFalse( Card.Value.SIX.equals( _JOKER.getValue() ));
    }

    private static final Card _TEN_SPADE = new Card( Card.Value.TEN, Card.Suit.SPADE );
    private static final Card _JOKER = new Card();
    private static final Card _TWO_HEART = new Card( Card.Value.TWO, Card.Suit.HEART );
}
