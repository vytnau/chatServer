package lt.bt.chat.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

import lt.bt.chat.ConsoleApi;
import lt.bt.chat.Utils;
import lt.bt.chat.server.data.User;

public class ChatServer {		
	private static final String SERVER_IP = "Serverio ip adresas: ";
	private static final String INSERT_SERVER_PORT = "Iveskite serverio porta: ";
	private static final String BAD_PORT = "Ivestas blogas porto numeris";
	
	private int port;
	private Set<User> userNames = new HashSet<>();
	private Set<UserThread> userThreads = new HashSet<>();
	private ConsoleApi console;

	public ChatServer(int port) {
		this.port = port;
		console = new ConsoleApi();
	}
	
	public void printUsage(){
		
	}

	public void startServer() {
		try (ServerSocket serverSocket = new ServerSocket(port)) {
			System.out.println("Chat Serveris klausosi porto " + port);
			while (true) {
				Socket socket = serverSocket.accept();
				console.print("New user connected", true);
				UserThread newUser = new UserThread(socket, this);
				userThreads.add(newUser);
				newUser.start();
			}

		} catch (IOException ex) {
			System.out.println("Klaida serveryje: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	public static void main(String[] args) {
		ConsoleApi console = new ConsoleApi();
		int port = Utils.readNumericValue(console, INSERT_SERVER_PORT);		
		if(port < 0){
			console.print(BAD_PORT);
			return;
		}
		String serverIP = Utils.getServerIp();
		console.print(SERVER_IP + serverIP, true);
		ChatServer server = new ChatServer(port);
		server.startServer();		
	}

	/**
	 * Persiuncia zinute visiems useriams (broadcasting)
	 */
	public void broadcast(String message, UserThread excludeUser) {
		for (UserThread aUser : userThreads) {
			if (aUser != excludeUser) {
				aUser.sendMessage(message);
			}
		}
	}

	/**
	 * Issaugo userio varda.
	 */
	public void addUserName(User userName) {
		userNames.add(userName);
	}

	/**
	 * kai useris atsijungia, pasalinama 
	 */
	void removeUser(String userName, UserThread aUser) {
		boolean removed = userNames.remove(userName);
		if (removed) {
			userThreads.remove(aUser);
			console.print("The user " + userName + " quitted", true);
		}
	}

	Set<User> getUserNames() {
		return this.userNames;
	}

	/**
	 * Grazina ar true jei yra bent vinas useris 
	 */
	boolean hasUsers() {
		return !this.userNames.isEmpty();
	}

	
}
