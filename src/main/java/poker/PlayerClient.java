package poker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class PlayerClient extends Thread {

	private BufferedReader input;
	private PrintWriter output;
	private PokerClientApp clientApp;
	
	public PlayerClient(PokerClientApp pokerClient) throws IOException{
		System.out.println("Creating Client");
		clientApp = pokerClient;
		try {
			input = new BufferedReader(new InputStreamReader(clientApp.getSocket().getInputStream()));
			output = new PrintWriter(clientApp.getSocket().getOutputStream(), true);
			String in = input.readLine();
			clientApp.getGameView().setNumOfTables(Integer.parseInt(in.substring(7)));
		} catch (IOException e) {
			clientApp.getGameModel().showMessage("An error ocurred in PlayerClient " + e);
			throw e;
		}
	}
	
	public void init()
	{
		String line;
		while (true)
		{
			try
			{
				line = input.readLine();
				System.out.println(line);
				if (line.startsWith("END INIT"))
					break;
				if (line.startsWith("INIT PLACE"))
				{
					int seat = Integer.parseInt(line.substring(11, 12));
					clientApp.getGameModel().addTakenSeat(seat, line.substring(13));
				}
			}
			catch(IOException e)
			{
				clientApp.getGameModel().showMessage("Error while initializing client " + e);
				System.exit(-1);
			}
		}
		output.println("NEW PLAYER " + clientApp.getPlayer().getName());
		start();
	}
	
	public void sendMessage(String message)
	{
		output.println(message);
	}

	@Override
	public void run() 
	{
		System.out.println("Client run");
		while (true) {
			String line;
			try {
				while (true)
				{
					line = input.readLine();
					System.out.println(line);
					if (line.startsWith("MESSAGE"))
					{
						clientApp.getGameModel().showMessage(line.substring(8));
					}
					else if (line.startsWith("CARD"))
					{
						clientApp.addPlayerCard(line.substring(9), line.charAt(5) - 48, line.charAt(7) - 48);
					}
					else if (line.startsWith("BOARD CARD"))
					{
						clientApp.addBoardCard(line.substring(13), line.charAt(11) - 48);
					}
					else if (line.startsWith("MONEY"))
					{
						int seat = Integer.parseInt(line.substring(6, 7));
						if (seat == clientApp.getPlayer().getSeat())
							clientApp.addMoney(Integer.parseInt(line.substring(8)));
						else clientApp.getGameView().setMoney(Integer.parseInt(line.substring(8)), seat);
					}
					else if (line.startsWith("END TURN"))
					{
						clientApp.getGameView().hideButtons();
					}
					else if (line.startsWith("YOUR TURN"))
					{
						String[] args = line.split(" ");
						int call = Integer.parseInt(args[2]);
						int raise = Integer.parseInt(args[3]);
						int maxRaise = Integer.parseInt(args[4]);
						String buttons = "";
						for (int i = 4; i < args.length; ++i)
							buttons += args[i] + " ";
						clientApp.getGameView().setCallAndRaiseValue(call, raise, maxRaise);
						clientApp.getGameView().showButtons(buttons);
					}
					else if (line.startsWith("NEXT ROUND"))
					{
						clientApp.getPlayer().getPlayerPot().newRound();
					}
					else if (line.startsWith("POT"))
					{
						clientApp.getGameView().setPot(Integer.parseInt(line.substring(4)));
					}
					else if (line.startsWith("DEALER"))
					{
						clientApp.getGameView().setDealer(Integer.parseInt(line.substring(7)));
					}
					else if (line.startsWith("SEAT"))
					{
						clientApp.getGameModel().addTakenSeat(Integer.parseInt(line.substring(5, 6)), line.substring(7));
					}
					else if (line.startsWith("END ROUND"))
					{
						clientApp.getGameView().restartGame();
					}
					else if (line.startsWith("PLAYER LEFT"))
					{
						clientApp.getGameModel().removeTakenSeat(Integer.parseInt(line.substring(12)));
					}
					else if (line.startsWith("GAME STARTING"))
					{
						clientApp.getGameView().pokerGameView();
					}
					else if (line.startsWith("HOST"))
					{
						clientApp.getPlayer().setIsHost(Boolean.parseBoolean(line.substring(5)));
					}
					sleep(500);
				}
			} 
			catch (IOException e) {
				System.out.println("Error occured while reading message in PlayerClient " + e);
			}
			catch (NullPointerException e)
			{
				System.out.println("Null pointer exception in PlayerClient " + e);
			} 
			catch (InterruptedException e) {
				System.out.println("Sleep interrupted " + e);
			}
			finally
			{
				try
				{
					clientApp.getSocket().close();
					System.exit(-1);
				}
				catch (IOException e)
				{
					System.out.println("PlayerClient error Couldnt close connection " + e);
				}
			}
		}

	}

}
