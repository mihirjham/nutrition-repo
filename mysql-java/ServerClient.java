import java.net.Socket;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;

class ServerClient
{
	public static void main(String args[])
	{
		Socket outgoing;

		try
		{
			outgoing = new Socket("moore07.cs.purdue.edu", 3001);
			PrintWriter out = new PrintWriter(outgoing.getOutputStream(), true);
			//String command = "GET-USER-INFO|root|password|mihir";
			//out.print(command);
			//out.close();
			
			BufferedReader in = new BufferedReader (new InputStreamReader(outgoing.getInputStream()));
			BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

			String userInput;
			while((userInput = stdIn.readLine()) != null)
			{
				out.println(userInput);
				String serverOutput;
				while((serverOutput = in.readLine()) != null)
					System.out.println(serverOutput);
			}
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
		}
	}
}
