package poker.AI;

import java.io.IOException;

import poker.GameModel;
import poker.IPokerApp;
import poker.Player;

public class PokerAIApp implements IPokerApp{

	public PokerAIApp(int port, String name, GameModel model) throws IOException
	{
		connectSocket(port, name);
	}

	private void connectSocket(int port, String name) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Player getThisPlayer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void sendMessage(String message) {
		// TODO Auto-generated method stub
		
	}
	
}
