package il.co.ilrd.iot;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

public class JarDirMonitor {
	JarDirObserver observer = new JarDirObserver();
	Dispatcher<String> dispatcher = new Dispatcher<String>();
	String jarDirPath = "HARDCODE ME TO THE DIR";
	//FIXME do i get it or it is sent from the gateway starter?
	public JarDirMonitor(JarDirObserver observer, Dispatcher<String> dispatcher) {
		super();
		this.observer = observer;
		this.dispatcher = dispatcher;
		
		dispatcher.register(observer.cb);
		
		loadBefore(jarDirPath);
		monitor(jarDirPath);
	}
	
	private void loadBefore(String jarDirPath) {
		File file = new File(jarDirPath);
		for (String jarPath : file.list()) {
			dispatcher.updateAll(jarPath);
		}
	}
	
	private void monitor(String jarDirPath) {
		try {
			WatchService watcher = FileSystems.getDefault().newWatchService();
			Path dir = Paths.get(new File(jarDirPath).getParent());
			WatchKey key = dir.register(watcher, StandardWatchEventKinds.ENTRY_MODIFY);
            while (true) {
                WatchKey key = watcher.take();
                key.pollEvents();
                if (run_flag) {
                	setChanged();
                	notifyObservers(reader.readLine());
                }
                if (!key.reset()) { break; }
            }
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}