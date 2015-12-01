package poker;

import java.io.IOException;

public class PokerClientAppGenerator extends PokerAppGenerator {

	@Override
	protected IPokerApp instantiate(int port, String name, GameModel gameModel) throws IOException {
		return new PokerClientApp(port, name, gameModel);
	}

}
