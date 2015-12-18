package poker.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;

import poker.BestHand;
import poker.Card;

public class BestHandTest {

	public HashMap<Integer, Integer> createMap(List<Card> cards )
	{
		HashMap<Integer, Integer> valueMap = new HashMap<Integer, Integer>();
		for (int i = 0; i < cards.size(); ++i)
		{
			Integer count = valueMap.get(cards.get(i).getNumber());
					if (count == null)
				valueMap.put(cards.get(i).getNumber(), 1);
			
			else valueMap.put(cards.get(i).getNumber(), count + 1);
		}
		return valueMap;
	}
	
	@Test
	public void compareStraightFlushTest()
	{
		List<Card> cards = new ArrayList<Card>(); 
		cards.add(new Card(5, 1));
		cards.add(new Card(4, 1));
		cards.add(new Card(3, 1));
		cards.add(new Card(2, 1));
		cards.add(new Card(1, 1));
		
		List<Card> cards2 = new ArrayList<Card>(); 
		cards2.add(new Card(7, 1));
		cards2.add(new Card(6, 1));
		cards2.add(new Card(5, 1));
		cards2.add(new Card(4, 1));
		cards2.add(new Card(3, 1));
		
		BestHand hand1 = new BestHand();
		BestHand hand2 = new BestHand();
		
		hand1.setCards(cards);
		hand2.setCards(cards2);
		hand1.setRank(9);
		hand2.setRank(9);
		
		assertEquals(-1, hand1.compareHighCards(hand2));
	}
	
	@Test
	public void compareFourOfKindTest(){
		
		List<Card> cards = new ArrayList<Card>(); 
		cards.add(new Card(5, 3));
		cards.add(new Card(1, 4));
		cards.add(new Card(1, 3));
		cards.add(new Card(1, 2));
		cards.add(new Card(1, 1));
		
		List<Card> cards2 = new ArrayList<Card>(); 
		cards2.add(new Card(5, 3));
		cards2.add(new Card(2, 4));
		cards2.add(new Card(2, 3));
		cards2.add(new Card(2, 2));
		cards2.add(new Card(2, 1));
		
		BestHand hand1 = new BestHand();
		BestHand hand2 = new BestHand();
		
		hand1.setCards(cards);
		hand1.setRank(8);
		hand1.setMap(createMap(cards));
		hand2.setCards(cards2);
		hand2.setRank(8);
		hand2.setMap(createMap(cards2));
		
		assertEquals(-1, hand1.compareHighCards(hand2));
	}
	
	@Test
	public void compareFullTest()
	{
		List<Card> cards = new ArrayList<Card>(); 
		cards.add(new Card(5, 1));
		cards.add(new Card(5, 1));
		cards.add(new Card(5, 1));
		cards.add(new Card(2, 1));
		cards.add(new Card(2, 1));
		
		List<Card> cards2 = new ArrayList<Card>(); 
		cards2.add(new Card(6, 1));
		cards2.add(new Card(6, 1));
		cards2.add(new Card(3, 1));
		cards2.add(new Card(3, 1));
		cards2.add(new Card(3, 1));
		
		BestHand hand1 = new BestHand();
		BestHand hand2 = new BestHand();
		
		hand1.setCards(cards);
		hand1.setRank(7);
		hand1.setMap(createMap(cards));
		hand2.setCards(cards2);
		hand2.setRank(7);
		hand2.setMap(createMap(cards2));
		
		assertEquals(1, hand1.compareHighCards(hand2));
	}
	
	@Test
	public void comapreFlushTest(){
		
		List<Card> cards = new ArrayList<Card>(); 
		cards.add(new Card(5, 3));
		cards.add(new Card(7, 3));
		cards.add(new Card(8, 3));
		cards.add(new Card(9, 3));
		cards.add(new Card(10, 3));
		
		List<Card> cards2 = new ArrayList<Card>(); 
		cards2.add(new Card(2, 4));
		cards2.add(new Card(3, 4));
		cards2.add(new Card(4, 4));
		cards2.add(new Card(5, 4));
		cards2.add(new Card(11, 4));
		
		BestHand hand1 = new BestHand();
		BestHand hand2 = new BestHand();
		
		hand1.setCards(cards);
		hand1.setRank(6);
		hand2.setCards(cards2);
		hand2.setRank(6);
		
		assertEquals(-1, hand1.compareHighCards(hand2));
	}
	
	@Test
	public void comapreStraightTest(){
		
		List<Card> cards = new ArrayList<Card>(); 
		cards.add(new Card(5, 3));
		cards.add(new Card(6, 3));
		cards.add(new Card(7, 2));
		cards.add(new Card(8, 3));
		cards.add(new Card(9, 3));
		
		List<Card> cards2 = new ArrayList<Card>(); 
		cards2.add(new Card(2, 1));
		cards2.add(new Card(3, 0));
		cards2.add(new Card(4, 3));
		cards2.add(new Card(5, 2));
		cards2.add(new Card(6, 1));
		
		BestHand hand1 = new BestHand();
		BestHand hand2 = new BestHand();
		
		hand1.setCards(cards);
		hand1.setRank(5);
		hand2.setCards(cards2);
		hand2.setRank(5);
		
		assertEquals(1, hand1.compareHighCards(hand2));
	}
	
	@Test
	public void comapreThreeOfKindTest(){
		
		List<Card> cards = new ArrayList<Card>(); 
		cards.add(new Card(5, 3));
		cards.add(new Card(5, 3));
		cards.add(new Card(5, 2));
		cards.add(new Card(8, 3));
		cards.add(new Card(9, 3));
		
		List<Card> cards2 = new ArrayList<Card>(); 
		cards2.add(new Card(2, 1));
		cards2.add(new Card(2, 0));
		cards2.add(new Card(2, 3));
		cards2.add(new Card(5, 2));
		cards2.add(new Card(6, 1));
		
		BestHand hand1 = new BestHand();
		BestHand hand2 = new BestHand();
		
		hand1.setCards(cards);
		hand1.setRank(4);
		hand1.setMap(createMap(cards));
		hand2.setCards(cards2);
		hand2.setRank(4);
		hand2.setMap(createMap(cards2));
		
		assertEquals(1, hand1.compareHighCards(hand2));
	}
	
	@Test
	public void comapreTwoPairsTest(){
		
		List<Card> cards = new ArrayList<Card>(); 
		cards.add(new Card(5, 3));
		cards.add(new Card(5, 3));
		cards.add(new Card(8, 2));
		cards.add(new Card(8, 3));
		cards.add(new Card(7, 3));
		
		List<Card> cards2 = new ArrayList<Card>(); 
		cards2.add(new Card(2, 1));
		cards2.add(new Card(2, 0));
		cards2.add(new Card(9, 3));
		cards2.add(new Card(9, 2));
		cards2.add(new Card(6, 1));
		
		BestHand hand1 = new BestHand();
		BestHand hand2 = new BestHand();
		
		hand1.setCards(cards);
		hand1.setRank(3);
		hand1.setMap(createMap(cards));
		hand2.setCards(cards2);
		hand2.setRank(3);
		hand2.setMap(createMap(cards2));
		
		assertEquals(-1, hand1.compareHighCards(hand2));
	}
	
	@Test
	public void comaprePairTest(){
		
		List<Card> cards = new ArrayList<Card>(); 
		cards.add(new Card(5, 3));
		cards.add(new Card(6, 3));
		cards.add(new Card(8, 2));
		cards.add(new Card(8, 3));
		cards.add(new Card(7, 3));
		
		List<Card> cards2 = new ArrayList<Card>(); 
		cards2.add(new Card(2, 1));
		cards2.add(new Card(5, 0));
		cards2.add(new Card(9, 3));
		cards2.add(new Card(9, 2));
		cards2.add(new Card(6, 1));
		
		BestHand hand1 = new BestHand();
		BestHand hand2 = new BestHand();
		
		hand1.setCards(cards);
		hand1.setRank(2);
		hand1.setMap(createMap(cards));
		hand2.setCards(cards2);
		hand2.setRank(2);
		hand2.setMap(createMap(cards2));
		
		assertEquals(-1, hand1.compareHighCards(hand2));
	}
	
	@Test
	public void comapreNothingTest(){
		
		List<Card> cards = new ArrayList<Card>(); 
		cards.add(new Card(5, 3));
		cards.add(new Card(6, 3));
		cards.add(new Card(8, 2));
		cards.add(new Card(7, 3));
		cards.add(new Card(10, 3));
		
		List<Card> cards2 = new ArrayList<Card>(); 
		cards2.add(new Card(2, 1));
		cards2.add(new Card(5, 0));
		cards2.add(new Card(6, 3));
		cards2.add(new Card(7, 2));
		cards2.add(new Card(9, 1));
		
		BestHand hand1 = new BestHand();
		BestHand hand2 = new BestHand();
		
		hand1.setCards(cards);
		hand1.setRank(1);
		hand2.setCards(cards2);
		hand2.setRank(1);
		
		assertEquals(1, hand1.compareHighCards(hand2));
	}
}
