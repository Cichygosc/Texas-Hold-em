package poker;

import java.awt.Color;
import java.awt.Component;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashSet;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import poker.server.GameSettings;
import sun.tools.jar.resources.jar;

//ZNANE BLEDY
//brak nickow graczy ktorzy juz sa na serwerze(gamePanel.removeAll usuwa je)

public class GameView {

	private static int ButtonWidth = 102;
	private static int ButtonHeight = 50;

	private GameRightPanel rightPanel;
	private IPokerApp pokerApp;
	private String dealerName = "";
	private int dealerSeat = -1;
	private int numOfTables = 1;

	private GameScreen gameScreen;
	private JPanel gamePanel;
	private JButton takeSeatButton[];
	private JLabel playerCardLabel[];
	private JLabel cardOnTabelLabel[];
	private JLabel potLabel;
	private JLabel playerNameLabel[];
	private JLabel playerMoneyLabel[];
	private int buttonPos[][];

	public GameView(GameScreen gameScreen, JPanel gamePanel) {
		this.gamePanel = gamePanel;
		this.gameScreen = gameScreen;
		rightPanel = new GameRightPanel(this);
		takeSeatButton = new JButton[10];
		buttonPos = new int[10][2];
		playerCardLabel = new JLabel[20];
		cardOnTabelLabel = new JLabel[5];
		playerNameLabel = new JLabel[10];
		playerMoneyLabel = new JLabel[10];
		potLabel = new JLabel("");

		initView();
	}

	private void initView() {
		buttonPos[0][0] = 157;
		buttonPos[0][1] = 57;
		buttonPos[1][0] = 269;
		buttonPos[1][1] = 57;
		buttonPos[2][0] = 381;
		buttonPos[2][1] = 57;
		buttonPos[3][0] = 493;
		buttonPos[3][1] = 178;
		buttonPos[4][0] = 493;
		buttonPos[4][1] = 317;
		buttonPos[5][0] = 381;
		buttonPos[5][1] = 457;
		buttonPos[6][0] = 269;
		buttonPos[6][1] = 457;
		buttonPos[7][0] = 157;
		buttonPos[7][1] = 457;
		buttonPos[8][0] = 45;
		buttonPos[8][1] = 317;
		buttonPos[9][0] = 45;
		buttonPos[9][1] = 178;
		for (int i = 0; i < 10; ++i)
			takeSeatButton[i] = new JButton("Seat");
		rightPanel.setLocation(600, 400);
		potLabel.setBounds(310, 178, ButtonWidth, ButtonHeight);
	}

	public void show() {
		gamePanel.removeAll();
		
		chooseTableView();

		gamePanel.revalidate();
		gamePanel.repaint();
	}
	
	private void chooseTableView()
	{
		gamePanel.setLayout(new BoxLayout(gamePanel, BoxLayout.Y_AXIS));
		gamePanel.add(Box.createHorizontalStrut(100));
		if (numOfTables == 1)
			chooseTable(0);
		for (int i = 0; i < numOfTables; ++i)
		{
			final int j = i;
			JButton table = new JButton("Table " + (i+1));
			table.setAlignmentX(Component.CENTER_ALIGNMENT);
			table.setForeground(Color.black);
			table.addMouseListener(new MouseAdapter()
					{
						public void mousePressed(MouseEvent e)
						{
							chooseTable(j);
						}
					});
			gamePanel.add(table);
		}
		gamePanel.add(Box.createHorizontalStrut(100));
	}
	
	private void chooseTable(int table)
	{
		sendMessage("TABLE " + table);
		pokerApp.initPlayer();
		gamePanel.removeAll();
		takeASeatView();
		gamePanel.revalidate();
		gamePanel.repaint();
	}

	private void takeASeatView() {
		gamePanel.setLayout(null);
		HashSet<Integer> takenSeats = gameScreen.getTakenSeats();
		for (int i = 0; i < 10; ++i) {
			final int j = i;
			takeSeatButton[i].setBounds(new Rectangle(buttonPos[i][0], buttonPos[i][1], ButtonWidth, ButtonHeight));
			takeSeatButton[i].addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent e) {
					takeSeat(j);
				}
			});
			if (takenSeats.contains(i))
				takeSeatButton[i].setVisible(false);
			gamePanel.add(takeSeatButton[i]);
		}
	}

	private void waitingForPlayersView() {
		for (int i = 0; i < 10; ++i) {
			if (gameScreen.getPlayer().isHost()) {
				final int j = i;
				takeSeatButton[j].setText("Add Bot");
				takeSeatButton[j].addMouseListener(new MouseAdapter() {
					public void mousePressed(MouseEvent e) {
						addBot(j);
					}
				});
			} else
				takeSeatButton[i].setVisible(false);
		}
		gamePanel.add(potLabel);
	}

	public void pokerGameView() {
		for (int i = 0; i < 10; ++i)
			gamePanel.remove(takeSeatButton[i]);

		for (int i = 0; i < 5; ++i)
		{
			cardOnTabelLabel[i] = new JLabel();
			gamePanel.add(cardOnTabelLabel[i]);
		}
		
		gamePanel.add(potLabel);
		gamePanel.add(rightPanel);
		gamePanel.revalidate();
		gamePanel.repaint();
	}

	public void setMoney(int money, int seat) {
		playerMoneyLabel[seat].setText("$" + String.valueOf(money));
		refreshPlayerLabels(seat);
	}

	public void setPot(int money) {
		potLabel.setText("$" + String.valueOf(money));
	}

	public void setDealer(int seat) {
		if (!dealerName.equals("")) {
			playerNameLabel[dealerSeat].setText(dealerName);
		}
		dealerName = playerNameLabel[seat].getText();
		dealerSeat = seat;
		playerNameLabel[seat].setText("<Dealer>" + playerNameLabel[seat].getText());
		refreshPlayerLabels(seat);
	}

	public void addPlayerCard(String path, int seat, int pos) {
		ImageIcon icon;
		icon = new ImageIcon("images/" + path + ".png");

		playerCardLabel[seat + pos].setIcon(new ImageIcon(
				icon.getImage().getScaledInstance(ButtonWidth / 2, ButtonHeight * 2, java.awt.Image.SCALE_SMOOTH)));
		playerCardLabel[seat + pos].setBounds(buttonPos[seat][0] + pos * (ButtonWidth / 2 + 5), buttonPos[seat][1],
				ButtonWidth / 2, ButtonHeight * 2);

		gamePanel.revalidate();
		gamePanel.repaint();
	}
	
	public void addMiddleCards(String path, int pos)
	{
		ImageIcon icon;
		icon = new ImageIcon("images/" + path + ".png");
		cardOnTabelLabel[pos].setIcon(new ImageIcon(
				icon.getImage().getScaledInstance(ButtonWidth / 2, ButtonHeight * 2, java.awt.Image.SCALE_SMOOTH)));
		cardOnTabelLabel[pos].setBounds(buttonPos[0][0] + 30 + pos * (ButtonWidth / 2 + 5), 247, ButtonWidth / 2, ButtonHeight * 2);
		
		gamePanel.revalidate();
		gamePanel.repaint();
	}

	// TODO ADDING BOT
	private void addBot(int seat) {

	}

	private void takeSeat(int seat) {
		gameScreen.takeSeat(seat);
		waitingForPlayersView();
	}

	public void addTakenSeat(int seat, String playerName) {
		playerNameLabel[seat] = new JLabel(playerName);
		playerMoneyLabel[seat] = new JLabel();
		playerCardLabel[seat] = new JLabel();
		playerCardLabel[seat + 1] = new JLabel();
		gamePanel.add(playerCardLabel[seat]);
		gamePanel.add(playerCardLabel[seat + 1]);
		gamePanel.add(playerMoneyLabel[seat]);
		gamePanel.add(playerNameLabel[seat]);
		refreshPlayerLabels(seat);

		takeSeatButton[seat].setVisible(false);
	}

	private void refreshPlayerLabels(int seat) {
		playerNameLabel[seat].setBounds(
				buttonPos[seat][0] + ButtonWidth / 2
						- playerNameLabel[seat].getFontMetrics(playerNameLabel[seat].getFont())
								.stringWidth(playerNameLabel[seat].getText()) / 2,
				buttonPos[seat][1] - 40, ButtonWidth, 25);
		playerMoneyLabel[seat].setBounds(
				buttonPos[seat][0] + ButtonWidth / 2
						- playerMoneyLabel[seat].getFontMetrics(playerMoneyLabel[seat].getFont())
								.stringWidth(playerMoneyLabel[seat].getText()) / 2,
				buttonPos[seat][1] - 25, ButtonWidth, 25);
		gamePanel.revalidate();
		gamePanel.repaint();
	}
	
	public void restartGame()
	{
		for (int seat : gameScreen.getGameModel().getTakenSeats())
		{
			playerCardLabel[seat].setIcon(new ImageIcon());
			playerCardLabel[seat + 1].setIcon(new ImageIcon());
			refreshPlayerLabels(seat);
		}
		for (int i = 0; i < 5; ++i)
		{
			cardOnTabelLabel[i].setIcon(new ImageIcon());
		}
		potLabel.setText("0");
		gamePanel.revalidate();
		gamePanel.repaint();
	}
	
	public void hideButtons()
	{
		rightPanel.hideAllButtons();
	}
	
	public void showButtons(String buttons)
	{
		rightPanel.showButtons(buttons);
	}
	
	public void setCallAndRaiseValue(int callValue, int raiseValue, int maxRaise)
	{
		rightPanel.setCallAndRaiseValue(callValue, raiseValue, maxRaise);
	}

	public void removeTakenSeat(int seat) {
		takeSeatButton[seat].setVisible(true);
	}

	public void sendMessage(String message) {
		pokerApp.sendMessage(message);
	}
	
	public void setNumOfTables(int numOfTables)
	{
		this.numOfTables = numOfTables;
	}
	
	public void bet(int amount)
	{
		sendMessage("BET " + amount);
		pokerApp.getThisPlayer().getPlayerPot().bet(amount);
		pokerApp.getThisPlayer().getPlayerPot().setCurrentBet(amount);
		sendMessage("NEXT PLAYER");
	}
	
	public void raise(int callValue, int amount)
	{
		sendMessage("CALL " + callValue);
		sendMessage("BET " + amount);
		pokerApp.getThisPlayer().getPlayerPot().bet(callValue);
		pokerApp.getThisPlayer().getPlayerPot().bet(amount);
		pokerApp.getThisPlayer().getPlayerPot().increaseCurrentBet(amount);
		sendMessage("NEXT PLAYER");
	}
	
	public void check()
	{
		sendMessage("NEXT PLAYER");
	}
	
	public void call(int amount)
	{
		sendMessage("CALL " + amount);
		pokerApp.getThisPlayer().getPlayerPot().bet(amount);
		sendMessage("NEXT PLAYER");
	}
	
	public void fold()
	{
		sendMessage("FOLD");
		pokerApp.getThisPlayer().getPlayerPot().fold();
		sendMessage("NEXT PLAYER");
	}
	
	public void allIn()
	{
		sendMessage("ALLIN");
		pokerApp.getThisPlayer().getPlayerPot().allIn();
		sendMessage("NEXT PLAYER");
	}

	public void setPokerApp(IPokerApp app) {
		this.pokerApp = app;
	}
}
