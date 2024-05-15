/**
 * 
 */
package chat.rest.api;

import java.io.File;
import java.util.Arrays;

import org.junit.Test;

import chat.rest.api.ChatBot.API;
import chat.rest.api.service.core.ChatBotService;
import chat.rest.api.service.core.GTPRequest;
import chat.rest.api.service.core.ImageGTPMessage;
import chat.rest.api.service.core.TextGTPMessage;
import chat.rest.api.service.impl.ChatGpt4oService;

/**
 * 이미지를 업로드하고 이미지의 내용을 설명하는 요청.
 * 
 */
public class ImageRequestTest {

	@Test
	public void imageRequestTest() throws Exception {
		ChatGpt4oService gtp4oService = new ChatGpt4oService();

		ImageGTPMessage imageGTPMessage = new ImageGTPMessage(new File("테스트 이미지.png"));
		TextGTPMessage textGTPMessage = new TextGTPMessage("이미지에 대한 설명");

		GTPRequest request = GTPRequest.gpt4oModel();
		request.setSystemMessage("그림을 설명합니다.");
		request.setList(Arrays.asList(textGTPMessage, imageGTPMessage));

		String message = gtp4oService.send(request);
		System.out.println(message);

	}
}
