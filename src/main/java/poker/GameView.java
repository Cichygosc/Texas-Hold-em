package poker;

import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashSet;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameView {

	private static String CardBackPath = "cb.png";
	private static int ButtonWidth = 102;
	private static int ButtonHeight = 50;
	
	private GameScreen gameScreen;
	private JPanel gamePanel;
	private JButton takeSeatButton[];
	private int buttonPos[][];
	
	
	public GameView(GameScreen gameScreen, JPanel gamePanel)
	{
		this.gamePanel = gamePanel;
		this.gameScreen = gameScreen;
		takeSeatButton = new JButton[10];
		buttonPos = new int[10][2];
		
		initView();
	}
	
	private void initView()
	{
		buttonPos[0][0] = 157; buttonPos[0][1] = 57;
		buttonPos[1][0] = 269; buttonPos[1][1] = 57;
		buttonPos[2][0] = 381; buttonPos[2][1] = 57;
		buttonPos[3][0] = 493; buttonPos[3][1] = 173;
		buttonPos[4][0] = 493; buttonPos[4][1] = 307;
		buttonPos[5][0] = 381; buttonPos[5][1] = 442;
		buttonPos[6][0] = 269; buttonPos[6][1] = 442;
		buttonPos[7][0] = 157; buttonPos[7][1] = 442;
		buttonPos[8][0] = 45; buttonPos[8][1] = 307;
		buttonPos[9][0] = 45; buttonPos[9][1] = 173;
		for (int i = 0; i < 10; ++i)
			takeSeatButton[i] = new JButton("Seat");
	}
	
	public void show()
	{
		gamePanel.removeAll();
		
		takeASeatView();
		
		gamePanel.revalidate();
		gamePanel.repaint();
	}
	
	private void takeASeatView()
	{
		gamePanel.setLayout(null);
		HashSet<Integer> takenSeats = gameScreen.getTakenSeats();
		for (int i = 0; i < 10; ++i)
		{
			final int j = i;
			takeSeatButton[i].setBounds(new Rectangle(buttonPos[i][0], buttonPos[i][1], ButtonWidth, ButtonHeight));
			takeSeatButton[i].addMouseListener(new MouseAdapter()
					{
						public void mousePressed(MouseEvent e)
						{
							takeSeat(j);
						}
					});
			if (takenSeats.contains(i))
				takeSeatButton[i].setVisible(false);
			gamePanel.add(takeSeatButton[i]);
		}
	}
	
	private void waitingForPlayersView()
	{
		for (int i = 0; i < 10; ++i)
		{
			if (gameScreen.getPlayer().isHost())
			{
				final int j = i;
				takeSeatButton[j].setText("Add Bot");
				takeSeatButton[j].addMouseListener(new MouseAdapter()
						{
							public void mousePressed(MouseEvent e)
							{
								addBot(j);
							}
						});
			}
			else takeSeatButton[i].setVisible(false);
		}
	}
	
	public void pokerGameView()
	{
		for (int i = 0; i < 10; ++i)
			gamePanel.remove(takeSeatButton[i]);
		
		gamePanel.revalidate();
		gamePanel.repaint();
	}
	
	public void addPlayerCard(String path, int seat, int pos)
	{
		ImageIcon icon;
		if (gameScreen.getPlayer().getSeat() == seat)
			icon = new ImageIcon("images/" + path + ".png");
		else icon = new ImageIcon("images/cb.png");

		JLabel label = new JLabel(new ImageIcon(icon.getImage().getScaledInstance(ButtonWidth / 2, ButtonHeight * 2, java.awt.Image.SCALE_SMOOTH)));
		label.setBounds(buttonPos[seat][0] + pos * (ButtonWidth / 2 + 5), buttonPos[seat][1], ButtonWidth / 2, ButtonHeight * 2);
		gamePanel.add(label);
		
		gamePanel.revalidate();
		gamePanel.repaint();
	}
	
	//TODO ADDING BOT
	private void addBot(int seat)
	{
		
	}
	
	private void takeSeat(int seat)
	{
		gameScreen.takeSeat(seat);
		addTakenSeat(seat);
		waitingForPlayersView();
	}
	
	public void addTakenSeat(int seat)
	{
		takeSeatButton[seat].setVisible(false);
	}
	
	public void removeTakenSeat(int seat)
	{
		takeSeatButton[seat].setVisible(true);
	}
}
