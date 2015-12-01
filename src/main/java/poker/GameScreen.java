package poker;

import java.awt.Color;
import java.io.IOException;
import java.util.HashSet;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameScreen {

	private GameModel gameModel;
	
	private MainMenu mainMenu;
	private GameView gameView;
	
	private JFrame gameFrame;
	private JPanel gamePanel;
	private JLabel messageLabel;
	
	public GameScreen(GameModel gameModel)
	{
		this.gameModel = gameModel;
		
		gameFrame = new JFrame("Texas Hold'em");
		gamePanel = new JPanel();
		messageLabel = new JLabel("");
		
		messageLabel.setBackground(Color.lightGray);
		gameFrame.getContentPane().add(messageLabel, "South");
		
		gamePanel.setBackground(Color.darkGray);
		gameFrame.getContentPane().add(gamePanel, "Center");
		
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameFrame.setSize(800, 600);
		gameFrame.setVisible(true);
		gameFrame.setResizable(false);
		
		mainMenu = new MainMenu(this, gamePanel);
		
		mainMenu.show();
	}
	
	public void hostServer(int players, int money, int port, String name)
	{
		try
		{
			gameModel.hostServer(players, money, port);
			joinServer(port, name);
		}
		catch(IOException e)
		{
			messageLabel.setText("Couldnt host server " + e);
		}
	}
	
	public void joinServer(int port, String name)
	{
		try
		{
			gameModel.joinServer(port, name);
			gameView = new GameView(this, gamePanel);
			gameView.show();
		}
		catch(IOException e)
		{
			messageLabel.setText("Couldnt join to the server " + e);
		}
	}
	
	public void showMessage(String message)
	{
		messageLabel.setText(message);
	}
	
	public void takeSeat(int seat)
	{
		gameModel.sendMessage("SEAT " + seat);
	}
	
	public void addTakenSeat(int seat)
	{
		gameView.addTakenSeat(seat);
	}
	
	public void removeTakenSeat(int seat)
	{
		gameView.removeTakenSeat(seat);
	}
	
	public GameModel getGameModel()
	{
		return gameModel;
	}
	
	public HashSet<Integer> getTakenSeats()
	{
		return gameModel.getTakenSeats();
	}
	
	public Player getPlayer()
	{
		return gameModel.getPlayer();
	}
	
}
