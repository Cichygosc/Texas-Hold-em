package poker.server;

public class NoLimitRules implements GameRules {

	@Override
	public BettingValues calculateBet(PokerRoom pokerRoom) {
		BettingValues values = new BettingValues();
		values.setRaise(pokerRoom.getDealer().getTable().getRoundBet() == 0 ? GameSettings.getInstance().getBigBlind()
				: pokerRoom.getDealer().getTable().getLastBet());
		values.setMaxRaise(pokerRoom.getCurrentPlayer().getPlayerPot().getMoney());
		return values;
	}

}
