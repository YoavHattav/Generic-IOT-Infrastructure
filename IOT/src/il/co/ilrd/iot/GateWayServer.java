package il.co.ilrd.iot;

import java.net.InetSocketAddress;

public class GateWayServer {
	HTTPCommunication httpCom;
	TaskManagment tm;
	int port;
	
	public GateWayServer(int port, int numOfThreads) {
		this.port = port;
		tm = new TaskManagment(numOfThreads);
	}
	public void start() {
		JarLoader jl = new JarLoader();
		jl.load();
		httpCom = new HTTPCommunication(new InetSocketAddress("0.0.0.0", port), tm);
	}
	public void stop() {}
	
	public static void main(String[] args) {
		GateWayServer gws = new GateWayServer(55554, 5);
		gws.start();
	}
}
