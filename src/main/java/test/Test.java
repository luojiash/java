package test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Enumeration;
import java.util.Locale;
import java.util.ResourceBundle;
import org.apache.commons.lang3.time.DateUtils;
import com.alibaba.fastjson.JSON;
import test.bean.Person;

public class Test {
	public static void testAutoboxing(){
		Integer a = 1; 
		Integer b = 2;
		Integer c = 3;
		Integer d = 3;
		Integer e = 321;
		Integer f = 321;
		Long g = 3L;
		System.out.println(c==d);
		System.out.println(e==f);
		System.out.println(c==(a+b));
		System.out.println(c.equals(a+b));
		System.out.println(g==(a+b));
		System.out.println(g.equals(a+b));
	}
	
	public static void main(String[] args) {
		testAutoboxing();
	}

	public static void testBoot() {
		for (String string : System.getProperty("sun.boot.class.path").split(";")) {
			System.out.println(string);
		}
		System.out.println();

		URL[] urls = sun.misc.Launcher.getBootstrapClassPath().getURLs();
		for (int i = 0; i < urls.length; i++) {
			System.out.println(urls[i].toExternalForm());
		}
	}

	public static void testBundle() {
		ResourceBundle bundle = ResourceBundle.getBundle("conf");
		Enumeration<String> e = bundle.getKeys();
		while (e.hasMoreElements()) {
			String string = (String) e.nextElement();
			System.out.println(string + "=" + bundle.getString(string));
		}
	}

	public static void testFormat() throws ParseException {
		Date date = DateUtils.parseDate("2016-12-13", "yyyy-MM-dd");
		System.out.println(new SimpleDateFormat("E, d M y", Locale.ENGLISH).format(date));
		System.out.println(new SimpleDateFormat("EE, dd MM yy", Locale.ENGLISH).format(date));
		System.out.println(new SimpleDateFormat("EEE, ddd MMM yyy", Locale.ENGLISH).format(date));
		System.out.println(new SimpleDateFormat("EEEE, dddd MMMM yyyy", Locale.ENGLISH).format(date));
		System.out.println(new SimpleDateFormat("EEEEE, ddddd MMMMM yyyyy", Locale.ENGLISH).format(date));
	}

	public static void testSerialize() throws IOException, ClassNotFoundException {
		Person p = new Person();
		p.setName("abc");

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(out);
		oos.writeObject(p);

		ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
		ObjectInputStream ois = new ObjectInputStream(in);

		System.out.println(JSON.toJSONString(ois.readObject()));
	}

	public static void testZonedDateTime() throws ParseException {
		Instant now = Instant.now();
		System.out.println(now);

		now = now.plus(1, ChronoUnit.HOURS);
		System.out.println(now);

		System.out.println(now.atZone(ZoneId.systemDefault()));// 东8区

		System.out.println(now.atZone(ZoneId.of("GMT+09:00")));// 东9区
		System.out.println(now.atZone(ZoneId.of("GMT+09:00")).toLocalDateTime());// 东9区
	}
}
