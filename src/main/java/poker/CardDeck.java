package poker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class CardDeck {

	private static final int NumberOfCards = 52;
	private List<Card> cards;
	private int position;
	private Random r;
	
	public CardDeck()
	{
		cards = new ArrayList<Card>();
		for (int i = 0; i < 4; ++i)
			for (int j = 0; j < 13; ++j)
				cards.add(new Card(j, i));
		
		r = new Random(System.currentTimeMillis());
		position = 0;
	}
	
	public Card getCard()
	{
		//TODO throw exception
		if (position >= NumberOfCards)
			return null;

		++position;
		return cards.get(position - 1);
	}
	
	public void shuffleCards()
	{
		for (int i = 0; i < NumberOfCards; ++i)
		{
			int j = r.nextInt(NumberOfCards - i);
			Collections.swap(cards, i, j);
		}
		
		position = 0;
	}
	
}
