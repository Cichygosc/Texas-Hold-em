package poker.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import poker.HumanPlayer;
import poker.Player;

public class PlayerThread extends Thread {

	private Socket playerSocket;
	private BufferedReader input;
	private PrintWriter output;
	private Server serverListener;
	private Player player;
	
	public PlayerThread(Socket socket, Server server) throws IOException
	{
		System.out.println("Creating thread");
		this.playerSocket = socket;
		this.serverListener = server;
		try
		{
			input = new BufferedReader(new InputStreamReader(playerSocket.getInputStream()));
			output = new PrintWriter(playerSocket.getOutputStream(), true);
			GameController.getInstance().addPlayerOutput(output);
			GameController.getInstance().addPlayerThread(this);
			output.println("WELCOME");
			HashMap<Integer, String> seats = serverListener.getTakenSeats();
			for (Map.Entry<Integer, String> entry: seats.entrySet())
				output.println("INIT PLACE " + entry.getKey() + " " + entry.getValue());
			output.println("END INIT");
			output.println("MESSAGE Choose a seat...");
		}
		catch(IOException e)
		{
			throw e;
		}
	}
	
	public void run()
	{
		System.out.println("Thread run");
		try
		{
			String in;
			while(true)
			{
				in = input.readLine();
				if (in.startsWith("NEW PLAYER"))
				{
					if (serverListener.getNumberOfConnectedPlayers() == 0)
					{
						System.out.println("Players: " + serverListener.getNumberOfConnectedPlayers());
						player = new HumanPlayer(in.substring(11), true);
						output.println("HOST True");
					}
					else player = new HumanPlayer(in.substring(11), false);
					serverListener.addPlayer(player);
					GameController.getInstance().sendMessageToAllPlayers(output, "MESSAGE " + in.substring(11) + " has joined the game");
				}
				else if (in.startsWith("SEAT"))
				{
					int seat = Integer.parseInt(in.substring(5));
					player.takeASeat(seat);
					serverListener.addTakenSeat(seat);
					GameController.getInstance().sendMessageToAllPlayers("SEAT " + seat + " " + player.getName());
					GameController.getInstance().sendMessageToAllPlayers(output, "MESSAGE " + player.getName() + " has taken seat number " + seat);
				}
				else
				{
					output.println("ERROR Unrecognized command");
				}
				sleep(500);
			}
		}
		catch (IOException e)
		{
			System.out.println(player.getName() + " PlayerThread IO error " + e);
		}
		catch (NullPointerException e)
		{
			System.out.println("PLAYER LEFT " + player.getSeat());		
		}
		catch (InterruptedException e)
		{
			System.out.println("PlayerThread error " + e);
		}
		finally
		{
			try
			{
				GameController.getInstance().removePlayerOutput(output);
				GameController.getInstance().removePlayerThread(this);
				if (!player.equals(null))
				{
					serverListener.removeTakenSeat(player.getSeat());
					serverListener.removePlayer(player);
				}
				GameController.getInstance().sendMessageToAllPlayers("PLAYER LEFT " + player.getSeat());
				GameController.getInstance().sendMessageToAllPlayers("MESSAGE " + player.getName() + " has left the game");
				playerSocket.close();
			}
			catch(Exception e)
			{
				System.out.println("PlayerThread error Couldnt close connection " + e);
			}
		}
	}
	
	public Player getPlayer()
	{
		return player;
	}
	
	public PrintWriter getPrintWriter()
	{
		return output;
	}
	
}
