package ai.agents.main;

public class GameLog {

	public static enum Level {
		ERROR, ALL, NONE
	}
	
	public static Level level = Level.ALL;
	
	public static void println(String s) {
		if(GameLog.level == Level.ALL) {
			System.out.println(s);
		}
	}
	
	public static void print(String s) {
		if(GameLog.level == Level.ALL) {
			System.out.print(s);
		}
	}
	
	public static void err(String s) {
		if(GameLog.level != Level.NONE) {
			System.err.println(s);
		}
	}
	
	public static void println() {
		println("");
	}
	
	public static void print() {
		print("");
	}
	
	public static void print(int s) {
		print(Integer.toString(s));
	}
}
