package chat.rest.api.service.core;

import java.io.File;
import java.io.Serializable;

public class SpeechRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String model;
	private File file;

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

}
