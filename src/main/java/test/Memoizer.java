package test;

import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * 计算任务缓存
 * TODO FutureTask的子类解决缓存清理的问题
 */
public class Memoizer {
	private final ConcurrentHashMap<Integer, Future<Integer>> cache = new ConcurrentHashMap<>();
	
	public int compute(int i) {
		while(true) {
			Future<Integer> f = cache.get(i);
			if (null==f) {
				Callable<Integer> eval = new Callable<Integer>() {
	
					@Override
					public Integer call() throws Exception {
						System.out.println("called");
						return i * i;
					}
				};
				FutureTask<Integer> ft = new FutureTask<>(eval);
				f = cache.putIfAbsent(i, ft);
				if(null==f) { f=ft; ft.run(); }
			}
			try {
				return f.get();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		Memoizer m = new Memoizer();
		Thread [] threads = new Thread[10];
		for (int i = 0; i < 10; i++) {
			Thread t = new Thread() {
				@Override
				public void run() {
					m.compute(3);
				}
			};
			threads[i]=t;
		}
		for (int i = 0; i < 10; i++) {
			threads[i].start();
		}
		for (int i = 0; i < 10; i++) {
			try {
				threads[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
