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

// $Id: TablePlayer.java,v 1.5 2005/06/04 08:50:53 gsmith Exp $

package garrettsmith.blackjack.autoplayer.tableplayer;

// Java packages
import java.io.*;
import java.text.DecimalFormat;
import java.util.*;

// third-party packages
// NONE

// garrett-smith packages
import garrettsmith.blackjack.*;
import garrettsmith.blackjack.autoplayer.*;
import garrettsmith.playingcards.CardList;

/**
 * TODO2 add comments
 *
 * @author Garrett Smith, gsmith at northwestern dot edu
 * @version Blackjack v1.0, RCS $Revision: 1.5 $
 * @since   Blackjack v1.0
 */

/*
 * Revision history:
 *
 * DO NOT MODIFY THE LOG INFORMATION BELOW.  It is an image of what resides
 * in the CVS repository.  If you want to modify the log information use cvs.
 *
 * $Log: TablePlayer.java,v $
 * Revision 1.5  2005/06/04 08:50:53  gsmith
 * Cleaned up source for distribution.
 *
 * Revision 1.4  2005/03/13 16:34:35  gsmith
 * Added typesafe collection CardList.
 *
 * Revision 1.3  2005/03/13 05:17:51  gsmith
 * Replaced int move with typesafe enum Move.
 *
 * Revision 1.2  2005/03/12 07:30:45  gsmith
 * Replaced int result with typesafe enum.
 *
 * Revision 1.1  2005/02/01 06:52:10  gsmith
 * *** empty log message ***
 *
 * Revision 1.7  2002/06/28 22:06:31  gms
 * 1.  Added ability to write data to a file.
 *
 * Revision 1.6  2002/06/27 16:48:38  gms
 * 1. Added "Units won per hand X 1000" statistic to getStatus.
 * 2. getStatus output is now better formatted.
 *
 * Revision 1.5  2002/06/27 05:56:25  gms
 * Added number of hands played per second statistic to TablePlayer status report.
 *
 * Revision 1.4  2002/06/27 05:33:02  gms
 * Added start and end time to the getStatus() report.
 *
 * Revision 1.3  2002/06/26 22:12:21  gms
 * 1. Implemented TablePlayer to never store results and to only gather basic
 *    statistics:
 *    a. Number of hands played.
 *    b. Net winnings (or losses).
 * 2. BlackjackInfoSelector01 now implements StrategySelector.
 *
 * Revision 1.2  2002/06/26 21:58:03  gms
 * Eliminated the concept of a DataContainer as a required component of Player.
 * The DataContainer is now just a recommended way to store information.
 * 1. Solver is no longer reflectively instansiating a DataContainer.
 * 2. DataContainer-specific methods have been removed from Player.
 *
 * Revision 1.1.1.1  2002/06/26 21:36:26  gms
 * Initial checkin of reorganized module.  See module blackjack.old for the old
 * stuff.
 *
 */
public class TablePlayer
    implements Player {

    //--------------------------------------------------------------------------
    // public interface
    //--------------------------------------------------------------------------


    // public methods



    // TODO2 consider enhancement
    public
    void fatalErrorOccurred( Exception e ) {

        e.printStackTrace();
        System.exit( 1 );
    }
    
    public
    String getCSVDataPoint() {

        // TODO potential bug: _SIX_DECIMALS_FORMAT may put commas in its output
        return _numHandsPlayed + ","
                 +_SIX_DECIMALS_FORMAT.format(getUnitsWonPerHandTimesThousand()) 
                 + _SEPARATOR;
    }

    public
    String getStatus() {

        StringBuffer result = new StringBuffer();
        result.append( "Start Time:                " + _startDate.toString()
                                                                 + _SEPARATOR );
        if ( _stopDate != null ) {

            result.append( "Stop Time:                 " + _stopDate.toString()
                                                                 + _SEPARATOR );
        }
        result.append( "Net winnings:              "
            + _FORMAT.format( _netWinnings ) + _SEPARATOR
            + "Number of hands played:    " + _FORMAT.format( _numHandsPlayed )
            + _SEPARATOR + "Units won per hand X 1000: "
            + _SIX_DECIMALS_FORMAT.format( getUnitsWonPerHandTimesThousand() )
            + _SEPARATOR );
        if ( _stopDate != null ) {

            double handsPerSecond = _numHandsPlayed * 1000 /
                                 ( _stopDate.getTime() - _startDate.getTime() );
            result.append( "Hands played per second:   "
                              + _FORMAT.format( handsPerSecond ) + _SEPARATOR );
        }
        return result.toString();
    }

    public
    double getUnitsWonPerHandTimesThousand() {
        
        return _netWinnings * 1000 / _numHandsPlayed;
    }
    
    public
    void handFinished( Hand hand,
                       double gainOrLoss,
                       Result result,
                       CardList dealerCards ) {

        _netWinnings += gainOrLoss;
        _numHandsPlayed++;
        if ( _numHandsPlayed % _writeThreshold == 0 ) {
            
            try {
                _output.write( getCSVDataPoint() );
            }
            catch ( IOException ioe ) {
                
                ioe.printStackTrace();
                // TODO shut down
            }
        }
    }

    public
    void initialize( Properties properties )
        throws Exception {

        String className = properties.getProperty( SELECTOR_CLASSNAME );
        if ( className == null ) {

            throw new Exception( "property " + SELECTOR_CLASSNAME
                                                               + " not found" );
        }
        _selector = ( StrategySelector )
                                       Class.forName( className ).newInstance();
        _selector.initialize( properties );
        _writeThreshold = Long.parseLong(
                                    properties.getProperty( WRITE_THRESHOLD ) );
        _dataFile = new File( properties.getProperty( DATA_FILE_LOCATION ) );
        if ( _dataFile.exists() )
            _dataFile.delete();
        _dataFile.createNewFile();
        if ( !_dataFile.canWrite() )
            throw new IOException( "cannot write to file "
                                                + _dataFile.getAbsolutePath() );
        _output = new BufferedWriter( new FileWriter( _dataFile ) );
        _startDate = new Date();
    }

    public
    boolean offerEarlySurrender( Hand hand ) {

        return Move.SURRENDER == _selector.getMove( hand );
    }

    public
    boolean offerInsurance( Hand hand ) {

        return false;
    }

    public
    Move offerRegularTurn( Hand hand ) {

        return _selector.getMove( hand );
    }


    public
    void signalDone() {

        _stopDate = new Date();
        System.out.println( this.getStatus() );
        try {
            
            _output.write( getCSVDataPoint() );
            _output.close();
        }
        catch ( IOException ioe ) {
            
            // do nothing
        }
    }


    // public attributes


    public static final String SELECTOR_CLASSNAME = "selectorClassname";
    
    public static final String DATA_FILE_LOCATION = "dataFileLocation";
    
    public static final String WRITE_THRESHOLD = "writeThreshold";


    //--------------------------------------------------------------------------
    // private interface
    //--------------------------------------------------------------------------

    // private attributes

    private static final String _SEPARATOR
                                       = System.getProperty( "line.separator" );
    private static final DecimalFormat _FORMAT = new DecimalFormat();
    private static final DecimalFormat _SIX_DECIMALS_FORMAT =
                                  new DecimalFormat( "###,###,###,##0.######" );

    private Date                _startDate        = null;
    private Date                _stopDate         = null;
    private double              _netWinnings      = 0.0;
    private long                _numHandsPlayed   = 0;
    private StrategySelector    _selector         = null;
    private File                _dataFile         = null;
    private long                _writeThreshold   = -1;
    private Writer              _output           = null;

} // class TablePlayer

// EOF
