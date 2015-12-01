package poker;

import java.util.ArrayList;
import java.util.List;

public class Hand {

	private List<Card> cardsInHand;
	private List<Card> cardsOnBoard;
	
	public Hand()
	{
		newGame();
	}
	
	public void newGame()
	{
		cardsInHand = new ArrayList<Card>();
	}
	
	public int numberOfCards()
	{
		return cardsInHand.size();
	}
	
	public void addCardToHand(Card c)
	{
		cardsInHand.add(c);
	}
	
	public void addCardOnBoard(Card c)
	{
		cardsOnBoard.add(c);
	}
	
	public BestHand getBestHand()
	{
		List<Card> allCards = new ArrayList<Card>(cardsInHand);
		allCards.addAll(cardsOnBoard);
		return CardEvaluator.getInstance().checkCards(allCards);
	}
	
	public Card getCardFromHand(int index)
	{
		return (index < cardsInHand.size() ? cardsInHand.get(index) : null);
	}
	
}
