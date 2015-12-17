package poker.AI;

import java.io.IOException;
import java.net.Socket;

import poker.Card;
import poker.GameModel;
import poker.IPokerApp;
import poker.Player;

public class PokerAIApp implements IPokerApp{
	
	private GameModel gameModel;
	
	private Player player;
	private Socket clientSocket;
	private AIClient aiClient;
	private int port;

	public PokerAIApp(int port, String name, GameModel model) throws IOException
	{
		this.port = port;
		this.gameModel = model;
	}

	public void connectSocket(String name, int seat, int table) throws IOException {
		clientSocket = new Socket("localhost", port);
		player = new AIPlayer(name);
		player.takeASeat(seat);
		aiClient = new AIClient(this, table);
	}
	
	public void makeMove(int call, int raise, int maxRaise, String buttons)
	{
		sendMessage(((AIPlayer)player).makeMove(call, raise, maxRaise, buttons));
		sendMessage("NEXT PLAYER");
	}
	
	public void addPlayerCard(String path, int seat) {
		if (seat == player.getSeat())
			player.getHand().addCardToHand(createCard(path));
	}

	public void addBoardCard(String path) {
		player.getHand().addCardOnBoard(createCard(path));
	}
	
	public void addMoney(int money) {
		player.getPlayerPot().setMoney(money);
	}
	
	public Card createCard(String path) {
		String n = path.substring(0, path.length() - 1);
		int number;
		if (n.equals("j"))
			number = 9;
		else if (n.equals("q"))
			number = 10;
		else if (n.equals("k"))
			number = 11;
		else if (n.equals("a"))
			number = 12;
		else
			number = Integer.parseInt(n);
		String s = path.substring(path.length() - 1);
		if (s.equals("s"))
			return new Card(number, 0);
		else if (s.equals("c"))
			return new Card(number, 1);
		else if (s.equals("h"))
			return new Card(number, 2);
		else if (s.equals("d"))
			return new Card(number, 3);
		return null;
	}

	@Override
	public Player getPlayer() {
		return player;
	}

	@Override
	public void sendMessage(String message) {
		aiClient.sendMessage(message);
	}

	@Override
	public void initPlayer() {
		// DO NOTHING
		// NOT NEED TO INIT BOT
	}

	@Override
	public Socket getSocket() {
		return clientSocket;
	}
}
