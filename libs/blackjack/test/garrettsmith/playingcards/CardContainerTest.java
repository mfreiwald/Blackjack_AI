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

public class CardContainerTest extends TestCase {

    public CardContainerTest(String arg0) {
        super(arg0);
    }

    protected void setUp() throws Exception {
        super.setUp();
        _container = new CardContainer( StandardDeck.DECK, _NUM_DECKS );
        _deckTypeUsed = StandardDeck.DECK;
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        _container = null;
    }

    // TODO figure out how to test for randomness/shuffling
    
    public void testOneArgConstructor()
    {
        CardContainer container = new CardContainer( StandardDeck.DECK );
        assertEquals( StandardDeck.DECK.size(), container.size() );
        assertEquals( StandardDeck.DECK.size(), container.originalSize() );
        TestingUtilities.checkTypeDistribution( container, StandardDeck.DECK, 1 );
    }
    
    public void testTwoArgConstructor()
    {
        final int numDecks = 5;
        CardContainer container = new CardContainer( StandardDeck.DECK, numDecks );
        assertEquals( StandardDeck.DECK.size() * numDecks, container.size() );
        assertEquals( StandardDeck.DECK.size() * numDecks, container.originalSize() );
        TestingUtilities.checkTypeDistribution( container, StandardDeck.DECK, numDecks );
    }
    
    public void testThreeArgConstructor()
    {
        final int numDecks = 66;
        CardContainer container = new CardContainer( StandardDeck.DECK, numDecks, true );
        assertEquals( StandardDeck.DECK.size() * numDecks, container.size() );
        assertEquals( StandardDeck.DECK.size() * numDecks, container.originalSize() );
        TestingUtilities.checkTypeDistribution( container, StandardDeck.DECK, numDecks );
    }
    
    public void testDeckWithSpecificCards() {
    	CardList list = new CardList();
    	list.add( new Card( Card.Value.TWO, Card.Suit.SPADE ) );
    	list.add( new Card( Card.Value.ACE, Card.Suit.DIAMOND ) );
    	list.add( new Card( Card.Value.FOUR, Card.Suit.CLUB ) );
    	
    	_container = new CardContainer( new CardList( list ) );
    	
    	for ( int i = 0; i < list.size(); i++ ) {
    		assertTrue( list.get( i ).equals( _container.getCard() ) );
    	}
    }
    
    public void testGetCardAndHasMoreCardsAndIsEmpty() {
        _container.setAutoResetOff();
        Card card = null;
        while ( _container.hasMoreCards() ) {
            card = ( Card ) _container.getCard();
        }
        try {
            assertTrue( "the container should be empty", _container.isEmpty() );
            _container.getCard();
            fail( "should have thrown NoMoreCardsException" );        
        }
        catch ( NoMoreCardsException e ) {}
    }

    public void testSize() {
        assertEquals( _deckTypeUsed.size() * _NUM_DECKS, _container.size() );
    }

    public void testReset() {
        final int numUndealt = 22;
        final int originalSize = _container.size();
        for ( int i = 0; i < originalSize - numUndealt; i++ ) {
            _container.getCard();
        }
        assertEquals( numUndealt, _container.size() );
        _container.reset();
        assertEquals( originalSize, _container.size() );
    }

    public void testResetShuffle() {
        final int numUndealt = 22;
        final int originalSize = _container.size();
        for ( int i = 0; i < originalSize - numUndealt; i++ ) {
            _container.getCard();
        }
        assertEquals( numUndealt, _container.size() );
        _container.resetShuffle();
        assertEquals( originalSize, _container.size() );
        TestingUtilities.checkTypeDistribution( _container, _deckTypeUsed, _NUM_DECKS );
    }

    public void testAutoResetWithDouble() {
        final double resetPercent = 0.2f;
        _container.setAutoReset( resetPercent );
        final int originalSize = _container.size();
        final double resetPoint = resetPercent * originalSize;
        for ( int i = originalSize; i > resetPoint; i-- ) {
            _container.getCard();
        }
        _container.getCard();
        assertEquals( originalSize - 1, _container.size() );
    }

    public void testAutoResetWithInt() {
        final int resetNum = 20;
        final int originalSize = _container.size();
        _container.setAutoReset( resetNum );
        for ( int i = originalSize; i > resetNum; i-- ) {
            _container.getCard();
        }
        assertEquals( resetNum, _container.size() );
        _container.getCard();
        assertEquals( originalSize - 1, _container.size() );
    }
    
    public void testAutoResetOff() {
        _container.setAutoReset( 10 );
        _container.setAutoResetOff();
        for ( int i = _container.originalSize(); i > 0; i-- ) {
            _container.getCard();       
        }
        assertTrue( _container.isEmpty() );
        try {
            _container.getCard();
            fail( "expected NoMoreCardsException" );
        }
        catch ( NoMoreCardsException e ) {}
        catch ( Exception e ) {
            fail( "did not expect a " + e.getClass().getName() );
        }
    }

    public void testIsEmptyAndHadMoreCards() {
        final int originalSize = _container.size();
        for ( int i = originalSize; i > 0; i-- ) {
            assertTrue( _container.hasMoreCards() );
            assertFalse( _container.isEmpty() );
            _container.getCard();
        }
        assertFalse( _container.hasMoreCards() );
        assertTrue( _container.isEmpty() );
    }
    
    private CardContainer _container = null;
    private Deck _deckTypeUsed = null;
   
    private final int _NUM_DECKS = 3;
    
    public static
    void main( String[] arguments ) {
        CardContainer container = new CardContainer( StandardDeck.DECK );
        System.out.println( container.toString() );
    }
    
} // class StandardDeckTest