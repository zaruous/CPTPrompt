package chat.persona;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.google.gson.Gson;

import chat.rest.api.ChatBot;
import chat.rest.api.ChatBot.API;
import chat.rest.api.service.core.ChatBotService;
import chat.rest.api.service.core.Rules;
import chat.rest.api.service.core.VelocitySupport;

public class 페르소나_MES_테스트 {

	@Test
	public void test() throws Exception {
		ChatBotService newBotService = ChatBot.newBotService(API.GTP_4_O, Rules.newInstance().assistantRuleContent(
				"주어진 입력에 대해 다음과 같은 일련의 처리를 순차적으로 수행해주세요.\r\n" + "\n다음 과정으로 진행하기 전에 작업을 멈추고 피드백을 요청해주세요."));

		// chatgpt template binding
		String message = VelocitySupport.toString(new File("scripts/chat_gpt/페르소나/페르소나_MES"),
				Map.of("content", """
						우리회사는 MES 솔루션 개발 기업이다.
						MES를 제조, 의료, 화학등 다양한 분야에 적용할 수 있도록 컨설팅 개발을 선도하는 기업이다.
						
						AI가 발전하는 현실을 반영하여 회사에서도 이에 발맞춰 변화의 움직임이 필요하다.
						우리 회사가 발전하는 AI 기술을 적용하려면 어떠한 움직임을 취해야할까?
						
						"""));

		System.out.println("#################");
		System.out.println(message);
		System.out.println("#################");
		String send = newBotService.send(message);
		System.out.println("#################");
		System.out.println(send);
		System.out.println("#################");
		Gson gson = new Gson();
		HashMap fromJson = gson.fromJson(send, HashMap.class);
		System.out.println(((Map) ((Map) ((List) fromJson.get("choices")).get(0)).get("message")).get("content"));

	}
}
