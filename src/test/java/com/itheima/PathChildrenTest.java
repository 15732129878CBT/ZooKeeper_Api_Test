package com.itheima;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.junit.Test;

/**
 * @author Cbt
 * @createDate 2022-03-12 18:30
 */
public class PathChildrenTest {
    @Test
    public void test01() throws Exception {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000,3,1000);
        CuratorFramework client = CuratorFrameworkFactory.newClient("127.0.0.1:2181",1000,2000,retryPolicy);
        client.start();
        PathChildrenCache pathChildrenCache = new PathChildrenCache(client, "/app1", true);
        pathChildrenCache.start(PathChildrenCache.StartMode.POST_INITIALIZED_EVENT);
        System.out.println(pathChildrenCache.getCurrentData());

        pathChildrenCache.getListenable().addListener(new PathChildrenCacheListener() {
            @Override
            public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
                if(event.getType() == PathChildrenCacheEvent.Type.CHILD_UPDATED){
                    System.out.println("子节点更新");
                    System.out.println("节点:"+event.getData().getPath());
                    System.out.println("数据" + new String(event.getData().getData()));
                }else if(event.getType() == PathChildrenCacheEvent.Type.INITIALIZED ){
                    System.out.println("初始化操作");
                }else if(event.getType() == PathChildrenCacheEvent.Type.CHILD_REMOVED ){
                    System.out.println("删除子节点");
                    System.out.println("节点:"+event.getData().getPath());
                    System.out.println("数据" + new String(event.getData().getData()));
                }else if(event.getType() == PathChildrenCacheEvent.Type.CHILD_ADDED ){
                    System.out.println("添加子节点");
                    System.out.println("节点:"+event.getData().getPath());
                    System.out.println("数据" + new String(event.getData().getData()));
                }else if(event.getType() == PathChildrenCacheEvent.Type.CONNECTION_SUSPENDED ){
                    System.out.println("连接失效");
                }else if(event.getType() == PathChildrenCacheEvent.Type.CONNECTION_RECONNECTED ){
                    System.out.println("重新连接");
                }else if(event.getType() == PathChildrenCacheEvent.Type.CONNECTION_LOST ){
                    System.out.println("连接失效后稍等一会儿执行");
                }
            }
        });
        System.in.read();
    }
}
