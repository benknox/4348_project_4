package utd.persistentDataStore.simpleSocket.server;

public class ServerException extends Exception
{
	public ServerException(String msg) {
		super(msg);
	}

	public ServerException(String msg, Throwable ex) {
		super(msg, ex);
	}

}
