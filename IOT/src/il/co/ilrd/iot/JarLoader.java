package il.co.ilrd.iot;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;


import com.google.gson.JsonObject;

import il.co.ilrd.iot.Dispatcher.Callback;

public class JarLoader {
	SingletonCommandFactory factory;
	Callback<String> cb = null;
	
	public JarLoader() {
		Consumer<String> consumer = jarPath -> {
			try {
				load(jarPath);
			} catch (IOException e) {
				e.printStackTrace();
			}
		};
		cb = new Callback<String>(consumer, null);
		factory = SingletonCommandFactory.getInstance();
	}

	@SuppressWarnings("unchecked")
	public void load(String jarPath) throws IOException {
		System.out.println("load " + jarPath);
		List<Class<?>> commandClasses = getCommandClasses(jarPath);
		for (Class<?> commandClass : commandClasses) {
			String key = null;
			Function<JsonObject, Command> cTor = null;
			try {
				key = (String) commandClass.getMethod("getKey").invoke(null);
				cTor = (Function<JsonObject, Command>) commandClass.getMethod("getCommandInstance").invoke(null);
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("in load func key: " + key + " cTor: " + cTor);
			factory.Add(key, cTor);
		}
	}

	private List<Class<?>> getCommandClasses(String jarPath) throws IOException {
		List<Class<?>> commandClassList = new ArrayList<>();
		List<Class<?>> classList = getClassList(jarPath);
		for (Class<?> currClass : classList) {
			if (Command.class.isAssignableFrom(currClass)) {
				commandClassList.add(currClass);
			}
		}
		return commandClassList;
	}

	private List<Class<?>> getClassList(String jarPath) throws IOException {
		List<Class<?>> classList = new ArrayList<>();
		try (JarFile jf = new JarFile(jarPath)) {
			for (Enumeration<JarEntry> en = jf.entries(); en.hasMoreElements();) {
				JarEntry e = en.nextElement();
				String name = e.getName();
				// Check for package or sub-package (you can change the test for *exact* package here)
//				name.startsWith("my/specific/package/") &&
				if (name.endsWith(".class")) {
					// Strip out ".class" and reformat path to package name
					String javaName = name.substring(0, name.lastIndexOf('.')).replace('/', '.');
					System.out.println("Checking "+javaName+" ... ");
					Class<?> cls;
					URL[] urls = {Path.of(jarPath).toUri().toURL()};
					try {
						URLClassLoader cll = new URLClassLoader(urls);
						cls = cll.loadClass(javaName);
						classList.add(cls);
					} catch (ClassNotFoundException ex)  { // E.g. internal classes, ...
						System.out.println("ouch");
					}

				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return classList;
	}
}


