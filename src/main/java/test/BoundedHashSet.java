package test;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Semaphore;

/**
 * 《Java并发编程实战》 p83
 * 程序清单5-14 使用Semaphore为容器设置边界
 */
public class BoundedHashSet<T> {
	private Set<T> set;
	private Semaphore sem;
	
	public BoundedHashSet (int bound) {
		this.set = Collections.synchronizedSet(new HashSet<>());
		this.sem = new Semaphore(bound);
	}
	
	public boolean add(T o) throws InterruptedException {
		sem.acquire();
		boolean wasAdded = false;
		try {
			wasAdded = set.add(o);
			return wasAdded;
		} finally {
			if(!wasAdded)
				sem.release();
		}
	}
	
	public boolean remove(Object o) {
		boolean wasRemvoed = set.remove(o);
		if (wasRemvoed) {
			sem.release();
		}
		return wasRemvoed;
	}
}
