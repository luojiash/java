package test;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * p252
 * 程序清单14-11 使用显式条件变量的有界缓存
 * 
 * 在Condition对象中，与内置条件队列的wait, notify, notifyAll对应的方法
 * 分别是await, signal, signalAll. Condition继承了Object, 所以也包含
 * wait和notify方法，保证使用正确的方法。Condition是一种广义的条件队列，
 * await会释放获得的锁，挂起当前线程，signal通知后会重新获得锁。
 */
public class ConditionBoundedBuffer<T> {
	private final int BUFFER_SIZE = 10;
	
	protected final Lock lock = new ReentrantLock();
	// 条件谓词：notFull(count < item.length)
	private final Condition notFull = lock.newCondition();
	//条件谓词：notEmpty(count > 0)
	private final Condition notEmpty = lock.newCondition();
	
	private final T[] items = (T[]) new Object[BUFFER_SIZE];
	private int tail, head, count;
	
	// 阻塞并直到：notFull
	public void put(T x) throws InterruptedException {
		lock.lock();
		try {
			while (count==items.length)
				notFull.await();
			items[tail] = x;
			if(++tail == items.length) 
				tail = 0;
			++count;
			notEmpty.signal();
		} finally {
			lock.unlock();
		}
	}
	
	public T take() throws InterruptedException {
		lock.lock();
		try {
			while(count==0) 
				notEmpty.await();
			T x = items[head];
			items[head] = null;
			if(++head==items.length)
				head = 0;
			--count;
			notFull.signal();
			return x;
		} finally {
			lock.unlock();
		}
	}
}