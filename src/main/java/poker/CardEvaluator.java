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
		
		HashMap<Integer, Integer> valueMap = new HashMap<Integer, Integer>();
		for (int i = 0; i < cards.size(); ++i)
		{
			Integer count = valueMap.get(cards.get(i).getNumber());
					if (count == null)
				valueMap.put(cards.get(i).getNumber(), 1);
			
			else valueMap.put(cards.get(i).getNumber(), count + 1);
		}
		
		
		if (valueMap.containsValue(4))
			return true;
		return false;
	}
	
	public boolean isFullHouse(List<Card> cards)
	{
		
		HashMap<Integer, Integer> valueMap = new HashMap<Integer, Integer>();
		for (int i = 0; i < cards.size(); ++i)
		{
			Integer count = valueMap.get(cards.get(i).getNumber());
			
			if (count == null)
				valueMap.put(cards.get(i).getNumber(), 1);
			
			else valueMap.put(cards.get(i).getNumber(), count + 1);
		}
		
		if (valueMap.containsValue(3) && valueMap.containsValue(2))
			
			return true;
		return false;
	}
	
	
	
	
	public boolean isStraight(List<Card>  cards)
	{
		
		for (int i = 1; i < cards.size(); i++) {
	        if (cards.get(i).getNumber() - cards.get(i-1).getNumber() == 1) {
	            return true;
	        }
	    }

	    return false;
	}
	
	public boolean isFlush(List<Card> cards)
	{
		HashMap<Integer, Integer> valueMap = new HashMap<Integer, Integer>();
		for (int i = 0; i < cards.size(); ++i)
		{
			Integer count = valueMap.get(cards.get(i).getSuit());
			
			if (count == null)
				valueMap.put(cards.get(i).getSuit(), 1);
			
			else valueMap.put(cards.get(i).getSuit(), count + 1);
		}
		
		if (valueMap.containsValue(5))
			
			return true;
	
		return false;
	}
	
	public boolean isThreeOfAKind(List<Card> cards)
	{
		
		HashMap<Integer, Integer> valueMap = new HashMap<Integer, Integer>();
		for (int i = 0; i < cards.size(); ++i)
		{
			Integer count = valueMap.get(cards.get(i).getNumber());
			
			if (count == null)
				valueMap.put(cards.get(i).getNumber(), 1);
			
			else valueMap.put(cards.get(i).getNumber(), count + 1);
		}
		
		if (valueMap.containsValue(3))
			
			return true;
		return false;
	}
	
	
	public boolean isTwoPair(List<Card> cards)
	{   
		
		Card tmpCard = null;
        int pairCount = 0;
        for (Card card : cards) {
            
            if (tmpCard != null && (tmpCard.getNumber() == card.getNumber())) {
                pairCount = pairCount + 1;
            }
            tmpCard = card;

        }
       
        if (pairCount == 2)
            return true;

        return false; 
	}
	
	public boolean isOnePair(List<Card> cards)
	{
		
		HashMap<Integer, Integer> valueMap = new HashMap<Integer, Integer>();
		for (int i = 0; i < cards.size(); ++i)
		{
			Integer count = valueMap.get(cards.get(i).getNumber());
			
			if (count == null)
				valueMap.put(cards.get(i).getNumber(), 1);
			
			else valueMap.put(cards.get(i).getNumber(), count + 1);
		}
		
		if (valueMap.containsValue(2))
			
			return true;
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
