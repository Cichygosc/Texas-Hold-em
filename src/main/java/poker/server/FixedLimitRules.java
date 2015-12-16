package poker.server;

public class FixedLimitRules implements GameRules{

	@Override
	public BettingValues calculateBet(PokerRoom pokerRoom) {
		BettingValues values = new BettingValues();
		if (pokerRoom.getRaisedTimes() == GameSettings.getInstance().getMaxRaiseTimes())
			values.setRaise(99999);
		else
			values.setRaise(pokerRoom.getDealer().getTable().getRoundBet() == 0 ? GameSettings.getInstance().getBigBlind()
					: pokerRoom.getDealer().getTable().getLastBet());
		values.setMaxRaise(GameSettings.getInstance().getMaxRaiseAmount());
		pokerRoom.increaseRaiseTimes();
		return values;
	}
	
}
