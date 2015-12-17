package poker.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public class Server extends Thread {

	private ServerSocket listener;
	private List<PokerRoom> pokerRooms;
	private int connectedClients;
	private boolean isListening;

	public Server(int players, int tables, int money, int smallBlind, int bigBlind, String rules, int raiseAmount,
			int raiseTimes, int port) throws IOException {
		try {
			listener = new ServerSocket(port);
			pokerRooms = new ArrayList<PokerRoom>();
			connectedClients = 0;
			isListening = true;

		} catch (IOException e) {
			throw e;
		}
		System.out.println("Server is running");
		GameSettings.getInstance().setCustomSettings(players, tables, money, smallBlind, bigBlind, rules, raiseAmount,
				raiseTimes);
		for (int i = 0; i < tables; ++i) {
			PokerRoom room = new PokerRoom();
			pokerRooms.add(room);
		}
		this.start();
	}

	public void run() {
		try {
			try {
				while (true) {
					if (isListening) {
						for (int i = 0; i < GameSettings.getInstance().getNumOfPlayers()
								* GameSettings.getInstance().getNumOfTables(); ++i) {
							PlayerThread thread = new PlayerThread(listener.accept(), this);
							thread.start();
						}
						for (PokerRoom pokerRoom : pokerRooms)
						{
							pokerRoom.getGameController().sendMessageToAllPlayers("MESSAGE All players connected. Game starting in the moment...");
							pokerRoom.startGame();
						}
						isListening = false;
					}
					sleep(2000);
				}
			} catch (IOException e) {
				System.out.println("Error occured while joining to the server " + e);
			} catch (InterruptedException e) {
				System.out.println("Server error " + e);
			} finally {

				listener.close();
			}
		} catch (IOException e) {
			System.out.println("Error " + e);
		}
	}

	public PokerRoom getPokerRoom(int index) {
		return pokerRooms.get(index);
	}

	public int getNumberOfConnectedPlayers() {
		return connectedClients;
	}
}
