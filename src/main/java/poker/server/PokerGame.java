package poker.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.TimeUnit;

import poker.Card;
import poker.Dealer;
import poker.Player;
import poker.Table;

public class PokerGame {
	
	private static int ante = 5;
	
	private List<Player> connectedPlayers;
	private Dealer dealer;
	private int numOfPlayers;
	private int startingMoney;
	private int smallBlind;
	private int bigBlind;
	private String rules;
	private int maxRaiseAmount;
	private int maxRaiseTimes;
	
	public PokerGame(int numOfPlayers, int startingMoney, int smallBlind, int bigBlind, String rules, int maxRaiseAmount, int maxRaiseTimes)
	{
		connectedPlayers = new ArrayList<Player>();
		dealer = new Dealer(this);
		this.numOfPlayers = numOfPlayers;
		this.startingMoney = startingMoney;
		this.smallBlind = smallBlind;
		this.bigBlind = bigBlind;
		this.rules = rules;
		this.maxRaiseAmount = maxRaiseAmount;
		this.maxRaiseTimes = maxRaiseTimes;
	}
	
	public void startGame()
	{
		while (true)
		{
			if (gameReadyToStart())
			{
				GameController.getInstance().sendMessageToAllPlayers("GAME STARTING");
				GameController.getInstance().sendMessageToAllPlayers("MESSAGE Game started, have fun");
				for (Player player: connectedPlayers)
				{
					player.getPlayerPot().addMoney(startingMoney);
					GameController.getInstance().sendMessageToAllPlayers("MONEY " + player.getSeat() + " " + startingMoney);
				}
				dealer.startGame();
				break;
			}
			else
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					System.out.println("PokerGame error in startGame " + e);
				}
		}
	}
	
	public void addNewPlayer(Player player)
	{
		connectedPlayers.add(player);
		dealer.getTable().addPlayerToTable(player);
	}
	
	public void removePlayer(Player player)
	{
		connectedPlayers.remove(player);
		dealer.getTable().removePlayer(player);
	}
	
	public int getMaxNumberOfPlayers()
	{
		return numOfPlayers;
	}
	
	public int getNumberOfConnectedPlayers()
	{
		return connectedPlayers.size();
	}
	
	public void addTakenSeat(int seat)
	{
		dealer.getTable().addTakenSeat(seat);
	}
	
	public void removeTakenSeat(int seat)
	{
		dealer.getTable().removeTakenSeat(seat);
	}
	
	public HashMap<Integer, String> getTakenSeats()
	{
		HashMap<Integer, String> seats = new HashMap<Integer, String>();
		for (Player player : connectedPlayers)
			if (player.getSeat() != -1)
				seats.put(player.getSeat(), player.getName());
		
		return seats;
	}
	
	public boolean gameReadyToStart()
	{
		if (connectedPlayers.size() < numOfPlayers)
			return false;
		for (Player p: connectedPlayers)
			if (p.getSeat() == -1)
				return false;
		return true;
	}
	
	public int getAnte()
	{
		return ante;
	}
	
	public int getSmallBlind()
	{
		return smallBlind;
	}
	
	public int getBigBlind()
	{
		return bigBlind;
	}
}
