package test;

import java.util.concurrent.CountDownLatch;

/**
 * 《Java并发编程实战》p80
 * 程序清单5-11 闭锁
 */
public class HarnessTest {
	public long timeTasks(int nThreads, final Runnable task) throws InterruptedException {
		final CountDownLatch startGate = new CountDownLatch(1);
		final CountDownLatch endGate = new CountDownLatch(nThreads);
		
		for (int i = 0; i < nThreads; i++) {
			Thread t = new Thread() {
				public void run() {
					try {
						startGate.await();
						try {
							task.run();	
						} finally {
							endGate.countDown();
						}
					} catch (InterruptedException ignored) { }	
				}
			};
			t.start();
		}
		
		long start = System.nanoTime();
		startGate.countDown();
		endGate.await();
		long end = System.nanoTime();
		return end - start;
	}
	
	public static void main(String[] args) {
		Runnable task = new Thread() {
			@Override
			public void run() {
				System.out.println("cat");
			}
		};
		try {
			long time = new HarnessTest().timeTasks(5, task);
			System.out.println(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
