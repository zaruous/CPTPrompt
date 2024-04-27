/**
 * 
 */
package chat.rest.api;

import java.io.IOException;

import chat.rest.api.service.core.ChatBotService;
import chat.rest.api.service.impl.ChatGpt3Service;

/**
 * 
 */
public class ChatBot {

	public enum API {
		GTP_3_5
	}

	public static ChatBotService newBotService(API api) throws IOException {

		ChatGpt3Service chatGpt3Service = null;
		switch (api) {
		case GTP_3_5:
			chatGpt3Service = new ChatGpt3Service();
		}
		return chatGpt3Service;
	}
}
