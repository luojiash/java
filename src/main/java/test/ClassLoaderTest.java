package test;
import java.io.IOException;
import java.io.InputStream;

/**
 * 《深入理解Java虚拟机》p228
 * 类加载器
 */
public class ClassLoaderTest {
	public static void main(String[] args) throws Exception {
		ClassLoader myLoader = new ClassLoader() {
			@Override
			public Class<?> loadClass(String name) throws ClassNotFoundException {
				try {
					String fileName = name.substring(name.lastIndexOf(".") + 1) + ".class";
					InputStream is = getClass().getResourceAsStream(fileName);
					if (is == null) {
						return super.loadClass(name);
					}
					byte[] b = new byte[is.available()];
					is.read(b);
					return defineClass(name, b, 0, b.length);
				} catch (IOException e) {
					throw new ClassNotFoundException(name);
				}
			}
		};
		Object obj = myLoader.loadClass("ClassLoaderTest").newInstance();
		System.out.println(obj.getClass());
		System.out.println(obj instanceof ClassLoaderTest);
	}
}
