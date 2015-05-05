package edu.hm.cs.ai.ownBJ;

public class Card {

	public final Value value;
	public final Color color;
	
	public Card(Value value, Color color) {
		this.value = value;
		this.color = color;
	}
	
	enum Value {
		Ace(11), King(10), Queen(10), Jack(10), Ten(10), Nine(9), Eight(8), Seven(7), Six(6), Five(5), Four(4), Three(3), Two(2);
		
		private int numVal;

	    Value(int numVal) {
	        this.numVal = numVal;
	    }

	    public int getNumVal() {
	        return numVal;
	    }
	}
	
	enum Color {
		Heart, Diamonds, Spade, Clubs
	}
	
	
}
