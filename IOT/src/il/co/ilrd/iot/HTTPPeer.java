package il.co.ilrd.iot;

import java.io.IOException;
import java.io.OutputStream;

import com.sun.net.httpserver.HttpExchange;

public class HTTPPeer implements Peer{
	HttpExchange exchange;
	
	public HTTPPeer(HttpExchange exchange) {
		this.exchange = exchange;
	}
	
	@Override
	public void send(Response response) {
		
		OutputStream outputStream = exchange.getResponseBody();
		String htmlBuilder = response.getStatusCode() + response.getResponseMsg();
		try {
			exchange.sendResponseHeaders(response.getStatusCode(), htmlBuilder.length());
			outputStream.write(htmlBuilder.getBytes());
			outputStream.flush();
			outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
