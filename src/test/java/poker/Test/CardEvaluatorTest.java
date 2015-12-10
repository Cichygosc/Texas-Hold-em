package poker.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import poker.Card;
import poker.CardEvaluator;

public class CardEvaluatorTest {

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
		assertTrue(CardEvaluator.getInstance().isFullHouse(temp));
	}
	
	@Test
	public void isFlush()
	{
		List<Card> temp = new ArrayList<Card>();
		temp.add(new Card(0, 1));
		temp.add(new Card(0, 1));
		temp.add(new Card(0, 1));
		temp.add(new Card(6, 3));
		temp.add(new Card(0, 1));
		temp.add(new Card(0, 1));
		temp.add(new Card(11, 5));
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
		temp.add(new Card(5, 2));
		temp.add(new Card(6, 4));
		temp.add(new Card(7, 4));
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
		assertTrue(CardEvaluator.getInstance().isOnePair(temp));
	}

	
	
}
