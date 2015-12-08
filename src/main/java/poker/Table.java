package poker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import poker.server.GameController;

public class Table {

	private HashSet<Integer> takenSeats;
	private List<Player> players; 
	private Player currentPlayer;
	private int currentPlayerNum;
	private int pot;
	private int currentBet;
	private int dealerButtonPos;
	private boolean isOpen;
	
	public Table()
	{
		takenSeats = new HashSet<Integer>();
		players = new ArrayList<Player>();
		currentPlayer = null;
		isOpen = false;
		currentPlayerNum = -1;
		pot = 0;
		currentBet = 0;
		dealerButtonPos = -1;
	}
	
	public void addPot(int pot)
	{
		this.pot += pot;
	}
	
	public void newGame()
	{
		pot = 0;
		currentBet = 0;
	}
	
	public int getPot()
	{
		return pot;
	}
	
	public void startGame()
	{
		Collections.sort(players);
	}
	
	public void addPlayerToTable(Player player)
	{
		players.add(player);
	}
	
	public void removePlayer(Player player)
	{
		players.remove(player);
	}
	
	public void addTakenSeat(int seat)
	{
		takenSeats.add(seat);
	}
	
	public void removeTakenSeat(int seat)
	{
		if (seat > 0)
			takenSeats.remove(seat);
	}
	
	public void setDealerButton(int pos)
	{
		dealerButtonPos = pos;
		currentPlayer = players.get(dealerButtonPos);
		currentPlayerNum = dealerButtonPos;
		GameController.getInstance().sendMessageToAllPlayers("DEALER " + players.get(pos).getSeat());
	}
	
	public void nextPlayer()
	{
		if (currentPlayerNum == players.size() - 1)
			currentPlayerNum = 0;
		else currentPlayerNum++;
		currentPlayer = players.get(currentPlayerNum);
	}
	
	public int getDealerPos()
	{
		return dealerButtonPos;
	}
	
	public List<Player> getPlayers() 
	{
		return players;
	}
	
	public int numOfPlayers()
	{
		return players.size();
	}
	
	public Player getCurrentPlayer()
	{
		return currentPlayer;
	}
	
	public int getCurrentPlayerNumber()
	{
		return currentPlayerNum;
	}
	
	public void setCurrentBet(int currentBet)
	{
		this.currentBet = currentBet;
	}
	
	public int getCurrentBet()
	{
		return currentBet;
	}
	
	public void setIsOpen(boolean isOpen)
	{
		this.isOpen = isOpen;
	}
	
	public boolean getIsOpen()
	{
		return isOpen;
	}
	
	public String getTakenSeats()
	{
		if (takenSeats.size() == 0)
			return "";
		String seats = "";
		for (int seat: takenSeats)
			seats += seat + " ";
		seats.substring(0, seats.length() - 2);
		System.out.println(seats);
		return seats;
	}
	
}
