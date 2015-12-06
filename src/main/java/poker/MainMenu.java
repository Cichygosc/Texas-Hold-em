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
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.text.NumberFormatter;

public class MainMenu {

	private static String[] gameRules = { "No-Limit", "Pot-Limit", "Fixed-Limit" };
	
	private JPanel gamePanel;
	private JLabel titleLabel;
	private GameScreen gameScreen;
	private GridBagConstraints gridBagConstraints;
	
 	public MainMenu(GameScreen gameScreen, JPanel gamePanel)
	{
 		this.gameScreen = gameScreen;
 		this.gamePanel = gamePanel;
 		this.gridBagConstraints = new GridBagConstraints();
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
		
		gridBagConstraints.anchor = GridBagConstraints.CENTER;
		gridBagConstraints.fill = GridBagConstraints.NONE;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.ipady = 30;
		gamePanel.add(titleLabel, gridBagConstraints);
		
		JLabel port = generateLabel("Port number", 0, 1);
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.ipady = 10;
		gamePanel.add(port, gridBagConstraints);
		
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
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.anchor = GridBagConstraints.EAST;
		gridBagConstraints.gridx = 1;
		gamePanel.add(portNumber, gridBagConstraints);
		
		JLabel name = generateLabel("Name", 0, 2);
		gamePanel.add(name, gridBagConstraints);
		
		final JTextField nameText = new JTextField(15);
		nameText.setText("Client");
		nameText.setHorizontalAlignment(JTextField.RIGHT);
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.anchor = GridBagConstraints.EAST;
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gamePanel.add(nameText, gridBagConstraints);
		
		JButton back = new JButton("Back");
		back.addMouseListener(new MouseAdapter()
				{
					public void mousePressed(MouseEvent e)
					{
						mainMenuView();
					}
				});
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.fill = GridBagConstraints.NONE;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gamePanel.add(back, gridBagConstraints);
		
		JButton joinGame = new JButton("Join game");
		joinGame.addMouseListener(new MouseAdapter()
				{
					public void mousePressed(MouseEvent e)
					{
						gameScreen.joinServer((int)portNumber.getValue(), nameText.getText());
					}
				});
		gridBagConstraints.gridx = 1;
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.anchor = GridBagConstraints.EAST;
		gamePanel.add(joinGame, gridBagConstraints);
		
		gamePanel.revalidate();
		gamePanel.repaint();
	}
	
	private void hostServerView()
	{
		gamePanel.removeAll();
		
		gamePanel.setLayout(new GridBagLayout());
		
		JLabel players = generateLabel("Number of players", 0, 1);
		gridBagConstraints.ipady = 10;
		gamePanel.add(players, gridBagConstraints);
		gamePanel.add(Box.createHorizontalStrut(20));
		
		final JSpinner numberOfPlayers = new JSpinner();
		SpinnerNumberModel playerModel = generateSpinner(10, 2, 1, 2, 1, 1);
		numberOfPlayers.setModel(playerModel);
		gamePanel.add(numberOfPlayers, gridBagConstraints);

		
		JLabel cash = generateLabel("Starting amount of money", 0, 2);
		gamePanel.add(cash, gridBagConstraints);
		
		final JSpinner moneyAmount = new JSpinner();
		SpinnerNumberModel moneyModel = generateSpinner(500, 200, 20, 200, 1, 2);
		moneyAmount.setModel(moneyModel);;
		gamePanel.add(moneyAmount, gridBagConstraints);
		
		JLabel smallBlind = generateLabel("Small blind", 0, 3);
		gamePanel.add(smallBlind, gridBagConstraints);
		
		final JSpinner smallBlindAmount = new JSpinner();
		SpinnerNumberModel smallBlindModel = generateSpinner(30, 5, 5, 15, 1, 3);
		smallBlindAmount.setModel(smallBlindModel);
		gamePanel.add(smallBlindAmount, gridBagConstraints);
		
		JLabel bigBlind = generateLabel("Big blind", 0, 4);
		gamePanel.add(bigBlind, gridBagConstraints);
		
		final JSpinner bigBlindAmount = new JSpinner();
		SpinnerNumberModel bigBlindModel = generateSpinner(50, 15, 5, 30, 1, 4);
		bigBlindAmount.setModel(bigBlindModel);
		gamePanel.add(bigBlindAmount, gridBagConstraints);
		
		final JLabel raiseAmountLabel = generateLabel("Max raise amount", 0, 6);
		raiseAmountLabel.setVisible(false);
		gamePanel.add(raiseAmountLabel, gridBagConstraints);
		
		final JSpinner raiseAmount = new JSpinner();
		SpinnerNumberModel raiseAmountModel = generateSpinner(100, 10, 10, 50, 1, 6);
		raiseAmount.setModel(raiseAmountModel);
		raiseAmount.setVisible(false);
		gamePanel.add(raiseAmount, gridBagConstraints);
		
		final JLabel raiseTimeLabel = generateLabel("Max raise times", 0, 7);
		raiseTimeLabel.setVisible(false);
		gamePanel.add(raiseTimeLabel, gridBagConstraints);
		
		final JSpinner raiseTimeAmount = new JSpinner();
		SpinnerNumberModel raiseTimeAmountModel = generateSpinner(5, 1, 1, 2, 1, 7);
		raiseTimeAmount.setModel(raiseTimeAmountModel);
		raiseTimeAmount.setVisible(false);
		gamePanel.add(raiseTimeAmount, gridBagConstraints);
		
		JLabel rules = generateLabel("Rules", 0, 5);
		gamePanel.add(rules, gridBagConstraints);
		
		final JComboBox chooseRules = new JComboBox(gameRules);
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 5;
		gridBagConstraints.anchor = GridBagConstraints.EAST;
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		chooseRules.setSelectedIndex(0);
		chooseRules.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						if (chooseRules.getSelectedItem().equals("Fixed-Limit"))
						{
							raiseAmountLabel.setVisible(true);
							raiseAmount.setVisible(true);
							raiseTimeLabel.setVisible(true);
							raiseTimeAmount.setVisible(true);
						}
						else
						{
							raiseAmountLabel.setVisible(false);
							raiseAmount.setVisible(false);
							raiseTimeLabel.setVisible(false);
							raiseTimeAmount.setVisible(false);
						}
					}
				});
		gamePanel.add(chooseRules, gridBagConstraints);
		
		JLabel name = generateLabel("Name", 0, 8);
		gamePanel.add(name, gridBagConstraints);
		
		final JTextField nameText = new JTextField(15);
		nameText.setText("Host");
		nameText.setHorizontalAlignment(JTextField.RIGHT);
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 8;
		gridBagConstraints.anchor = GridBagConstraints.EAST;
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gamePanel.add(nameText, gridBagConstraints);
		
		JLabel port = generateLabel("Port", 0, 9);
		gamePanel.add(port, gridBagConstraints);
		
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
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.anchor = GridBagConstraints.EAST;
		gridBagConstraints.gridx = 1;
		gamePanel.add(portNumber, gridBagConstraints);
		
		JButton back = new JButton("Back");
		back.addMouseListener(new MouseAdapter()
				{
					public void mousePressed(MouseEvent e)
					{
						mainMenuView();
					}
				});
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.fill = GridBagConstraints.NONE;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 10;
		gamePanel.add(back, gridBagConstraints);
		
		JButton createGame = new JButton("Create game");
		createGame.addMouseListener(new MouseAdapter()
				{
					public void mousePressed(MouseEvent e)
					{
						gameScreen.hostServer((int)numberOfPlayers.getValue(), (int)moneyAmount.getValue(), (int)portNumber.getValue(), 
											  (int)smallBlindAmount.getValue(), (int)bigBlindAmount.getValue(), (String)chooseRules.getSelectedItem(),
											  nameText.getText(), (int)raiseAmount.getValue(), (int)raiseTimeAmount.getValue());
					}
				});
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.anchor = GridBagConstraints.EAST;
		gridBagConstraints.gridx = 1;
		gamePanel.add(createGame, gridBagConstraints);
		
		gridBagConstraints.anchor = GridBagConstraints.CENTER;
		gridBagConstraints.fill = GridBagConstraints.NONE;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridwidth = 3;
		gridBagConstraints.ipady = 30;
		gamePanel.add(titleLabel, gridBagConstraints);
		
		gamePanel.revalidate();
		gamePanel.repaint();
	}	
	
	private SpinnerNumberModel generateSpinner(int max, int min, int stepSize, int value, int x, int y)
	{
		SpinnerNumberModel temp = new SpinnerNumberModel();
		temp.setMaximum(max);
		temp.setMinimum(min);
		temp.setStepSize(stepSize);
		temp.setValue(value);
		gridBagConstraints.gridx = x;
		gridBagConstraints.gridy = y;
		gridBagConstraints.anchor = GridBagConstraints.EAST;
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		return temp;
	}
	
	private JLabel generateLabel(String text, int x, int y)
	{
		JLabel temp = new JLabel(text);
		temp.setForeground(Color.black);
		gridBagConstraints.gridx = x;
		gridBagConstraints.gridy = y;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.fill = GridBagConstraints.NONE;
		return temp;
	}
}

