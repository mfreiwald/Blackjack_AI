package edu.hm.cs.ai.agents;

import java.io.IOException;

import garrettsmith.blackjack.Hand;
import garrettsmith.blackjack.Move;
import garrettsmith.blackjack.refimpl.TextPlayer;

public class Test extends TextPlayer {
	
	public static
    Move getMoveFromUser( Hand hand )
        throws IOException {

        String input;
        Move response;

        while ( true ) {

            input = "";
            response = Move.STAND;
            if ( response == null ) {

                System.out.println( "I'm sorry, I didn't understand \"" + input
                                                    + "\"; please try again." );
            }
            else if ( !hand.isMoveAllowed( response ) ) {

                System.out.println( "I'm sorry, but that move is not allowed.");
            }
            else {

                break;
            }
        }
        return response;
    }
}
