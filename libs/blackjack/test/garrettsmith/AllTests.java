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

package garrettsmith;

import garrettsmith.blackjack.*;
import garrettsmith.blackjack.refimpl.TextPlayerTest;
import garrettsmith.playingcards.*;
import junit.framework.*;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for garrettsmith");
        suite.addTest(new TestSuite(CardTest.class));
        suite.addTest(new TestSuite(CardContainerTest.class));
        suite.addTest(new TestSuite(StandardDeckTest.class));
        suite.addTest(new TestSuite(BlackjackTest.class));
        suite.addTest(new TestSuite(RulesTest.class));
        suite.addTest(new TestSuite(TextPlayerTest.class));
        suite.addTest(new TestSuite(ResultTest.class));
        suite.addTest(new TestSuite(MoveTest.class));
		return suite;
	}
}
