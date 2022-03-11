package com.itheima;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.junit.Test;

/**
 * @author Cbt
 * @createDate 2022-03-10 18:42
 */
public class zookeeper {
    @Test
    public void testCreateNodeData01() throws Exception {
        //1. 创建一个空节点(a)（只能创建一层节点）
        //2. 创建一个有内容的b节点（只能创建一层节点）
        //3. 创建持久节点，同时创建多层节点
        //4. 创建带有的序号的持久节点
        //5. 创建临时节点（客户端关闭，节点消失），设置延时5秒关闭（Thread.sleep(5000)）
        //6. 创建临时带序号节点（客户端关闭，节点消失），设置延时5秒关闭（Thread.sleep(5000)）

        //创建客户端
        //connectString   连接zookeeper服务器：ip:port
        String connectString = "127.0.0.1:2181";
        //sessionTimeoutMs  会话超时时间   连接上了服务器但是一直没有去操作
        int sessionTimeoutMs = 3000;
        //connectionTimeoutMs 连接超时 客户端连接服务器一直连不上，超过时间后就放弃连接
        int connectionTimeoutMs = 3000;
        //retryPolicy  重试策略
        //baseSleepTimeMs initial amount of time to wait between retries
        //maxRetries max number of times to retry
        //maxSleepMs max time in ms to sleep on each retry
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3, 1000);
        CuratorFramework client = CuratorFrameworkFactory.newClient(connectString, sessionTimeoutMs, connectionTimeoutMs, retryPolicy);
        //启动客户端
        client.start();
        //使用客户端
        client.create().forPath("/curator");
        //关闭客户端
        client.close();
    }
}
