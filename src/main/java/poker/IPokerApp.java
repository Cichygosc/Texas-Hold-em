package poker;

public interface IPokerApp {
	Player getThisPlayer();
	void sendMessage(String message);
	void initPlayer();
}
