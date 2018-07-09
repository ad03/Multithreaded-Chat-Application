package chatApplication;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client 
{
	final static int port=1264;
	public static void main(String[] args) throws UnknownHostException, IOException 
	{
		Scanner scan=new Scanner(System.in);
		Socket connection=new Socket("127.0.0.1", port);
		DataInputStream input=new DataInputStream(connection.getInputStream());
		DataOutputStream output=new DataOutputStream(connection.getOutputStream());
		
		Thread sendMessage=new Thread(new Runnable() 
		{
			public void run() 
			{
				while(true)
				{
					String messageToBeSend=scan.nextLine();
					try 
					{
						output.writeUTF(messageToBeSend);
					} 
					catch (IOException e) 
					{
						e.printStackTrace();
					}
				}
			}
		});
		
		Thread receiveMessage=new Thread(new Runnable() {
			public void run() 
			{
				while(true)
				{
					try 
					{
						String messageToBeRead=input.readUTF();
						System.out.println(""+messageToBeRead);
					}
					catch (IOException e) 
					{
						e.printStackTrace();
					}
				}
			}
		});
		
		sendMessage.start();
		receiveMessage.start();
		
	}
}
