package utd.persistentDataStore.simpleSocket;

import static org.junit.Assert.assertEquals;

import java.net.InetAddress;

import org.junit.Test;

import utd.persistentDataStore.simpleSocket.client.Client;
import utd.persistentDataStore.simpleSocket.server.Server;

public class DatastoreClientTestCase
{
	@Test
	public void testEcho() throws Exception
	{
		byte byteAddr[] = { 127, 0, 0, 1 };
		InetAddress address = InetAddress.getByAddress(byteAddr);
		int port = Server.port;
		Client client = new Client(address, port);

		String msg = "Is this the time?";
		String result = client.echo(msg);
		assertEquals(msg, result);
	}

	@Test
	public void testReverse() throws Exception
	{
		byte byteAddr[] = { 127, 0, 0, 1 };
		InetAddress address = InetAddress.getByAddress(byteAddr);
		int port = Server.port;
		Client client = new Client(address, port);

		String result = client.reverse("Is this the time?");
		assertEquals("?emit eht siht sI", result);
		
	}

}
