package poker;

import java.io.IOException;
import java.net.Socket;

public class PokerClientApp implements IPokerApp{

	private GameModel gameModel;
	private GameView gameView;
	
	private Player player;
	private Socket clientSocket;
	private PlayerClient pokerClient;
	private int port;
	
	public PokerClientApp(int port, String playerName, GameModel gameModel) throws IOException
	{
		this.port = port;
		this.gameModel = gameModel;
		this.gameView = gameModel.getGameView();
		this.gameView.setPokerApp(this);
		try
		{
			connectSocket(playerName);
		}
		catch (IOException e)
		{
			throw e;
		}
	}
	
	private void connectSocket(String playerName) throws IOException
	{
		try
		{
			clientSocket = new Socket("localhost", this.port);
			player = new HumanPlayer(playerName, false);
			pokerClient = new PlayerClient(this);
		}
		catch(IOException e)
		{
			throw e;
		}
	}
	
	public void showMessage(String message)
	{
		gameModel.showMessage(message);
	}
	
	public void addTakenSeat(int seat, String playerName)
	{
		gameModel.addTakenSeat(seat, playerName);
	}
	
	public void removeTakenSeat(int seat)
	{
		gameModel.removeTakenSeat(seat);
	}
	
	public Socket getSocket()
	{
		return clientSocket;
	}
	
	public Player getThisPlayer()
	{
		return player;
	}
	
	public void startingGame()
	{
		gameView.pokerGameView();
	}
	
	public void addPlayerCard(String path, int seat, int pos)
	{
		if (seat == player.getSeat())
			player.getHand().addCardToHand(createCard(path));
		gameModel.addPlayerCard(path, seat, pos);
	}
	
	public void addBoardCard(String path, int pos)
	{
		player.getHand().addCardOnBoard(createCard(path));
		gameView.addMiddleCards(path, pos);
	}
	
	public Card createCard(String path)
	{
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
		else number = Integer.parseInt(n);
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
	
	public void setDealer(int seat)
	{
		gameView.setDealer(seat);
	}
	
	public void addMoney(int money)
	{
		player.getPlayerPot().setMoney(money);
		gameView.setMoney(player.getPlayerPot().getMoney(), player.getSeat());
	}

	public void setMoney(int money, int seat)
	{
		gameView.setMoney(money, seat);
	}
	
	public void setPot(int money)
	{
		gameView.setPot(money);
	}
	
	public void hideAllButtons()
	{
		gameView.hideButtons();
	}
	
	public void showButtons(String buttons)
	{
		gameView.showButtons(buttons);
	}
	
	public void setCallAndRaiseValue(int callValue, int raiseValue, int maxRaise)
	{
		gameView.setCallAndRaiseValue(callValue, raiseValue, maxRaise);
	}
	
	@Override
	public void sendMessage(String message) {
		pokerClient.sendMessage(message);
		
	}
}
