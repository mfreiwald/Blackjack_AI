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

// $Id: StatisticsContainer.java,v 1.2 2005/06/04 08:50:53 gsmith Exp $

// Copyright (C) 2001 Chad Wathington and Garrett Smith


package garrettsmith.blackjack.autoplayer.solver;


// Java packages
// none

// third-party packages
// none

// garrett_smith packages
//none


public class StatisticsContainer
    implements  java.io.Serializable {


    public
    Object clone() {

        // TODO implement
        return null;
    }


    public
    boolean equals( Object object ) {

        if ( object != null && object instanceof StatisticsContainer ) {

            // TODO implement
        }
        return false;
    }


    public
    int hashCode() {

        // TODO implement
        return 0;
    }


    public
    void record( double result ) {

        _count++;
        _cumulativeResult += result;
    }


    public
    String toString() {

        return "average result=" +Double.toString( _cumulativeResult / _count )
            + ", n=" + Integer.toString( _count );
    }


    StatisticsContainer( String path,
                         int initialHandValue,
                         boolean isSoft,
                         int dealerPublicValue ) {

        _isSoft = isSoft;
        _playerValue = initialHandValue;
        _path = path;
        _dealerValue = dealerPublicValue;
    }


    private
    StatisticsContainer() {}


    private int             _playerValue = -1;
    private int             _dealerValue = -1;
    private boolean         _isSoft      = false;
    private String          _path        = null;

    private int             _count       = 0;
    private double          _cumulativeResult = 0.0;

}

// EOF
