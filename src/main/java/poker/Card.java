package poker;

import java.util.Arrays;
import java.util.List;

public class Card implements Comparable<Card>{

//	private static List<String> s_suites = Arrays.asList("Spades", "Clubs", "Hearts", "Diamonds");
//	private static List<String> s_numbers = Arrays.asList("Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Jack", "Queen", "King", "Ace");
	
	private static List<String> s_suites = Arrays.asList("s", "c", "h", "d");
	private static List<String> s_numbers = Arrays.asList("2", "3", "4", "5", "6", "7", "8", "9", "10", "j", "q", "k", "a");
	
	private int number;
	private int suit;
	
	public Card()
	{
		number = -1;
		suit = -1;
	}
	
	public Card(int number, int suit)
	{
		//TODO throw exception
		if (number < 0 || number >= s_numbers.size() || suit < 0 || suit >= s_suites.size()) 
		{ }
		this.number = number;
		this.suit = suit;
	}
	
	public int getNumber()
	{
		return this.number;
	}
	
	public int getSuit()
	{
		return this.suit;
	}
	
	public String toString()
	{
		if (number == -1 || suit == -1)
			return "Wrong card";
		return s_numbers.get(number) + s_suites.get(suit);
	}
	
//	public String toString()
//	{
//		if (number == -1 || suit == -1)
//			return "Wrong card";
//		return s_numbers.get(number) + " of " + s_suites.get(suit);
//	}
	
	public boolean equals(Object obj)
	{
		if (obj == this)
			return true;
		if (!(obj instanceof Card))
			return false;
		Card card = (Card)obj;
		return this.number == card.getNumber();
	}
	
	public int hashCode()
	{
		return this.number;
	}

	@Override
	public int compareTo(Card o) {
		if (this.getNumber() < o.getNumber())
			return -1;
		else if (this.getNumber() > o.getNumber())
			return 1;
		return 0;
	}
}
