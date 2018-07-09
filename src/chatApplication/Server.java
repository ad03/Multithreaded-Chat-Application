package chatApplication;

import java.io.*;
import java.net.*;
import java.util.Vector;

public class Server
{
	private ServerSocket server;
	private Socket connection;
	private DataInputStream input;
	private DataOutputStream output;
	static Vector<ClientHandler> clients=new Vector<>();
	static int countOfClients=0;
	public Server(int port) 
	{
		try 
		{
			server=new ServerSocket(port);
			System.out.println("server is listening on port "+port);
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
	}
	public void initServer()
	{
		while(true)
		{
			try 
			{
				connection=server.accept();
				System.out.println("New user request received");
				//Initializing IO streams
				input=new DataInputStream(connection.getInputStream());
				output=new DataOutputStream(connection.getOutputStream());
				String nameOfClient=input.readUTF();
				ClientHandler handler=new ClientHandler(connection,nameOfClient,input,output);
				Thread newThread=new Thread(handler);
				clients.addElement(handler);
				newThread.start();
			}
			catch (IOException e) 
			{
				e.printStackTrace();
			}
			
		}
	}
}
