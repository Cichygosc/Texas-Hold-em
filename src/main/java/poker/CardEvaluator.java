package poker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;


public class CardEvaluator {

	private static volatile CardEvaluator instance;
	private HashMap<Integer, Integer> valueMap;
	private HashMap<Integer, Integer> suitMap;
	
	private CardEvaluator()
	{}
	
	public BestHand checkCards(List<Card> cards)
	{
		BestHand hand = new BestHand();
		createMaps(cards);

		int rank;
		List<Card> cardList = new ArrayList<Card>();
		
		if( isStraightFlush(cards)){
			cardList = findStraightFlush(cards);
			rank = 9;
		}
		else if( isFourOfKind(cards)){
			cardList = findFourOfKind(cards);
			rank = 8;
		}
		else if (isFullHouse(cards)){
			cardList = findFullHouse(cards);
			rank = 7;
		}
		else if (isFlush(cards)){
			cardList = findFlush(cards);
			rank = 6;
		}
		else if (isStraight(cards)){
			cardList = findStraight(cards);
			rank = 5;
		}
		else if (isThreeOfAKind(cards)){
			cardList = findThreeOfAKind(cards);
			rank = 4;
		}
		else if (isTwoPair(cards)){
			cardList = findTwoPair(cards);
			rank = 3;
		}
		else if (isOnePair(cards)){
			cardList = findOnePair(cards);
			rank = 2;
		}
		else {
		 cardList = findHighCard(cards);
		 rank = 1;
		}
		hand.setCards(cardList);
		hand.setRank(rank);
		
		
		return hand;
	}
	

	public void createMaps(List<Card> cards)
	{
		Collections.sort(cards);
		
		valueMap = new HashMap<Integer, Integer>();
		for (int i = 0; i < cards.size(); ++i)
		{
			Integer count = valueMap.get(cards.get(i).getNumber());
					if (count == null)
				valueMap.put(cards.get(i).getNumber(), 1);
			
			else valueMap.put(cards.get(i).getNumber(), count + 1);
		}
		
		suitMap = new HashMap<Integer, Integer>();
		for (int i = 0; i < cards.size(); ++i)
		{
			Integer count = suitMap.get(cards.get(i).getSuit());
			
			if (count == null)
				suitMap.put(cards.get(i).getSuit(), 1);
			
			else suitMap.put(cards.get(i).getSuit(), count + 1);
		}
		
	}
	
	public boolean isStraightFlush(List<Card> cards)
	{
		if (isStraight(cards) && isFlush(cards))
			return true;
		return false;
	}
	
	public boolean isFourOfKind(List<Card> cards)
	{
		if (valueMap.containsValue(4))
			return true;
		return false;
	}
	
	public boolean isFullHouse(List<Card> cards)
	{
		if (valueMap.containsValue(3) && valueMap.containsValue(2))
			return true;
		return false;
	}
	
	public boolean isStraight(List<Card>  cards)
	{
		for (int i = 0; i < 3; ++i)
		{
			if (cards.get(i).getNumber() + 1 == cards.get(i + 1).getNumber() && cards.get(i).getNumber() + 2 == cards.get(i + 2).getNumber()
					&& cards.get(i).getNumber() + 3 == cards.get(i + 3).getNumber() && cards.get(i).getNumber() + 4 == cards.get(i + 4).getNumber()
					&& cards.get(i).getNumber() + 4 == cards.get(i + 4).getNumber())
				return true;
		}
		return false;
	}
	
	public boolean isFlush(List<Card> cards)
	{
		if (suitMap.containsValue(5))
			return true;
		return false;
	}
	
	public boolean isThreeOfAKind(List<Card> cards)
	{
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
		if (valueMap.containsValue(2))
			return true;
		return false;
	}
	
	public List<Card> findStraightFlush(List<Card> cards){
		
		List<Card> bestCards = new ArrayList<Card>();
		
		int suit = -1;
		for (Entry<Integer, Integer> entry : suitMap.entrySet()) {
	            if (entry.getValue().intValue() >= 5) {
		            suit = entry.getKey();
		            break;
	            }
	        
		}
		for (int i = cards.size() - 1; i >= 0; i--)
			if (cards.get(i).getSuit() != suit)
				cards.remove(i);
		
	            if (cards.size() == 7 && cards.get(2).getNumber() + 1 == cards.get(3).getNumber() && cards.get(2).getNumber() + 2 == cards.get(4).getNumber() && cards.get(3).getNumber() + 1 == cards.get(4).getNumber()){
		            	bestCards.add(cards.get(2));
	        			bestCards.add(cards.get(3));
	        			bestCards.add(cards.get(4));
	        			bestCards.add(cards.get(5));
	        			bestCards.add(cards.get(6));
	        	}
	        	else 
	        	if ((cards.size() == 7 || cards.size() == 6) && cards.get(1).getNumber() + 1 == cards.get(2).getNumber() && cards.get(1).getNumber() + 2 == cards.get(3).getNumber() && cards.get(2).getNumber() + 1 == cards.get(3).getNumber()){
	        			bestCards.add(cards.get(1));
	        			bestCards.add(cards.get(2));
	        			bestCards.add(cards.get(3));
	        			bestCards.add(cards.get(4));
	        			bestCards.add(cards.get(5));
	        	}
	        	else 
	        	{
	        			bestCards.add(cards.get(0));
	        			bestCards.add(cards.get(1));
	        			bestCards.add(cards.get(2));
	        			bestCards.add(cards.get(3));
		            	bestCards.add(cards.get(4));
	        	}
           
		
		return bestCards;
	}
	
	public List<Card> findFourOfKind(List<Card> cards){
	
		
			Collections.sort(cards);
			boolean findFour = false;
			int cardNumber = cards.get(6).getNumber();
			List<Card> bestCards = new ArrayList<Card>();
			bestCards.add(cards.get(6));
			for (int i = cards.size() - 2; i >= 0; --i) {
				if (!findFour) {
					if (cardNumber == cards.get(i).getNumber()) {
						findFour = true;
						bestCards.add(cards.get(i));
						if (i != 5)
							bestCards.add(cards.get(i + 1));
					} else {
						cardNumber = cards.get(i).getNumber();
						if (bestCards.size() < 1) {
							bestCards.add(cards.get(i));
						}
					}
				} else {
					if (bestCards.size() < 5) {
						bestCards.add(cards.get(i));
					} else 
						break;
				}

			}
		
		return bestCards;
	}
	
	public List<Card> findFullHouse(List<Card> cards){
		Collections.sort(cards);
		int value1 = -1;
		int value2 = -1;
		List<Card> bestCards = new ArrayList<Card>();
		for (Entry<Integer, Integer> entry : valueMap.entrySet()) {
	            if (entry.getValue().intValue() == 3) 
		            value1 = entry.getKey();
	            else if (entry.getValue().intValue() == 2 && entry.getValue().intValue() > value2)
	            	value2 = entry.getKey();
		}
		
		
		for(int i = 0; i <= cards.size() - 1; ++i){
			if(cards.get(i).getNumber() == value2 || cards.get(i).getNumber() == value1 ){
				bestCards.add(cards.get(i));	
			}
		}				
		
		return bestCards;
	}
	
public List<Card> findFlush(List<Card> cards){
	
	List<Card> bestCards = new ArrayList<Card>();
	
	int suit = -1;
	for (Entry<Integer, Integer> entry : suitMap.entrySet()) {
            if (entry.getValue().intValue() >= 5) {
	            suit = entry.getKey();
	            break;
            }
	}
	
	for(int i=cards.size() -1 ; i >= 0; --i){
		if(cards.get(i).getSuit() == suit){
			bestCards.add(cards.get(i));
			if(bestCards.size() == 5){
				break;
			}
		}
	}
		
	return bestCards;
}


public List<Card> findStraight(List<Card> cards){
	
	Collections.sort(cards);
	List<Card> bestCards = new ArrayList<Card>();
	
	if (cards.get(2).getNumber() + 1 == cards.get(3).getNumber() && cards.get(2).getNumber() + 2 == cards.get(4).getNumber() && cards.get(3).getNumber() + 1 == cards.get(4).getNumber()){
		bestCards.add(cards.get(2));
		bestCards.add(cards.get(3));
		bestCards.add(cards.get(4));
		bestCards.add(cards.get(5));
		bestCards.add(cards.get(6));
	}
	else if (cards.get(1).getNumber() + 1 == cards.get(2).getNumber() && cards.get(1).getNumber() + 2 == cards.get(3).getNumber() && cards.get(2).getNumber() + 1 == cards.get(3).getNumber()){
		bestCards.add(cards.get(1));
		bestCards.add(cards.get(2));
		bestCards.add(cards.get(3));
		bestCards.add(cards.get(4));
		bestCards.add(cards.get(5));
	}
	else if (cards.get(0).getNumber() + 1 == cards.get(1).getNumber() && cards.get(0).getNumber() + 2 == cards.get(2).getNumber() && cards.get(1).getNumber() + 1 == cards.get(2).getNumber()){
		bestCards.add(cards.get(0));
		bestCards.add(cards.get(1));
		bestCards.add(cards.get(2));
		bestCards.add(cards.get(3));
		bestCards.add(cards.get(4));
	}
	
	return bestCards;
}
	
	
public List<Card> findThreeOfAKind(List<Card> cards){
		
		Collections.sort(cards);
		boolean findThree = false;
		int cardNumber = cards.get(6).getNumber();
		List<Card> bestCards = new ArrayList<Card>();
		bestCards.add(cards.get(6));
		for (int i = cards.size() - 2; i >= 0; --i) {
			if (!findThree) {
				if (cardNumber == cards.get(i).getNumber()) {
					findThree = true;
					bestCards.add(cards.get(i));
					if (i != 5)
						bestCards.add(cards.get(i + 1));
				} else {
					cardNumber = cards.get(i).getNumber();
					if (bestCards.size() < 2) {
						bestCards.add(cards.get(i));
					}
				}
			} else {
				if (bestCards.size() < 5) {
					bestCards.add(cards.get(i));
				} else 
					break;
			}

		}
	
		return bestCards;
	}
	
public List<Card> findTwoPair(List<Card> cards){
	
	int countPair = 0;
	int lastCard = cards.get(6).getNumber();
	
	List<Card> bestCards = new ArrayList<Card>();
	bestCards.add(cards.get(6));
	
	for(int i=cards.size() - 2; i >= 0; --i)
		if(countPair == 2){
			if(bestCards.size() < 5){
				bestCards.add(cards.get(i));
			}else{
				break;
			}
				
		} else {
			if(lastCard == cards.get(i).getNumber()){
				countPair++;
				bestCards.add(cards.get(i));
				if (i != 5)
					bestCards.add(cards.get(i + 1));
			}else{
				lastCard = cards.get(i).getNumber();
			}
		}
	return bestCards;
}
	
	
	public List<Card> findOnePair(List<Card> cards) {
		Collections.sort(cards);
		boolean findPair = false;
		int pairNumber = cards.get(6).getNumber();
		List<Card> bestCards = new ArrayList<Card>();
		bestCards.add(cards.get(6));
		for (int i = cards.size() - 2; i >= 0; --i) {
			if (!findPair) {
				if (pairNumber == cards.get(i).getNumber()) {
					findPair = true;
					bestCards.add(cards.get(i));
					if (i != 5)
						bestCards.add(cards.get(i + 1));
				} else {
					pairNumber = cards.get(i).getNumber();
					if (bestCards.size() < 3) {
						bestCards.add(cards.get(i));
					}
				}
			} else {
				if (bestCards.size() < 5) {
					bestCards.add(cards.get(i));
				} else 
					break;
			}

		}

		return bestCards;
	}	 
	
	public List<Card> findHighCard(List<Card>cards)
	{
		List<Card> bestCards = new ArrayList<Card>();
		for (int i = cards.size() - 1; i >= 2; --i)
			bestCards.add(cards.get(i));
		return bestCards;
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
