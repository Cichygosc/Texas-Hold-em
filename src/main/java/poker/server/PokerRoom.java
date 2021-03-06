package poker.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.sun.org.apache.bcel.internal.generic.ReturnaddressType;

import poker.Dealer;
import poker.Player;
import poker.Table;

public class PokerRoom {

	private List<Player> connectedPlayers;
	private int numOfConnectedPlayers;
	private GameStateBehavior gameState;
	private GameController gameController;
	private Dealer dealer;
	private Random random;

	private Player currentPlayer;
	private int currentPlayerNumber;
	private int dealerPosition;
	private boolean isDealerTurn;

	private int playersFoldOrAllIn;
	private int raisedTimes;
	private boolean gameEnded;

	public PokerRoom() {
		connectedPlayers = new ArrayList<Player>();
		numOfConnectedPlayers = 0;
		gameController = new GameController();
		dealer = new Dealer(this);
		isDealerTurn = false;
		currentPlayer = null;
		currentPlayerNumber = -1;
		dealerPosition = -1;
		raisedTimes = 0;
		playersFoldOrAllIn = 0;
		gameEnded = false;
		random = new Random();
	}

	public void startGame() {
		while (true) {
			System.out.println("trying to start the game");
			if (gameReadyToStart()) {
				gameController.sendMessageToAllPlayers("GAME STARTING");
				gameController.sendMessageToAllPlayers("MESSAGE Game started, have fun");
				Collections.sort(connectedPlayers);
				int startingMoney = GameSettings.getInstance().getStartingMoney();
				for (Player player : connectedPlayers) {
					player.getPlayerPot().addMoney(startingMoney);
					gameController.sendMessageToAllPlayers("MONEY " + player.getSeat() + " " + startingMoney);
				}
				newGame();
				break;
			} else {
				try {
					TimeUnit.SECONDS.sleep(3);
				} catch (InterruptedException e) {
					System.out.println("Error occured while starting the game in PokerRoom " + e);
				}
			}
		}
	}

	private void newGame() {
		System.out.println("Starting new game");
		for (Player player:connectedPlayers)
		{
			player.getPlayerPot().newGame();
			player.getHand().newGame();
		}
		gameState = GameState.Preflop.getStateBehavior();
		raisedTimes = 0;
		playersFoldOrAllIn = 0;
		gameEnded = false;
		dealer.newGame();
		chooseDealer();
		findAvailableNextPlayer();
		dealer.throwCards();
		getBlinds();
		nextPlayerTurn();
	}

	public boolean gameReadyToStart() {
		if (GameSettings.getInstance().getNumOfPlayers() > connectedPlayers.size())
			return false;
		for (Player player : connectedPlayers)
			if (player.getSeat() == -1)
				return false;
		return true;
	}

	public void getBlinds() {
		Table table = dealer.getTable();
		currentPlayer.getPlayerPot().bet(GameSettings.getInstance().getSmallBlind());
		currentPlayer.getPlayerPot().setCurrentBet(GameSettings.getInstance().getSmallBlind());
		dealer.addPot(GameSettings.getInstance().getSmallBlind());
		gameController.sendMessageToAllPlayers("MESSAGE " + currentPlayer.getName() + " small blind");
		gameController.sendMessageToAllPlayers(
				"MONEY " + currentPlayer.getSeat() + " " + currentPlayer.getPlayerPot().getMoney());
		findAvailableNextPlayer();
		currentPlayer.getPlayerPot().bet(GameSettings.getInstance().getBigBlind());
		currentPlayer.getPlayerPot().setCurrentBet(GameSettings.getInstance().getBigBlind());
		dealer.addPot(GameSettings.getInstance().getBigBlind());
		gameController.sendMessageToAllPlayers("MESSAGE " + currentPlayer.getName() + " big blind");
		gameController.sendMessageToAllPlayers(
				"MONEY " + currentPlayer.getSeat() + " " + currentPlayer.getPlayerPot().getMoney());
		table.increaseRoundBet(GameSettings.getInstance().getBigBlind());
		table.setLastBet(GameSettings.getInstance().getBigBlind());
		table.setIsOpen(true);
	}

	public void setDealerPos(int pos) {
		dealerPosition = pos;
		currentPlayer = connectedPlayers.get(dealerPosition);
		currentPlayerNumber = dealerPosition;
		gameController.sendMessageToAllPlayers("DEALER " + currentPlayer.getSeat());
	}

	public void findAvailableNextPlayer() {
		isDealerTurn = false;
		nextPlayer();
		if (currentPlayerNumber == dealerPosition)
			isDealerTurn = true;
		while (currentPlayer.getPlayerPot().isAllIn() || currentPlayer.getPlayerPot().isFold()) {
			nextPlayer();
			if (currentPlayerNumber == dealerPosition)
				isDealerTurn = true;
		}
	}

	private void nextPlayer() {
		if (currentPlayerNumber == connectedPlayers.size() - 1)
			currentPlayerNumber = 0;
		else
			currentPlayerNumber++;
		currentPlayer = connectedPlayers.get(currentPlayerNumber);
	}

	public void nextPlayerTurn() {
		gameController.sendMessageToPlayer(currentPlayer, "END TURN");
		if (gameEnded)
			while (nextRound());
		if (isDealerTurn && checkEndOfRound())
			if (!nextRound())
				return;
		findAvailableNextPlayer();
		int call = dealer.getTable().getRoundBet() - currentPlayer.getPlayerPot().getTotalCashUsedInRound();
		BettingValues values = GameSettings.getInstance().getRules().calculateBet(this);
		int raise = values.getRaise();
		int maxRaise = values.getMaxRaise();
		String buttons = "";
		if (currentPlayer.getPlayerPot().getMoney() - call < 0)
			buttons += "AllIn Fold";
		else {
			if (raise < maxRaise) {
				if (dealer.getTable().getIsOpen())
					buttons += "Raise ";
				else
					buttons += "Bet ";
			}
			if (call == 0)
				buttons += "Check ";
			if (call > 0)
				buttons += "Call Fold ";
		}

		gameController.sendMessageToPlayer(currentPlayer,
				"YOUR TURN " + call + " " + raise + " " + maxRaise + " " + buttons);
	}

	public void chooseDealer() {
		if (dealerPosition == -1)
			dealerPosition = random.nextInt(connectedPlayers.size());
		else {
			if (dealerPosition == connectedPlayers.size() - 1)
				dealerPosition = 0;
			else
				dealerPosition++;
		}
		currentPlayerNumber = dealerPosition;
		currentPlayer = connectedPlayers.get(currentPlayerNumber);
		gameController.sendMessageToAllPlayers("DEALER " + connectedPlayers.get(dealerPosition).getSeat());
	}

	public boolean nextRound() {
		for (Player player : connectedPlayers) {
			player.getPlayerPot().newRound();
		}
		gameController.sendMessageToAllPlayers("NEXT ROUND");
		dealer.getTable().newRound();
		gameState = gameState.nextState();
		int numOfCards = gameState.getNumberOfCards();
		if (numOfCards == 0) {
			dealer.showdown();
			return false;
		}
		int pos = gameState.getCardsStartPos();
		dealer.showMiddleCards(numOfCards, pos);
		return true;
	}

	public boolean checkEndOfRound() {
		int i = 0;
		for (i = 0; i < connectedPlayers.size(); ++i) {
			System.out.println("DEALER: "  + dealer.getTable().getRoundBet());
			if (connectedPlayers.get(i).getPlayerPot().isAllIn() || connectedPlayers.get(i).getPlayerPot().isFold())
				continue;
		//	if (cash == -1) {
		//		cash = connectedPlayers.get(i).getPlayerPot().getTotalCashUsedInRound();
		//		continue;
		//	}
		//	if (cash != connectedPlayers.get(i).getPlayerPot().getTotalCashUsedInRound())
		//		return false;
			if (dealer.getTable().getRoundBet() - connectedPlayers.get(i).getPlayerPot().getTotalCashUsedInRound() != 0)
				return false;
		}
		return true;
	}
	
	public void restartGame()
	{
		gameController.sendMessageToAllPlayers("END ROUND");
		gameController.sendMessageToAllPlayers("New game starts in 10 seconds");
		final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
		executorService.schedule(new Runnable() {
			@Override
			public void run() {
				System.out.println("RUNNING NEWW GAME");
				newGame();
			}
		}, 10, TimeUnit.SECONDS);
	}

	public void addPlayer(Player player) {
		connectedPlayers.add(player);
		numOfConnectedPlayers++;
		gameController.sendMessageToAllPlayers("MESSAGE " + player.getName() + " has joined the game!");
	}

	public void removePlayer(Player player) {
		connectedPlayers.remove(player);
		numOfConnectedPlayers--;
		gameController.sendMessageToAllPlayers("MESSAGE " + player.getName() + " has left the game!");
	}
	
	public void playerFoldOrAllIn()
	{
		playersFoldOrAllIn++;
		if (connectedPlayers.size() == playersFoldOrAllIn + 1)
			gameEnded = true;
	}

	public int getRaisedTimes() {
		return raisedTimes;
	}

	public void increaseRaiseTimes() {
		raisedTimes++;
	}

	public int getNumOfConnectedPlayers() {
		return numOfConnectedPlayers;
	}

	public int getDealerPos() {
		return dealerPosition;
	}

	public int getCurrentPlayerNum() {
		return currentPlayerNumber;
	}

	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	public HashMap<Integer, String> getTakenSeats() {
		HashMap<Integer, String> seats = new HashMap<Integer, String>();
		for (Player player : connectedPlayers)
			if (player.getSeat() != -1)
				seats.put(player.getSeat(), player.getName());
		return seats;
	}

	public List<Player> getPlayers() {
		return connectedPlayers;
	}

	public GameController getGameController() {
		return gameController;
	}

	public Dealer getDealer() {
		return dealer;
	}

	public Table getTable() {
		return dealer.getTable();
	}

}
