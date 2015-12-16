package poker;

import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;
import org.omg.PortableServer.POAManagerOperations;

import com.sun.corba.se.impl.protocol.AddressingDispositionException;

import poker.server.PokerRoom;

public class Dealer {

	private Table table;
	private CardDeck deck;
	private PokerRoom pokerRoom;
	
	public Dealer(PokerRoom pokerRoom) {
		this.pokerRoom = pokerRoom;
		table = new Table();
		deck = new CardDeck();
		deck.shuffleCards();
	}

	public void newGame() {
		table.newGame();
	}

	public void throwCards() {
		for (Player player : pokerRoom.getPlayers()) {
			for (int i = 0; i < 2; ++i) {
				Card card = deck.getCard();
				pokerRoom.getGameController().sendMessageToPlayer(player,
						"CARD " + player.getSeat() + " " + i + " " + card.toString());
				pokerRoom.getGameController().sendMessageToAllPlayers(player,
						"CARD " + player.getSeat() + " " + i + " " + "cb");
				player.getHand().addCardToHand(card);
			}
		}
	}
	
	public void showMiddleCards(int amount, int pos)
	{
		for (int i = 0; i < amount; ++i, ++pos)
		{
			Card card = deck.getCard();
			for (Player player : pokerRoom.getPlayers())
				player.getHand().addCardOnBoard(card);
			pokerRoom.getGameController().sendMessageToAllPlayers("BOARD CARD " + pos + " " + card.toString());
		}
	}

	public void showdown() {
		pokerRoom.getGameController().sendMessageToAllPlayers("END TURN");
		pokerRoom.getGameController().sendMessageToAllPlayers("MESSAGE Checking cards...");
		for (Player player: pokerRoom.getPlayers())
		{
			for (int i = 0; i < 2; ++i)
				pokerRoom.getGameController().sendMessageToAllPlayers(player, "CARD " + player.getSeat() + " " + i + " " + player.getHand().getCardFromHand(i).toString());
		}
		lookForWinner();
	}
	
	private void lookForWinner()
	{
		table.optimalizeSidePots();
		for (int i = 0; i < table.getTablePots().size(); ++i)
		{
			TablePot pot = table.getTablePots().get(i);
			List<Player> winners = new ArrayList<Player>();
			BestHand winnerHand = null;
			for (Player player: pot.getPlayers())
			{
				if (player.getPlayerPot().isFold())
					continue;
				if (winners.isEmpty())
				{
					winners.add(player);
					winnerHand = player.getHand().getBestHand();
					continue;
				}
				BestHand comHand = player.getHand().getBestHand();
				if (winnerHand.getRank() < comHand.getRank())
				{
					winners.clear();
					winners.add(player);
					winnerHand = comHand;
					continue;
				}
				else if (winnerHand.getRank() > comHand.getRank())
				{
					continue;
				}
				int handCompare = winnerHand.compareHighCards(comHand);
				if (handCompare == 1)
				{
					continue;
				}
				else if (handCompare == -1)
				{
					winners.clear();
					winners.add(player);
					winnerHand = comHand;
				}
				else if (handCompare == 0)
				{
					winners.add(player);
				}
			}
			int moneyWin = pot.getPot() / winners.size();
			for (Player player: winners)
			{
				player.getPlayerPot().addMoney(moneyWin);
				pokerRoom.getGameController().sendMessageToAllPlayers("MONEY " + player.getSeat() + " " + player.getPlayerPot().getMoney());
			}
		}
	}
	
	public void setCurrentBet(int bet)
	{
		if (table.getIsOpen())
			pokerRoom.increaseRaiseTimes();
		table.setLastBet(bet);
		table.increaseRoundBet(bet);
		table.setIsOpen(true);
		addPot(bet);
	}
	
	public void addPot(int pot)
	{
		table.addPot(pot);
		table.checkSidePots(pot, pokerRoom.getCurrentPlayer());
		pokerRoom.getGameController().sendMessageToAllPlayers("POT " + table.getPot());
	}

	public Table getTable() {
		return table;
	}

	public String getTakenSeats() {
		return table.getTakenSeats();
	}
	
	////////////////////////////////////////////////////
	//////////// METHODS USED ONLY IN TESTS/////////////
	////////////////////////////////////////////////////
	
	public void setCurrentBet(int bet, Player player)
	{
		table.setLastBet(bet);
		table.increaseRoundBet(bet);
		table.setIsOpen(true);
		addPot(bet, player);
	}
	
	public void addPot(int pot, Player player)
	{
		table.addPot(pot);
		table.checkSidePots(pot, player);
	}
	
}
