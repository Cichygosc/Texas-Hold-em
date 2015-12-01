package poker;

import java.io.IOException;
import java.net.Socket;

public class PokerClientApp implements IPokerApp{

	private GameModel gameModel;
	
	private Player player;
	private Socket clientSocket;
	private PlayerClient pokerClient;
	private int port;
	
	public PokerClientApp(int port, String playerName, GameModel gameModel) throws IOException
	{
		this.port = port;
		this.gameModel = gameModel;
		try
		{
			connectSocket(port, playerName);
		}
		catch (IOException e)
		{
			throw e;
		}
	}
	
	private void connectSocket(int port, String playerName) throws IOException
	{
		try
		{
			clientSocket = new Socket("localhost", this.port);
			player = new HumanPlayer(playerName);
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
	
	public void addTakenSeat(int seat)
	{
		gameModel.addTakenSeat(seat);
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

	@Override
	public void sendMessage(String message) {
		pokerClient.sendMessage(message);
		
	}

	@Override
	public void takeASeat(int seat) {
		player.takeASeat(seat);
	}
}
