package poker.server;

public class PreflopState implements GameStateBehavior {
	@Override
	public int getNumberOfCards() {
		return 0;
	}

	@Override
	public GameStateBehavior nextState() {
		return GameState.Flop.getStateBehavior();
	}

	@Override
	public GameState getState() {
		return GameState.Preflop;
	}

	@Override
	public int getCardsStartPos() {
		return 0;
	}	
}
