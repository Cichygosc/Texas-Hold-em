package poker.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;

import com.sun.corba.se.impl.protocol.AddressingDispositionException;

import poker.Player;

public class Server extends Thread {

	private ServerSocket listener;
	private PokerGame pokerGame;
	private boolean isListening;

	public Server(int players, int money, int smallBlind, int bigBlind, String rules, int raiseAmount, int raiseTimes, int port) throws IOException {
		try {
			listener = new ServerSocket(port);
			isListening = true;

		} catch (IOException e) {
			throw e;
		}
		System.out.println("Server is running");
		pokerGame = new PokerGame(players, money, smallBlind, bigBlind, rules, raiseAmount, raiseTimes);
		this.start();
	}

	public void run() {
		try {
			try {
				while (true) {
					if (isListening) {
						for (int i = 0; i < pokerGame.getMaxNumberOfPlayers(); ++i) {
							System.out.println(i);
							PlayerThread thread = new PlayerThread(listener.accept(), this);
							thread.start();
							System.out.println("Thread is running");
						}
						GameController.getInstance().sendMessageToAllPlayers("MESSAGE All players connected...Game starts in the moment");
						isListening = false;
						pokerGame.startGame();
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
	
	public int getMaxNumberOfPlayers()
	{
		return pokerGame.getMaxNumberOfPlayers();
	}
	
	public int getNumberOfConnectedPlayers()
	{
		return pokerGame.getNumberOfConnectedPlayers();
	}

	public void addTakenSeat(int seat)
	{
		pokerGame.addTakenSeat(seat);
	}
	
	public void removeTakenSeat(int seat)
	{
		pokerGame.removeTakenSeat(seat);
	}
	
	public void setCurrentBet(int bet)
	{
		pokerGame.setCurrentBet(bet);
	}
	
	public void addPot(int pot)
	{
		pokerGame.addPot(pot);
	}
	
	public void nextPlayer()
	{
		pokerGame.nextPlayer();
	}
	
	public HashMap<Integer, String> getTakenSeats()
	{
		return pokerGame.getTakenSeats();
	}
}
