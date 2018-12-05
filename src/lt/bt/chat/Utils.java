package lt.bt.chat;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.InputMismatchException;

public class Utils {
	private static final int CONNECTION_PORT = 10002;
	private static final String CONNECTION_IP = "8.8.8.8";
	private static final int MAX_RETRY = 3;

	/*
	 * Funkcija grazina musu serverio ip adresa, kuriuo gales jungtis klientai.
	 */
	public static String getServerIp() {
		String ip = "";
		try (final DatagramSocket socket = new DatagramSocket()) {
			socket.connect(InetAddress.getByName(CONNECTION_IP), CONNECTION_PORT);
			ip = socket.getLocalAddress().getHostAddress();
		} catch (SocketException | UnknownHostException e) {
			e.printStackTrace();
		}
		return ip;
	}

	/*
	 * Skaitymas is konsoles skaitines reksmes su retry logika
	 */
	public static int readNumberFromConsole(String errorMsg, boolean retry) {
		String line = System.console().readLine();
		int number = 0;
		try {
			number = Integer.parseInt(line);
		} catch (NumberFormatException e) {
			System.out.println(errorMsg);
			if (retry) {
				number = readNumberFromConsole(errorMsg, retry);
			}
		}

		return number;
	}

	public static int readNumericValue(ConsoleApi console, String txt) {
		int tryCount = 0;
		return readNumericValue(console, txt, tryCount);
	}

	private static int readNumericValue(ConsoleApi console, String txt, int count) {

		int result = -1;
		String numeric = console.readLine(txt);
		if (count < MAX_RETRY) {
			try {
				result = Integer.parseInt(numeric);
			} catch (NumberFormatException e) {
				result = readNumericValue(console, txt, count);
			}
		}
		return result;
	}

}
