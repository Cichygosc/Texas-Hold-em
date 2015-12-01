package poker;

public class PlayerPot {

	private TablePot tablePot;
	
	public PlayerPot()
	{
		tablePot = new TablePot();
	}
	
	public TablePot getTablePot()
	{
		return tablePot;
	}
	
}
