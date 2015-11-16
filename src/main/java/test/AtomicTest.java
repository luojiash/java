package test;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 《深入理解Java虚拟机》p395
 * Atomic原子自增运算
 */
public class AtomicTest {
	public static AtomicInteger race = new AtomicInteger(0);

	public static void increase() {
		race.incrementAndGet();
	}

	private static final int THREAD_COUNT = 20;

	public static void main(String[] args) throws Exception {
		Thread[] threads = new Thread[THREAD_COUNT];
		for(int i=0;i<THREAD_COUNT;++i) {
			threads[i] = new Thread(new Runnable(){
				@Override
				public void run() {
					for(int i=0;i<1000;++i) {
						increase();
					}
				}
			});
			threads[i].start();
		}

		while(Thread.activeCount()>1)
			Thread.yield();
		System.out.println(race);
	}
}
