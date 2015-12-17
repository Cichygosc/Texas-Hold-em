package poker;

import java.net.Socket;

public interface IPokerApp {
	Player getPlayer();
	void sendMessage(String message);
	void initPlayer();
	Socket getSocket();
}
