package poker;

public class PlayerPot {

	private int money;
	private int currentBet;			//last bet or raise amount
	private int totalCashInRound;	//sum of bet, raise and call in this round
	private boolean isAllIn;
	private boolean isFold;
	
	public PlayerPot()
	{
		money = 0;
		newGame();
	}
	
	public void newRound()
	{
		currentBet = 0;
		totalCashInRound = 0;
	}
	
	public void newGame()
	{
		currentBet = 0;
		totalCashInRound = 0;
		isAllIn = false;
		isFold = false;
	}
	
	public void bet(int money)
	{
		this.money -= money;
		this.totalCashInRound += money;
	}
	
	public void setCurrentBet(int bet)
	{
		currentBet = bet;
	}
	
	public void increaseCurrentBet(int bet)
	{
		currentBet += bet;
	}
	
	public void addMoney(int money)
	{
		this.money += money;
	}
	
	public void setMoney(int money)
	{
		this.money = money;
	}
	
	public void allIn()
	{
		bet(money);
		isAllIn = true;
	}
	
	public void fold()
	{
		isFold = true;
	}
	
	public boolean isAllIn()
	{
		return isAllIn;
	}
	
	public boolean isFold()
	{
		return isFold;
	}
	
	public int getMoney()
	{
		return money;
	}
	
	public int getCurrentBet()
	{
		return currentBet;
	}
	
	public int getTotalCashUsedInRound()
	{
		return totalCashInRound;
	}
	
}
