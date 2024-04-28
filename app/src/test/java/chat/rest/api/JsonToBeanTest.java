/**
 * 
 */
package chat.rest.api;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import com.google.gson.Gson;

import chat.rest.api.ChatBot.API;
import chat.rest.api.service.core.ChatBotService;
import chat.rest.api.service.core.ResponseModelDVO;
import chat.rest.api.service.core.VelocitySupport;

/**
 * 
 */
public class JsonToBeanTest {

	@Test
	public void test() throws Exception {

		ChatBotService newBotService = ChatBot.newBotService(API.GTP_3_5);

		// chatgpt template binding
		String message = VelocitySupport.toString(new File("scripts/chat_gpt/code_gen/model_generate"),
				Map.of("json", IOUtils.toString(JsonToBeanTest.class.getResourceAsStream("sample.json"),StandardCharsets.UTF_8)
						,"fileName", "Sample.java"
						));

		System.out.println("#################");
		System.out.println(message);
		System.out.println("#################");
		String send = newBotService.send(message);
		System.out.println("#################");
		System.out.println(send);
		System.out.println("#################");
		Gson gson = new Gson();
//		HashMap fromJson = gson.fromJson(send, HashMap.class);
//		System.out.println(((Map) ((Map) ((List) fromJson.get("choices")).get(0)).get("message")).get("content"));
		ResponseModelDVO fromJson = gson.fromJson(send, ResponseModelDVO.class);
		System.out.println(fromJson.getChoices().get(0).getMessage().getContent());
//		System.out.println(((Map) ((Map) ((List) fromJson.get("choices")).get(0)).get("message")).get("content"));
		
	}
}
