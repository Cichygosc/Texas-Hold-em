package poker.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import poker.Card;
import poker.CardEvaluator;

public class CardEvaluatorTest {

	@Test
	public void isFourOfKindTest()
	{
		List<Card> temp = new ArrayList<Card>();
		temp.add(new Card(1, 2));
		temp.add(new Card(1, 3));
		temp.add(new Card(3, 2));
		temp.add(new Card(4, 3));
		temp.add(new Card(5, 2));
		temp.add(new Card(1, 4));
		temp.add(new Card(1, 1));
		assertTrue(CardEvaluator.getInstance().isFourOfKind(temp));
	}
}
