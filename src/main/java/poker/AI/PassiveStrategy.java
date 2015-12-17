package poker.AI;

public class PassiveStrategy implements IAIStrategy{

	@Override
	public String makeMove(int call, int raise, int maxRaise, String buttons) {
		String[] allOptions = buttons.split(" ");
		
		for (String s: allOptions)
			if (s.equals("Call"))
				return "CALL " + call;
		
		for (String s: allOptions)
			if (s.equals("Check"))
				return "";
		
		return "FOLD";
	}

	
	
}
