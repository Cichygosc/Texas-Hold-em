package poker.server;

public class PotLimitRules implements GameRules{

	@Override
	public BettingValues calculateBet(PokerRoom pokerRoom) {
		BettingValues values = new BettingValues();
		values.setRaise(pokerRoom.getDealer().getTable().getRoundBet() == 0 ? GameSettings.getInstance().getBigBlind()
				: pokerRoom.getDealer().getTable().getLastBet());
		values.setMaxRaise(pokerRoom.getDealer().getTable().getPot());
		return values;
	}

}
