package poker.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import poker.Dealer;
import poker.HumanPlayer;
import poker.Player;

public class SidePotsTest {

	@Test
	public void firstGameplayTest() {
		Dealer dealer = new Dealer(null);
		List<Player> players = dummyPlayers();
		//first move
		dummyBet(players.get(0), 50, dealer);
		assertEquals(1, dealer.getTable().getTablePots().size());
		assertEquals(50, dealer.getTable().getTablePots().get(0).getBet());   
		assertEquals(50, dealer.getTable().getTablePots().get(0).getPot());  
		//second move
		dummyBet(players.get(1), 80, dealer);
		assertEquals(2, dealer.getTable().getTablePots().size());
		assertEquals(50, dealer.getTable().getTablePots().get(0).getBet());   
		assertEquals(100, dealer.getTable().getTablePots().get(0).getPot());  
		assertEquals(30, dealer.getTable().getTablePots().get(1).getBet());   
		assertEquals(30, dealer.getTable().getTablePots().get(1).getPot()); 
		//third move
		dummyCall(players.get(2), 80, dealer);
		assertEquals(2, dealer.getTable().getTablePots().size());
		assertEquals(50, dealer.getTable().getTablePots().get(0).getBet());   
		assertEquals(150, dealer.getTable().getTablePots().get(0).getPot());  
		assertEquals(30, dealer.getTable().getTablePots().get(1).getBet());   
		assertEquals(60, dealer.getTable().getTablePots().get(1).getPot()); 
		//fourth move
		dummyBet(players.get(3), 150, dealer);
		assertEquals(3, dealer.getTable().getTablePots().size());
		assertEquals(50, dealer.getTable().getTablePots().get(0).getBet());   
		assertEquals(200, dealer.getTable().getTablePots().get(0).getPot());  
		assertEquals(30, dealer.getTable().getTablePots().get(1).getBet());   
		assertEquals(90, dealer.getTable().getTablePots().get(1).getPot()); 
		assertEquals(70, dealer.getTable().getTablePots().get(2).getBet());   
		assertEquals(70, dealer.getTable().getTablePots().get(2).getPot()); 
		//fifth move
		dummyCall(players.get(0), 100, dealer);
		assertEquals(3, dealer.getTable().getTablePots().size());
		assertEquals(50, dealer.getTable().getTablePots().get(0).getBet());   
		assertEquals(200, dealer.getTable().getTablePots().get(0).getPot());  
		assertEquals(30, dealer.getTable().getTablePots().get(1).getBet());   
		assertEquals(120, dealer.getTable().getTablePots().get(1).getPot()); 
		assertEquals(70, dealer.getTable().getTablePots().get(2).getBet());   
		assertEquals(140, dealer.getTable().getTablePots().get(2).getPot()); 
		//sixth move
		dummyCall(players.get(1), 70, dealer);
		assertEquals(3, dealer.getTable().getTablePots().size());
		assertEquals(50, dealer.getTable().getTablePots().get(0).getBet());   
		assertEquals(200, dealer.getTable().getTablePots().get(0).getPot());  
		assertEquals(30, dealer.getTable().getTablePots().get(1).getBet());   
		assertEquals(120, dealer.getTable().getTablePots().get(1).getPot()); 
		assertEquals(70, dealer.getTable().getTablePots().get(2).getBet());   
		assertEquals(210, dealer.getTable().getTablePots().get(2).getPot()); 
		//seventh move
		dummyBet(players.get(2), 100, dealer);
		assertEquals(4, dealer.getTable().getTablePots().size());
		assertEquals(50, dealer.getTable().getTablePots().get(0).getBet());   
		assertEquals(200, dealer.getTable().getTablePots().get(0).getPot());  
		assertEquals(30, dealer.getTable().getTablePots().get(1).getBet());   
		assertEquals(120, dealer.getTable().getTablePots().get(1).getPot()); 
		assertEquals(70, dealer.getTable().getTablePots().get(2).getBet());   
		assertEquals(280, dealer.getTable().getTablePots().get(2).getPot()); 
		assertEquals(30, dealer.getTable().getTablePots().get(3).getBet());   
		assertEquals(30, dealer.getTable().getTablePots().get(3).getPot()); 
		//eighth move
		dummyBet(players.get(3), 70, dealer);
		assertEquals(5, dealer.getTable().getTablePots().size());
		assertEquals(50, dealer.getTable().getTablePots().get(0).getBet());   
		assertEquals(200, dealer.getTable().getTablePots().get(0).getPot());  
		assertEquals(30, dealer.getTable().getTablePots().get(1).getBet());   
		assertEquals(120, dealer.getTable().getTablePots().get(1).getPot()); 
		assertEquals(70, dealer.getTable().getTablePots().get(2).getBet());   
		assertEquals(280, dealer.getTable().getTablePots().get(2).getPot()); 
		assertEquals(30, dealer.getTable().getTablePots().get(3).getBet());   
		assertEquals(60, dealer.getTable().getTablePots().get(3).getPot()); 
		assertEquals(40, dealer.getTable().getTablePots().get(4).getBet());   
		assertEquals(40, dealer.getTable().getTablePots().get(4).getPot());
		//ninth move
		dummyAllIn(players.get(0), dealer);
		assertEquals(6, dealer.getTable().getTablePots().size());
		assertEquals(50, dealer.getTable().getTablePots().get(0).getBet());   
		assertEquals(200, dealer.getTable().getTablePots().get(0).getPot());  
		assertEquals(30, dealer.getTable().getTablePots().get(1).getBet());   
		assertEquals(120, dealer.getTable().getTablePots().get(1).getPot()); 
		assertEquals(70, dealer.getTable().getTablePots().get(2).getBet());   
		assertEquals(280, dealer.getTable().getTablePots().get(2).getPot()); 
		assertEquals(30, dealer.getTable().getTablePots().get(3).getBet());   
		assertEquals(90, dealer.getTable().getTablePots().get(3).getPot()); 
		assertEquals(20, dealer.getTable().getTablePots().get(4).getBet());   
		assertEquals(40, dealer.getTable().getTablePots().get(4).getPot());
		assertEquals(20, dealer.getTable().getTablePots().get(5).getBet());   
		assertEquals(20, dealer.getTable().getTablePots().get(5).getPot());
		//tenth move
		dummyAllIn(players.get(2), dealer);
		assertEquals(6, dealer.getTable().getTablePots().size());
		assertEquals(50, dealer.getTable().getTablePots().get(0).getBet());   
		assertEquals(200, dealer.getTable().getTablePots().get(0).getPot());  
		assertEquals(30, dealer.getTable().getTablePots().get(1).getBet());   
		assertEquals(120, dealer.getTable().getTablePots().get(1).getPot()); 
		assertEquals(70, dealer.getTable().getTablePots().get(2).getBet());   
		assertEquals(280, dealer.getTable().getTablePots().get(2).getPot()); 
		assertEquals(30, dealer.getTable().getTablePots().get(3).getBet());   
		assertEquals(90, dealer.getTable().getTablePots().get(3).getPot()); 
		assertEquals(20, dealer.getTable().getTablePots().get(4).getBet());   
		assertEquals(60, dealer.getTable().getTablePots().get(4).getPot());
		assertEquals(20, dealer.getTable().getTablePots().get(5).getBet());   
		assertEquals(40, dealer.getTable().getTablePots().get(5).getPot());
		
		dealer.getTable().optimalizeSidePots();
		assertEquals(3, dealer.getTable().getTablePots().size());
		assertEquals(150, dealer.getTable().getTablePots().get(0).getBet());   
		assertEquals(600, dealer.getTable().getTablePots().get(0).getPot());  
		assertEquals(50, dealer.getTable().getTablePots().get(1).getBet());   
		assertEquals(150, dealer.getTable().getTablePots().get(1).getPot()); 
		assertEquals(20, dealer.getTable().getTablePots().get(2).getBet());   
		assertEquals(40, dealer.getTable().getTablePots().get(2).getPot());
		
	}
	
	public List<Player> dummyPlayers()
	{
		Player player1 = new HumanPlayer("Player1", false);
		Player player2 = new HumanPlayer("Player2", false);
		Player player3 = new HumanPlayer("Player3", false);
		Player player4 = new HumanPlayer("Player4", false);
		player1.getPlayerPot().setMoney(200);
		player2.getPlayerPot().setMoney(150);
		player3.getPlayerPot().setMoney(220);
		player4.getPlayerPot().setMoney(300);
		List<Player> players = new ArrayList<Player>();
		players.add(player1);
		players.add(player2);
		players.add(player3);
		players.add(player4);
		return players;
	}
	
	public void dummyCall(Player player, int call, Dealer dealer)
	{
		player.getPlayerPot().bet(call);
		player.getPlayerPot().setCurrentBet(call);
		dealer.addPot(call, player);
	}
	
	public void dummyBet(Player player, int bet, Dealer dealer)
	{
		player.getPlayerPot().bet(bet);
		player.getPlayerPot().setCurrentBet(bet);
		dealer.setCurrentBet(bet, player);
	}
	
	public void dummyAllIn(Player player, Dealer dealer)
	{
		dealer.addPot(player.getPlayerPot().getMoney(), player);
		player.getPlayerPot().allIn();
	}

}
