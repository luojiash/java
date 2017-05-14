package test;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.*;

public class MyTest {

    public static void testGetProperty() throws IOException {
        InputStream is = Test.class.getResourceAsStream("/conf.properties");// use absolute path
        Properties properties = new Properties();
        properties.load(is);
        is.close();
        properties.list(System.out);
    }

    public static void testReadResource() throws IOException {
        InputStream is = Test.class.getClassLoader().getResourceAsStream("conf.properties");
        byte[] b = new byte[is.available()];
        is.read(b);
        is.close();
        System.out.println(new String(b));
    }

    public static void testCancel() throws InterruptedException {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1,
                60, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
        CountDownLatch latch = new CountDownLatch(1);
        Future future = executor.submit(() -> {
            List<Future> futures = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                final int j = i;
                futures.add(executor.submit(() -> System.out.println(j)));
            }
            for (Future f : futures) {
                f.cancel(true);
            }
            latch.countDown();
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        latch.await();
        System.out.println(executor.getQueue().size()); // 10
        future.cancel(true);
        TimeUnit.SECONDS.sleep(1); // waiting for worker threads to remove all canceled tasks, you can use executor.purge() to remove them
        System.out.println(executor.getQueue().size()); // 0

        executor.shutdown();
    }

    public static void main(String[] args) throws Exception {
//        testGetProperty();
//        testReadResource();
        testCancel();
    }
}
