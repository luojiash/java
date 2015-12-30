package com.luojiash.zookeeper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

/**
 * 《从Paxos到Zookeeper》p151
 */
public class Recipes_NoLock {
	
	public static void main(String[] args) {
		final CountDownLatch latch = new CountDownLatch(1);
		for (int i = 0; i < 10; i++) {
			new Thread(new Runnable() {
				public void run() {
					try {
						latch.await();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println(new SimpleDateFormat("HH:mm:ss|SSS").format(new Date()));
				}
			}).start();
		}
		latch.countDown();
	}
}
