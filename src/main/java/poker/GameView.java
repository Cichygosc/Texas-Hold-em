package poker;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashSet;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

//ZNANE BLEDY
//brak nickow graczy ktorzy juz sa na serwerze(gamePanel.removeAll usuwa je)

public class GameView {

	private static int ButtonWidth = 102;
	private static int ButtonHeight = 50;

	private GameRightPanel rightPanel;
	private IPokerApp pokerApp;
	private String dealerName = "";
	private int dealerSeat = -1;

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
				takeSeatButton[j].removeMouseListener(takeSeatButton[j].getMouseListeners()[0]);
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

		playerCardLabel[seat + pos] = new JLabel(new ImageIcon(
				icon.getImage().getScaledInstance(ButtonWidth / 2, ButtonHeight * 2, java.awt.Image.SCALE_SMOOTH)));
		playerCardLabel[seat + pos].setBounds(buttonPos[seat][0] + pos * (ButtonWidth / 2 + 5), buttonPos[seat][1],
				ButtonWidth / 2, ButtonHeight * 2);
		gamePanel.add(playerCardLabel[seat + pos]);

		gamePanel.revalidate();
		gamePanel.repaint();
	}

	// TODO ADDING BOT
	private void addBot(int seat) {

	}

	private void takeSeat(int seat) {
		gameScreen.takeSeat(seat);
		addTakenSeat(seat, gameScreen.getPlayer().getName());
		rightPanel.setPlayerSeat(seat);
		waitingForPlayersView();
	}

	public void addTakenSeat(int seat, String playerName) {
		playerNameLabel[seat] = new JLabel(playerName);
		playerMoneyLabel[seat] = new JLabel();
		refreshPlayerLabels(seat);
		takeSeatButton[seat].setVisible(false);
	}

	private void refreshPlayerLabels(int seat) {
		gamePanel.remove(playerMoneyLabel[seat]);
		gamePanel.remove(playerNameLabel[seat]);
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
		gamePanel.add(playerMoneyLabel[seat]);
		gamePanel.add(playerNameLabel[seat]);
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
	
	public void setCallValue(int callValue)
	{
		rightPanel.setCallValue(callValue);
	}

	public void removeTakenSeat(int seat) {
		takeSeatButton[seat].setVisible(true);
	}

	public void sendMessage(String message) {
		pokerApp.sendMessage(message);
	}

	public void setPokerApp(IPokerApp app) {
		this.pokerApp = app;
	}
}
