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
	private PokerRoom pokerRoom;
	
	public PlayerThread(Socket socket, Server server) throws IOException
	{
		System.out.println("Creating thread");
		this.playerSocket = socket;
		this.serverListener = server;
		this.pokerRoom = null;
		try
		{
			input = new BufferedReader(new InputStreamReader(playerSocket.getInputStream()));
			output = new PrintWriter(playerSocket.getOutputStream(), true);
		}
		catch(IOException e)
		{
			throw e;
		}
	}
	
	private void initClient()
	{
		HashMap<Integer, String> seats = pokerRoom.getTakenSeats();
		for (Map.Entry<Integer, String> entry: seats.entrySet())
			output.println("INIT PLACE " + entry.getKey() + " " + entry.getValue());
		output.println("END INIT");
		output.println("MESSAGE Choose a seat...");
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
					if (pokerRoom.getNumOfConnectedPlayers() == 0)
					{
						System.out.println("Players: " + serverListener.getNumberOfConnectedPlayers());
						player = new HumanPlayer(in.substring(11), true);
						output.println("HOST True");
					}
					else player = new HumanPlayer(in.substring(11), false);
					pokerRoom.addPlayer(player);
					pokerRoom.getGameController().addPlayerThread(this);
					pokerRoom.getGameController().addPlayerOutput(output);
				}
				else if (in.startsWith("SEAT"))
				{
					int seat = Integer.parseInt(in.substring(5));
					player.takeASeat(seat);
					pokerRoom.getTable().addTakenSeat(seat);
					pokerRoom.getGameController().sendMessageToAllPlayers(output, "SEAT " + seat + " " + player.getName());
					pokerRoom.getGameController().sendMessageToAllPlayers(output, "MESSAGE " + player.getName() + " has taken seat number " + seat);
				}
				else if (in.startsWith("TABLE "))
				{
					pokerRoom = serverListener.getPokerRoom(Integer.parseInt(in.substring(6)));
					initClient();
				}
				else if (in.startsWith("BET"))
				{
					int bet = Integer.parseInt(in.substring(4));
					player.getPlayerPot().bet(bet);
					player.getPlayerPot().setCurrentBet(bet);
					pokerRoom.getDealer().setCurrentBet(bet);
					pokerRoom.getGameController().sendMessageToAllPlayers("MESSAGE " + player.getName() + " bet " + bet);
				}
				else if (in.startsWith("CALL"))
				{
					int call = Integer.parseInt(in.substring(5));
					player.getPlayerPot().bet(call);
					player.getPlayerPot().setCurrentBet(call);
					pokerRoom.getDealer().addPot(call);
					pokerRoom.getGameController().sendMessageToAllPlayers("MESSAGE " + player.getName() + " call " + call);
				}
				else if (in.startsWith("FOLD"))
				{
					player.getPlayerPot().fold();
					pokerRoom.getGameController().sendMessageToAllPlayers("MESSAGE " + player.getName() + " has fold");
				}
				else if (in.startsWith("ALLIN"))
				{
					pokerRoom.getDealer().addPot(player.getPlayerPot().getMoney());
					player.getPlayerPot().allIn();
					pokerRoom.getGameController().sendMessageToAllPlayers("MESSAGE " + player.getName() + " all in");
				}
				else if (in.startsWith("NEXT PLAYER"))
				{
					pokerRoom.getGameController().sendMessageToAllPlayers("MONEY " + player.getSeat() + " " + player.getPlayerPot().getMoney());
					pokerRoom.nextPlayerTurn();
				}
				else
				{
					output.println("MESSAGE Unrecognized command");
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
			System.out.println("PLAYER LEFT " + player.getName());		
		}
		catch (InterruptedException e)
		{
			System.out.println("PlayerThread error " + e);
		}
		finally
		{
			try
			{
				System.out.println("Closing thread");
				pokerRoom.getGameController().removePlayerOutput(output);
				pokerRoom.getGameController().removePlayerThread(this);
				if (!player.equals(null))
				{
					pokerRoom.getTable().removeTakenSeat(player.getSeat());
					pokerRoom.removePlayer(player);
				}
				pokerRoom.getGameController().sendMessageToAllPlayers("PLAYER LEFT " + player.getSeat());
				pokerRoom.getGameController().sendMessageToAllPlayers("MESSAGE " + player.getName() + " has left the game");
				playerSocket.close();
			}
			catch(Exception e)
			{
				System.out.println("PlayerThread error Couldnt close connection " + e);
			}
		}
	}
	
	public void setPokerRoom(PokerRoom pokerRoom)
	{
		this.pokerRoom = pokerRoom;
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
