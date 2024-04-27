/**
 * 
 */
package chat.rest.api.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Stream;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;

import chat.rest.api.service.core.ChatBotConfig;
import chat.rest.api.service.core.ChatBotService;
import chat.rest.api.service.core.ResponseHandler;
import chat.rest.api.service.core.VirtualPool;

/**
 * 
 */
public class ChatGpt3Service implements ChatBotService {

	private ChatBotConfig config;

	public ChatGpt3Service() throws IOException {
		this.config = createConfig();
	}

	ChatBotConfig createConfig() throws IOException {
		ChatBotConfig chatBotConfig = new ChatBotConfig();
		chatBotConfig.setRootUrl("https://api.openai.com/v1/chat/completions");

		Properties properties = new Properties();

		try (InputStream in = new FileInputStream(new File("chat.gpt.properties"))) {
			properties.load(in);
		}

		chatBotConfig.setConfig(properties);
		return chatBotConfig;
	}

	public Map<String, String> systemRole() {
		return Map.of("role", "system", "content", "Write English");
	}

	public Map<String, String> assistant() {
		return Map.of("role", "assistant", "content", "");
	}

	@Override
	public void send(String message, ResponseHandler handler) throws Exception {
		StringEntity entity = new StringEntity(message);
		HttpPost httpPost = new HttpPost(this.config.getRootUrl());
		httpPost.setHeader("Content-Type", "application/json");
		httpPost.setHeader("Authorization", "Bearer " + this.config.getConfig().getProperty("api.key"));
		httpPost.setEntity(entity);
		VirtualPool.newInstance().execute(runAsync(handler, httpPost));
	}

	public String send(String message) throws Exception {

		var param = new HashMap<>();
		param.put("model", this.config.getConfig().getProperty("model"));
		param.put("messages", List.of(systemRole(), Map.of("role", "user", "content", message)));

		// API 요청 생성
		Gson gson = new Gson();
		String requestJson = gson.toJson(param);
		StringEntity entity = new StringEntity(requestJson, StandardCharsets.UTF_8);
		HttpPost httpPost = new HttpPost(this.config.getRootUrl());
		httpPost.setHeader("Content-Type", "application/json");
		httpPost.setHeader("Authorization", "Bearer " + this.config.getConfig().getProperty("apikey"));
		httpPost.setEntity(entity);

		// HttpClient를 사용하여 API 호출
		HttpEntity responseEntity = null;
		try (CloseableHttpClient httpClient = HttpClients.createDefault();
				CloseableHttpResponse response = httpClient.execute(httpPost)) {
			// API 응답 처리
//			System.out.println(response.getStatusLine().getStatusCode());
			Stream.of(response.getAllHeaders()).forEach(System.out::println);
			responseEntity = response.getEntity();
			return EntityUtils.toString(responseEntity, StandardCharsets.UTF_8);
		}
	}

	private Runnable runAsync(ResponseHandler handler, HttpPost httpPost) {
		return new Runnable() {

			@Override
			public void run() {
				// HttpClient를 사용하여 API 호출
				try (CloseableHttpClient httpClient = HttpClients.createDefault();
						CloseableHttpResponse response = httpClient.execute(httpPost)) {

					System.out.println(response.getStatusLine().getStatusCode());
					if (200 == response.getStatusLine().getStatusCode()) {
						// API 응답 처리
						HttpEntity responseEntity = response.getEntity();
						if (responseEntity != null) {
							handler.onSuccess(responseEntity);
						}
					} else {
						// API 응답 처리
						HttpEntity responseEntity = response.getEntity();
						handler.onSuccess(responseEntity);
					}

				} catch (Exception e) {
					handler.onException(e);
				}

			}
		};
	}

}
