/**
 * 
 */
package chat.rest.api;

import java.io.IOException;

import chat.rest.api.service.core.ChatBotService;
import chat.rest.api.service.impl.ChatGpt3Service;
import chat.rest.api.service.impl.Ollama3Service;

/**
 * 
 */
public class ChatBot {

	public enum API {
		GTP_3_5, OLLAMA_3
	}

	/**
	 * @param api
	 * @return
	 * @throws IOException
	 */
	public static ChatBotService newBotService(API api) throws Exception {
		ChatBotService chatGpt3Service = null;
		switch (api) {
		case GTP_3_5:
			chatGpt3Service = new ChatGpt3Service();
			break;
		case OLLAMA_3:
			chatGpt3Service = new Ollama3Service();
		}
		return chatGpt3Service;
	}
}
