package poker;

public abstract class Player implements Comparable<Player>{

	protected int seat;
	protected Hand hand;
	protected String name;
	protected PlayerPot pot;
	protected boolean isHost;

	protected Player(String name)
	{
		this.name = name;
		hand = new Hand();
		pot = new PlayerPot();
		isHost = false;
		this.seat = -1;
	}
	
	public String getName()
	{
		return name;
	}
	
	public Hand getHand()
	{
		return hand;
	}
	
	public int getSeat()
	{
		return seat;
	}
	
	public boolean isHost()
	{
		return isHost;
	}
	
	public PlayerPot getPlayerPot()
	{
		return pot;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public void takeASeat(int seat)
	{
		this.seat = seat;
	}
	
	public void setIsHost(boolean isHost)
	{
		this.isHost = isHost;
	}
	
	public int compareTo(Player p)
	{
		return this.seat > p.getSeat() ? 1 : this.seat < p.getSeat() ? -1 : 0;
	}
}
