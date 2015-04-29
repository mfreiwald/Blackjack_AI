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

// $Id: BlackjackInfoSelector01.java,v 1.3 2005/06/04 08:50:53 gsmith Exp $

package garrettsmith.blackjack.autoplayer.tableplayer;

// Java packages
import java.util.*;

// third-party packages
// NONE

// garrett-smith packages
import garrettsmith.blackjack.*;

/**
 *
 * @author Garrett Smith, gsmith at northwestern dot edu
 * @version Blackjack v1.0, RCS $Revision: 1.3 $
 */

/*
 * Revision history:
 *
 * DO NOT MODIFY THE LOG INFORMATION BELOW.  It is an image of what resides
 * in the CVS repository.  If you want to modify the log information use cvs.
 *
 * $Log: BlackjackInfoSelector01.java,v $
 * Revision 1.3  2005/06/04 08:50:53  gsmith
 * Cleaned up source for distribution.
 *
 * Revision 1.2  2005/03/13 05:17:51  gsmith
 * Replaced int move with typesafe enum Move.
 *
 * Revision 1.1  2005/02/01 06:52:10  gsmith
 * *** empty log message ***
 *
 * Revision 1.3  2002/06/27 04:53:14  gms
 * Fixed bugs.
 *
 * Revision 1.2  2002/06/26 22:12:21  gms
 * 1. Implemented TablePlayer to never store results and to only gather basic
 *    statistics:
 *    a. Number of hands played.
 *    b. Net winnings (or losses).
 * 2. BlackjackInfoSelector01 now implements StrategySelector.
 *
 * Revision 1.1.1.1  2002/06/26 21:36:26  gms
 * Initial checkin of reorganized module.  See module blackjack.old for the old
 * stuff.
 *
 */
public final class BlackjackInfoSelector01
    implements StrategySelector {

    //--------------------------------------------------------------------------
    // public interface
    //--------------------------------------------------------------------------


    // public constructors


    // tables from http://blackjackinfo.com/bjbse.htm
    // 6 decks, stand on soft 17, double any, DAS allowed, late surrender
    // NOTE these tables are ambiguous in a few places; see comments marked
    //      with NOTE for details
    public
    BlackjackInfoSelector01() {

        HashMap<Integer, ArrayList<Move>> dealerHandMap = null;

        final ArrayList<Move> HIT = new ArrayList<Move>( 1 );
        HIT.add( Move.HIT );

        final ArrayList<Move> STAND = new ArrayList<Move>( 1 );
        STAND.add( Move.STAND );

        final ArrayList<Move> SPLIT_HIT = new ArrayList<Move>( 2 );
        SPLIT_HIT.add( Move.SPLIT );
        SPLIT_HIT.add( Move.HIT );

        final ArrayList<Move> DOUBLE_HIT = new ArrayList<Move>( 2 );
        DOUBLE_HIT.add( Move.DOUBLE );
        DOUBLE_HIT.add( Move.HIT );

        final ArrayList<Move> DOUBLE_STAND = new ArrayList<Move>( 2 );
        DOUBLE_STAND.add( Move.DOUBLE );
        DOUBLE_STAND.add( Move.STAND );

        final ArrayList<Move> SPLIT_STAND = new ArrayList<Move>( 2 );
        SPLIT_STAND.add( Move.SPLIT );
        SPLIT_STAND.add( Move.STAND );

        final ArrayList<Move> SURRENDER_HIT = new ArrayList<Move>( 2 );
        SURRENDER_HIT.add( Move.SURRENDER );
        SURRENDER_HIT.add( Move.HIT );

        final ArrayList<Move> SURRENDER_SPLIT_HIT = new ArrayList<Move>( 3 );
        SURRENDER_SPLIT_HIT.add( Move.SURRENDER );
        SURRENDER_SPLIT_HIT.add( Move.SPLIT );
        SURRENDER_SPLIT_HIT.add( Move.HIT );


        // ******** HARD PLAYER HANDS ********


        // **** always hit for very low player hands (4-8)
        setSameForAllDealerPositions( "4", HIT ); // note exceptions below
        setSameForAllDealerPositions( "5", HIT );
        setSameForAllDealerPositions( "6", HIT ); // note exceptions below
        setSameForAllDealerPositions( "7", HIT );
        setSameForAllDealerPositions( "8", HIT ); // note exceptions below

        // for player 4 and dealer 2-7 split or hit
        dealerHandMap = ( HashMap<Integer, ArrayList<Move>> ) _playerPositionMap.get( "4" );
        dealerHandMap.put( _TWO,   SPLIT_HIT );
        dealerHandMap.put( _THREE, SPLIT_HIT );
        dealerHandMap.put( _FOUR,  SPLIT_HIT );
        dealerHandMap.put( _FIVE,  SPLIT_HIT );
        dealerHandMap.put( _SIX,   SPLIT_HIT );
        dealerHandMap.put( _SEVEN, SPLIT_HIT );

        // for player 6 and dealer 2-7 split or hit
        dealerHandMap = ( HashMap<Integer, ArrayList<Move>> ) _playerPositionMap.get( "6" );
        dealerHandMap.put( _TWO,   SPLIT_HIT );
        dealerHandMap.put( _THREE, SPLIT_HIT );
        dealerHandMap.put( _FOUR,  SPLIT_HIT );
        dealerHandMap.put( _FIVE,  SPLIT_HIT );
        dealerHandMap.put( _SIX,   SPLIT_HIT );
        dealerHandMap.put( _SEVEN, SPLIT_HIT );

        // for player 8 and dealer 5 and 6 split or hit
        dealerHandMap = ( HashMap<Integer, ArrayList<Move>> ) _playerPositionMap.get( "8" );
        dealerHandMap.put( _FIVE,  SPLIT_HIT );
        dealerHandMap.put( _SIX,   SPLIT_HIT );


        // **** if player has 9...
        dealerHandMap = new HashMap<Integer, ArrayList<Move>>( 10 );
        _playerPositionMap.put( "9", dealerHandMap );

        // .. and dealer has 2 or 7-ace then hit
        dealerHandMap.put( _TWO,   HIT );
        dealerHandMap.put( _SEVEN, HIT );
        dealerHandMap.put( _EIGHT, HIT );
        dealerHandMap.put( _NINE,  HIT );
        dealerHandMap.put( _TEN,   HIT );
        dealerHandMap.put( _ACE,   HIT );

        // ... and dealer has 3-6 then double
        dealerHandMap.put( _THREE,  DOUBLE_HIT );
        dealerHandMap.put( _FOUR,   DOUBLE_HIT );
        dealerHandMap.put( _FIVE,   DOUBLE_HIT );
        dealerHandMap.put( _SIX,    DOUBLE_HIT );


        // **** if player has 10...
        dealerHandMap = new HashMap<Integer, ArrayList<Move>>( 10 );
        _playerPositionMap.put( "10", dealerHandMap );

        // .. and dealer has 2-9 then double
        dealerHandMap.put( _TWO,   DOUBLE_HIT );
        dealerHandMap.put( _THREE, DOUBLE_HIT );
        dealerHandMap.put( _FOUR,  DOUBLE_HIT );
        dealerHandMap.put( _FIVE,  DOUBLE_HIT );
        dealerHandMap.put( _SIX,   DOUBLE_HIT );
        dealerHandMap.put( _SEVEN, DOUBLE_HIT );
        dealerHandMap.put( _EIGHT, DOUBLE_HIT );
        dealerHandMap.put( _NINE,  DOUBLE_HIT );

        // .. and dealer has 10 or an ace then hit
        dealerHandMap.put( _TEN,   HIT );
        dealerHandMap.put( _ACE,   HIT );


        // **** if player has 11...
        dealerHandMap = new HashMap<Integer, ArrayList<Move>>( 10 );
        _playerPositionMap.put( "11", dealerHandMap );

        // .. and dealer has 2-10 then double
        dealerHandMap.put( _TWO,   DOUBLE_HIT );
        dealerHandMap.put( _THREE, DOUBLE_HIT );
        dealerHandMap.put( _FOUR,  DOUBLE_HIT );
        dealerHandMap.put( _FIVE,  DOUBLE_HIT );
        dealerHandMap.put( _SIX,   DOUBLE_HIT );
        dealerHandMap.put( _SEVEN, DOUBLE_HIT );
        dealerHandMap.put( _EIGHT, DOUBLE_HIT );
        dealerHandMap.put( _NINE,  DOUBLE_HIT );
        dealerHandMap.put( _TEN,   DOUBLE_HIT );

        // .. and dealer has an ace then hit
        dealerHandMap.put( _ACE,   HIT );


        // **** if player has 12...
        dealerHandMap = new HashMap<Integer, ArrayList<Move>>( 10 );
        _playerPositionMap.put( "12", dealerHandMap );

        // ... and dealer has 2 or 3 then split or hit
        dealerHandMap.put( _TWO,   SPLIT_HIT );
        dealerHandMap.put( _THREE, SPLIT_HIT );

        // ... and dealer has 4-6 then split or stand
        dealerHandMap.put( _FOUR, SPLIT_STAND );
        dealerHandMap.put( _FIVE, SPLIT_STAND );
        dealerHandMap.put( _SIX,  SPLIT_STAND );

        // ... and dealer has 7-10 or an ace then hit
        dealerHandMap.put( _SEVEN,   HIT );
        dealerHandMap.put( _EIGHT,   HIT );
        dealerHandMap.put( _NINE,    HIT );
        dealerHandMap.put( _TEN,     HIT );
        dealerHandMap.put( _ACE,     HIT );


        // **** if player has 13...
        dealerHandMap = new HashMap<Integer, ArrayList<Move>>( 10 );
        _playerPositionMap.put( "13", dealerHandMap );

        // ... and dealer has 2-6 then stand
        dealerHandMap.put( _TWO,   STAND );
        dealerHandMap.put( _THREE, STAND );
        dealerHandMap.put( _FOUR,  STAND );
        dealerHandMap.put( _FIVE,  STAND );
        dealerHandMap.put( _SIX,   STAND );

        // ... and dealer has 7-10 or an ace then hit
        dealerHandMap.put( _SEVEN, HIT );
        dealerHandMap.put( _EIGHT, HIT );
        dealerHandMap.put( _NINE,  HIT );
        dealerHandMap.put( _TEN,   HIT );
        dealerHandMap.put( _ACE,   HIT );


        // **** if player has 14...
        dealerHandMap = new HashMap<Integer, ArrayList<Move>>( 10 );
        _playerPositionMap.put( "14", dealerHandMap );

        // ... and dealer has 2-6 then split or stand
        dealerHandMap.put( _TWO,   SPLIT_STAND );
        dealerHandMap.put( _THREE, SPLIT_STAND );
        dealerHandMap.put( _FOUR,  SPLIT_STAND );
        dealerHandMap.put( _FIVE,  SPLIT_STAND );
        dealerHandMap.put( _SIX,   SPLIT_STAND );

        // ... and dealer has 7 then split or hit
        dealerHandMap.put( _SEVEN,   SPLIT_HIT );

        // ... and dealer has 8 or higher then hit
        dealerHandMap.put( _EIGHT, HIT );
        dealerHandMap.put( _NINE,  HIT );
        dealerHandMap.put( _TEN,   HIT );
        dealerHandMap.put( _ACE,   HIT );


        // **** if player has 15...
        dealerHandMap = new HashMap<Integer, ArrayList<Move>>( 10 );
        _playerPositionMap.put( "15", dealerHandMap );

        // ... and dealer has 2-6 higher then stand
        dealerHandMap.put( _TWO,   STAND );
        dealerHandMap.put( _THREE, STAND );
        dealerHandMap.put( _FOUR,  STAND );
        dealerHandMap.put( _FIVE,  STAND );
        dealerHandMap.put( _SIX,   STAND );

        // ... and dealer has 7-9 or an ace then hit
        dealerHandMap.put( _SEVEN, HIT );
        dealerHandMap.put( _EIGHT, HIT );
        dealerHandMap.put( _NINE,  HIT );
        dealerHandMap.put( _ACE,   HIT );

        // ... and dealer has 10 then surrender or hit
        dealerHandMap.put( _TEN,  SURRENDER_HIT );


        // **** if player has 16...
        dealerHandMap = new HashMap<Integer, ArrayList<Move>>( 10 );
        _playerPositionMap.put( "16", dealerHandMap );

        // ... and dealer has 2-6 then split or stand
        dealerHandMap.put( _TWO,   SPLIT_STAND );
        dealerHandMap.put( _THREE, SPLIT_STAND );
        dealerHandMap.put( _FOUR,  SPLIT_STAND );
        dealerHandMap.put( _FIVE,  SPLIT_STAND );
        dealerHandMap.put( _SIX,   SPLIT_STAND );

        // ... and dealer has 7 or 8 then split or hit
        dealerHandMap.put( _SEVEN,   SPLIT_HIT );
        dealerHandMap.put( _EIGHT,   SPLIT_HIT );

        // ... and dealer has 9 or higher then surrender, split, or hit
        // NOTE the tables are ambiguous; they say both to split and surrender
        //      am guessing that surrender is the better move
        dealerHandMap.put( _NINE,   SURRENDER_SPLIT_HIT );
        dealerHandMap.put( _TEN,    SURRENDER_SPLIT_HIT );
        dealerHandMap.put( _ACE,    SURRENDER_SPLIT_HIT );


        // **** if player has 18...
        dealerHandMap = new HashMap<Integer, ArrayList<Move>>( 10 );
        _playerPositionMap.put( "18", dealerHandMap );

        // ... and dealer has 2-6, 8, or 9 then split or stand
        dealerHandMap.put( _TWO,   SPLIT_STAND );
        dealerHandMap.put( _THREE, SPLIT_STAND );
        dealerHandMap.put( _FOUR,  SPLIT_STAND );
        dealerHandMap.put( _FIVE,  SPLIT_STAND );
        dealerHandMap.put( _SIX,   SPLIT_STAND );
        dealerHandMap.put( _EIGHT, SPLIT_STAND );
        dealerHandMap.put( _NINE,  SPLIT_STAND );

        // ... and dealer has a 7, 10, or an ace stand
        dealerHandMap.put( _SEVEN, STAND );
        dealerHandMap.put( _TEN,   STAND );
        dealerHandMap.put( _ACE,   STAND );


        // **** if player has hard 17 or hard 19-21 always stand
        setSameForAllDealerPositions( "17", STAND );
        setSameForAllDealerPositions( "19", STAND );
        setSameForAllDealerPositions( "20", STAND );
        setSameForAllDealerPositions( "21", STAND );


        // ******** SOFT PLAYER HANDS ********

        // **** if player has "soft 12", or a pair of aces, split them
        setSameForAllDealerPositions( "soft 12", SPLIT_HIT );

        // **** if player has soft 13
        dealerHandMap = new HashMap<Integer, ArrayList<Move>>( 10 );
        _playerPositionMap.put( "soft 13", dealerHandMap );

        // ...and dealer has 2-4 or 7-ace then hit
        dealerHandMap.put( _TWO,    HIT );
        dealerHandMap.put( _THREE,  HIT );
        dealerHandMap.put( _FOUR,   HIT );
        dealerHandMap.put( _SEVEN,  HIT );
        dealerHandMap.put( _EIGHT,  HIT );
        dealerHandMap.put( _NINE,   HIT );
        dealerHandMap.put( _TEN,    HIT );
        dealerHandMap.put( _ACE,    HIT );

        // ...and dealer has 5-6 then double or hit
        dealerHandMap.put( _FIVE, DOUBLE_HIT );
        dealerHandMap.put( _SIX,  DOUBLE_HIT );


        // **** if player has soft 14
        dealerHandMap = new HashMap<Integer, ArrayList<Move>>( 10 );
        _playerPositionMap.put( "soft 14", dealerHandMap );

        // ...and dealer has 2-4 or 7-ace then hit
        dealerHandMap.put( _TWO,    HIT );
        dealerHandMap.put( _THREE,  HIT );
        dealerHandMap.put( _FOUR,   HIT );
        dealerHandMap.put( _SEVEN,  HIT );
        dealerHandMap.put( _EIGHT,  HIT );
        dealerHandMap.put( _NINE,   HIT );
        dealerHandMap.put( _TEN,    HIT );
        dealerHandMap.put( _ACE,    HIT );

        // ...and dealer has 5-6 then double or hit
        dealerHandMap.put( _FIVE, DOUBLE_HIT );
        dealerHandMap.put( _SIX,  DOUBLE_HIT );


        // **** if player has soft 15
        dealerHandMap = new HashMap<Integer, ArrayList<Move>>( 10 );
        _playerPositionMap.put( "soft 15", dealerHandMap );

        // ...and dealer has 2, 3, or 7-ace then hit
        dealerHandMap.put( _TWO,    HIT );
        dealerHandMap.put( _THREE,  HIT );
        dealerHandMap.put( _SEVEN,  HIT );
        dealerHandMap.put( _EIGHT,  HIT );
        dealerHandMap.put( _NINE,   HIT );
        dealerHandMap.put( _TEN,    HIT );
        dealerHandMap.put( _ACE,    HIT );

        // ...and dealer has 4-6 then double or hit
        dealerHandMap.put( _FOUR, DOUBLE_HIT );
        dealerHandMap.put( _FIVE, DOUBLE_HIT );
        dealerHandMap.put( _SIX,  DOUBLE_HIT );


        // **** if player has soft 16
        dealerHandMap = new HashMap<Integer, ArrayList<Move>>( 10 );
        _playerPositionMap.put( "soft 16", dealerHandMap );

        // ...and dealer has 2, 3, or 7-ace then hit
        dealerHandMap.put( _TWO,    HIT );
        dealerHandMap.put( _THREE,  HIT );
        dealerHandMap.put( _SEVEN,  HIT );
        dealerHandMap.put( _EIGHT,  HIT );
        dealerHandMap.put( _NINE,   HIT );
        dealerHandMap.put( _TEN,    HIT );
        dealerHandMap.put( _ACE,    HIT );

        // ...and dealer has 4-6 then double or hit
        dealerHandMap.put( _FOUR, DOUBLE_HIT );
        dealerHandMap.put( _FIVE, DOUBLE_HIT );
        dealerHandMap.put( _SIX,  DOUBLE_HIT );


        // **** if player has soft 17
        dealerHandMap = new HashMap<Integer, ArrayList<Move>>( 10 );
        _playerPositionMap.put( "soft 17", dealerHandMap );

        // ...and dealer has 2, or 7-ace then hit
        dealerHandMap.put( _TWO,    HIT );
        dealerHandMap.put( _SEVEN,  HIT );
        dealerHandMap.put( _EIGHT,  HIT );
        dealerHandMap.put( _NINE,   HIT );
        dealerHandMap.put( _TEN,    HIT );
        dealerHandMap.put( _ACE,    HIT );

        // ...and dealer has 3-6 then double or hit
        dealerHandMap.put( _THREE, DOUBLE_HIT );
        dealerHandMap.put( _FOUR,  DOUBLE_HIT );
        dealerHandMap.put( _FIVE,  DOUBLE_HIT );
        dealerHandMap.put( _SIX,   DOUBLE_HIT );


        // **** if player has soft 18
        dealerHandMap = new HashMap<Integer, ArrayList<Move>>( 10 );
        _playerPositionMap.put( "soft 18", dealerHandMap );

        // ...and dealer has 2, 7, or 8 then stand
        dealerHandMap.put( _TWO,   STAND );
        dealerHandMap.put( _SEVEN, STAND );
        dealerHandMap.put( _EIGHT, STAND );


        // ...and dealer has 3-6 then double or stand
        dealerHandMap.put( _THREE, DOUBLE_STAND );
        dealerHandMap.put( _FOUR,  DOUBLE_STAND );
        dealerHandMap.put( _FIVE,  DOUBLE_STAND );
        dealerHandMap.put( _SIX,   DOUBLE_STAND );
 
        // ...and dealer has 9-ace then hit
        dealerHandMap.put( _NINE,  HIT );
        dealerHandMap.put( _TEN,   HIT );
        dealerHandMap.put( _ACE,   HIT );


        // **** if player has soft 19, 20, or 21 always stand
        setSameForAllDealerPositions( "soft 19", STAND );
        setSameForAllDealerPositions( "soft 20", STAND );
        setSameForAllDealerPositions( "soft 21", STAND );

    } // BlackjackInfoSelector01()


    // public methods


    public
    Move getMove( Hand hand ) {

        ArrayList<Move> moves = ( ArrayList<Move> )
                            ( ( HashMap<Integer, ArrayList<Move>> )
                                _playerPositionMap.get(
                                     getNormalizedPosition( hand ) ) )
                                            .get( new Integer(
                                                      hand.getDealerValue() ) );
        Move move;
        for ( int i = 0; i < moves.size(); i++ ) {

            move = (Move) moves.get( i );
            if ( hand.isMoveAllowed( move ) )

                return move;
        }
        throw new IllegalStateException( "no allowable moves found: hand="
                                                            + hand.toString() );
    }
        

    /**
     * Does nothing; this class does not require initialization.
     *
     * @param properties is ignored
     * @throws Exception never.
     */
    public
    void initialize( Properties properties )
        throws Exception {

    }


    //--------------------------------------------------------------------------
    // private interface
    //--------------------------------------------------------------------------


    // private methods


    private static
    String getNormalizedPosition( Hand hand ) {

        return ( hand.isSoft() ? "soft "
                                 + Integer.toString( hand.getBestValue() )
                               : Integer.toString( hand.getBestValue() ) );
    }


    private
    void setSameForAllDealerPositions( String playerPosition, ArrayList<Move> moves ){

        if ( playerPosition == null || moves == null )

            throw new NullPointerException();

        if ( moves.size() < 1 )

            throw new IllegalArgumentException( "moves must have at least "
                                                                 + "one item" );
        HashMap<Integer, ArrayList<Move>> dealerHandMap = new HashMap<Integer, ArrayList<Move>>( 10 );
        _playerPositionMap.put( playerPosition, dealerHandMap );

        dealerHandMap.put( _TWO,   moves );
        dealerHandMap.put( _THREE, moves );
        dealerHandMap.put( _FOUR,  moves );
        dealerHandMap.put( _FIVE,  moves );
        dealerHandMap.put( _SIX,   moves );
        dealerHandMap.put( _SEVEN, moves );
        dealerHandMap.put( _EIGHT, moves );
        dealerHandMap.put( _NINE,  moves );
        dealerHandMap.put( _TEN,   moves );
        dealerHandMap.put( _ACE,   moves );
    }


    // private attributes


    private static final Integer _TWO        = new Integer( 2 );
    private static final Integer _THREE      = new Integer( 3 );
    private static final Integer _FOUR       = new Integer( 4 );
    private static final Integer _FIVE       = new Integer( 5 );
    private static final Integer _SIX        = new Integer( 6 );
    private static final Integer _SEVEN      = new Integer( 7 );
    private static final Integer _EIGHT      = new Integer( 8 );
    private static final Integer _NINE       = new Integer( 9 );
    private static final Integer _TEN        = new Integer( 10 );
    private static final Integer _ACE        = new Integer( 1 );

    private HashMap<String, HashMap<Integer, ArrayList<Move>>> _playerPositionMap = new HashMap<String, HashMap<Integer, ArrayList<Move>>>( 24 );

}  // class BlackjackInfoSelector01

// EOF
