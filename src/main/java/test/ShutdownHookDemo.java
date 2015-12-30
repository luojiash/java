package test;

public class ShutdownHookDemo {
	{
		Runtime.getRuntime().addShutdownHook(new ShutdownHook());
	}
	class ShutdownHook extends Thread {
		public void run() {
			System.out.println("shutdown...");
		}
	}
	public static void main(String[] args) {
		new ShutdownHookDemo();
	}
}
