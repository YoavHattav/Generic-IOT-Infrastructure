package il.co.ilrd.iot;

import il.co.ilrd.iot.Dispatcher.Callback;

public class JarLoader {
	SingletonCommandFactory factory;
	Callback<String> cb = new Callback<String>(this::load, null);
	Dispatcher<String> dispacher = null;
	
	public JarLoader(SingletonCommandFactory factory, Dispatcher<String> dispacher) {
		super();
		this.factory = factory;
		this.dispacher = dispacher;
	}

	public void load(String jarPath) {
		factory = SingletonCommandFactory.getInstance();
		factory.Add("IOT", IOTCommand :: new);
	}
}
