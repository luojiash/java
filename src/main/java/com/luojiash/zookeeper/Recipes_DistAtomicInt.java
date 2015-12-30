package com.luojiash.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.atomic.AtomicValue;
import org.apache.curator.framework.recipes.atomic.DistributedAtomicInteger;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.retry.RetryNTimes;

/**
 * 《从Paxos到Zookeeper》p154
 */
public class Recipes_DistAtomicInt {
    private static String connectString = "192.168.1.133:2181,192.168.1.135:2181";
    private static String counterPath = "/curator_recipes_distatomicint_path";
    private static CuratorFramework client = CuratorFrameworkFactory.builder().connectString(connectString)
            .retryPolicy(new ExponentialBackoffRetry(1000, 3)).build();
    
    public static void main(String[] args) throws Exception {
        client.start();
        DistributedAtomicInteger atomicInteger = new DistributedAtomicInteger(client, counterPath, new RetryNTimes(3, 1000));
        AtomicValue<Integer> rc = atomicInteger.add(8);
        System.out.println("Result: "+rc.succeeded());
    }
}
