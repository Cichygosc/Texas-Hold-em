package poker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

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
	private HashMap<Integer, Integer> valueMap;

	public BestHand() {
		valueMap = new HashMap<Integer, Integer>();
		cards = new ArrayList<Card>();
		rank = 0;
	}

	public int compareHighCards(BestHand hand) {
		if (rank == 10 || rank == 9 || rank == 6 || rank == 5 || rank == 1)
		{
			for (int i = cards.size() - 1; i >= 0; --i) {
				if (cards.get(i).getNumber() > hand.getCards().get(i).getNumber())
					return 1;
				else if (cards.get(i).getNumber() < hand.getCards().get(i).getNumber())
					return -1;
			}
		}
		else if (rank == 8)
		{
			if (getKey(4) > hand.getKey(4))
				return 1;
			else if (getKey(4) < hand.getKey(4))
				return -1;
			else if (getKey(1) > hand.getKey(1))
				return 1;
			else if (getKey(1) < hand.getKey(1))
				return 1;
		}
		else if (rank == 7)
		{
			int value = compareLeftCards(3, hand);
			if (value == 0)
				return compareLeftCards(2, hand);
			return value;
		}
		else if (rank == 4)
		{
			if (getKey(3) > hand.getKey(3))
				return 1;
			else if (getKey(3) < hand.getKey(3))
				return -1;
			return compareLeftCards(1, hand);
		}
		else if (rank == 3 || rank == 2)
		{
			int value = compareLeftCards(2, hand);
			if (value == 0)
				return compareLeftCards(1, hand);
			return value;
		}		
		return 0;
	}
	
	public int compareLeftCards(int value, BestHand hand)
	{
		List<Integer> l1 = getLeftCards(value);
		List<Integer> l2 = hand.getLeftCards(value);
		for (int i = l1.size() - 1; i >= 0; --i)
		{
			if (l1.get(i) > l2.get(i))
				return 1;
			else if (l1.get(i) < l2.get(i))
				return -1;
		}
		return 0;
	}
	
	public List<Integer> getLeftCards(int value)
	{
		List<Integer> temp = new ArrayList<Integer>(); 
		for (Entry<Integer, Integer> entry : valueMap.entrySet()) 
            if (entry.getValue().intValue() == value) 
	           temp.add(entry.getKey().intValue());
		Collections.sort(temp);
        return temp;
	}
	
	public int getKey(int value)
	{
		for (Entry<Integer, Integer> entry : valueMap.entrySet()) 
            if (entry.getValue().intValue() == value) 
	           return entry.getKey().intValue();
        return -1;
	           
	}

	public void setMap(HashMap<Integer, Integer> map) {
		this.valueMap = map;
	}

	public void setCards(List<Card> cards) {
		this.cards = cards;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public List<Card> getCards() {
		return cards;
	}

	public int getRank() {
		return rank;
	}

	public String toString() {
		String string = "";
		switch (rank) {
		case 1:
			string = "High Card";
			break;
		case 2:
			string = "Pair";
			break;
		case 3:
			string = "Two Pairs";
			break;
		case 4:
			string = "Three of Kind";
			break;
		case 5:
			string = "Straight";
			break;
		case 6:
			string = "Flush";
			break;
		case 7:
			string = "Full";
			break;
		case 8:
			string = "Four of Kind";
			break;
		case 9:
			string = "Straight Flush";
			break;
		case 10:
			string = "Royal Flush";
			break;
		default:
			string = "Unrecognized combination";
			break;
		}
		return string;
	}

}
