/**
 * 
 */
package chat.rest.api.service.impl;

import static org.junit.Assert.*;

import java.io.File;
import java.util.Map;

import org.junit.Test;

import chat.rest.api.ChatBot.API;
import chat.rest.api.service.core.VelocitySupport;
import chat.rest.api.service.impl.ArenaService.ArenaHandler;

/**
 * 
 */
public class ArenaServiceTest {

	@Test
	public void test() throws Exception {
		ArenaService arenaService = new ArenaService();
		
		String message = VelocitySupport.toString(new File("scripts/chat_gpt/페르소나/페르소나"),
				Map.of("content", "닭이 먼저인지 달걀이 먼저인가?"));
		
		arenaService.send(message, new ArenaHandler() {
			
			@Override
			public void onFinish(API api, String result, long costTime) {
				String msg = String.format(""" 
						############################################
						%s
						%s
						%d
						""", api.name(), result, costTime);
				System.out.println(msg);
			}
		});
	}

}
