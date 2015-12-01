package poker;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.NumberFormat;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.text.NumberFormatter;

public class MainMenu {

	private JPanel gamePanel;
	private JLabel titleLabel;
	private GameScreen gameScreen;
	
 	public MainMenu(GameScreen gameScreen, JPanel gamePanel)
	{
 		this.gameScreen = gameScreen;
 		this.gamePanel = gamePanel;
 		titleLabel = new JLabel("Texas Hold'em");
	}
 	
 	public void show()
 	{
 		mainMenuView();
 	}
 	
	private void mainMenuView()
	{
		gamePanel.removeAll();
		
		gamePanel.setLayout(new BoxLayout(gamePanel, BoxLayout.Y_AXIS));
		titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		Font font = new Font("SansSerif", Font.BOLD, 20);
		titleLabel.setFont(font);
		titleLabel.setForeground(Color.black);
		final JButton hostGame = new JButton("Host game");
		hostGame.setAlignmentX(Component.CENTER_ALIGNMENT);
		hostGame.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						hostServerView();
					}
				});
		JButton joinGame = new JButton("Join game");
		joinGame.setAlignmentX(Component.CENTER_ALIGNMENT);
		joinGame.addMouseListener(new MouseAdapter()
				{
					public void mousePressed(MouseEvent e)
					{
						joinServerView();
					}
				});
		JButton exitGame = new JButton("Exit");
		exitGame.setAlignmentX(Component.CENTER_ALIGNMENT);
		exitGame.addMouseListener(new MouseAdapter()
				{
					public void mousePressed(MouseEvent e)
					{
						System.exit(0);
					}
				});
		gamePanel.add(Box.createHorizontalStrut(100));
		gamePanel.add(titleLabel);
		gamePanel.add(Box.createVerticalStrut(30));
		gamePanel.add(hostGame);
		gamePanel.add(Box.createVerticalStrut(5));
		gamePanel.add(joinGame);
		gamePanel.add(Box.createVerticalStrut(5));
		gamePanel.add(exitGame);
		gamePanel.add(Box.createHorizontalStrut(100));
		
		gamePanel.revalidate();
		gamePanel.repaint();
	}
	
	private void joinServerView()
	{
		gamePanel.removeAll();
		
		gamePanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.NONE;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		c.ipady = 30;
		gamePanel.add(titleLabel, c);
		
		JLabel port = new JLabel("Port number");
		port.setForeground(Color.black);
		c.anchor = GridBagConstraints.WEST;
		c.gridy = 1;
		c.gridwidth = 1;
		c.ipady = 5;
		gamePanel.add(port, c);
		
		NumberFormat format = NumberFormat.getInstance();
		format.setMinimumIntegerDigits(1);
		NumberFormatter formatter = new NumberFormatter(format);
		formatter.setValueClass(Integer.class);
		formatter.setMinimum(1);
		formatter.setMaximum(65536);
		formatter.setCommitsOnValidEdit(true);
		formatter.setAllowsInvalid(false);
		final JFormattedTextField portNumber = new JFormattedTextField(formatter);
		portNumber.setValue(12343);
		portNumber.setHorizontalAlignment(JTextField.RIGHT);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.EAST;
		c.gridx = 1;
		gamePanel.add(portNumber, c);
		
		JButton back = new JButton("Back");
		back.addMouseListener(new MouseAdapter()
				{
					public void mousePressed(MouseEvent e)
					{
						mainMenuView();
					}
				});
		c.gridx = 0;
		c.gridy = 2;
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.WEST;
		gamePanel.add(back, c);
		
		JButton joinGame = new JButton("Join game");
		joinGame.addMouseListener(new MouseAdapter()
				{
					public void mousePressed(MouseEvent e)
					{
						gameScreen.joinServer((int)portNumber.getValue(), "CLIENT");
					}
				});
		c.gridx = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.EAST;
		gamePanel.add(joinGame, c);
		
		gamePanel.revalidate();
		gamePanel.repaint();
	}
	
	private void hostServerView()
	{
		gamePanel.removeAll();
		
		gamePanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		JLabel players = new JLabel("Number of players");
		players.setForeground(Color.black);
		c.anchor = GridBagConstraints.WEST;
		c.gridx = 0;
		c.gridy = 1;
		c.ipady = 10;
		gamePanel.add(players, c);
		gamePanel.add(Box.createHorizontalStrut(20));
		
		final JSpinner numberOfPlayers = new JSpinner();
		SpinnerNumberModel playerModel = new SpinnerNumberModel();
		playerModel.setMaximum(10);
		playerModel.setMinimum(2);
		playerModel.setValue(2);
		numberOfPlayers.setModel(playerModel);
		numberOfPlayers.setMinimumSize(new Dimension(100, 20));
		c.anchor = GridBagConstraints.EAST;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		gamePanel.add(numberOfPlayers, c);

		
		JLabel cash = new JLabel("Starting amount of money");
		cash.setForeground(Color.black);
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.NONE;
		c.gridx = 0;
		c.gridy = 2;
		gamePanel.add(cash, c);
		
		final JSpinner moneyAmount = new JSpinner();
		SpinnerNumberModel moneyModel = new SpinnerNumberModel();
		moneyModel.setMaximum(500);
		moneyModel.setMinimum(200);
		moneyModel.setStepSize(20);
		moneyModel.setValue(200);
		moneyAmount.setModel(moneyModel);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.EAST;
		c.gridx = 1;
		gamePanel.add(moneyAmount, c);
		
		JLabel port = new JLabel("Port");
		port.setForeground(Color.black);
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.NONE;
		c.gridx = 0;
		c.gridy = 3;
		gamePanel.add(port, c);
		
		NumberFormat format = NumberFormat.getInstance();
		format.setMinimumIntegerDigits(1);
		NumberFormatter formatter = new NumberFormatter(format);
		formatter.setValueClass(Integer.class);
		formatter.setMinimum(1);
		formatter.setMaximum(65536);
		formatter.setCommitsOnValidEdit(true);
		formatter.setAllowsInvalid(false);
		final JFormattedTextField portNumber = new JFormattedTextField(formatter);
		portNumber.setValue(12343);
		portNumber.setHorizontalAlignment(JTextField.RIGHT);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.EAST;
		c.gridx = 1;
		gamePanel.add(portNumber, c);
		
		JButton back = new JButton("Back");
		back.addMouseListener(new MouseAdapter()
				{
					public void mousePressed(MouseEvent e)
					{
						mainMenuView();
					}
				});
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.NONE;
		c.gridx = 0;
		c.gridy = 4;
		gamePanel.add(back, c);
		
		JButton createGame = new JButton("Create game");
		createGame.addMouseListener(new MouseAdapter()
				{
					public void mousePressed(MouseEvent e)
					{
						gameScreen.hostServer((int)numberOfPlayers.getValue(), (int)moneyAmount.getValue(), (int)portNumber.getValue(), "HOST");
					}
				});
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.EAST;
		c.gridx = 1;
		gamePanel.add(createGame, c);
		
		c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.NONE;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 3;
		c.ipady = 30;
		gamePanel.add(titleLabel, c);
		
		gamePanel.revalidate();
		gamePanel.repaint();
	}	
}
