package poker;

public class PlayerPot {

	private int money;
	private int currentBet;
	private boolean isAllIn;
	private boolean isFold;
	
	public PlayerPot()
	{
		money = 0;
		newRound();
	}
	
	public void newRound()
	{
		currentBet = 0;
		isAllIn = false;
		isFold = false;
	}
	
	public void bet(int money)
	{
		this.money -= money;
	}
	
	public void setCurrentBet(int bet)
	{
		currentBet = bet;
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
	
}
