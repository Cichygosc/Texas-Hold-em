package poker.server;

public class RiverState implements GameStateBehavior {
	@Override
	public int getNumberOfCards() {
		return 1;
	}

	@Override
	public GameStateBehavior nextState() {
		return GameState.Showdown.getStateBehavior();
	}

	@Override
	public GameState getState() {
		return GameState.River;
	}

	@Override
	public int getCardsStartPos() {
		return 4;
	}
}
