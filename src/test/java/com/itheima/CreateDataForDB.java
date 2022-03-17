package com.itheima;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.shaded.com.google.common.base.Utf8;
import org.junit.Test;

/**
 * @author Cbt
 * @createDate 2022-03-12 14:44
 */
public class CreateDataForDB {
    @Test
    public void test01() throws Exception {
        //创建失败策略对象
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000,3,1000);
        CuratorFramework client = CuratorFrameworkFactory.newClient("127.0.0.1:2181",1000,2000,retryPolicy);
        client.start();
        client.create().creatingParentsIfNeeded().forPath("/config/jdbc.driver", "com.mysql.jdbc.Driver".getBytes());
        client.create().creatingParentsIfNeeded().forPath("/config/jdbc.url", "jdbc:mysql://localhost:3306/itcastdubbo?characterEncoding=utf8".getBytes());
        client.create().creatingParentsIfNeeded().forPath("/config/jdbc.username", "root".getBytes());
        client.create().creatingParentsIfNeeded().forPath("/config/jdbc.password", "root".getBytes());
        client.close();
    }
}