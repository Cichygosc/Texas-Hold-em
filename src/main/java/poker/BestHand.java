package poker;

import java.util.List;

//ranks:
//1 - nothing
//2 - pair
//3 - two pairs
//4 - three
//5 - straight
//6 - flush
//7 - full
//8 - four
//9 - straight flush
//10 - royal flush

public class BestHand {

	private List<Card> cards;
	private int rank;
	
	public BestHand()
	{
		
	}
	
	public int compareHighCards(BestHand hand)
	{
		return 0;
	}
	
	public void setCards(List<Card> cards)
	{
		this.cards.addAll(cards);
	}
	
	public void setRank(int rank)
	{
		this.rank = rank;
	}
	
	public List<Card> getCards()
	{
		return cards;
	}
	
	public int getRank()
	{
		return rank;
	}
	
	
}
