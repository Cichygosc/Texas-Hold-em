package poker;

public class HumanPlayer extends Player {

	public HumanPlayer(String name, boolean isHost) {
		super(name);
		super.setIsHost(isHost);
	}
}
