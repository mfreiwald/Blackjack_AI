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

// $Id: CardList.java,v 1.4 2005/06/04 08:50:51 gsmith Exp $

package garrettsmith.playingcards;

import java.util.*;

/**
 * Provides a List with some protection to ensure only {@link Card} objects are
 * put into the list.
 * 
 * @author Garrett Smith, gsmith at northwestern dot edu
 * @version Blackjack v1.0
 * @since Blackjack v1.0
 */
public final class CardList extends ArrayList {

	/**
	 * Creates an empty card list with an initial capaciy of 10.
	 */
	public CardList() {
		super( 10 );
	}
	
	/**
	 * Creates an empty card list with an initial capaciy of <code>capacity</code>.
	 */
	public CardList( final int capacity ) {
		super( capacity );
	}

	/**
	 * Creates a card list which contains the contents of <code>cards</code>.
	 */
	public CardList( CardList cards ) {
		super( cards );
	}

	/**
	 * Adds a card to the list.
	 * 
	 * @throws IllegalArgumentException if <code>o</code> isn't a {@link Card}.
	 */
	public boolean add( final Object o ) {
		if ( ! ( o instanceof Card ) ) {
			throw new IllegalArgumentException( "must be a card" );
		}
		return super.add( o );
	}
	
	/**
	 * Returns a {@link Card} from the specified index.
	 */
	public Card getCard( final int index ) {
		return (Card) super.get( index );
	}

	/**
	 * Removes a {@link Card} at the specified index.
	 */
	public Card removeCard(int i) {
		return (Card) super.remove( i );
	}
}