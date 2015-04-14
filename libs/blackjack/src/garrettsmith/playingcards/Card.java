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

// $Id: Card.java,v 1.5 2005/06/04 08:50:51 gsmith Exp $

package garrettsmith.playingcards;

/**
 * Represents a card in a typical deck of playing cards.
 * Card is immutable, and, therefore, thread safe.
 *
 * @author Garrett Smith, gsmith at northwestern dot edu
 * @version Blackjack v1.0
 * @since Blackjack v1.0
 */
public final class Card {

    private final Suit       _suit;
    private final Value      _value;
    private final Color      _color;
    private       int        _hashCode;

    /**
     * Creates a joker.
     */
    public
    Card() {
        this( Value.JOKER, null );
    }

    /**
     * <p>
     * Creates a new card with the specified suit and value.
     * </p>
     * 
     * @param suit the card's suit, for example, hearts
     * @param value the card's value, for example, a ten
     */
    public
    Card( final Value value, final Suit suit ) {
        _value = value;
        if ( Value.JOKER.equals( _value ) ) {
            _suit = Suit.NONE;
            _color = Color.NONE;
            return;
        }
        else {
            _suit = suit;
        }
        if ( Suit.HEART.equals( suit ) || Suit.DIAMOND.equals( suit ) ) {
            _color = Color.RED;
        }
        else if ( Suit.CLUB.equals( suit ) || Suit.SPADE.equals( suit ) ) {
            _color = Color.BLACK;
        }
        else {
            _color = Color.NONE;
        }
        _hashCode = ( _suit.hashCode() * 37 + 11 ) * _value.hashCode() + 11;
    }

    /**
     * Returns whether the object referred to in the
     * <code>object</code> parameter is equal to this instance.
     *
     * @return whether the two cards are the same.
     */
    public
    boolean equals( final Object object ) {
        if ( object != null && object instanceof Card ) {
            Card card = ( Card ) object;
            return ( this.getSuit().equals( card.getSuit() ) &&
                     this.getValue().equals( card.getValue() ) );
        }
        return false;
    }

    /**
     * Returns a string representation of this card's color.
     *
     * @return a representation of this card's color.
     */
    public
    Color getColor() {
        return _color;
    }

    /**
     * Returns the card's suit.
     *
     * @return the card's suit.
     */
    public
    Suit getSuit() {
        return _suit;
    }

    /**
     * Returns the card's value.
     *
     * @return the card's value.
     */
    public
    Value getValue() {
        return _value;
    }

    /**
     * Returns a hash value for this card.
     *
     * @return a hash value for this object.
     */
    public
    int hashCode() {
        return _hashCode;
    }

    /**
     * Returns a string representation this card's state.
     *
     * @return representing this card's state.
     */
    public
    String toString() {
        return "Card="
            + "[suit=" + _suit
            + ",value=" + _value
            + "]";
    }

    /**
     * Represents the card's color: red, black, or none.  Colors are immutable.
     */
    public static class Color {

        private int    _value       = -1;
        private int    _hashCode    = -1;
        private String _stringColor = null;

        private static final int _RED_VALUE = 1;
        private static final int _BLACK_VALUE = 2;
        private static final int _NONE_VALUE = 3;

        
        private static final String _RED_STRING = "red";
        private static final String _BLACK_STRING = "black";
        private static final String _NONE_STRING = "none";

        /**
         * Indicates a red card.
         */
        public static final Color RED = new Color( _RED_VALUE, _RED_STRING );
        
        /**
         * Indicates a black card.
         */
        public static final Color BLACK = new Color( _BLACK_VALUE, _BLACK_STRING );        

        /**
         * Indicates a card with no color.
         */
        public static final Color NONE = new Color( _NONE_VALUE, _NONE_STRING );        

        /**
    	 * Returns whether object is a <tt>Color</tt> equal to this one.
    	 * @return <tt>true</tt> if the objects are equal, <tt>false</tt> otherwise
    	 */
        public
        boolean equals( Object object ) {
            return object != null
                   && object instanceof Card.Color 
                   && ( ( Card.Color ) object )._value == this._value;
        }

        /**
         * Returns a hash value for this card.
         *
         * @return a hash value for this object.
         */
        public
        int hashCode() {
            return _hashCode;
        }

        /**
         * Returns a string representation.
         */
        public
        String toString() {
            return _stringColor;
        }

        private Color( int value, String color ) {
            _value = value;
            _hashCode = _value * 37 + 17;
            _stringColor = color;
        }
    } // class Color

    /**
     * Represents a card's suit, such as spades or clubs.
     */
    public static class Suit {
        
    	/**
    	 * Returns whether <tt>object</tt> is equal to this suit.
    	 */
        public
        boolean equals( Object object ) {
            return object != null
                   && object instanceof Card.Suit
                   && ( ( Card.Suit ) object )._value == this._value;
        }

        /**
         * Returns a hash value for this card.
         *
         * @return a hash value for this object.
         */
        public
        int hashCode() {
            return _hashCode;
        }

        /**
         * Returns a string representation of this value.
         */
        public
        String toString() {
            return _stringSuit;
        }
        
        private static final int _CLUB_VALUE = 1;
        private static final int _DIAMOND_VALUE = 2;
        private static final int _HEART_VALUE = 3;
        private static final int _NONE_VALUE = 4;
        private static final int _SPADE_VALUE = 5;

        private static final String _CLUB_STRING = "club";
        private static final String _DIAMOND_STRING = "diamond";
        private static final String _HEART_STRING = "heart";
        private static final String _NONE_STRING = "none";
        private static final String _SPADE_STRING = "spade";

        /**
         * Represents the clubs.
         */
        public static final Suit CLUB = new Suit( _CLUB_VALUE, _CLUB_STRING );        

        /**
         * Represents the diamonds.
         */
        public static final Suit DIAMOND = new Suit( _DIAMOND_VALUE, _DIAMOND_STRING );        

        /**
         * Represents no suit.
         */
        public static final Suit NONE = new Suit( _NONE_VALUE, _NONE_STRING );        

        /**
         * Represents the hearts.
         */
        public static final Suit HEART = new Suit( _HEART_VALUE, _HEART_STRING );

        /**
         * Represents the spades.
         */
        public static final Suit SPADE = new Suit( _SPADE_VALUE, _SPADE_STRING );
        
        protected
        Object clone()
            throws CloneNotSupportedException {
            throw new CloneNotSupportedException();
        }

        private Suit( int value, String suit ) {
            _value = value;
            _hashCode = _value * 37 + 17;
            _stringSuit = suit;
        }
        
        private int    _value      = -1;
        private int    _hashCode   = -1;
        private String _stringSuit = null;
        
    } // class Suit

    /**
     * Represents a card's value: two, queen, or ace, for example.
     */
    public static class Value {

    	/**
    	 * Returns whether <tt>object</tt> is equal to this Value.
    	 */
        public
        boolean equals( Object object ) {
            return object != null
                   && object instanceof Card.Value
                   && ( ( Card.Value ) object )._value == this._value;
        }

        /**
         * Returns a hash value for this card.
         *
         * @return a hash value for this object.
         *
         * @see <a href="http://java.sun.com/j2se/1.3/docs/api/java/lang/Object.html#hashCode()">the
         *      <code>java.lang.Object</code> API specification</a>
         */
        public
        int hashCode() {
            return _hashCode;
        }

        /**
         * Returns a string repesentation.
         */
        public
        String toString() {
            return _stringValue;
        }

        private static final int _TWO_VALUE = 1;
        private static final int _THREE_VALUE = 2;
        private static final int _FOUR_VALUE = 3;
        private static final int _FIVE_VALUE = 4;
        private static final int _SIX_VALUE = 5;
        private static final int _SEVEN_VALUE = 6;
        private static final int _EIGHT_VALUE = 7;
        private static final int _NINE_VALUE = 8;
        private static final int _TEN_VALUE = 9;
        private static final int _JACK_VALUE = 10;
        private static final int _QUEEN_VALUE = 11;
        private static final int _KING_VALUE = 12;
        private static final int _ACE_VALUE = 13;
        private static final int _JOKER_VALUE = 14;

        private static final String _TWO_STRING = "two";
        private static final String _THREE_STRING = "three";
        private static final String _FOUR_STRING = "four";
        private static final String _FIVE_STRING = "five";
        private static final String _SIX_STRING = "six";
        private static final String _SEVEN_STRING = "seven";
        private static final String _EIGHT_STRING = "eight";
        private static final String _NINE_STRING = "nine";
        private static final String _TEN_STRING = "ten";
        private static final String _JACK_STRING = "jack";
        private static final String _QUEEN_STRING = "queen";
        private static final String _KING_STRING = "king";
        private static final String _ACE_STRING = "ace";
        private static final String _JOKER_STRING = "joker";

        /**
         * Represents a two.
         */
        public static final Value TWO = new Value( _TWO_VALUE, _TWO_STRING );        

        /**
         * Represents a three.
         */
        public static final Value THREE = new Value( _THREE_VALUE, _THREE_STRING );        

        /**
         * Represents a four.
         */
        public static final Value FOUR = new Value( _FOUR_VALUE, _FOUR_STRING );        

        /**
         * Represents a five.
         */
        public static final Value FIVE = new Value( _FIVE_VALUE, _FIVE_STRING );        

        /**
         * Represents a six.
         */
        public static final Value SIX = new Value( _SIX_VALUE, _SIX_STRING );        

        /**
         * Represents a seven.
         */
        public static final Value SEVEN = new Value( _SEVEN_VALUE, _SEVEN_STRING );        

        /**
         * Represents a eight.
         */
        public static final Value EIGHT = new Value( _EIGHT_VALUE, _EIGHT_STRING );        

        /**
         * Represents a nine.
         */
        public static final Value NINE = new Value( _NINE_VALUE, _NINE_STRING );        

        /**
         * Represents a ten.
         */
        public static final Value TEN = new Value( _TEN_VALUE, _TEN_STRING );        

        /**
         * Represents a jack.
         */
        public static final Value JACK = new Value( _JACK_VALUE, _JACK_STRING );        

        /**
         * Represents a queen.
         */
        public static final Value QUEEN = new Value( _QUEEN_VALUE, _QUEEN_STRING );        

        /**
         * Represents a king.
         */
        public static final Value KING = new Value( _KING_VALUE, _KING_STRING );        

        /**
         * Represents a ace.
         */
        public static final Value ACE = new Value( _ACE_VALUE, _ACE_STRING );       

        /**
         * Represents a joker.
         */
        public static final Value JOKER = new Value( _JOKER_VALUE, _JOKER_STRING );       
        
        private int _value = -1;
        private int _hashCode = -1;
        private String _stringValue = null;

        private Value( int value, String string ) {
            _value = value;
            _hashCode = _value * 37 + 17;
            _stringValue = string;
        }
        
    } // class Value
} // class Card