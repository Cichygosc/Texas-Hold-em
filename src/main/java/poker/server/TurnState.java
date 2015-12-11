package poker.server;

public class TurnState implements GameStateBehavior{
	@Override
	public int getNumberOfCards() {
		return 1;
	}

	@Override
	public GameStateBehavior nextState() {
		return GameState.River.getStateBehavior();
	}

	@Override
	public GameState getState() {
		return GameState.Turn;
	}

	@Override
	public int getCardsStartPos() {
		return 3;
	}
}
