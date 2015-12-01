package poker.AI;

import java.io.IOException;

import poker.GameModel;
import poker.IPokerApp;
import poker.PokerAppGenerator;

public class PokerAIAppGenerator extends PokerAppGenerator{

	@Override
	protected IPokerApp instantiate(int port, String name, GameModel gameModel) throws IOException{
		return new PokerAIApp(port, name, gameModel);
	}

}
