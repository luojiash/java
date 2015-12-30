package test;

import java.util.*;
import java.util.concurrent.*;

public class ConcurrentExecutor {

    private static ExecutorService executorService;

    private static int MINNUM = 1024;
    private static final int MAXNUM = 1024;
    private static final long ALIVE = 60000L;

    static {
        BlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>(1024);
        ThreadFactory factory =  Executors.defaultThreadFactory();
        RejectedExecutionHandler handler = new ThreadPoolExecutor.AbortPolicy();
        executorService = new ThreadPoolExecutor(MINNUM, MAXNUM, ALIVE, TimeUnit.MILLISECONDS, queue, factory, handler);
    }

    private List<Callable<?>> tasks = new ArrayList<Callable<?>>();

    private ConcurrentExecutor() {
    }

    public static ConcurrentExecutor build() {
        return new ConcurrentExecutor();
    }

    public <T> ConcurrentExecutor add(Callable<T> task) {
        tasks.add(task);
        return this;
    }
    
    public <T> void execute(long timeoutmilliSeconds) throws InterruptedException {
        if (null == tasks || tasks.size() == 0) {
            return;
        }
        List<Future<T>> futures;
		
		futures = executorService.invokeAll((Collection<? extends Callable<T>>) tasks, timeoutmilliSeconds, TimeUnit.MILLISECONDS);
		for (Future<T> future : futures) {
			try {
				T v = future.get();
				System.out.println(v);
			} catch (ExecutionException e) {
				e.printStackTrace();
			} catch (CancellationException e) {
				e.printStackTrace();
			} 
		}
    }

    public static void main(String[] args) throws InterruptedException {
    	ConcurrentExecutor executor = ConcurrentExecutor.build();
		for (int i = 0; i < 10; i++) {
			final int j = i;
			executor.add(new Callable<Integer>() {
				@Override
				public Integer call() throws Exception {
					return j;
				}
			});
		}
		executor.execute(2);
		executorService.shutdown();
	}
}
