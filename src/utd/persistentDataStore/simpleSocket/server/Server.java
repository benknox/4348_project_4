package utd.persistentDataStore.simpleSocket.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.log4j.Logger;

import utd.persistentDataStore.simpleSocket.StreamUtil;

public class Server
{
	private static Logger logger = Logger.getLogger(Server.class);

	static public final int port = 10023;

	public void startup() throws IOException
	{
		logger.debug("Starting Service at port " + port);

		InputStream inputStream = null;
		OutputStream outputStream = null;

		ServerSocket serverSocket = new ServerSocket(port);
		
		while (true) {
			try {
				logger.debug("Waiting for request");
				Socket clientSocket = serverSocket.accept();

				logger.debug("Request received");
				inputStream = clientSocket.getInputStream();
				outputStream = clientSocket.getOutputStream();
				Handler handler = parseRequest(inputStream);
				
				logger.debug("Processing Request: " + handler);
				handler.setInputStream(inputStream);
				handler.setOutputStream(outputStream);
				handler.run();
				
				StreamUtil.closeSocket(inputStream);
			}
			catch (ServerException ex) {
				logger.info("Problem processing request. " + ex.getMessage());
				StreamUtil.sendError(ex.getMessage(), outputStream);
				StreamUtil.closeSocket(inputStream);
			}
			catch (IOException ex) {
				logger.error("Exception while processing request. " + ex.getMessage());
				ex.printStackTrace();
				StreamUtil.closeSocket(inputStream);
			}
		}
	}

	private Handler parseRequest(InputStream inputStream) throws IOException, ServerException
	{
		String commandString = StreamUtil.readLine(inputStream);

		if ("echo".equalsIgnoreCase(commandString)) {
			Handler handler = new EchoHandler();
			return handler;
		}
		else if ("reverse".equalsIgnoreCase(commandString)) {
			Handler handler = new ReverseHandler();
			return handler;
		}
		else if ("read".equalsIgnoreCase(commandString)) {
			Handler handler = new ReadHandler();
			return handler;
		}
		else if ("readObject".equalsIgnoreCase(commandString)) {
			Handler handler = new ReadObjectHandler();
			return handler;
		}
		else if ("write".equalsIgnoreCase(commandString)) {
			Handler handler = new WriteHandler();
			return handler;
		}
		else if ("writeObject".equalsIgnoreCase(commandString)) {
			Handler handler = new WriteObjectHandler();
			return handler;
		}
		else if ("delete".equalsIgnoreCase(commandString)) {
			Handler handler = new DeleteHandler();
			return handler;
		}
		else if ("directory".equalsIgnoreCase(commandString)) {
			Handler handler = new DirectoryHandler();
			return handler;
		}
		else {
			throw new ServerException("Unknown Request: " + commandString);
		}
	}

	public static void main(String args[])
	{
		try {
			Server simpleServer = new Server();
			simpleServer.startup();
		}
		catch (IOException ex) {
			logger.error(ex);
		}
	}
}
