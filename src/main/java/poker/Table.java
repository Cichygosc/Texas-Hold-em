package poker;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Table {

	private HashSet<Integer> takenSeats;
	private List<Hand> playerHands;
	private List<TablePot> tablePots;
	
	public Table()
	{
		takenSeats = new HashSet<Integer>();
		playerHands = new ArrayList<Hand>();
		tablePots = new ArrayList<TablePot>();
	}
	
	public void addPlayerToTable(Hand hand)
	{
		playerHands.add(hand);
	}
	
	public void removePlayer(Hand hand)
	{
		playerHands.remove(hand);
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
	
	public void addTablePot(TablePot pot)
	{
		tablePots.add(pot);
	}
	
	public void removeTablePot(TablePot pot)
	{
		tablePots.remove(pot);
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
