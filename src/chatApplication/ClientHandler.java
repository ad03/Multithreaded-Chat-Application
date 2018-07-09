package chatApplication;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.StringTokenizer;

public class ClientHandler implements Runnable 
{
	Scanner scan=new Scanner(System.in);
	private String nameOfClient;
	private Socket connection;
	private DataInputStream input;
	private DataOutputStream output;
	private boolean isLoggedIn;
	public ClientHandler(Socket connection,String nameOfClient,
			DataInputStream input,DataOutputStream output) 
	{
		this.connection=connection;
		this.nameOfClient=nameOfClient;
		this.input=input;
		this.output=output;
		this.isLoggedIn=true;
	}
	private String messageFromClient;
	public void run() 
	{
		while(true)
		{
			try 
			{
				messageFromClient=input.readUTF();
				System.out.println(nameOfClient+": "+messageFromClient);
				if(messageFromClient.equals("exit"))
				{
					this.isLoggedIn=false;
					this.connection.close();
					break;
				}
				StringTokenizer st=new StringTokenizer(messageFromClient,":");
				String receiever=st.nextToken();
				String message=st.nextToken();
				for(ClientHandler rc:Server.clients)
				{
					if(rc.nameOfClient.equals(receiever) && rc.isLoggedIn==true)
					{
						rc.output.writeUTF(this.nameOfClient+": "+message);
					}
				}
			}
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
		try 
		{
			this.input.close();
			this.output.close();
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}	
}