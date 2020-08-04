package il.co.ilrd.iot;

public class Response {
	String responseMsg;
	Integer statusCode;
	
	public Response(String responseMsg, Integer statusCode) {
		this.responseMsg = responseMsg;
		this.statusCode = statusCode;
	}
	public String getResponseMsg() {
		return responseMsg;
	}
	
	public Integer getStatusCode() {
		return statusCode;
	}
}
