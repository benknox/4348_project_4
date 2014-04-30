package utd.persistentDataStore.simpleSocket.server;

import java.io.IOException;
import java.io.ObjectOutputStream;

import org.apache.log4j.Logger;

import utd.persistentDataStore.simpleSocket.FileUtil;
import utd.persistentDataStore.simpleSocket.StreamUtil;

public class ReadObjectHandler extends Handler {
	private static Logger logger = Logger.getLogger(ReadObjectHandler.class);

	@Override
	protected void run() throws IOException {
		String responseCode = "";
		Object data = null;
		String name = StreamUtil.readLine(inputStream);
		ObjectOutputStream oos = new ObjectOutputStream(outputStream);
		try {
			data = FileUtil.readObject(name);
		} catch (ServerException e) {
			responseCode = e.getMessage();
		}
		if (responseCode.isEmpty()) {
			StreamUtil.writeLine("ok", outputStream);
			oos.writeObject(data);
			oos.flush();
			logger.debug("Finished writing message");
		} else {
			StreamUtil.writeLine(responseCode, outputStream);
			logger.debug("Finished writing error message");
		}
	}

}
