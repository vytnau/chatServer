package lt.bt.chat;

import java.io.Console;
import java.util.Scanner;

public class ConsoleApi {
	private Console console;
	private Scanner scanner;

	public ConsoleApi() {
		console = System.console();
		if (console == null) {
			scanner = new Scanner(System.in);
		}
	}

	public String readLine(String txt) {
		if (console != null) {
			return console.readLine(txt);
		}
		System.out.print(txt);
		return scanner.nextLine();
	}

	public void print(String txt) {
		if (console != null) {
			console.printf("%s", txt);
		} else {
			System.out.print(txt);
		}
	}
	
	public void print(String txt, boolean newLine) {
		print(txt);
		System.out.println("");
	}

}
