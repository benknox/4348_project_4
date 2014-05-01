package utd.persistentDataStore.simpleSocket.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.List;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import utd.persistentDataStore.simpleSocket.StreamUtil;

public class Client
{
	private static Logger logger = Logger.getLogger(Client.class);
	
	private InetAddress address;
	private int port;

	public Client(InetAddress address, int port)
	{
		this.address = address;
		this.port = port;
	}

	/**
	 * Sends the given string to the server which will echo it back
	 */
	public String echo(String message) throws ClientException
	{
		try {
			logger.debug("Opening Socket");
			Socket socket = new Socket();
			SocketAddress saddr = new InetSocketAddress(address, port);
			socket.connect(saddr);
			InputStream inputStream = socket.getInputStream();
			OutputStream outputStream = socket.getOutputStream();
			
			logger.debug("Writing Message");
			StreamUtil.writeLine("echo\n", outputStream);
			StreamUtil.writeLine(message, outputStream);
			
			logger.debug("Reading Response");
			String result = StreamUtil.readLine(inputStream);
			logger.debug("Response " + result);
			
			return result;
		}
		catch (IOException ex) {
			throw new ClientException(ex.getMessage(), ex);
		}
	}
		
	/**
	 * Sends the given string to the server which will echo it back
	 */
	public String reverse(String message) throws ClientException
	{
		try {
			logger.debug("Opening Socket");
			Socket socket = new Socket();
			SocketAddress saddr = new InetSocketAddress(address, port);
			socket.connect(saddr);
			InputStream inputStream = socket.getInputStream();
			OutputStream outputStream = socket.getOutputStream();
			
			logger.debug("Writing Message");
			StreamUtil.writeLine("reverse\n", outputStream);
			StreamUtil.writeLine(message, outputStream);
			
			logger.debug("Reading Response");
			String result = StreamUtil.readLine(inputStream);
			logger.debug("Response " + result);
			
			return result;
		}
		catch (IOException ex) {
			throw new ClientException(ex.getMessage(), ex);
		}
	}
	
	/**
	 * Send the name of a file to the server, then receive the contents of the file
	 */
	public byte[] read(String name) throws ClientException
	{
		try {
			logger.debug("Opening Socket");
			Socket socket = new Socket();
			SocketAddress saddr = new InetSocketAddress(address, port);
			socket.connect(saddr);
			InputStream inputStream = socket.getInputStream();
			OutputStream outputStream = socket.getOutputStream();
			
			logger.debug("Passing the request message");
			// Pass the operation command
			StreamUtil.writeLine("read\n", outputStream);
			// Pass the file name
			StreamUtil.writeLine(name, outputStream);
			
			logger.debug("Reading the response message");
			String response = StreamUtil.readLine(inputStream);
			if ( "ok".equalsIgnoreCase(response) )
			{	
				logger.debug("Response Code: " + response);
				int length = Integer.parseInt( StreamUtil.readLine(inputStream) );
				logger.debug("Size in bytes: " + length);
				byte[] result = StreamUtil.readData(length, inputStream);
				logger.debug("Response: " + new String(result) );
				
				return result;
			}
			else
				throw new ClientException(response);
		}
		catch (IOException ex) {
			throw new ClientException(ex.getMessage(), ex);
		}
	}
	
	/**
	 * Send the server a byte array to be written to a given file
	 */
	public void write(String name, byte[] data) throws ClientException
	{
		try {
			logger.debug("Opening Socket");
			Socket socket = new Socket();
			SocketAddress saddr = new InetSocketAddress(address, port);
			socket.connect(saddr);
			InputStream inputStream = socket.getInputStream();
			OutputStream outputStream = socket.getOutputStream();
			
			logger.debug("Passing the request message");
			// Pass the operation command
			StreamUtil.writeLine("write\n", outputStream);
			// Pass the file name
			StreamUtil.writeLine(name, outputStream);
			// Pass the size of the byte array
			StreamUtil.writeLine( ( (Integer)data.length ).toString(), outputStream);
			// Pass the byte array
			StreamUtil.writeData(data, outputStream);
			
			logger.debug("Reading the response message");
			String response = StreamUtil.readLine(inputStream);
			if ( "ok".equalsIgnoreCase(response) )
			{	
				logger.debug("Response: " + response );
			}
			else
				throw new ClientException(response);
		}
		catch (IOException ex) {
			throw new ClientException(ex.getMessage(), ex);
		}
	}
	
	/**
	 * Tell the server to delete a given file
	 */
	public void delete(String name) throws ClientException
	{
		try {
			logger.debug("Opening Socket");
			Socket socket = new Socket();
			SocketAddress saddr = new InetSocketAddress(address, port);
			socket.connect(saddr);
			InputStream inputStream = socket.getInputStream();
			OutputStream outputStream = socket.getOutputStream();
			
			logger.debug("Passing the request message");
			// Pass the operation command
			StreamUtil.writeLine("delete\n", outputStream);
			// Pass the file name
			StreamUtil.writeLine(name, outputStream);
			
			logger.debug("Reading the response message");
			String response = StreamUtil.readLine(inputStream);
			if ( "ok".equalsIgnoreCase(response) )
			{	
				logger.debug("Response Code: " + response );
			}
			else
				throw new ClientException(response);
		}
		catch (IOException ex) {
			throw new ClientException(ex.getMessage(), ex);
		}
	}
	
	/**
	 * 
	 */
	public List<String> directory() throws ClientException
	{
		List<String> result = new ArrayList<String>();
		try {
			logger.debug("Opening Socket");
			Socket socket = new Socket();
			SocketAddress saddr = new InetSocketAddress(address, port);
			socket.connect(saddr);
			InputStream inputStream = socket.getInputStream();
			OutputStream outputStream = socket.getOutputStream();
			
			logger.debug("Passing the request message");
			// Pass the operation command
			StreamUtil.writeLine("directory\n", outputStream);
			
			logger.debug("Reading the response message");
			String response = StreamUtil.readLine(inputStream);
			if ( "ok".equalsIgnoreCase(response) )
			{	
				logger.debug("Response Code: " + response);
				int length = Integer.parseInt( StreamUtil.readLine(inputStream) );
				logger.debug("Number of file names: " + length);
				
				for (int i=0; i < length; i++)
				{
					result.add(i, StreamUtil.readLine(inputStream) );
					logger.debug("Name" + (i+1) + ": " + result.get(i) );					
				}
				
				return result;
			}
			else
				throw new ClientException(response);
		}
		catch (IOException ex) {
			throw new ClientException(ex.getMessage(), ex);
		}
	}

}
