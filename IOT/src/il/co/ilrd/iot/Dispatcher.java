package il.co.ilrd.iot;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

public class Dispatcher<T> {
	
	private List<Callback<T>> callbackList = new LinkedList<>();
	
	public void register(Callback<T> cb) {
		if (null == cb) {
			throw new NullPointerException();
		}
		cb.dispatcher = this;
		callbackList.add(cb);
	}
	
	public void stopAll() {
		for (Callback<T> cb : callbackList) {
			cb.notifyDeath();
		}
	}
	
	public void updateAll(T msg) {
		for (Callback<T> cb : callbackList) {
			cb.update(msg);
		}
	}
	
	public void unregister(Callback<T> cb) {
		callbackList.remove(cb);
	}
	
	public static class Callback <T>{
		private Consumer<T> c;
		private Runnable r;
		Dispatcher<T> dispatcher;
		
		public Callback(Consumer<T> c, Runnable r) {
			this.c = c;
			this.r = r;
		}
		
		private void update(T t) {
			c.accept(t);
		}	
		
		private void notifyDeath() {
			r.run();
		}
		
		public void stopService() {
			dispatcher.unregister(this);
		}
	}
}
