/**
 * 
 */
package chat.rest.api.service.impl;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Map;

import org.junit.Test;

import chat.rest.api.ChatBot;
import chat.rest.api.ResponseModelDVO;
import chat.rest.api.ResponseModelDVO.Message;
import chat.rest.api.ChatBot.API;
import chat.rest.api.service.core.ChatBotService;
import chat.rest.api.service.core.TTSGTPRequest;
import chat.rest.api.service.core.TextGTPMessage;
import chat.rest.api.service.core.VelocitySupport;

/**
 * 
 */
public class TextToSpeechGptServiceTest {

	@Test
	public void textToSpeechTest() throws Exception {
		String message;
		message = """
				안녕하세요 김영준입니다. 만나서 반갑습니다.!
				""";
		createTTS(message);
	}

	/**
	 * @param message
	 * @return 
	 * @throws Exception
	 */
	private File createTTS(String message) throws Exception {
		TextToSpeechGptService arenaService = new TextToSpeechGptService();

		TTSGTPRequest tts = TTSGTPRequest.ttsModel();
		tts.setMessage(new TextGTPMessage(message));
		byte[] send = arenaService.send(tts);
		String outFileName = "test." + tts.getResponseFormat();
		try (FileOutputStream fileOutputStream = new FileOutputStream(outFileName)) {
			fileOutputStream.write(send);
			fileOutputStream.flush();
		}
		return new File(outFileName);
	}

	@Test
	public void textToSpeechTest2() throws Exception {
		ChatBotService newBotService = ChatBot.newBotService(API.GTP_4_o);

		// chatgpt template binding
		String message = "간단한 전래동화 하나 만들어줘";

		System.out.println("#################");
		System.out.println(message);
		System.out.println("#################");
		String send = newBotService.send(message);
		System.out.println("#################");
		System.out.println(send);
		System.out.println("#################");
		
		Message message2 = ResponseModelDVO.fromGtpResultMessage(send).getChoices().get(0).getMessage();
		File tts = createTTS(message2.getContent());
		
		
		Desktop.getDesktop().open(tts);
		
	}

}
