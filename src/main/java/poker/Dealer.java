package poker;

import java.util.ArrayList;
import java.util.List;

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
		List<Player> winners = new ArrayList<Player>();
		BestHand winnerHand = null;
		for (Player player: pokerRoom.getPlayers())
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
	}
	
	public void setCurrentBet(int bet)
	{
		table.setLastBet(bet);
		table.increaseRoundBet(bet);
		table.setIsOpen(true);
		addPot(bet);
	}
	
	public void addPot(int pot)
	{
		table.addPot(pot);
		pokerRoom.getGameController().sendMessageToAllPlayers("POT " + table.getPot());
	}

	public Table getTable() {
		return table;
	}

	public String getTakenSeats() {
		return table.getTakenSeats();
	}
}
