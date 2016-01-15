package poker.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import poker.Card;
import poker.CardEvaluator;

public class CardEvaluatorTest {

	private static boolean cmp (List<Card> l1, List<Card> l2)
	{
		List<Card> temp = new ArrayList<Card>(l1);
		for (Card card: l2)
			if (!temp.remove(card))
				return false;
		return temp.isEmpty();
	}
	
	
	@Test
	public void isStraightFlushTest(){
		
			List<Card> temp = new ArrayList<Card>();
			temp.add(new Card(1, 4));
			temp.add(new Card(1, 1));
			temp.add(new Card(2, 1));
			temp.add(new Card(4, 1));
			temp.add(new Card(1, 2));
			temp.add(new Card(5, 1));
			temp.add(new Card(3, 1));
			CardEvaluator.getInstance().createMaps(temp);
			assertTrue(CardEvaluator.getInstance().isStraightFlush(temp));
	}

	@Test
	public void isFourOfKindTest()
	{
		List<Card> temp = new ArrayList<Card>();
		temp.add(new Card(1, 1));
		temp.add(new Card(1, 2));
		temp.add(new Card(3, 2));
		temp.add(new Card(4, 3));
		temp.add(new Card(5, 2));
		temp.add(new Card(1, 3));
		temp.add(new Card(1, 4));
		CardEvaluator.getInstance().createMaps(temp);
		assertTrue(CardEvaluator.getInstance().isFourOfKind(temp));
	}
	
	@Test
	public void isFullHouse()
	{
		List<Card> temp = new ArrayList<Card>();
		temp.add(new Card(1, 1));
		temp.add(new Card(1, 2));
		temp.add(new Card(1, 3));
		temp.add(new Card(5, 3));
		temp.add(new Card(0, 1));
		temp.add(new Card(0, 4));
		temp.add(new Card(11, 5));
		CardEvaluator.getInstance().createMaps(temp);
		assertTrue(CardEvaluator.getInstance().isFullHouse(temp));
	}
	
	@Test
	public void isFlush()
	{
		List<Card> temp = new ArrayList<Card>();
		temp.add(new Card(0, 1));
		temp.add(new Card(5, 1));
		temp.add(new Card(4, 1));
		temp.add(new Card(6, 3));
		temp.add(new Card(2, 1));
		temp.add(new Card(3, 1));
		temp.add(new Card(11, 5));
		CardEvaluator.getInstance().createMaps(temp);
		assertTrue(CardEvaluator.getInstance().isFlush(temp));
	}
	
	@Test
	public void isStraightTest()
	{
		List<Card> temp = new ArrayList<Card>();
		temp.add(new Card(1, 1));
		temp.add(new Card(1, 2));
		temp.add(new Card(2, 2));
		temp.add(new Card(4, 3));
		temp.add(new Card(1, 2));
		temp.add(new Card(5, 3));
		temp.add(new Card(3, 4));
		CardEvaluator.getInstance().createMaps(temp);
		assertTrue(CardEvaluator.getInstance().isStraight(temp));
	}
	
	@Test
	public void isThreeOfAKindTest()
	{
		List<Card> temp = new ArrayList<Card>();
		temp.add(new Card(1, 1));
		temp.add(new Card(1, 2));
		temp.add(new Card(3, 1));
		temp.add(new Card(1, 3));
		temp.add(new Card(4, 2));
		temp.add(new Card(6, 4));
		temp.add(new Card(7, 4));
		CardEvaluator.getInstance().createMaps(temp);
		assertTrue(CardEvaluator.getInstance().isThreeOfAKind(temp));
	}
	
	@Test
	public void isTwoPairTest()
	{
		List<Card> temp = new ArrayList<Card>();
		temp.add(new Card(1, 1));
		temp.add(new Card(1, 2));
		temp.add(new Card(3, 3));
		temp.add(new Card(5, 3));
		temp.add(new Card(0, 1));
		temp.add(new Card(0, 4));
		temp.add(new Card(11, 5));
		CardEvaluator.getInstance().createMaps(temp);
		assertTrue(CardEvaluator.getInstance().isTwoPair(temp));

			}
	
	@Test
	public void isOnePairTest()
	{
		List<Card> temp = new ArrayList<Card>();
		temp.add(new Card(1, 1));
		temp.add(new Card(1, 2));
		temp.add(new Card(3, 1));
		temp.add(new Card(4, 3));
		temp.add(new Card(5, 2));
		temp.add(new Card(6, 4));
		temp.add(new Card(7, 4));
		CardEvaluator.getInstance().createMaps(temp);
		assertTrue(CardEvaluator.getInstance().isOnePair(temp));
	}
	
	@Test
	public void findStraightFlushTest()
	{
		List<Card> temp = new ArrayList<Card>();
		temp.add(new Card(1, 3));
		temp.add(new Card(1, 1));
		temp.add(new Card(2, 1));
		temp.add(new Card(4, 1));
		temp.add(new Card(1, 2));
		temp.add(new Card(5, 1));
		temp.add(new Card(3, 1));
		
		List<Card> cards = new ArrayList<Card>(); 
		cards.add(new Card(5, 1));
		cards.add(new Card(4, 1));
		cards.add(new Card(3, 1));
		cards.add(new Card(2, 1));
		cards.add(new Card(1, 1));
		
		CardEvaluator.getInstance().createMaps(temp);
		assertTrue(cmp(CardEvaluator.getInstance().findStraightFlush(temp), cards));
	}
	
	@Test
	public void findFourOfKindTest()
	{
		List<Card> temp = new ArrayList<Card>();
		temp.add(new Card(1, 1));
		temp.add(new Card(1, 2));
		temp.add(new Card(3, 2));
		temp.add(new Card(4, 3));
		temp.add(new Card(5, 2));
		temp.add(new Card(1, 3));
		temp.add(new Card(1, 4));
		
		List<Card> cards = new ArrayList<Card>(); 
		cards.add(new Card(5, 3));
		cards.add(new Card(1, 4));
		cards.add(new Card(1, 3));
		cards.add(new Card(1, 2));
		cards.add(new Card(1, 1));
		
		assertTrue(cmp(CardEvaluator.getInstance().findFourOfKind(temp), cards));
	}


	@Test
	public void findFullHouseTest()
	{
		List<Card> temp = new ArrayList<Card>();
		temp.add(new Card(1, 1));
		temp.add(new Card(0, 2));
		temp.add(new Card(3, 1));
		temp.add(new Card(3, 2));
		temp.add(new Card(3, 3));
		temp.add(new Card(2, 1));
		temp.add(new Card(2, 3));
		
		List<Card> cards = new ArrayList<Card>(); 
		cards.add(new Card(3, 3));
		cards.add(new Card(3, 2));
		cards.add(new Card(3, 1));
		cards.add(new Card(2, 3));
		cards.add(new Card(2, 1));
		CardEvaluator.getInstance().createMaps(temp);
		assertTrue(cmp(CardEvaluator.getInstance().findFullHouse(temp), cards));
	}

	@Test
	public void findFlushTest()
	{
		List<Card> temp = new ArrayList<Card>();
		temp.add(new Card(1, 1));
		temp.add(new Card(2, 1));
		temp.add(new Card(3, 1));
		temp.add(new Card(6, 3));
		temp.add(new Card(4, 1));
		temp.add(new Card(5, 1));
		temp.add(new Card(11, 5));
		
		List<Card> cards = new ArrayList<Card>(); 
		cards.add(new Card(5, 1));
		cards.add(new Card(4, 1));
		cards.add(new Card(3, 1));
		cards.add(new Card(2, 1));
		cards.add(new Card(1, 1));
		CardEvaluator.getInstance().createMaps(temp);
		assertTrue(cmp(CardEvaluator.getInstance().findFlush(temp), cards));
	}
	
	@Test
	public void findStraightTest()
	{
		List<Card> temp = new ArrayList<Card>();
		temp.add(new Card(1, 1));
		temp.add(new Card(1, 2));
		temp.add(new Card(2, 2));
		temp.add(new Card(4, 3));
		temp.add(new Card(1, 2));
		temp.add(new Card(5, 3));
		temp.add(new Card(3, 4));
		
		List<Card> cards = new ArrayList<Card>(); 
		cards.add(new Card(5, 3));
		cards.add(new Card(4, 3));
		cards.add(new Card(3, 4));
		cards.add(new Card(2, 2));
		cards.add(new Card(1, 1));
		assertTrue(cmp(CardEvaluator.getInstance().findStraight(temp), cards));

	}


	
	@Test
	public void findThreeOfAKindTest()
	{
		List<Card> temp = new ArrayList<Card>();
		temp.add(new Card(1, 1));
		temp.add(new Card(1, 2));
		temp.add(new Card(2, 1));
		temp.add(new Card(1, 3));
		temp.add(new Card(4, 2));
		temp.add(new Card(5, 4));
		temp.add(new Card(6, 4));
		
		List<Card> cards = new ArrayList<Card>(); 
		cards.add(new Card(6, 4));
		cards.add(new Card(5, 4));
		cards.add(new Card(1, 3));
		cards.add(new Card(1, 2));
		cards.add(new Card(1, 1));
		assertTrue(cmp(CardEvaluator.getInstance().findThreeOfAKind(temp), cards));

	}
	
	@Test
	public void findTwoPairTest()
	{
		List<Card> temp = new ArrayList<Card>();
		temp.add(new Card(1, 1));
		temp.add(new Card(1, 2));
		temp.add(new Card(3, 1));
		temp.add(new Card(3, 3));
		temp.add(new Card(5, 2));
		temp.add(new Card(6, 4));
		temp.add(new Card(7, 4));
		
		List<Card> cards = new ArrayList<Card>(); 
		cards.add(new Card(7, 4));
		cards.add(new Card(3, 3));
		cards.add(new Card(3, 1));
		cards.add(new Card(1, 2));
		cards.add(new Card(1, 1));
		assertTrue(cmp(CardEvaluator.getInstance().findTwoPair(temp), cards));
	}
	
	@Test
	public void findOnePairTest()
	{
		List<Card> temp = new ArrayList<Card>();
		temp.add(new Card(1, 1));
		temp.add(new Card(1, 2));
		temp.add(new Card(2, 1));
		temp.add(new Card(3, 3));
		temp.add(new Card(4, 2));
		temp.add(new Card(5, 4));
		temp.add(new Card(6, 4));
		
		List<Card> cards = new ArrayList<Card>(); 
		cards.add(new Card(6, 4));
		cards.add(new Card(5, 4));
		cards.add(new Card(4, 2));
		cards.add(new Card(1, 2));
		cards.add(new Card(1, 1));
		assertTrue(cmp(CardEvaluator.getInstance().findOnePair(temp), cards));

	}
	
	@Test
	public void findHighCardTest()
	{
		List<Card> temp = new ArrayList<Card>();
		temp.add(new Card(1, 1));
		temp.add(new Card(2, 2));
		temp.add(new Card(3, 1));
		temp.add(new Card(4, 3));
		temp.add(new Card(5, 2));
		temp.add(new Card(6, 4));
		temp.add(new Card(7, 4));
		
		List<Card> cards = new ArrayList<Card>(); 
		cards.add(new Card(7, 4));
		cards.add(new Card(6, 4));
		cards.add(new Card(5, 2));
		cards.add(new Card(4, 3));
		cards.add(new Card(3, 1));
		assertTrue(cmp(CardEvaluator.getInstance().findHighCard(temp), cards));

	}
	
	
	
}
