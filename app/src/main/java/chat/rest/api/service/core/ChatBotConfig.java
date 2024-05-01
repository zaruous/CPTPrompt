package chat.rest.api.service.core;

import java.util.Properties;

import lombok.Data;

@Data
public class ChatBotConfig {
//	private String rootUrl;
	private Properties config;
//	private String proxyIp;
//	private int proxyPort;
	
	public String getRootUrl() {
		return getConfig().getProperty("rootUrl");
	}
	public String getModel() {
		return getConfig().getProperty("model");
	}
}
