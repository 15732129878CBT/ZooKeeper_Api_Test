package com.itheima;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.TreeCache;
import org.apache.curator.framework.recipes.cache.TreeCacheEvent;
import org.apache.curator.framework.recipes.cache.TreeCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.junit.Test;

import java.io.IOException;

/**
 * @author Cbt
 * @createDate 2022-03-12 21:14
 */
public class TreeCacheTest {
    @Test
    public void test01() throws Exception {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000,1);
        CuratorFramework client = CuratorFrameworkFactory.newClient("127.0.0.1:2181", 1000, 1000, retryPolicy);
        client.start();
        TreeCache treeCache = new TreeCache(client,"/app1");
        treeCache.start();
        System.out.println(treeCache.getCurrentData("/app1"));
        treeCache.getListenable().addListener(new TreeCacheListener() {
            @Override
            public void childEvent(CuratorFramework client, TreeCacheEvent event) throws Exception {
                if(event.getType() == TreeCacheEvent.Type.NODE_ADDED){
                    System.out.println(event.getData().getPath() + "节点添加");
                }else if (event.getType() == TreeCacheEvent.Type.NODE_REMOVED){
                    System.out.println(event.getData().getPath() + "节点移除");
                }else if(event.getType() == TreeCacheEvent.Type.NODE_UPDATED){
                    System.out.println(event.getData().getPath() + "节点修改");
                }else if(event.getType() == TreeCacheEvent.Type.INITIALIZED){
                    System.out.println("初始化完成");
                }else if(event.getType() ==TreeCacheEvent.Type.CONNECTION_SUSPENDED){
                    System.out.println("连接过时");
                }else if(event.getType() ==TreeCacheEvent.Type.CONNECTION_RECONNECTED){
                    System.out.println("重新连接");
                }else if(event.getType() ==TreeCacheEvent.Type.CONNECTION_LOST){
                    System.out.println("连接过时一段时间");
                }
            }
        });
        System.in.read();
    }
}
