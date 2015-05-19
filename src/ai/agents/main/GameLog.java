package ai.agents.main;

public class GameLog {

	public static enum Level {
		ERROR, ALL, NONE
	}
	
	public static Level level = Level.ALL;
	
	public static void println(Object s) {
		if(GameLog.level == Level.ALL) {
			System.out.println(s.toString());
		}
	}
	
	public static void print(Object s) {
		if(GameLog.level == Level.ALL) {
			System.out.print(s.toString());
		}
	}
	
	public static void err(Object s) {
		if(GameLog.level != Level.NONE) {
			System.err.println(s.toString());
		}
	}
	
	public static void println() {
		println("");
	}
	
	public static void print() {
		print("");
	}
}
