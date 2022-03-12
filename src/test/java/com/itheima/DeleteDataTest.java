package com.itheima;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.junit.Test;

/**
 * @author Cbt
 * @createDate 2022-03-12 15:07
 */
public class DeleteDataTest {
    @Test
    public void test01() throws Exception {
        //创建失败策略对象
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000,3,1000);
        CuratorFramework client = CuratorFrameworkFactory.newClient("127.0.0.1:2181",1000,2000,retryPolicy);
        client.start();

        // 删除单点节点  client.delete().forPath("/curator");
        //删除多级节点
        client.delete().deletingChildrenIfNeeded().forPath("/app2");
        client.close();
    }
}
