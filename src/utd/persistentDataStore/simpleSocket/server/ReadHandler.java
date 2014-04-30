package utd.persistentDataStore.simpleSocket.server;

import java.io.IOException;

import org.apache.log4j.Logger;

import utd.persistentDataStore.simpleSocket.FileUtil;
import utd.persistentDataStore.simpleSocket.StreamUtil;

public class ReadHandler extends Handler {
	private static Logger logger = Logger.getLogger(ReadHandler.class);

	@Override
	protected void run() throws IOException {
		String name = StreamUtil.readLine(inputStream);
		logger.debug("name: " + name);
		byte[] data = null;
		String responseCode = "";
		try {
			data = FileUtil.readData(name);
		} catch (ServerException e) {
			responseCode = e.getMessage();
		}
		if (responseCode.isEmpty()) {
			StreamUtil.writeLine("ok", outputStream);
			StreamUtil.writeLine(((Integer) data.length).toString(),
					outputStream);
			StreamUtil.writeData(data, outputStream);
			logger.debug("Finished writing message");
		} else {
			StreamUtil.writeLine(responseCode, outputStream);
			logger.debug("Finished writing error message");
		}
	}

}
