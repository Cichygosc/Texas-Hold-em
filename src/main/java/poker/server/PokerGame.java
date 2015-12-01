package poker.server;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import poker.Player;
import poker.Table;

public class PokerGame {
	
	private List<Player> connectedPlayers;
	private Table table;
	private Player currentPlayer;
	private int numOfPlayers;
	private int startingMoney;
	
	public PokerGame(int numOfPlayers, int startingMoney)
	{
		connectedPlayers = new ArrayList<Player>();
		table = new Table();
		this.numOfPlayers = numOfPlayers;
		this.startingMoney = startingMoney;
	}
	
	public void addNewPlayer(Player player)
	{
		connectedPlayers.add(player);
		table.addPlayerToTable(player.getHand());
	}
	
	public void removePlayer(Player player)
	{
		connectedPlayers.remove(player);
		table.removePlayer(player.getHand());
	}
	
	public int getNumberOfPlayers()
	{
		return numOfPlayers;
	}
	
	public void addTakenSeat(int seat)
	{
		table.addTakenSeat(seat);
	}
	
	public String getTakenSeats()
	{
		return table.getTakenSeats();
	}
	
	public boolean gameReadyToStart()
	{
		for (Player p: connectedPlayers)
			if (p.getSeat() == -1)
				return false;
		return true;
	}
}
