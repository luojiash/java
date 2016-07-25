package com.luojiash.test.timer;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * 《Java并发编程实战》 p102
 * 程序清单6-9 错误的Timer行为
 * TimerTask抛出异常后，Timer不会恢复线程的执行，而是错误地认为整个Timer都被取消了。
 * 因此，已被调度但尚未执行的TimerTask不会再执行，新的任务也不能被调度。
 */
public class OutOfTime {
    public static void main(String[] args) throws InterruptedException {
        Timer timer=new Timer();
        timer.schedule(new ThrowTask(), 1);
        TimeUnit.SECONDS.sleep(1);
        timer.schedule(new ThrowTask(), 1);
        TimeUnit.SECONDS.sleep(5);
    }

    static class ThrowTask extends TimerTask {
        @Override
        public void run() {
            throw new RuntimeException();
        }
    }
}
