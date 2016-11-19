package test;

public class VisibilityTest extends Thread {

    private boolean stop;

    public void run() {
        int i = 0;
        while(!stop) {
            i++;
        }
        System.out.println("finish loop,i=" + i);
    }

    public void stopIt() {
        stop = true;
    }

    public boolean getStop(){
        return stop;
    }

    public static void main(String[] args) throws Exception {
        VisibilityTest v = new VisibilityTest();
        v.start();

        Thread.sleep(100);
        v.stopIt();
        System.out.println("finish main");
        System.out.println(v.getStop());
        /**
         * 结果只会打印出
         *   finish main
         *   true
         * stop的值已经是true，循环却不会结束
         * 使用volatile修饰stop能解决这个问题
         */
    }
}
