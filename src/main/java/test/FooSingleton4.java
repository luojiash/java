package test;

/**
 * 单例
 */
public class FooSingleton4 {
	private FooSingleton4() {
		System.out.println("constructor");
    }
    public static FooSingleton4 getInstance() {
    	System.out.println("getInstance");
        return FooSingleton4Holder.INSTANCE;
    }
    private static class FooSingleton4Holder {
        private static final FooSingleton4 INSTANCE = new FooSingleton4();
    }
    public static void main(String[] args) {
		/*
		 * output:
		 * getInstance
		 * constructor
		 */
    	FooSingleton4.getInstance();
	}
}
