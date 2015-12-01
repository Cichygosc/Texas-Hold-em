package poker.server;

import java.io.IOException;
import java.net.ServerSocket;

import poker.Player;

public class Server extends Thread {

	private ServerSocket listener;
	private PokerGame pokerGame;
	private boolean isListening;

	public Server(int numOfPlayers, int startingMoney, int port) throws IOException {
		try {
			listener = new ServerSocket(port);
			isListening = true;

		} catch (IOException e) {
			throw e;
		}
		System.out.println("Server is running");
		pokerGame = new PokerGame(numOfPlayers, startingMoney);
		this.start();
	}

	public void run() {
		try {
			try {
				while (true) {
					if (isListening) {
						for (int i = 0; i < pokerGame.getNumberOfPlayers(); ++i) {
							System.out.println(i);
							PlayerThread thread = new PlayerThread(listener.accept(), this);
							thread.start();
							System.out.println("Thread is running");
						}
						GameController.getInstance().sendMessageToAllPlayers(null, "MESSAGE All players connected...Game starts in the moment");
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
	
	public void addPlayer(Player player)
	{
		pokerGame.addNewPlayer(player);
	}
	
	public void removePlayer(Player player)
	{
		pokerGame.removePlayer(player);
	}

	public void addTakenSeat(int seat)
	{
		pokerGame.addTakenSeat(seat);
		GameController.getInstance().sendMessageToAllPlayers(null, "SEAT " + seat);
	}
	
	public String getTakenSeats()
	{
		return pokerGame.getTakenSeats();
	}
}
