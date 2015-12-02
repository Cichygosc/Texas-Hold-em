package poker.server;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.TimeUnit;

import poker.Card;
import poker.Dealer;
import poker.Player;
import poker.Table;

public class PokerGame {
	
	private List<Player> connectedPlayers;
	private Dealer dealer;
	private Player currentPlayer;
	private int numOfPlayers;
	private int startingMoney;
	
	public PokerGame(int numOfPlayers, int startingMoney)
	{
		connectedPlayers = new ArrayList<Player>();
		dealer = new Dealer();
		this.numOfPlayers = numOfPlayers;
		this.startingMoney = startingMoney;
	}
	
	public void startGame()
	{
		while (true)
		{
			if (gameReadyToStart())
			{
				GameController.getInstance().sendMessageToAllPlayers(null, "GAME STARTING");
				GameController.getInstance().sendMessageToAllPlayers(null, "MESSAGE Game started, have fun");
				for (Player player: connectedPlayers)
				{
					for (int i = 0; i < 2; ++i)
					{
						Card card = dealer.drawCard();
						GameController.getInstance().sendMessageToAllPlayers(null, "CARD " + player.getSeat() + " " + i + " " + card.toString());
						player.getHand().addCardToHand(card);
					}
				}
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
		dealer.getTable().addPlayerToTable(player.getHand());
		dealer.getTable().addTablePot(player.getPlayerPot().getTablePot());
	}
	
	public void removePlayer(Player player)
	{
		connectedPlayers.remove(player);
		dealer.getTable().removePlayer(player.getHand());
		dealer.getTable().removeTablePot(player.getPlayerPot().getTablePot());
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
	
	public String getTakenSeats()
	{
		return dealer.getTakenSeats();
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
}
