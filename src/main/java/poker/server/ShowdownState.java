package poker.server;

public class ShowdownState implements GameStateBehavior {
	@Override
	public int getNumberOfCards() {
		return 0;
	}

	@Override
	public GameStateBehavior nextState() {
		return GameState.Preflop.getStateBehavior();
	}

	@Override
	public GameState getState() {
		return GameState.Showdown;
	}

	@Override
	public int getCardsStartPos() {
		return 0;
	}
}
