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

	public void hostServer(int players, int tables, int money, int port, int smallBlind, int bigBlind, String rules, int raiseAmount, int raiseTimes) throws IOException {
		new Server(players, tables, money, smallBlind, bigBlind, rules, raiseAmount, raiseTimes, port);
	}

	public void joinServer(int port, String name) throws IOException {
		PokerAppGenerator generator = new PokerClientAppGenerator();
		clientApp = generator.instantiate(port, name, this);
	}

	public void showMessage(String message) {
		gameScreen.showMessage(message);
	}

	public void sendMessage(String message) {
		clientApp.sendMessage(message);
	}

	public Player getPlayer() {
		return clientApp.getThisPlayer();
	}

	public void addTakenSeat(int seat, String playerName) {
		takenSeats.add(seat);
		gameScreen.getGameView().addTakenSeat(seat, playerName);
	}

	public void removeTakenSeat(int seat) {
		takenSeats.remove(seat);
		gameScreen.getGameView().removeTakenSeat(seat);
	}
	
	public void addPlayerCard(String path, int seat, int pos)
	{
		gameScreen.getGameView().addPlayerCard(path, seat, pos);
	}
	
	public GameView getGameView()
	{
		return gameScreen.getGameView();
	}

	public HashSet<Integer> getTakenSeats() {
		return takenSeats;
	}

	public static void main(String[] args) {
		new GameModel();
	}

}
