package utd.persistentDataStore.simpleSocket.server;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;

import utd.persistentDataStore.simpleSocket.FileUtil;
import utd.persistentDataStore.simpleSocket.StreamUtil;

public class DirectoryHandler extends Handler {
	private static Logger logger = Logger.getLogger(DirectoryHandler.class);
	
	@Override
	protected void run() throws IOException {
		String responseCode = "";
		List<String> files = null;
		try {
			files = FileUtil.directory();
			logger.debug("found names of files in directory");
		} catch (ServerException e) {
			responseCode = e.getMessage();
		}
		if (files != null) {
			StreamUtil.writeLine("ok", outputStream);
			StreamUtil.writeLine(((Integer) files.size()).toString(),
					outputStream);
			for (String string : files) {
				StreamUtil.writeLine(string, outputStream);
			}
			logger.debug("Finished writing message");
		} else {
			StreamUtil.writeLine(responseCode, outputStream);
			logger.debug("Finished writing error message");
		}
	}

}
