package poker;

import java.util.ArrayList;
import java.util.List;

public class TablePot implements Comparable<TablePot>{

	private int pot;
	private int bet;
	private List<Player> players;
	
	public TablePot()
	{
		pot = 0;
		bet = 0;
		players = new ArrayList<Player>();
	}
	
	public TablePot(int bet)
	{
		pot = bet;
		this.bet = bet;
		players = new ArrayList<Player>();
	}
	
	public void newRound()
	{
		pot = 0;
		bet = 0;
		players.clear();
	}
	
	public void increasePot()
	{
		pot += bet;
	}
	
	public void setBet(int bet)
	{
		this.bet = bet;
	}
	
	public void setPot(int pot)
	{
		this.pot = pot;
	}
	
	public void decreasePotByValue(int value)
	{
		this.pot -= value;
	}
	
	public void addPot(int pot)
	{
		this.pot += pot;
	}
	
	public void addPlayer(Player player)
	{
		this.players.add(player);
	}
	
	public boolean contains(Player player)
	{
		return players.contains(player);
	}
	
	public int getPot()
	{
		return pot;
	}
	
	public int getBet()
	{
		return bet;
	}
	
	public int getPlayersCount()
	{
		return players.size();
	}
	
	public List<Player> getPlayers()
	{
		return players;
	}

	@Override
	public int compareTo(TablePot o) {
		
		return this.getPlayersCount() > o.getPlayersCount() ? -1 : this.getPlayersCount() < o.getPlayersCount() ? 1 : 0;
	}	
	
}
