package poker.server;

public class GameSettings {

	private static volatile GameSettings instance;

	private static int numOfPlayers;
	private static int numOfTables;
	private static int startingMoney;
	private static int smallBlind;
	private static int bigBlind;
	private static String rules;
	private static int maxRaiseAmount;
	private static int maxRaiseTimes;

	private GameSettings() {
		System.out.println("Creating instnce of GameSettings");
		numOfPlayers = 2;
		numOfTables = 1;
		startingMoney = 200;
		smallBlind = 2;
		bigBlind = 4;
		rules = "NoLimit";
		maxRaiseAmount = 0;
		maxRaiseTimes = 0;
	}

	public static GameSettings getInstance() {
		if (instance == null) {
			synchronized (GameSettings.class) {
				if (instance == null)
					instance = new GameSettings();
			}
		}
		return instance;
	}

	public void setCustomSettings(int numOfPlayers, int numOfTables,  int startingMoney, int smallBlind, int bigBlind, String rules,
			int maxRaiseAmount, int maxRaiseTimes) {
		GameSettings.numOfPlayers = numOfPlayers;
		GameSettings.numOfTables = numOfTables;
		GameSettings.startingMoney = startingMoney;
		GameSettings.smallBlind = smallBlind;
		GameSettings.bigBlind = bigBlind;
		GameSettings.rules = rules;
		GameSettings.maxRaiseAmount = maxRaiseAmount;
		GameSettings.maxRaiseTimes = maxRaiseTimes;
	}

	public int getNumOfPlayers() {
		return numOfPlayers;
	}

	public int getNumOfTables() {
		return numOfTables;
	}

	public int getStartingMoney() {
		return startingMoney;
	}

	public int getSmallBlind() {
		return smallBlind;
	}

	public int getBigBlind() {
		return bigBlind;
	}

	public String getRules() {
		return rules;
	}

	public int getMaxRaiseAmount() {
		return maxRaiseAmount;
	}

	public int getMaxRaiseTimes() {
		return maxRaiseTimes;
	}

}
