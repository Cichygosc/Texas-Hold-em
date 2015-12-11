package poker;

import java.util.HashSet;

public class Table {

	private HashSet<Integer> takenSeats;
	private int pot;				//amount of money to win
	private int roundBet;			//sum of all players bets/raises
	private int lastBet;			//amount of last bet/raise
	private boolean isOpen;			//was bet used before in this round
	
	public Table()
	{
		takenSeats = new HashSet<Integer>();
		isOpen = false;
		pot = 0;
		roundBet = 0;
		lastBet = 0;
	}
	
	public void addPot(int pot)
	{
		this.pot += pot;
	}
	
	public void newGame()
	{
		pot = 0;
		newRound();
	}
	
	public void newRound()
	{
		roundBet = 0;
		lastBet = 0;
		isOpen = false;
	}
	
	public int getPot()
	{
		return pot;
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

	public void setLastBet(int bet)
	{
		this.lastBet = bet;
	}
	
	public void increaseRoundBet(int amount)
	{
		this.roundBet += amount;
	}
	
	public int getLastBet()
	{
		return lastBet;
	}
	
	public int getRoundBet()
	{
		return roundBet;
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
