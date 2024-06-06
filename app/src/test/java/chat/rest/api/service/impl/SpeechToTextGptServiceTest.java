/**
 * 
 */
package chat.rest.api.service.impl;

import java.nio.file.Path;

import org.junit.Test;

/**
 * 
 */
public class SpeechToTextGptServiceTest {

	/**
	 * Test method for {@link chat.rest.api.service.impl.SpeechToTextGptService#send(byte[])}.
	 * @throws Exception 
	 */
	@Test
	public void speechToTextTest() throws Exception {
		SpeechToTextGptService service = new SpeechToTextGptService();
		String send = service.send(Path.of("0216660509_240606_232733.mp3"));
		System.out.println(send);
	}

}
