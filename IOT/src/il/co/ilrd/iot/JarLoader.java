package il.co.ilrd.iot;

public class JarLoader {
	SingletonCommandFactory factory;
	
	public void load() {
		factory = SingletonCommandFactory.getInstance();
		factory.Add("IOT", IOTCommand :: new);
	}
}
