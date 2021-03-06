package poker;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

@SuppressWarnings("serial")
public class GameRightPanel extends JPanel {

	private static int width = 200;
	private static int height = 150;
	
	private GameView gameView;
	private int callValue;
	private int raiseValue;
	private int maxRaise;
	
	//bet available only if nobody bet
	private JButton betViewButton;
	//call + bet is raise
	private JButton raiseViewButton;
	//if no player bet you can check
	private JButton checkButton;
	//if sb bet you can call
	private JButton callButton;
	//you can fold all time
	private JButton foldButton;
	//all in available all time
	private JButton allInButton;
	
	//-----------raise and bet panel--------------//
	private JLabel titleLabel;
	private JLabel cashLabel;
	final private JSpinner raiseAmount;
	private SpinnerNumberModel raiseModel;
	private JButton raiseButton;
	private JButton betButton;
	private JButton cancelButton;

	public GameRightPanel(GameView gameView) {
		callValue = 0;
		raiseValue = 0;
		maxRaise = 0;
		
		this.setSize(getPreferredSize());
		this.setOpaque(false);
		this.gameView = gameView;
		betViewButton = new JButton("Bet");
		raiseViewButton = new JButton("Raise...");
		checkButton = new JButton("Check");
		callButton = new JButton("Call");
		foldButton = new JButton("Fold");
		allInButton = new JButton("All In");
		
		titleLabel = new JLabel("Raise");
		cashLabel = new JLabel("$1000");
		raiseModel = new SpinnerNumberModel();
		raiseAmount = new JSpinner(raiseModel);
		raiseButton = new JButton("Raise");
		betButton = new JButton("Bet");
		cancelButton = new JButton("Cancel");
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		init();
	}
	
	private void init()
	{
		betViewButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		betViewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				betView("Bet");
			}
		});
		
		raiseViewButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		raiseViewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				betView("Raise");
			}
		});
		
		checkButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		checkButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				gameView.check();
			}
		});
		
		callButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		callButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				gameView.call(callValue);
			}
		});
		
		foldButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		foldButton.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e)
			{
				gameView.fold();
			}
		});
		
		allInButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		allInButton.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e)
			{
				gameView.allIn();
			}
		});
		
		titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		titleLabel.setVisible(true);
		
		cashLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		cashLabel.setVisible(true);
		
		raiseAmount.setAlignmentX(Component.CENTER_ALIGNMENT);
		raiseAmount.setMaximumSize(new Dimension(100, 20));
		raiseAmount.setVisible(true);
		raiseModel.setStepSize(5);
		raiseModel.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				cashLabel.setText("$" + raiseAmount.getValue());
				
			}
		});
		
		raiseButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		raiseButton.setVisible(true);
		raiseButton.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent event)
			{
				gameView.raise(callValue, (int)raiseAmount.getValue());
			}
		});
		
		betButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		betButton.setVisible(true);
		betButton.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent event)
			{
				gameView.bet((int)raiseAmount.getValue());
			}
		});
		
		cancelButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		cancelButton.setVisible(true);
		cancelButton.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent event)
			{
				draw();
			}
		});
		
		hideAllButtons();
	}

	private void draw()
	{
		removeAll();
		
		add(checkButton);
		add(Box.createVerticalStrut(5));
		add(raiseViewButton);
		add(betViewButton);
		add(Box.createVerticalStrut(5));
		add(callButton);
		add(Box.createVerticalStrut(5));
		add(allInButton);
		add(Box.createVerticalStrut(5));
		add(foldButton);
		
		revalidate();
		repaint();
	}
	
	private void betView(String title)
	{
		removeAll();
		
		titleLabel.setText(title);
		cashLabel.setText("$" + String.valueOf(raiseValue));
		
		add(titleLabel);
		add(Box.createVerticalStrut(5));
		add(cashLabel);
		add(Box.createVerticalStrut(5));
		add(raiseAmount);
		add(Box.createVerticalStrut(5));
		if (title == "Raise")
			add(raiseButton);
		else add(betButton);
		add(Box.createVerticalStrut(5));
		add(cancelButton);
		
		revalidate();
		repaint();
	}
	
	/*
	 * Sets how much cash you need to stay in game
	 * And how much can you raise bet
	 * maximumRaise = playerCash - cash to stay in game(ALL IN)
	 */
	public void setCallAndRaiseValue(int callValue, int raiseValue, int playerCash)
	{
		this.callValue = callValue;
		this.raiseValue = raiseValue;
		this.maxRaise = playerCash;
		this.callButton.setText("Call $" + String.valueOf(callValue));
		raiseModel.setMinimum(raiseValue);
		raiseModel.setMaximum(maxRaise - callValue);
		raiseModel.setValue(raiseValue);
	}
	
	public void hideAllButtons()
	{
		checkButton.setVisible(false);
		raiseViewButton.setVisible(false);
		betViewButton.setVisible(false);
		callButton.setVisible(false);
		foldButton.setVisible(false);
		allInButton.setVisible(false);
		draw();
	}
	
	public void showButtons(String allButtons)
	{
		String[] buttons = allButtons.split(" ");
		for (String str : buttons)
		{
			if (str.equals("Check"))
				checkButton.setVisible(true);
			else if (str.equals("Raise"))
				raiseViewButton.setVisible(true);
			else if (str.equals("Bet"))
				betViewButton.setVisible(true);
			else if (str.equals("Call"))
				callButton.setVisible(true);
			else if (str.equals("Fold"))
				foldButton.setVisible(true);
			else if (str.equals("AllIn"))
				allInButton.setVisible(true);
		}
		draw();
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(width, height);
	}
	
}
