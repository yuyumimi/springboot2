package com.yuyu.zookeeper.config;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

import java.util.concurrent.CountDownLatch;

public class ZKClinet {
    private static CountDownLatch connectedSemaphore=new CountDownLatch(1);

    private static final String connectStr="192.168.153.200:2181";
    public static void main(String[] args) throws Exception {
        ZooKeeper zk = new ZooKeeper(connectStr,3000,new MyWatcher(connectedSemaphore));
        connectedSemaphore.await();
        String znode="config";
        zk.exists(znode, true);
        String path = zk.create(znode, "456".getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);

        zk.close();
    }
}
