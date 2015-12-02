package poker;

public class Dealer {

	private Table table;
	private CardDeck deck;
	
	public Dealer()
	{
		table = new Table();
		deck = new CardDeck();
		deck.shuffleCards();
	}
	
	public Card drawCard()
	{
		return deck.getCard();
	}
	
	public Table getTable()
	{
		return table;
	}
	
	public String getTakenSeats()
	{
		return table.getTakenSeats();
	}
	
}
