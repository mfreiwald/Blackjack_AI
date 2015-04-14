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

// $Id: EventHandler.java,v 1.10 2005/06/04 08:50:51 gsmith Exp $

package garrettsmith.blackjack;

import garrettsmith.playingcards.CardList;

/**
 * <p>
 * Provides a callback interface for playing blackjack.  The user implements this interface
 * with logic that controls how the game should be played, then invokes
 * {@link Blackjack#playGame(EventHandler, double)} to play a game.
 * </p><p>
 * See {@link garrettsmith.blackjack.refimpl.TextPlayer} for an example implementation.
 * </p>
 * 
 * @author Garrett Smith, gsmith at northwestern dot edu
 * @version Blackjack v1.0, RCS $Revision: 1.10 $
 * @since   Blackjack v1.0
 */
public interface EventHandler {

    /**
     * This method is invoked when an error occurs that prevents the game from
     * completing.
     * 
     * @param e the exception that caused the error
     */
    public
    void fatalErrorOccurred( Exception e );

    /**
     * Method that is invoked when a hand is done being played.  Note that if a hand
     * is split there may be multiple hands for each game played.
     * 
     * @param hand the hand which has completed the game
     * @param gain the winnings from the hand; negative if the wager was lost
     * @param result the result of the hand; see {@link Result}
     * @param dealerCards the cards that the dealer ended the hand with
     */
    public
    void handFinished( Hand hand,
                       double gain,
                       Result result,
                       CardList dealerCards );

    /**
     * Method that is optionally invoked if the player has the option of early
     * surrender.
     *  
     * @param hand the hand for which early surrender is being offered
     * @return <code>true</code> if the user wishes to surrender; 
     *          <code>false</code> otherwise
     */
    public
    boolean offerEarlySurrender( Hand hand );

    /**
     * Method invoked when the player is offered a turn.  The implementor returns
     * the appropriate {@link Move} they wish to make.
     * 
     * @param hand the hand for which a turn is being offered
     * @return the {@link Move} the player whishes to make
     */
    public
    Move offerRegularTurn( Hand hand );

} // interface EventHandler