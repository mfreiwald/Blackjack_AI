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

// $Id: Player.java,v 1.2 2005/06/04 08:50:53 gsmith Exp $

package garrettsmith.blackjack.autoplayer;

// Java packages
import java.util.Properties;

// third-party packages
// none

// garrett-smith packages
import garrettsmith.blackjack.EventHandler;


/**
 * <b>THIS FILE IS IN DEVELOPMENT AND SHOULD NOT BE USED</b>
 *
 * @author Garrett Smith, gsmith at northwestern dot edu
 * @version Blackjack v1.0, RCS $Revision: 1.2 $
 * @since   Blackjack v1.0
 */

/*
 * Revision history:
 *
 * DO NOT MODIFY THE LOG INFORMATION BELOW.  It is an image of what resides
 * in the CVS repository.  If you want to modify the log information use cvs.
 *
 * $Log: Player.java,v $
 * Revision 1.2  2005/06/04 08:50:53  gsmith
 * Cleaned up source for distribution.
 *
 * Revision 1.1  2005/02/01 06:51:18  gsmith
 * *** empty log message ***
 *
 * Revision 1.2  2002/06/26 21:57:57  gms
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
public interface Player extends EventHandler {


    //--------------------------------------------------------------------------
    // public interface
    //--------------------------------------------------------------------------


    // public methods


    /**
     * <p>
     * Returns a implementation-dependent description on how the current
     * simulation is progressing.  Should be formatted to be read in a
     * fixed-width font, preferably with lines no longer than 80 characters.
     * </p><p>
     * Examples of what this report may contain are the total number of games
     * played or the cumulative results.
     * </p>
     *
     * @return a formatted report on the current simulation's progress
     */
    public abstract
    String getStatus();


    public abstract
    void initialize( Properties properties )
        throws Exception;


    /**
     * <p>
     * Notifies the <code>Player</code> that it will no long be used.  Any
     * clean up or de-initialization logic may be called when this method is
     * invoked.
     * </p><p>
     * An example of a task that typically goes here is storing the results of
     * the simulation.
     * </p>
     */
    public abstract
    void signalDone();


} // interface Player

// EOF
