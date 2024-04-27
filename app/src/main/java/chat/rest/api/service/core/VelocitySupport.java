package chat.rest.api.service.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

public class VelocitySupport {
	/**
	 * @param script
	 * @param param
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static String toString(File script, Map<String, Object> param) throws FileNotFoundException, IOException {
		String sc = "";
		try (Reader r = new FileReader(script)) {
			sc = IOUtils.toString(r);
		}
		return toString(sc, param);
	}

	public static String toString(String script, Map<String, Object> param) {

		VelocityContext context = new VelocityContext(param);

		Properties properties = new Properties();
		properties.setProperty("input.encoding", "UTF-8");
		properties.setProperty("output.encoding", "UTF-8");

		VelocityEngine engine = new VelocityEngine(properties);

		StringWriter writer = new StringWriter();
		engine.evaluate(context, writer, "DaoWizard", new StringReader(script));
		return writer.toString();
	}
}
