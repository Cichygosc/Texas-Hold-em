package poker.AI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class AIClient extends Thread {
	
	private BufferedReader input;
	private PrintWriter output;
	private PokerAIApp aiApp;

	public AIClient(PokerAIApp aiApp, int table) throws IOException
	{
		this.aiApp = aiApp;
		input = new BufferedReader(new InputStreamReader(aiApp.getSocket().getInputStream()));
		output = new PrintWriter(aiApp.getSocket().getOutputStream(), true);
		output.println("TABLE " + table);
		output.println("NEW BOT " + this.aiApp.getPlayer().getName());
		output.println("SEAT " + this.aiApp.getPlayer().getSeat());
		start();
	}
	
	public void sendMessage(String message)
	{
		output.println(message);
	}
	
	public void run()
	{
		while (true) {
			String line;
			try {
				while (true)
				{
					line = input.readLine();
					System.out.println(line);
					if (line.startsWith("CARD"))
					{
						aiApp.addPlayerCard(line.substring(9), line.charAt(5) - 48);
					}
					else if (line.startsWith("BOARD CARD"))
					{
						aiApp.addBoardCard(line.substring(13));
					}
					else if (line.startsWith("MONEY"))
					{
						int seat = Integer.parseInt(line.substring(6, 7));
						if (seat == aiApp.getPlayer().getSeat())
							aiApp.addMoney(Integer.parseInt(line.substring(8)));
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
						aiApp.makeMove(call, raise, maxRaise, buttons);
					}
					else if (line.startsWith("NEXT ROUND"))
					{
						aiApp.getPlayer().getPlayerPot().newRound();
					}
					else if (line.startsWith("HOST"))
					{
						aiApp.getPlayer().setIsHost(Boolean.parseBoolean(line.substring(5)));
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
					aiApp.getSocket().close();
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
