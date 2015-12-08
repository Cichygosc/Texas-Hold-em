package poker;

import java.util.List;
import java.util.Random;

import org.omg.PortableServer.ThreadPolicyValue;

import poker.server.GameController;
import poker.server.PokerGame;

public class Dealer {

	private Table table;
	private CardDeck deck;
	private PokerGame pokerGame;
	private Random random;
	private Dealer.GameStateBehavior gameState;

	public Dealer(PokerGame pokerGame) {
		this.pokerGame = pokerGame;
		random = new Random();
		table = new Table();
		deck = new CardDeck();
		deck.shuffleCards();
	}

	public void startGame() {
		table.startGame();
		newGame();
	}

	private void newGame() {
		table.newGame();
		gameState = GameState.Preflop.getStateBehavior();
		chooseDealer();
		table.nextPlayer();
		throwCards();
		getBlinds();
		nextPlayer();
	}

	private void getBlinds() {
		Player player = table.getCurrentPlayer();
		player.getPlayerPot().bet(pokerGame.getSmallBlind());
		player.getPlayerPot().setCurrentBet(pokerGame.getSmallBlind());
		table.addPot(pokerGame.getSmallBlind());
		GameController.getInstance().sendMessageToAllPlayers("MESSAGE " + player.getName() + " small blind");
		GameController.getInstance()
				.sendMessageToAllPlayers("MONEY " + player.getSeat() + " " + player.getPlayerPot().getMoney());
		GameController.getInstance().sendMessageToAllPlayers("POT " + table.getPot());
		table.nextPlayer();
		player = table.getCurrentPlayer();
		player.getPlayerPot().bet(pokerGame.getBigBlind());
		player.getPlayerPot().setCurrentBet(pokerGame.getBigBlind());
		table.addPot(pokerGame.getBigBlind());
		GameController.getInstance().sendMessageToAllPlayers("MESSAGE " + player.getName() + " big blind");
		GameController.getInstance()
				.sendMessageToAllPlayers("MONEY " + player.getSeat() + " " + player.getPlayerPot().getMoney());
		GameController.getInstance().sendMessageToAllPlayers("POT " + table.getPot());
		table.setCurrentBet(pokerGame.getBigBlind());
		table.setIsOpen(true);
	}

	private void nextPlayer() {
		GameController.getInstance().sendMessageToPlayer(table.getCurrentPlayer(), "END TURN");
		table.nextPlayer();
		Player player = table.getCurrentPlayer();
		if (table.getCurrentPlayerNumber() == table.getDealerPos() && checkEndOfRound())
			nextRound();
		while (player.getPlayerPot().isFold() || player.getPlayerPot().isAllIn()) {
			table.nextPlayer();
			player = table.getCurrentPlayer();
			if (table.getCurrentPlayerNumber() == table.getDealerPos() && checkEndOfRound())
				nextRound();
		}
		int call = table.getCurrentBet() - player.getPlayerPot().getCurrentBet();
		int raise = table.getCurrentBet() == 0 ? pokerGame.getBigBlind() : table.getCurrentBet();
		int maxRaise = player.getPlayerPot().getMoney();
		String buttons = "";
		if (table.getIsOpen())
			buttons += "Raise ";
		else
			buttons += "Bet ";
		if (call == 0)
			buttons += "Check ";
		if (call > 0)
			buttons += "Call Fold ";
		buttons += "AllIn";
		GameController.getInstance().sendMessageToPlayer(player,
				"YOUR TURN " + call + " " + raise + " " + maxRaise + " " + buttons);
	}
	
	private void nextRound()
	{
		for (Player player: table.getPlayers())
			player.getPlayerPot().newRound();
		GameController.getInstance().sendMessageToAllPlayers("NEXT ROUND");
		table.setCurrentBet(0);
		table.setIsOpen(false);
		gameState = gameState.nextState();
		int numOfCards = gameState.getNumberOfCards();		
		int pos = gameState.getCardsStartPos();
		if (numOfCards == 0)
			showdown();
		else showMiddleCards(numOfCards, pos);
	}

	private void chooseDealer() {
		int pos = table.getDealerPos();
		if (pos == -1)
			table.setDealerButton(random.nextInt(table.getPlayers().size()));
		else {
			if (pos == table.numOfPlayers() - 1)
				table.setDealerButton(0);
			else
				table.setDealerButton(pos + 1);
		}
	}

	private void throwCards() {
		for (Player player : table.getPlayers()) {
			for (int i = 0; i < 2; ++i) {
				Card card = deck.getCard();
				GameController.getInstance().sendMessageToPlayer(player,
						"CARD " + player.getSeat() + " " + i + " " + card.toString());
				GameController.getInstance().sendMessageToAllPlayers(player,
						"CARD " + player.getSeat() + " " + i + " " + "cb");
				player.getHand().addCardToHand(card);
			}
		}
	}
	
	private void showMiddleCards(int amount, int pos)
	{
		for (int i = 0; i < amount; ++i, ++pos)
		{
			Card card = deck.getCard();
			for (Player player : table.getPlayers())
				player.getHand().addCardOnBoard(card);
			GameController.getInstance().sendMessageToAllPlayers("BOARD CARD " + pos + " " + card.toString());
		}
	}

	private boolean checkEndOfRound() {
		List<Player> temp = table.getPlayers();
		int i = 0;
		int cash = -1;
		for (i = 0; i < temp.size(); ++i) {
			if (temp.get(i).getPlayerPot().isAllIn() || temp.get(i).getPlayerPot().isFold())
				continue;
			if (cash == -1) {
				cash = temp.get(i).getPlayerPot().getTotalCashUsedInRound();
				continue;
			}
			if (cash != temp.get(i).getPlayerPot().getTotalCashUsedInRound())
				return false;
		}
		return true;
	}

	private void showdown() {
		for (Player player: table.getPlayers())
		{
			for (int i = 0; i < 2; ++i)
				GameController.getInstance().sendMessageToAllPlayers(player, "CARD " + player.getSeat() + " " + i + " " + player.getHand().getCardFromHand(i).toString());
		}
	}

	public Table getTable() {
		return table;
	}

	public String getTakenSeats() {
		return table.getTakenSeats();
	}
	
	///////////////////////////////////////////////////////////////////
	/////////////////////////GAME STATES///////////////////////////////
	///////////////////////////////////////////////////////////////////
	interface GameStateBehavior
	{
		public int getNumberOfCards();
		public int getCardsStartPos();
		public GameStateBehavior nextState();
		public GameState getState();
	}
	
	enum GameState
	{
		Preflop
		{
			public GameStateBehavior getStateBehavior()
			{
				return new Dealer.PreflopState();
			}
		},
		
		Flop
		{
			public GameStateBehavior getStateBehavior()
			{
				return new Dealer.FlopState();
			}
		},
		
		Turn
		{
			public GameStateBehavior getStateBehavior()
			{
				return new Dealer.TurnState();
			}
		},
		
		River
		{
			public GameStateBehavior getStateBehavior()
			{
				return new Dealer.RiverState();
			}
		},
		
		Showdown
		{
			public GameStateBehavior getStateBehavior()
			{
				return new Dealer.ShowdownState();
			}
		};
		
		public GameStateBehavior getStateBehavior()
		{
			return null;
		}
	}
	
	static class PreflopState implements GameStateBehavior
	{

		@Override
		public int getNumberOfCards() {
			return 0;
		}

		@Override
		public GameStateBehavior nextState() {
			return GameState.Flop.getStateBehavior();
		}

		@Override
		public GameState getState() {
			return GameState.Preflop;
		}

		@Override
		public int getCardsStartPos() {
			return 0;
		}	
	}
	
	static class FlopState implements GameStateBehavior
	{

		@Override
		public int getNumberOfCards() {
			return 3;
		}

		@Override
		public GameStateBehavior nextState() {
			return GameState.Turn.getStateBehavior();
		}

		@Override
		public GameState getState() {
			return GameState.Flop;
		}

		@Override
		public int getCardsStartPos() {
			return 0;
		}
		
	}
	
	static class TurnState implements GameStateBehavior
	{

		@Override
		public int getNumberOfCards() {
			return 1;
		}

		@Override
		public GameStateBehavior nextState() {
			return GameState.River.getStateBehavior();
		}

		@Override
		public GameState getState() {
			return GameState.Turn;
		}

		@Override
		public int getCardsStartPos() {
			return 3;
		}
		
	}
	
	static class RiverState implements GameStateBehavior
	{

		@Override
		public int getNumberOfCards() {
			return 1;
		}

		@Override
		public GameStateBehavior nextState() {
			return GameState.Showdown.getStateBehavior();
		}

		@Override
		public GameState getState() {
			return GameState.River;
		}

		@Override
		public int getCardsStartPos() {
			return 4;
		}
		
	}
	
	static class ShowdownState implements GameStateBehavior
	{

		@Override
		public int getNumberOfCards() {
			return 0;
		}

		@Override
		public GameStateBehavior nextState() {
			return GameState.Preflop.getStateBehavior();
		}

		@Override
		public GameState getState() {
			return GameState.Showdown;
		}

		@Override
		public int getCardsStartPos() {
			return 0;
		}
		
	}

}
