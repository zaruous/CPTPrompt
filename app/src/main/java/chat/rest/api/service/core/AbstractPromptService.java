package chat.rest.api.service.core;

import java.io.IOException;
import java.util.Map;

public abstract class AbstractPromptService implements ChatBotService {

	private ChatBotConfig config;

	public AbstractPromptService() throws Exception {
		this.config = createConfig();
	}

	public ChatBotConfig getConfig() {
		return config;
	}

	/**
	 * @return
	 * @throws IOException
	 */
	public abstract ChatBotConfig createConfig() throws Exception;

	public Map<String, String> systemRole() {
		return Map.of("role", "system", "content", "Write English");
	}

	public Map<String, String> assistant() {
		return Map.of("role", "assistant", "content", "");
	}

}
