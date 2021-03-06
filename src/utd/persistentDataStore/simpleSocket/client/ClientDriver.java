package utd.persistentDataStore.simpleSocket.client;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.io.Serializable;

import org.apache.log4j.Logger;

public class ClientDriver
{
	private static Logger logger = Logger.getLogger(ClientDriver.class);
	static public final int port = 10023;
	
	public static void main (String args[]) throws ClientException
	{
		try
		{
			InetAddress ipAddress = InetAddress.getByName("localhost");
			Client client = new Client(ipAddress, port);
			
			// Test echo just to make sure all the connections are working
			logger.debug("Testing echo...");
			client.echo("Hello World!");
			
			// Test Write
			logger.debug("Testing write...");
			String data = "This is a string that I converted into a byte array for the purpose of testing the write functionality. I will be stored persistently in a file called writeFile.txt";
			byte[] byteData = data.getBytes();
			client.write("writeFile.txt", byteData);
			
			// Test Read
			logger.debug("Testing read...");
			client.read("readFile.txt");
			
			// Test Delete
			logger.debug("Testing delete...");
			client.delete("writeFile.txt");
			logger.debug("writeFile.txt should now be deleted");
			
			// Test Directory
			logger.debug("Testing directory...");
			client.directory();
			
			/* Extra Credit */
			// Test Write Object
			logger.debug("Testing writeObject...");
			SimpleObject obj = new SimpleObject("Test String", 12, 33.33);
			client.writeObject("Simple Object", obj);
			
			// Test Read Object
			logger.debug("Testing readObject...");
			client.readObject("Simple Object");
			
			 
		}
		catch (UnknownHostException ex)
		{
			throw new ClientException(ex.getMessage(), ex);
		}
	}
}

class SimpleObject implements Serializable
{
	String str;
	int integer;
	double dub;
	
	public SimpleObject(String str, int integer, double dub)
	{
		this.str = str;
		this.integer = integer;
		this.dub = dub;
	}
	
	public String getStr()
	{
		return str;
	}
	
	public int getInteger()
	{
		return integer;
	}
	
	public double getDub()
	{
		return dub;
	}
	
	public void setStr(String str)
	{
		this.str = str;
	}
	
	public void setInteger(int integer)
	{
		this.integer = integer;
	}
	
	public void setDub(double dub)
	{
		this.dub = dub;
	}
}