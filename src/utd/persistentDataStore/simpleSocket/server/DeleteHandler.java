package utd.persistentDataStore.simpleSocket.server;

import java.io.IOException;

import org.apache.log4j.Logger;

import utd.persistentDataStore.simpleSocket.FileUtil;
import utd.persistentDataStore.simpleSocket.StreamUtil;

public class DeleteHandler extends Handler {
	private static Logger logger = Logger.getLogger(DeleteHandler.class);

	@Override
	protected void run() throws IOException {
		String name = StreamUtil.readLine(inputStream);
		logger.debug("name: " + name);
		String responseCode = "";
		try {
			FileUtil.deleteData(name);
		} catch (ServerException e) {
			responseCode = e.getMessage();
		}
		if (responseCode.isEmpty()) {
			StreamUtil.writeLine("ok", outputStream);
			logger.debug("Finished writing message");
		} else {
			StreamUtil.writeLine(responseCode, outputStream);
			logger.debug("Finished writing error message");
		}
	}

}
