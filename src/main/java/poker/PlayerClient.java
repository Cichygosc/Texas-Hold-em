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
			output.println("NEW PLAYER " + clientApp.getThisPlayer().getName());
			init();
			start();
		} catch (IOException e) {
			clientApp.showMessage("An error ocurred in PlayerClient " + e);
			throw e;
		}
	}
	
	private void init()
	{
		String line;
		while (true)
		{
			try
			{
				line = input.readLine();
				if (line.startsWith("END INIT"))
					break;
				if (line.startsWith("INIT PLACES"))
				{
					line = line.substring(12);
					String num[] = line.split(" ");
					for (String n: num)
					{
						if (n.equals(""))
							break;
						clientApp.addTakenSeat(Integer.parseInt(n));
					}
				}
			}
			catch(IOException e)
			{
				clientApp.showMessage("Error while initializing client " + e);
				System.exit(-1);
			}
		}
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
					if (line.startsWith("MESSAGE"))
					{
						clientApp.showMessage(line.substring(8));
					}
					else if (line.startsWith("SEAT"))
					{
						clientApp.addTakenSeat(Integer.parseInt(line.substring(5)));
					}
					else if (line.startsWith("PLAYER LEFT"))
					{
						System.out.println("SEAT " + Integer.parseInt(line.substring(12)));
						clientApp.removeTakenSeat(Integer.parseInt(line.substring(12)));
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
					System.out.println("Couldnt close connection " + e);
				}
			}
		}

	}

}
