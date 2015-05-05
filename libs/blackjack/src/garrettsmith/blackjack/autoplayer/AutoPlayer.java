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

// $Id: AutoPlayer.java,v 1.4 2005/06/04 08:50:53 gsmith Exp $

package garrettsmith.blackjack.autoplayer;

// Java packages
import java.io.*;
import java.net.*;

// third-party packages
import gnu.getopt.*;

// garrett-smith packages
import garrettsmith.blackjack.*;

/**
 * <b>THIS FILE IS IN DEVELOPMENT AND SHOULD NOT BE USED</b>
 * 
 * @author Garrett Smith, gsmith at northwestern dot edu
 * @version Blackjack v1.0, RCS $Revision: 1.4 $
 * @since   Blackjack v1.0
 */
public final class AutoPlayer {

    private volatile boolean  _shouldSolve   = true;
    private Blackjack         _blackjack     = new Blackjack();
    private Player            _player        = null;
    private StopCheckThread   _checkThread   = null;
    private int               _id            = -1;

	public static final String   USE_DEFAULT_RULES = "useDefaultRules";
    public static final String   PLAYER_CLASSNAME = "playerClassname";

    private static final String _SEPARATOR    =
                                         System.getProperty( "line.separator" );
    private static final String _STOP_MESSAGE = "stop";
    private static final int    _LISTEN_PORT  = 11521;

    // TODO i don't think these should be needed
    private static AutoPlayer _autoPlayer    = new AutoPlayer();
    private static int        _nextId        = 1;
    private static int        _nextThreadId  = 1;

    private AutoPlayer() {
        _id = _nextId++;
    }

    public static
    void main( String[] arguments ) {

        try {

            Options options = new Options( arguments );
            if ( !options.argumentsCorrect || !options.commandSpecified
                 || options.helpSelected )
                return;

System.out.println( "propertiesFile=" + options.propertiesFile + ", portNumber="
        + options.portNumber + ", numberOfThreads=" + options.numberOfThreads );
            if ( Options.RUN.equals( options.command ) ) {
                
                System.out.println( "run selected" );
            }
            else if ( Options.REPORT.equals( options.command ) ) {
                
                System.out.println( "report selected: " + options.reportType  );
            }
            else if ( Options.STOP.equals( options.command ) ) {
                
                System.out.println( "stop selected" );
            }
            else {
                
                throw new IllegalStateException( "unknown command: "
                                                            + options.command );
            }
/*            
            if ( arguments.length == 1 && "stop".equals( arguments[ 0 ] ) ) {

                signalStop();
            }
            else if ( arguments.length == 1
                      && "report".equals( arguments[ 0 ] ) ) {

                System.out.println( _solver._player.getStatus() );
            }
            else if ( arguments.length == 2
                      && "start".equals( arguments[ 0 ] ) ) {

                // then invoke the solving algorithm and solve, solve, solve!
                Properties props = new Properties();
                props.load( new BufferedInputStream(
                                      new FileInputStream( arguments[ 1 ] ) ) );

                Player player = ( Player )
                                    Class.forName( props.getProperty(
                                             PLAYER_CLASSNAME ) ).newInstance();
                player.initialize( props );

                _solver.setPlayer( player );

                if ( !Boolean.valueOf(
                     props.getProperty( USE_DEFAULT_RULES ) ).booleanValue() ) {

                    _solver.setRules( props );
                }

                _solver.solve();
            }
            else {

                // print usage TODO2 improve (via GetOpt)
                System.out.println( "bad args" );
                System.exit( 1 );
            }
 */
        }
        catch ( Exception e ) {

            e.printStackTrace();
            System.exit( 1 );
        }
    }

    public
    void setRules( Rules rules ) {
        _blackjack.setRules( rules );
    }

    public void setPlayer( Player player ) {
        _player = player;
    }

    public static
    void signalStop()
        throws IOException, SocketException {
        try (DatagramSocket socket = new DatagramSocket())
        {
	        DatagramPacket packet = new DatagramPacket(
	                                                _STOP_MESSAGE.getBytes(),
	                                                _STOP_MESSAGE.getBytes().length,
	                                                InetAddress.getLocalHost(),
	                                                _LISTEN_PORT );
	        socket.send( packet );
        }
        catch (Exception ex)
        {
        	throw ex;
        }
    }

    public
    void solve()
        throws IOException {
        try {
            _checkThread = new StopCheckThread( this, Thread.currentThread() );
            synchronized ( _checkThread ) {
                _checkThread.start();
                while ( !_checkThread.isStarted() ) {
                    _checkThread.wait();
                }
            }
            while ( this.shouldSolve() ) {
                _blackjack.playGame( _player, 1.0 );
            }
            _autoPlayer._player.signalDone();
        }
        catch ( InterruptedException iE ) {
        	// TODO use log4j, should System.exit?
            iE.printStackTrace();
            System.exit( 1 );
        }
    }

    public
    String toString() {
        return this.getClass().getName() + "[_id=" + Integer.toString( _id )
            + ", _shouldSolve=" + _shouldSolve
            + ", _player" + _player + ", _checkThread=" + _checkThread;
    }

    synchronized
    void stop() {
        _shouldSolve = false;
    }

    private synchronized
    boolean shouldSolve() {
        return _shouldSolve;
    }

    private class StopCheckThread extends Thread {


        private
        boolean isStarted() {

            return _started;
        }


        public
        void run() {

            byte[] data = new byte[ 2048 ];
            DatagramSocket socket = null;
            DatagramPacket packet = null;

            synchronized ( this ) {

                try {

                    socket = new DatagramSocket( _LISTEN_PORT );
                    packet = new DatagramPacket( data, data.length );
                    _started = true;
                    this.notifyAll();
                }
                catch ( SocketException socketE ) {

                    // A SocketException will be thrown if port _LISTEN_PORT is
                    // already being used on this host (or, less likely, for
                    // other reasons).  In this case this object communicates
                    // to the Solver by signaling it to stop and by
                    // interrupting its thread, causing an InterruptedException
                    // to be thrown.
                    // See Solver.solve().
                    System.out.println( "socket error: "
                                                       + socketE.getMessage() );
                    _solverThread.interrupt();
                }
            }

            try {

                while ( true ) {

                    socket.receive( packet );
                    String message = new String( packet.getData(),
                                                 packet.getOffset(),
                                                 packet.getLength() );

                    if ( _STOP_MESSAGE.equals( message ) ) {

                        _autoPlayer.stop();
                    }
                }
            }
            catch ( IOException ioE ) {

                // In case of an exception being thrown in this try-catch block
                // this thread signals to the Solver via simply calling its stop
                // method.  No notification is needed, as the Solver is already
                // polling its shouldSolve() method, which checks to see if
                // stop() has been called.
                System.out.println( "IO error: " + ioE.getMessage() );
                _autoPlayer.stop();
            }
        }


        private
        StopCheckThread() {

            super( "StopCheckThread-" + Integer.toString( _nextId ) );
            _id = _nextThreadId++;
            this.setDaemon( true );
            this.setPriority( Thread.MIN_PRIORITY );
        }


        private
        StopCheckThread( AutoPlayer autoPlayer, Thread solverThread ) {

            this();
            this._autoPlayer = autoPlayer;
            _solverThread = solverThread;
        }

        private AutoPlayer        _autoPlayer    = null;
        private Thread            _solverThread  = null;
        private volatile boolean  _started       = false;

    } // class StopCheckThread
    
    private static class Options {
        
        private Options( String[] arguments ) {

            LongOpt[] longOpts = new LongOpt[ 7 ];

            // options
            longOpts[ 0 ] = new LongOpt( "help",
                                         LongOpt.NO_ARGUMENT,
                                         null,
                                         'h' );
            longOpts[ 1 ] = new LongOpt( "num-threads",
                                         LongOpt.REQUIRED_ARGUMENT,
                                         null,
                                         'n' );
            longOpts[ 2 ] = new LongOpt( "port",
                                         LongOpt.REQUIRED_ARGUMENT,
                                         null,
                                         'p' );
            longOpts[ 3 ] = new LongOpt( "properties-file",
                                         LongOpt.REQUIRED_ARGUMENT,
                                         null,
                                         'P' );

            // execution modes
            longOpts[ 4 ] = new LongOpt( "run",
                                         LongOpt.NO_ARGUMENT,
                                         null,
                                         'r' );
            longOpts[ 5 ] = new LongOpt( "report",
                                         LongOpt.OPTIONAL_ARGUMENT,
                                         null,
                                         'R' );
            longOpts[ 6 ] = new LongOpt( "stop",
                                         LongOpt.NO_ARGUMENT,
                                         null,
                                         's' );
            Getopt getopt = new Getopt( this.getClass().getName(),
                                        arguments,
                                        ":hrsR::n:p:P:",
                                        longOpts );

            int value;
            while ( ( value = getopt.getopt() ) != -1 ) {

                switch ( value ) {

                    case 'h':
                        helpSelected = true;
                        break;

                    case 'r':
                        commandSpecified = true;
                        command = Options.RUN;
                        break;

                    case 's':
                        commandSpecified = true;
                        command = Options.STOP;
                        break;
                        
                    case 'R':
                        commandSpecified = true;
                        command = Options.REPORT;
                        reportType = ( getopt.getOptarg() == null
                                       ? null
                                       : getopt.getOptarg() );
                        break;
                        
                    case 'n':
                        try {
                            
                            numberOfThreads =
                                         Integer.parseInt( getopt.getOptarg() );
                        }
                        catch ( NumberFormatException nfe ) {
                         
                            printBadNumberOfThreads( nfe.getMessage () );
                            argumentsCorrect = false;
                        }
                        break;
                    
                    case 'p':
                        try {
                            
                            portNumber = Integer.parseInt( getopt.getOptarg() );
                        }
                        catch ( NumberFormatException nfe ) {
                         
                            printBadPortNumber( nfe.getMessage () );
                            argumentsCorrect = false;
                        }
                        break;
                        
                    case 'P':
                        propertiesFile = new File( getopt.getOptarg() );
                        break;
                        
                    case '?':
                    case ':':
                        argumentsCorrect = false;
                        break;
               
                    default:
                        throw new IllegalArgumentException(
                            "getopt() returned '" + (char) value + "'" );
                }
            }
            // TODO further business validate options
            // check port num positive int
            // check thread num sane
            // check report name sane
            if ( !commandSpecified )
                argumentsCorrect = false;
                
            if ( !argumentsCorrect || helpSelected )
                printHelp();
            
        } // private Options( String[] )
            
        private
        void printHelp() {
            
            System.err.println( 
              "usage: " + this.getClass().getName() + " -r [options...]" + _SEPARATOR
            + "       " + this.getClass().getName() + " -R [report-name] [options...]" + _SEPARATOR
            + "       " + this.getClass().getName() + " -s [options...]" + _SEPARATOR
            + _SEPARATOR
            + "available options:" + _SEPARATOR
            + "-h/--help     Print this command line help"
            + _SEPARATOR
            + "-n/--num-threads <number-of-autoplayer-threads>" + _SEPARATOR
            + "              Number of autoplayer threads to use; defaults to 1"
            + _SEPARATOR 
            + "-p/--port <port-number>" + _SEPARATOR
            + "              Port that the solver listens on for commands; defaults to 11521"
            + _SEPARATOR
            + "-P/--properties-file <properties-file-name>" + _SEPARATOR
            + "              Run the application with the settings specified in"
            + " <properties-file>; defaults to autoplayer.properties"
            + _SEPARATOR );

        } // private void printHelp()

        private
        void printBadNumberOfThreads( String additionalMessage ) {
            
            System.err.println( this.getClass().getName()
              + ": <number-of-autoplayer-threads> must be a positive integer"
              + ( additionalMessage == null
                  ? ""
                  : ": " + additionalMessage ) );
        }
        
        private
        void printBadPortNumber( String additionalMessage ) {

            System.err.println( this.getClass().getName()
              + ": <port-number> must be a positive integer"
              + ( additionalMessage == null
                  ? ""
                  : ": " + additionalMessage ) );
        }

        private static final String   RUN              = "run";
        private static final String   STOP             = "stop";
        private static final String   REPORT           = "report";

        private boolean         argumentsCorrect       = true;
        private boolean         commandSpecified       = false;
        private String          command                = null; 
        private boolean         helpSelected           = false;
        private int             numberOfThreads        = 1;
        private String          reportType             = null;
        private File            propertiesFile         
                                          = new File( "autoplayer.properties" );
        private int             portNumber             = 11521;

    } // class Options

} // class Solver