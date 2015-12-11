package poker.server;

public enum GameState {
	Preflop {
		public GameStateBehavior getStateBehavior() {
			return new PreflopState();
		}
	},

	Flop {
		public GameStateBehavior getStateBehavior() {
			return new FlopState();
		}
	},

	Turn {
		public GameStateBehavior getStateBehavior() {
			return new TurnState();
		}
	},

	River {
		public GameStateBehavior getStateBehavior() {
			return new RiverState();
		}
	},

	Showdown {
		public GameStateBehavior getStateBehavior() {
			return new ShowdownState();
		}
	};

	public GameStateBehavior getStateBehavior() {
		return null;
	}
}
