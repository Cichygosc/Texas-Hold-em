package poker;

import java.util.Random;

import poker.server.GameController;
import poker.server.PokerGame;
import sun.misc.Timeable;

public class Dealer {

	private Table table;
	private CardDeck deck;
	private PokerGame pokerGame;
	private Random random;
	
	public Dealer(PokerGame pokerGame)
	{
		this.pokerGame = pokerGame;
		random = new Random();
		table = new Table();
		deck = new CardDeck();
		deck.shuffleCards();
	}

	public void startGame()
	{
		table.startGame();
		newRound();
	}
	
	private void newRound()
	{
		table.newRound();
		chooseDealer();
		table.nextPlayer();
		throwCards();
		getAnte();
		getBlinds();
		nextPlayer();
	}
	
	private void getBlinds()
	{
		Player player = table.getCurrentPlayer();
		player.getPlayerPot().bet(pokerGame.getSmallBlind());
		player.getPlayerPot().setCurrentBet(pokerGame.getSmallBlind());
		table.addPot(pokerGame.getSmallBlind());
		GameController.getInstance().sendMessageToAllPlayers("MESSAGE " + player.getName() + " small blind");
		GameController.getInstance().sendMessageToAllPlayers("MONEY " + player.getSeat() + " " + player.getPlayerPot().getMoney());
		GameController.getInstance().sendMessageToAllPlayers("POT " + table.getPot());
		table.nextPlayer();
		player = table.getCurrentPlayer();
		player.getPlayerPot().bet(pokerGame.getBigBlind());
		player.getPlayerPot().setCurrentBet(pokerGame.getBigBlind());
		table.addPot(pokerGame.getBigBlind());
		GameController.getInstance().sendMessageToAllPlayers("MESSAGE " + player.getName() + " big blind");
		GameController.getInstance().sendMessageToAllPlayers("MONEY " + player.getSeat() + " " + player.getPlayerPot().getMoney());
		GameController.getInstance().sendMessageToAllPlayers("POT " + table.getPot());
		table.setCurrentBet(pokerGame.getBigBlind());
		table.setIsOpen(true);
	}
	
	private void nextPlayer()
	{
		GameController.getInstance().sendMessageToPlayer(table.getCurrentPlayer(), "END TURN");
		table.nextPlayer();
		Player player = table.getCurrentPlayer();
		int call = table.getCurrentBet() - player.getPlayerPot().getCurrentBet();
		String buttons = "";
		if (table.getIsOpen())
			buttons += "Raise ";
		else buttons += "Bet ";
		if (call == 0)
			buttons += "Check ";
		if (call > 0)
			buttons += "Call Fold ";
		buttons += "AllIn";
		GameController.getInstance().sendMessageToPlayer(player, "YOUR TURN " + call + " " + buttons);
	}
	
	private void chooseDealer()
	{
		int pos = table.getDealerPos();
		if (pos == -1)
			table.setDealerButton(random.nextInt(table.getPlayers().size()));
		else 
		{
			if (pos == table.numOfPlayers() - 1)
				table.setDealerButton(0);
			else
				table.setDealerButton(pos + 1);
		}
	}
	
	private void throwCards()
	{
		for (Player player: table.getPlayers())
		{
			for (int i = 0; i < 2; ++i)
			{
				Card card = deck.getCard();
				GameController.getInstance().sendMessageToPlayer(player, "CARD " + player.getSeat() + " " + i + " " + card.toString());
				GameController.getInstance().sendMessageToAllPlayers(player, "CARD " + player.getSeat() + " " + i + " " + "cb");
				player.getHand().addCardToHand(card);
			}
		}
	}
	
	private void getAnte()
	{
		for (Player player: table.getPlayers())
		{
			table.addPot(pokerGame.getAnte());
			player.getPlayerPot().bet(pokerGame.getAnte());
			GameController.getInstance().sendMessageToAllPlayers("MONEY " + player.getSeat() + " " + player.getPlayerPot().getMoney());
			GameController.getInstance().sendMessageToAllPlayers("POT " + table.getPot());
		}
	}
	
	public Table getTable()
	{
		return table;
	}
	
	public String getTakenSeats()
	{
		return table.getTakenSeats();
	}
	
}
