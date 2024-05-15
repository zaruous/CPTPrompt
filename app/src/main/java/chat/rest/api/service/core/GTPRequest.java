/**
 * 
 */
package chat.rest.api.service.core;

import java.util.List;

/**
 * 
 */
public class GTPRequest {

	private SystemGTPMessage systemMessage;
	private String model;
	private List<AbstractGTPMessage> list;

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public List<AbstractGTPMessage> getList() {
		return list;
	}

	public void setList(List<AbstractGTPMessage> list) {
		this.list = list;
	}

	public SystemGTPMessage getSystemMessage() {
		return systemMessage;
	}

	/**
	 * @param systemMessage
	 */
	public void setSystemMessage(SystemGTPMessage systemMessage) {
		this.systemMessage = systemMessage;
	}
	
	/**
	 * @param systemMessage
	 */
	public void setSystemMessage(String systemMessage) {
		this.systemMessage = new SystemGTPMessage(systemMessage);
	}

	/**
	 * @return
	 */
	public static GTPRequest of(String model) {
		GTPRequest gtpRequest = new GTPRequest();
		gtpRequest.setModel("gpt-4o");
		return gtpRequest;
	}

	public static GTPRequest gpt4oModel() {
		return of("gpt-4o");
	}

}
