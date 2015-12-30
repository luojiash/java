package com.luojiash.zookeeper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * 《从Paxos到Zookeeper》p152
 */
public class Recipes_Lock {
	private static String connectString = "192.168.1.133:2181,192.168.1.135:2181";
	
	private static String lock_path = "/curator_recipes_lock_path";
	private static CuratorFramework client = CuratorFrameworkFactory.builder().connectString(connectString)
			.retryPolicy(new ExponentialBackoffRetry(1000, 3)).build();
	public static void main(String[] args) {
		client.start();
		final InterProcessMutex lock = new InterProcessMutex(client, lock_path);
		final CountDownLatch down = new CountDownLatch(1);
		for (int i = 0; i < 30; i++) {
			new Thread(new Runnable() {
				public void run() {
					try {
						down.await();
						lock.acquire();
					} catch (Exception e) {
						e.printStackTrace();
					}
					System.out.println(new SimpleDateFormat("HH:mm:ss|SSS").format(new Date()));
					try {
						lock.release();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}).start();
		}
		down.countDown();
	}
}
