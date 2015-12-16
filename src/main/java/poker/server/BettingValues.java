package poker.server;

public class BettingValues {

	private int raise;
	private int maxRaise;

	public BettingValues() {
		raise = 0;
		maxRaise = 0;
	}

	public BettingValues(int raise, int maxRaise) {
		this.raise = raise;
		this.maxRaise = maxRaise;
	}

	public void setRaise(int raise) {
		this.raise = raise;
	}

	public void setMaxRaise(int maxRaise) {
		this.maxRaise = maxRaise;
	}

	public int getRaise() {
		return raise;
	}

	public int getMaxRaise() {
		return maxRaise;
	}

}
