package poker;

import java.io.IOException;

public abstract class PokerAppGenerator {
	
	public IPokerApp generateApp(int port, String name, GameModel gameModel) throws IOException
	{
		IPokerApp generatedApp = instantiate(port, name, gameModel);
		return generatedApp;
	}
	
	protected abstract IPokerApp instantiate(int port, String name, GameModel gameModel) throws IOException;
	
}
