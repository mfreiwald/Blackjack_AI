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

// $Id: DataContainer.java,v 1.2 2005/06/04 08:50:53 gsmith Exp $

package garrettsmith.blackjack.autoplayer;

// Java packages
import java.io.IOException;
import java.util.Properties;

// third-party packages
// none

// garrett-smith packages
// none

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
 * $Log: DataContainer.java,v $
 * Revision 1.2  2005/06/04 08:50:53  gsmith
 * Cleaned up source for distribution.
 *
 * Revision 1.1  2005/02/01 06:51:18  gsmith
 * *** empty log message ***
 *
 * Revision 1.1.1.1  2002/06/26 21:36:26  gms
 * Initial checkin of reorganized module.  See module blackjack.old for the old
 * stuff.
 *
 */
public interface DataContainer {


    //--------------------------------------------------------------------------
    // public interface
    //--------------------------------------------------------------------------


    // public methods


    public abstract
    DataContainer getDataContainer();


    public abstract
    int getNumRecords();


    public abstract
    Object getData();


    public abstract
    void initialize( Properties properties )
        throws Exception;


    public abstract
    void persist()
        throws IOException;


    public abstract
    void setData( Object data );


} // interface DataContainer

// EOF
