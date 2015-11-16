package test;

/**
 * 《深入理解Java虚拟机》p366
 * volatile可见性
 */
public class VolatileTest {
	public static volatile int race =  0;

	public static void increase() {
		race++;
	}

	private static final int THREAD_COUNT = 20;

	public static void main(String[] args) {
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
