package com.itheima;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.junit.Test;

/**
 * @author Cbt
 * @createDate 2022-03-12 16:45
 */
public class NodeCacheTest {
    @Test
    public void test01() throws Exception {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000,3,1000);
        CuratorFramework client = CuratorFrameworkFactory.newClient("127.0.0.1:2181",1000,2000,retryPolicy);
        client.start();
        //监听当前节点变化
        NodeCache nodeCache = new NodeCache(client, "/app1");
        //初始化时获取当前节点数据
        nodeCache.start(true);
        System.out.println(new String(nodeCache.getCurrentData().getData()));
        nodeCache.getListenable().addListener(()->{
            System.out.println("修改之后节点数据==>"+new String(nodeCache.getCurrentData().getData()));
        });
        System.in.read();
        //client.close();
    }
}
