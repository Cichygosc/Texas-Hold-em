package poker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class Table {

	private HashSet<Integer> takenSeats;
	private List<TablePot> sidePots;
	private int pot; // amount of money to win
	private int roundBet; // sum of all players bets/raises
	private int lastBet; // amount of last bet/raise
	private boolean isOpen; // was bet used before in this round

	public Table() {
		takenSeats = new HashSet<Integer>();
		sidePots = new ArrayList<TablePot>();
		isOpen = false;
		pot = 0;
		roundBet = 0;
		lastBet = 0;
	}

	public void addPot(int pot) {
		this.pot += pot;
	}

	public void newGame() {
		pot = 0;
		newRound();
	}

	public void newRound() {
		roundBet = 0;
		lastBet = 0;
		isOpen = false;
		sidePots.clear();
	}

	public void optimalizeSidePots() {
		for (int i = sidePots.size() - 1; i > 0; --i) {
			if (cmp(sidePots.get(i).getPlayers(), sidePots.get(i - 1).getPlayers()))
			{
				sidePots.get(i - 1).setBet(sidePots.get(i).getBet() + sidePots.get(i - 1).getBet());
				sidePots.get(i - 1).setPot(sidePots.get(i).getPot() + sidePots.get(i - 1).getPot());
				sidePots.remove(i);
			}
		}
	}

	public void checkSidePots(int bet, Player player) {
		if (sidePots.size() == 0)
			createSidePot(bet, player);
		else {
			for (int i = 0; i < sidePots.size(); ++i) {
				if (sidePots.get(i).getBet() == bet && !sidePots.get(i).contains(player)) {
					sidePots.get(i).increasePot();
					sidePots.get(i).addPlayer(player);
					bet = 0;
					break;
				} else if (sidePots.get(i).getBet() < bet && !sidePots.get(i).contains(player)) {
					bet -= sidePots.get(i).getBet();
					sidePots.get(i).addPlayer(player);
					sidePots.get(i).increasePot();
				} else if (sidePots.get(i).getBet() > bet && !sidePots.get(i).contains(player)) {
					List<Player> players = sidePots.get(i).getPlayers();
					int newBet = sidePots.get(i).getBet() - bet;
					createSidePot(newBet, players);
					sidePots.get(i).decreasePotByValue(players.size() * newBet);
					sidePots.get(i).setBet(bet);
					sidePots.get(i).increasePot();
					sidePots.get(i).addPlayer(player);
					bet = 0;
					break;
				}
			}
			if (bet > 0)
				createSidePot(bet, player);
		}
	}

	private void createSidePot(int bet, List<Player> players) {
		TablePot newPot = new TablePot();
		System.out.println(players.size());
		newPot.setBet(bet);
		for (Player player : players) {
			newPot.addPlayer(player);
			newPot.increasePot();
		}
		sidePots.add(newPot);
		Collections.sort(sidePots);
	}

	private void createSidePot(int bet, Player player) {
		TablePot newPot = new TablePot(bet);
		newPot.addPlayer(player);
		sidePots.add(newPot);
		Collections.sort(sidePots);
	}
	
	private static boolean cmp (List<Player> l1, List<Player> l2)
	{
		List<Player> temp = new ArrayList<Player>(l1);
		for (Player player: l2)
			if (!temp.remove(player))
				return false;
		return temp.isEmpty();
	}

	public int getPot() {
		return pot;
	}

	public void addTakenSeat(int seat) {
		takenSeats.add(seat);
	}

	public void removeTakenSeat(int seat) {
		if (seat > 0)
			takenSeats.remove(seat);
	}

	public void setLastBet(int bet) {
		this.lastBet = bet;
	}

	public void increaseRoundBet(int amount) {
		this.roundBet += amount;
	}

	public int getLastBet() {
		return lastBet;
	}

	public int getRoundBet() {
		return roundBet;
	}

	public void setIsOpen(boolean isOpen) {
		this.isOpen = isOpen;
	}

	public boolean getIsOpen() {
		return isOpen;
	}

	public List<TablePot> getTablePots() {
		return sidePots;
	}

	public String getTakenSeats() {
		if (takenSeats.size() == 0)
			return "";
		String seats = "";
		for (int seat : takenSeats)
			seats += seat + " ";
		seats.substring(0, seats.length() - 2);
		System.out.println(seats);
		return seats;
	}

}
