package poker.server;

public interface GameRules {
	BettingValues calculateBet(PokerRoom pokerRoom);
}
