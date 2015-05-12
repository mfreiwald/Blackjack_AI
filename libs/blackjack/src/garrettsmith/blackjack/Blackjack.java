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

// $Id: Blackjack.java,v 1.31 2005/06/04 08:50:51 gsmith Exp $

package garrettsmith.blackjack;

import java.util.*;

import ai.agents.main.GameNotifications;
import garrettsmith.playingcards.*;

/**
 * <p>
 * Provides for playing a game of blackjack.
 * </p>
 * 
 * @author Garrett Smith, gsmith at northwestern dot edu
 * @version Blackjack v1.0
 * @since Blackjack v1.0
 */
public class Blackjack {

    private             CardContainer   _cards     = null;
    private             CardList        _dealerCards = new CardList();
    private             Rules           _rules     = null;
    
    private             ArrayList<Hand>       _handsToPlay     = new ArrayList<Hand>();
    private             ArrayList<Hand>       _handsToEvaluate = new ArrayList<Hand>();

    /**
     * Creates a new Blackjack game simulator with the default {@link Rules}.
     */
    public
    Blackjack() {
        this( new Rules() );
    }

    /**
     * Creates a new Blackjack simulator with the specified {@link Rules}.
     *
     * @param rules specifying which rules this simulator will follow
     */
    public
    Blackjack( final Rules rules ) {
        setRules( rules );
        _cards = new CardContainer( StandardDeck.DECK,
                                    rules.getNumberOfDecks() );
        _cards.setAutoReset( rules.getShuffleLimit() );
    }

    /**
     * Calculates the highest possible value for a list of cards without
     * busting, if possible.
     */
    public static
    int calculateBestValue( final CardList cards ) {
        int value = 0;
        int numAces = 0;
        Card card;
        for ( int i = 0; i < cards.size(); i++ ) {
            card = cards.getCard( i );
            if ( Card.Value.ACE.equals( card.getValue() ) ) {
                value += 11;
                numAces++;
            }
            else {
                value += Blackjack.calculateValue( card );
            }
        }
        while ( value > 21 ) {
            if ( numAces == 0 ) return value;
            value -= 10;
            numAces--;
        }
        return value;
    }

    /**
     * Accepts a {@link CardList} and returns its contents as a human-readable String.
     */
    static
    String cardsToString( final CardList cards ) {
        StringBuffer b = new StringBuffer();
        b.append( "Cards[" );
        for ( int i = 0; i < cards.size(); i++ ) {
            b.append( cardToString( cards.getCard( i ) ) );
            if ( i != cards.size() - 1 ) b.append( "," );
        }
        b.append( "]" );
        return b.toString();
    }

    /**
     * Accepts a {@link Card} and returns its contents as a human-readable String.
     */
    static String cardToString(final Card card) {
        return card.getValue().toString();
    }

    /**
     * Calculates the value of a playing card according to the rules of
     * Blackjack; assumes all aces are worth 1.
     *
     * @return the value of card assuming an ace is worth 1
     */
    public static
    int calculateValue( final Card card ) {
        final Card.Value value = card.getValue();
        if ( Card.Value.ACE.equals( value ) ) {
            return 1;
        }
        else if ( isNonAceFaceCard( card ) ) {
            return 10;
        }
        else if ( Card.Value.NINE.equals( value ) ) {
            return 9;
        }
        else if ( Card.Value.EIGHT.equals( value ) ) {
            return 8;
        }
        else if ( Card.Value.SEVEN.equals( value ) ) {
            return 7;
        }
        else if ( Card.Value.SIX.equals( value ) ) {
            return 6;
        }
        else if ( Card.Value.FIVE.equals( value ) ) {
            return 5;
        }
        else if ( Card.Value.FOUR.equals( value ) ) {
            return 4;
        }
        else if ( Card.Value.THREE.equals( value ) ) {
            return 3;
        }
        else if ( Card.Value.TWO.equals( value ) ) {
            return 2;
        }
        throw new IllegalArgumentException();
    }

    /**
     * Calculates the value of a list of cards according to the rules of
     * Blackjack; assumes all aces are worth 1.
     */
    public static
    int calculateValue( final CardList cards ) {
        int value = 0;
        for ( int i = 0; i < cards.size(); i++ ) {
            value += Blackjack.calculateValue( cards.getCard( i ) );
        }
        return value;
    }

    /**
     * Returns the current {@link Rules} being used.
     */
    public Rules getRules() {
        return _rules;
    }

    /**
     * Returns whether the card passed in is a non-Ace face card: a ten, jack, queen, or king.
     */
    public static
    boolean isNonAceFaceCard( final Card card ) {
        final Card.Value value = card.getValue();
        return ( Card.Value.KING.equals( value ) 
             || Card.Value.QUEEN.equals( value ) 
             || Card.Value.JACK.equals( value ) 
             || Card.Value.TEN.equals( value ) );
    }

    /**
     * Returns whether the list of cards passed in represents a blackjack.  This method doesn't take
     * into consideration whether these are the first cards dealt or the result of a split; in the
     * latter case the cards are not a blackjack.
     */
    public static
    boolean isBlackjack( final CardList cards ) {
        if ( cards.size() != 2 ) return false;
        for ( int i = 0; i < 2; i++ ) {
            if ( Card.Value.ACE.equals( cards.getCard( i ).getValue() ) ) {
                CardList cardsCopy = ( CardList ) cards.clone();
                cardsCopy.remove( i );
                final Card.Value otherCardValue = cardsCopy.getCard( 0 ).getValue();
                return ( Card.Value.KING.equals( otherCardValue )
                     || Card.Value.QUEEN.equals( otherCardValue )
                     || Card.Value.JACK.equals( otherCardValue ) 
                     || Card.Value.TEN.equals( otherCardValue ) );
            }
        }
        return false;
    }

    /**
     * Returns whether the list of cards is a soft hand.  That is, the list of cards
     * contain an ace whose value could be 1 or 11 without exceeding 21.
     */
    public static
    boolean isSoft( final CardList cards ) {
        boolean hasAce = false;
        for ( int i = 0; i < cards.size(); i++ ) {
            if ( Card.Value.ACE.equals( cards.getCard( i ).getValue() ) ) {
                hasAce = true;
                break;
            }
        }
        return hasAce && calculateValue( cards ) < 12;
    }

    /**
     * Plays one game of blackjack betting <code>wager</code> and using <code>handler</code>
     * to determine how the player's hand is played.
     */
    public
    void playGame( final EventHandler handler, final double wager ) {
        try {
            Hand hand = dealInitialCards(wager);
            if ( offerEarlySurrender( hand, handler ) ) return;
            if ( isBlackjack( hand, handler ) ) return;
            while ( !_handsToPlay.isEmpty() ) {
                hand = ( Hand ) _handsToPlay.remove( 0 );
                while ( hand.isInPlay() ) {
                    playTurn(handler, hand);
                }
                if ( hand.isEvaluationNeeded() ) _handsToEvaluate.add( hand );
            }
            if ( isDealerPlayNeeded( _handsToEvaluate ) ) {
                evaluateOutcome( playDealerHand(), handler );
            }
        }
        catch( RuntimeException e ) {
            handler.fatalErrorOccurred( e );
        }
    }

    /**
     * Sets the rules used to determine how the game is handled.
     */
    public
    void setRules( final Rules rules ) {
        _rules = rules;
    }

    Card getDealerCard() {
        return _dealerCards.getCard( 1 );
    }

    Card getCard()
        throws NoMoreCardsException {
        return _cards.getCard();
    }
    
    void setCards( CardContainer cards ) {
        _cards = cards;
    }

    private boolean isDealerPlayNeeded( final ArrayList<Hand> hands ) {
        for ( int i = 0; i < hands.size(); i++ ) {
            if ( ((Hand) hands.get( i )).isDealerPlayNeeded() ) return true;
        }
        return false;
    }

    private void evaluateOutcome( final int dealerValue, final EventHandler handler ) {
        while ( !_handsToEvaluate.isEmpty() ) {
            final Hand hand = ( Hand ) _handsToEvaluate.remove( 0 );
            int playerValue = hand.getBestValue();
            if ( hand.isBusted() ) {
                handler.handFinished( hand,
                                       -1 * hand.getWager(),
                                       Result.BUSTED,
                                       _dealerCards );
            }
            else if ( dealerValue > 21 ) {
                handler.handFinished( hand,
                                       hand.getWager(),
                                       Result.DEALER_BUSTED,
                                       _dealerCards );
            }
            else if ( dealerValue > playerValue ) {
                handler.handFinished( hand,
                                       -1 * hand.getWager(),
                                       Result.LOSE,
                                       _dealerCards );
            }
            else if ( playerValue > dealerValue ) {
                handler.handFinished( hand,
                                       hand.getWager(),
                                       Result.WIN,
                                       _dealerCards );
            }
            else if ( playerValue == dealerValue ) {
                handler.handFinished( hand, 0, Result.PUSH, _dealerCards);
            }
            else {
                throw new IllegalStateException();
            }
        }
    }

    private boolean isBlackjack( final Hand hand, final EventHandler handler ) {
        final boolean playerHasBlackjack = isBlackjack( hand.getCards() );
        final boolean dealerHasBlackjack = isBlackjack( _dealerCards );
        if (  dealerHasBlackjack && playerHasBlackjack ) {
            handler.handFinished( hand,
                    0.0,
                    Result.BLACKJACK_PUSH,
                    _dealerCards );
            return true;
        }
        else if ( dealerHasBlackjack && ! playerHasBlackjack ) {
            handler.handFinished( hand,
                    -1 * hand.getWager(),
                    Result.DEALER_BLACKJACK,
                    _dealerCards );
            return true;
        }
        else if ( playerHasBlackjack & ! dealerHasBlackjack ) {
            handler.handFinished( hand,
                                   _rules.getBlackjackPayoff() * hand.getWager(),
                                   Result.BLACKJACK,
                                   _dealerCards );
            return true;
        }
        return false;
    }

    private boolean offerEarlySurrender( final Hand hand, final EventHandler handler ) {
        if ( _rules.canSurrenderEarly()
             && handler.offerEarlySurrender( hand ) ) {
                handler.handFinished( hand,
                                       -0.5 * hand.getWager(),
                                       Result.EARLY_SURRENDER,
                                       _dealerCards );
            return true;
        }
        return false;
    }

    private Hand dealInitialCards(final double wager) {
        _handsToEvaluate.clear();
        _handsToPlay.clear();
        _dealerCards.clear();
        CardList cards = new CardList();
        cards.add( getCard() );
        _dealerCards.add( getCard() );
        cards.add( getCard() );
        _dealerCards.add( getCard() );
        Hand hand = new Hand( wager, this, cards );
        _handsToPlay.add( hand );
        return hand;
    }

    private
    int playDealerHand() {
        int value;
        while ( true ) {
            value = calculateBestValue( _dealerCards );
            if ( value < 17 ) {
                _dealerCards.add( getCard() );
                GameNotifications.dealerGetCard();
            }
            else if ( value > 17 ) {
                return value;
            }
            else if ( value == 17 && !isSoft( _dealerCards ) ) {
                return 17;
            }
            else if ( value == 17
                      && _rules.doesDealerStandOnSoft17()
                      && isSoft( _dealerCards ) ) {
                return 17;
            }
            else if ( value == 17
                      && !_rules.doesDealerStandOnSoft17()
                      && isSoft( _dealerCards ) ) {
                _dealerCards.add( getCard() );
                GameNotifications.dealerGetCard();

            }
            else {
                throw new IllegalStateException();
            }
        }
    }

    private void playTurn(final EventHandler handler, final Hand hand) {
        Move move = handler.offerRegularTurn( hand );
        if ( move == null ) 
            handler.fatalErrorOccurred( new NullPointerException( "null move returned" ) );
        Hand newHand = move.execute( hand, handler, _dealerCards );
        if ( newHand != null ) _handsToPlay.add( newHand );
    }

} // class Blackjack