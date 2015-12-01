package poker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class CardEvaluator {

	private static volatile CardEvaluator instance;
	
	private CardEvaluator()
	{}
	
	public BestHand checkCards(List<Card> cards)
	{
		BestHand hand = new BestHand();
		Collections.sort(cards);

		List<Card> bestCards = new ArrayList<Card>();
		
		return hand;
	}
	
	public boolean isStraightFlush(List<Card> cards)
	{
		if (isStraight(cards) && isFlush(cards))
			return true;
		return false;
	}
	
	public boolean isFourOfKind(List<Card> cards)
	{
		//HashMap<klucz, wartosc>
		//klucz - numer karty
		//wartosc - ilosc wystapien
		HashMap<Integer, Integer> valueMap = new HashMap<Integer, Integer>();
		for (int i = 0; i < cards.size(); ++i)
		{
			Integer count = valueMap.get(cards.get(i).getNumber());
			//jezeli numer karty nie istnieje w mapie dodaj go z wartoscia 1
			if (count == null)
				valueMap.put(cards.get(i).getNumber(), 1);
			//w przeciwnym wypadku zwieksz wartosc o 1
			else valueMap.put(cards.get(i).getNumber(), count + 1);
		}
		//jezeli wartosc ktoregos numeru wynosi 4 to znaczy ze wystepuje 4 razy
		//dla pary, dwoch par, trojki, full'a, flush'a mozna zrobic tak samo (dla flush'a kluczem bedzie kolor)
		if (valueMap.containsValue(4))
			return true;
		return false;
	}
	
	public boolean isStraight(List<Card> cards)
	{
		return true;
	}
	
	public boolean isFlush(List<Card> cards)
	{
		return false;
	}
	
	
	public static CardEvaluator getInstance()
	{
		if (instance == null)
		{
			synchronized(CardEvaluator.class)
			{
				if (instance == null)
				{
					instance = new CardEvaluator();
				}
			}
		}
		return instance;
	}
	
	
	
}
