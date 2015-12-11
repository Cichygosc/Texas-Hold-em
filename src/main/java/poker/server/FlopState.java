package poker.server;

public class FlopState implements GameStateBehavior {
	@Override
	public int getNumberOfCards() {
		return 3;
	}

	@Override
	public GameStateBehavior nextState() {
		return GameState.Turn.getStateBehavior();
	}

	@Override
	public GameState getState() {
		return GameState.Flop;
	}

	@Override
	public int getCardsStartPos() {
		return 0;
	}

}
