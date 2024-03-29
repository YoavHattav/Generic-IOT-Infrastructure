package il.co.ilrd.iot;

import java.net.InetSocketAddress;

public class GateWayServer {
	HTTPCommunication httpCom;
	TaskManagment taskManagment;
	int port;
	
	public GateWayServer(int port, int numOfThreads) {
		this.port = port;
		taskManagment = new TaskManagment(numOfThreads);
	}
	public void start() {
		JarLoader jl = new JarLoader();
		JarDirMonitor jdm = new JarDirMonitor("/home/student/Generic-IOT-Infrastructure/jarDir");
		jdm.register(jl.cb);
		jdm.startMonitoring();
		httpCom = new HTTPCommunication(new InetSocketAddress("0.0.0.0", port), taskManagment);
	}
	public void stop() {
		
	}
	
	public static void main(String[] args) {
		GateWayServer gws = new GateWayServer(55554, 5);
		gws.start();
	}
}
