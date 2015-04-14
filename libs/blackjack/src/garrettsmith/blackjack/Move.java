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

package garrettsmith.blackjack;

import garrettsmith.playingcards.CardList;

/**
 * Represents a player's move, such as hitting or standing.
 * 
 * @author Garrett Smith, gsmith at northwestern dot edu
 * @version Blackjack v1.0
 * @since Blackjack v1.0
 */
public interface Move {

    /**
     * The numeric value of the move used only internally for equality comparison.
     */
    public int value();

    /**
     * Executes the move; should only be invoked internally by the framework.
     */
    public Hand execute( Hand hand, EventHandler handler, CardList dealerCards );

    /**
     * Single instance of the move that represents hitting.
     */
    public static final Move HIT = new Hit();

    /**
     * Single instance of the move that represents standing.
     */
    public static final Move STAND = new Stand();

    /**
     * Single instance of the move that represents surrendering.
     */
    public static final Move SURRENDER = new Surrender();

    /**
     * Single instance of the move that represents doubling down.
     */
    public static final Move DOUBLE = new Double();

    /**
     * Single instance of the move that represents splitting the hand.
     */
    public static final Move SPLIT = new Split();
}

abstract class BaseMove implements Move {
    
    public abstract int value();
    
    public int hashCode() {
        return this.getClass().getName().hashCode();
    }
    
    public boolean equals( Object o ) {
        return ( o != null
                && this.getClass().isInstance( o )
                && ( (Move) o).value() == this.value() );
    }
    
    public Hand execute( Hand hand, EventHandler handler, CardList dealerCards ) {
        if ( ! hand.isMoveAllowed( this ) ) {
            throw new NotAllowedException( this.toString() );
        }
        return doMove( hand, handler, dealerCards );
    }

    protected abstract Hand doMove( Hand hand, EventHandler handler, CardList dealerCards );
}

final class Hit extends BaseMove {

    Hit() {}
    
    public int value() {
        return 0;
    }
    
    public Hand doMove( 
            final Hand hand, 
            final EventHandler handler,
            final CardList dealerCards ) {
        hand.hit();
        return null;
    }
}

final class Stand extends BaseMove {

    Stand() {}

    public int value() {
        return 1;
    }

    public Hand doMove( 
            final Hand hand, 
            final EventHandler handler,
            final CardList dealerCards ) {
        hand.stand();
        return null;
    }
}

final class Surrender extends BaseMove {

    Surrender() {}
    
    public int value() {
        return 2;
    }

    public Hand doMove( 
            final Hand hand, 
            final EventHandler handler,
            final CardList dealerCards ) {
        hand.surrender();
        handler.handFinished( 
                hand,
                -0.5 * hand.getWager(),
                Result.LATE_SURRENDER,
                dealerCards );
        return null;
    }
}

final class Double extends BaseMove {

    Double() {}
    
    public int value() {
        return 3;
    }
    
    public Hand doMove( 
            final Hand hand, 
            final EventHandler handler,
            final CardList dealerCards ) {
        hand.doubleDown();
        return null;
    }
}

final class Split extends BaseMove {

    Split() {}

    public int value() {
        return 4;
    }
    
    public Hand doMove( 
            final Hand hand, 
            final EventHandler handler,
            final CardList dealerCards ) {
        return hand.split();
    }
}