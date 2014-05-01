package utd.persistentDataStore.simpleSocket.server;

import java.io.IOException;

import org.apache.log4j.Logger;

import utd.persistentDataStore.simpleSocket.FileUtil;
import utd.persistentDataStore.simpleSocket.StreamUtil;

public class WriteHandler extends Handler {
	private static Logger logger = Logger.getLogger(WriteHandler.class);

	@Override
	protected void run() throws IOException {
		String name = StreamUtil.readLine(inputStream);
		logger.debug("name: " + name);
		int sizeInBytes = Integer.parseInt(StreamUtil.readLine(inputStream));
		logger.debug("size in bytes: " + sizeInBytes);
		byte[] data = StreamUtil.readData(sizeInBytes, inputStream);
		logger.debug("data: " + data);
		String responseCode = "";
		try {
			FileUtil.writeData(name, data);
			logger.debug("Finished writing data");
		} catch (IOException e) {
			responseCode = e.getMessage();
			logger.debug("Error writing message");
		}
		if (responseCode.isEmpty()) {
			StreamUtil.writeLine("ok", outputStream);
		} else {
			StreamUtil.writeLine(responseCode, outputStream);
		}
	}

}
