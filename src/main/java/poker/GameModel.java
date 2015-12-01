package poker;

import java.io.IOException;
import java.util.HashSet;

import poker.server.Server;

public class GameModel {

	private GameScreen gameScreen;
	private IPokerApp clientApp;
	private HashSet<Integer> takenSeats;
	public GameModel() {
		gameScreen = new GameScreen(this);
		takenSeats = new HashSet<Integer>();
	}

	public void hostServer(int numOfPlayers, int startingMoney, int port) throws IOException {
		new Server(numOfPlayers, startingMoney, port);
	}

	public void joinServer(int port, String name) throws IOException
	{
		PokerAppGenerator generator = new PokerClientAppGenerator();
		clientApp = generator.instantiate(port, name, this);
	}

	public void showMessage(String message) {
		gameScreen.showMessage(message);
	}
	
	public void sendMessage(String message) {
		clientApp.sendMessage(message);
	}

	public Player getPlayer()
	{
		return clientApp.getThisPlayer();
	}
	
	public void addTakenSeat(int seat)
	{
		takenSeats.add(seat);
		gameScreen.addTakenSeat(seat);
	}
	
	public void removeTakenSeat(int seat)
	{
		takenSeats.remove(seat);
		gameScreen.removeTakenSeat(seat);
	}
	
	public HashSet<Integer> getTakenSeats()
	{
		return takenSeats;
	}
	
	public static void main(String[] args) {
		new GameModel();
	}
	

}
