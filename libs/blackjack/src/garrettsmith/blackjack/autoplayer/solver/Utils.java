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

// $Id: Utils.java,v 1.2 2005/06/04 08:50:53 gsmith Exp $

// Copyright (C) 2001 Chad Wathington and Garrett Smith


package garrettsmith.blackjack.autoplayer.solver;


// Java packages
//none

// third-party packages
// none

// garrett_smith packages
// none


public final class Utils {


/*
    public static
    String getPositionId( int initialPlayerValue,
                          boolean isSoft, 
                          int dealerPublicValue ) {

        return "player=" + Integer.toString( initialPlayerValue )
                 + ";playerSoft=" + isSoft + ";dealer="
                 + Integer.toString( dealerPublicValue );
    }


    public static
    void outputContainer( DataContainer container ) {

        StringBuffer buffer = new StringBuffer();

        buffer.append( "N=" + Integer.toString( container.getNumRecords() )
                       + "\n" );

        Iterator entries = ( ( HashMap ) container.getData() )
                                                         .entrySet().iterator();

        Map.Entry entry = null;
        HashMap paths = null;
        while ( entries.hasNext() ) {

            entry = ( Map.Entry ) entries.next();
            buffer.append( ( String ) entry.getKey() + "\n" );
            paths = ( HashMap ) entry.getValue();

            Iterator pathEntries = paths.entrySet().iterator();
            Map.Entry pathEntry = null;
            while ( pathEntries.hasNext() ) {

              pathEntry = ( Map.Entry ) pathEntries.next();
              buffer.append( "    " + ( String ) pathEntry.getKey() + ": " );
              buffer.append( ( ( StatisticsContainer )
                                            pathEntry.getValue() ).toString() );
              buffer.append( "\n" );
            }
        }
        System.out.println( buffer.toString() );
    }
*/
}

// EOF
