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

// $Id: Result.java,v 1.8 2005/06/04 08:50:50 gsmith Exp $

package garrettsmith.blackjack;

/**
 * <p>
 * Represents the result of one hand that is complete.
 * </p>
 * @author Garrett Smith, gsmith at northwestern dot edu
 * @version Blackjack v1.0
 * @since Blackjack v1.0
 */
public interface Result {

    /**
     * Returns the value of the result used only internally for equality comparison.
     */
    public int value();

    /**
     * Represents when both the player and dealer have the same value 
     * hand that is equal to or less than 21. 
     */
    public static final Result PUSH = new PushResult();

    /**
     * Represents when the player has a hand whose value is greater than the dealer's
     * and both hands are equal to or less than 21.
     */
    public static final Result WIN = new WinResult();

    /**
     * Represents when the player has a hand whose value is less than the dealer's
     * and both hands are equal to or less than 21.
     */
    public static final Result LOSE = new LoseResult();

    /**
     * Represents when the player surrenders his hand.
     */
    public static final Result LATE_SURRENDER = new LateSurrenderResult();

    /**
     * Represents when the player surrenders his hand.
     */
    public static final Result EARLY_SURRENDER = new EarlySurrenderResult();

    /**
     * Represents when the dealer has a blackjack.
     */
    public static final Result DEALER_BLACKJACK = new DealerBlackjackResult();

    /**
     * Represents when the player's hand exceeded 21.
     */
    public static final Result BUSTED = new BustedResult();

    /**
     * Represents when the dealer's hand exceeded 21 and the player's hand
     * is equal to or less than 21.
     */
    public static final Result DEALER_BUSTED = new DealerBustedResult();

    /**
     * Represents when the player has a blackjack.
     */
    public static final Result BLACKJACK = new BlackjackResult();
    
    /**
     * Represents when both the player and the dealer have a blackjack.
     */
    public static final Result BLACKJACK_PUSH = new BlackjackPushResult();

}

abstract class BaseResult implements Result {
    
    protected String stringValue;
    
    public abstract int value();
    
    public int hashCode() {
        return this.getClass().getName().hashCode();
    }
    
    public boolean equals( Object o ) {
        return ( o != null
                && this.getClass().isInstance( o )
                && ( (Result) o).value() == this.value() );
    }
    
    public String toString() {
        return stringValue;
    }
}

final class PushResult extends BaseResult {
    PushResult() {
        stringValue = "push";
    }
    public int value() {
        return 0;
    }
}

final class WinResult extends BaseResult {
    WinResult() {
        stringValue = "win";
    }
    public int value() {
        return 1;
    }
}

final class LoseResult extends BaseResult {
    LoseResult() {
        stringValue = "lose";
    }
    public int value() {
        return 2;
    }
}

final class LateSurrenderResult extends BaseResult {
    LateSurrenderResult() {
        stringValue = "late surrender";
    }
    public int value() {
        return 3;
    }
}

final class DealerBlackjackResult extends BaseResult {
    DealerBlackjackResult() {
        stringValue = "dealer blackjack";
    }
    public int value() {
        return 4;
    }
}

final class BustedResult extends BaseResult {
    BustedResult() {
        stringValue = "busted";
    }
    public int value() {
        return 5;
    }
}

final class DealerBustedResult extends BaseResult {
    DealerBustedResult() {
        stringValue = "dealer busted";
    }
    public int value() {
        return 6;
    }
}

final class BlackjackResult extends BaseResult {
    BlackjackResult() {
        stringValue = "blackjack";
    }
    public int value() {
        return 7;
    }
}

final class BlackjackPushResult extends BaseResult {
    BlackjackPushResult() {
        stringValue = "blackjack push";
    }
    public int value() {
        return 8;
    }
}

final class EarlySurrenderResult extends BaseResult {
    EarlySurrenderResult() {
        stringValue = "early surrender";
    }
    public int value() {
        return 9;
    }
}