package test;

import okhttp3.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class OkHttpTest {
    public static void main(String[] args) throws IOException {
        int cacheSize = 10 * 1024 * 1024;
        File cacheDirectory = Files.createTempDirectory("cache").toFile();
        Cache cache = new Cache(cacheDirectory, cacheSize);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(
                        chain -> {
                            Request request = chain.request();
                            long t1 = System.nanoTime();
                            System.out.println(String.format("发送请求: [%s] %s%n%s",
                                    request.url(), chain.connection(), request.headers()));

                            Response response = chain.proceed(request);

                            long t2 = System.nanoTime();
                            System.out.println(String.format("接收响应: [%s] %.1fms%n%s",
                                    response.request().url(), (t2 - t1) / 1e6d, response.headers()));
                            return response;
                        })
                .cache(cache).build();

        Request request = new Request.Builder().url("http://st.jfz.com/res/css/common/common.css?v=1.4").build();

        Response response = okHttpClient.newCall(request).execute();
        System.out.println(response.code());
        System.out.println(response.header("Cache-Control"));
        response.close(); // 需要关闭连接才会缓存，参照例子https://github.com/square/okhttp/blob/master/samples/guide/src/main/java/okhttp3/recipes/CacheResponse.java
        System.out.println();

        response = okHttpClient.newCall(request).execute();
        System.out.println(response.code());
        System.out.println(response.cacheResponse());
        System.out.println(response.networkResponse());
    }


}
