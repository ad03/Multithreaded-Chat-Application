package chatApplication;

public class TestServer 
{

	public static void main(String[] args) 
	{
		int port=1264;
		Server myServer=new Server(port);
		myServer.initServer();
	}

}
