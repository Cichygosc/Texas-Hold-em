package poker.AI;

import poker.Player;

public class AIPlayer extends Player implements IAIStrategy{

	IAIStrategy strategy;
	
	public AIPlayer(String name) {
		super(name);
		strategy = null;
	}
	
	public String makeMove(int call, int raise, int maxRaise, String buttons)
	{
		calculateOdds();
		return strategy.makeMove(call, raise, maxRaise, buttons);
	}
	
	private void calculateOdds()
	{
		strategy = new PassiveStrategy();
	}

}
