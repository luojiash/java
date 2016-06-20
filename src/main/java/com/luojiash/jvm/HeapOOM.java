package com.luojiash.jvm;

import java.util.ArrayList;
import java.util.List;

public class HeapOOM {
	static class HeapObject {

	}

	/**
	 * -verbose:gc -Xms20m -Xmx20m -Xmn10m -XX:+PrintGCDetails -XX:SurvivorRatio=8 -XX:+HeapDumpOnOutOfMemoryError  -XX:HeapDumpPath=E:\Dev\workspace
	 */
	public static void main(String[] args) {
		List<HeapObject> list=new ArrayList<HeapOOM.HeapObject>();

		while (true) {
			list.add(new HeapObject());
		}
	}
}
