package com.luojiash.jvm;

public class JavaVMStackSOF {
	private int stackLength=1;

	public void stackLead() {
		stackLength ++;
		stackLead();
	}

	public static void main(String[] args) throws Throwable {
		JavaVMStackSOF oom = new JavaVMStackSOF();
		try {
			oom.stackLead();
		} catch (Throwable e) {
			System.out.println("stack length:" + oom.stackLength);
			throw e;
		}
	}
	/*
	 * stack length:18409
	 */
}
