package com.yuyu.zookeeper.config;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.*;

import java.util.concurrent.CountDownLatch;

public class MyWatcher implements Watcher {

    private CountDownLatch connectedSemaphore;

    public MyWatcher(CountDownLatch connectedSemaphore) {
        this.connectedSemaphore = connectedSemaphore;
    }

    public MyWatcher(){

    }
    @Override
    public void process(WatchedEvent event) {

        KeeperState state = event.getState();
        EventType eventType = event.getType();
        String path = event.getPath();
        if (KeeperState.SyncConnected == event.getState()) {
            if (EventType.None == event.getType() && null == event.getPath()) {
                connectedSemaphore.countDown();
                System.out.println("Zookeeper session established");
            } else if (EventType.NodeCreated == event.getType()) {
                System.out.println("success create znode");

            } else if (EventType.NodeDataChanged == event.getType()) {
                System.out.println("success change znode: " + event.getPath());

            } else if (EventType.NodeDeleted == event.getType()) {
                System.out.println("success delete znode");

            } else if (EventType.NodeChildrenChanged == event.getType()) {
                System.out.println("NodeChildrenChanged");

            }

        }

    }
}
