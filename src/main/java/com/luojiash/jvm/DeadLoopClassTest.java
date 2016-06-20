package com.luojiash.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * 虚拟机会生成类的初始化方法<clinit>(),
 * 多线程环境下，该方法会被正确地加锁、同步，
 * 只会有一个线程执行该方法，其它线程会阻塞等待，
 * 直到<clinit>()方法完毕。
 *
 */
public class DeadLoopClassTest {
	static class DeadLoopClass {
		static {
			if (true) {
				System.out.println(Thread.currentThread() + "init DeadLoopClass");
				while (true) {
				}
			}
		}
	}

	public static void main(String[] args) {
		Runnable script = new Runnable() {
			public void run() {
				System.out.println(Thread.currentThread() + "start");
				DeadLoopClass dlc = new DeadLoopClass();
				System.out.println(Thread.currentThread() + "run over");
			}
		};

		Thread thread1 = new Thread(script);
		Thread thread2 = new Thread(script);
		thread1.start();
		thread2.start();
	}
}
