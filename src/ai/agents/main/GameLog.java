package ai.agents.main;

public class GameLog {

	public static void println() {
		println("");
	}
	
	public static void println(String s) {
		System.out.println(s);
	}
	
	public static void print(String s) {
		System.out.print(s);
	}
	
	public static void print() {
		print("");
	}
	
	public static void print(int s) {
		print(Integer.toString(s));
	}
	
	public static void err(String s) {
		System.err.println(s);
	}
	
}
