package poker.server;

import java.io.PrintWriter;
import java.util.HashSet;

public class GameController {
	
	private static HashSet<PrintWriter> playerOutput = new HashSet<PrintWriter>();
	private static HashSet<PlayerThread> playerThread = new HashSet<PlayerThread>();
	
	private static volatile GameController instance;
	
	private GameController()
	{}
	
	public static GameController getInstance()
	{
		if (instance == null)
		{
			synchronized(GameController.class)
			{
				if (instance == null)
					instance = new GameController();
			}
		}
		return instance;
	}
	
	public void addPlayerOutput(PrintWriter writer)
	{
		playerOutput.add(writer);
	}
	
	public void removePlayerOutput(PrintWriter writer)
	{
		playerOutput.remove(writer);
	}
	
	public void addPlayerThread(PlayerThread thread)
	{
		playerThread.add(thread);
	}
	
	public void removePlayerThread(PlayerThread thread)
	{
		playerThread.remove(thread);
	}
	
	public void sendMessageToAllPlayers(PrintWriter sender, String message)
	{
		for (PrintWriter writer: playerOutput)
		{
			if (!writer.equals(sender))
				writer.println(message);
		}
	}
	
	public void sendMessageToPlayer(PrintWriter receiver, String message)
	{
		receiver.println(message);
	}

}