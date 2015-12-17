package poker.AI;

public interface IAIStrategy {
	String makeMove(int call, int raise, int maxRaise, String buttons);
}
