/**
 * 
 */
package chat.rest.api.service.impl;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.Map;

import org.junit.Test;

import chat.rest.api.ResponseModelDVO;
import chat.rest.api.ResponseModelDVO.Message;
import chat.rest.api.service.core.GTPRequest;
import chat.rest.api.service.core.TTSGTPRequest;
import chat.rest.api.service.core.TextGTPMessage;

/**
 * 
 */
public class TextToSpeechGptServiceTest {

	@Test
	public void textToSpeechTest() throws Exception {
		String message;
		message = """
				HI MSDSDSDSDSDSASD
				""";
		createTTS("20240605", message);
	}

	/**
	 * @param message
	 * @return
	 * @throws Exception
	 */
	private File createTTS(String outputSimpleName, String message) throws Exception {
		TextToSpeechGptService arenaService = new TextToSpeechGptService();

		TTSGTPRequest tts = TTSGTPRequest.ttsModel();
		tts.setMessage(new TextGTPMessage(message));
		byte[] send = arenaService.send(tts);
		String outFileName = outputSimpleName + "." + tts.getResponseFormat();
		try (FileOutputStream fileOutputStream = new FileOutputStream(outFileName)) {
			fileOutputStream.write(send);
			fileOutputStream.flush();
		}
		return new File(outFileName);
	}

	@Test
	public void textToSpeechTest2() throws Exception {
//		ChatBotService newBotService = ChatBot.newBotService(API.GTP_4_o);
		ChatGpt4oService newBotService = new ChatGpt4oService() {

			@Override
			public Map<String, String> getSystemRule() {
				// TODO Auto-generated method stub
				return super.getSystemRule();
			}

		};

		// chatgpt template binding
		String message = "간단한 전래동화 하나 만들어줘";
		System.out.println("#################");
		System.out.println(message);
		System.out.println("#################");
		String send = newBotService.send(message);
		System.out.println("#################");
		System.out.println(send);
		System.out.println("#################");
		// json 형태 결과
		Message message2 = ResponseModelDVO.fromGtpResultMessage(send).getChoices().get(0).getMessage();
		String content = message2.getContent();
		System.out.println(content);

		File tts;
		try {
			tts = createTTS("textToSpeechTest2",content);
			Desktop desktop = Desktop.getDesktop();
			desktop.open(tts);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Get json format.
		message = "간단한 전래동화 하나 만들어줘, 결과는 json 형태이고 키는 message";
		GTPRequest gpt4oModel = GTPRequest.gpt4oModel();
		gpt4oModel.setResponseFormat(GTPRequest.RESPONSE_FORMAT_JSON);
		gpt4oModel.setList(Arrays.asList(TextGTPMessage.of(message)));
		System.out.println("#################");
		System.out.println(message);
		System.out.println("#################");
		send = newBotService.send(gpt4oModel);
		System.out.println("#################");
		System.out.println(send);
		System.out.println("#################");
		// json 형태 결과
		message2 = ResponseModelDVO.fromGtpResultMessage(send).getChoices().get(0).getMessage();
		String content2 = message2.getContent();
		System.out.println(content2);

	}

}
