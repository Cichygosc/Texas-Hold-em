package poker;

public interface IPokerApp {
	Player getThisPlayer();
	public void sendMessage(String message);
	public void takeASeat(int seat);
}
