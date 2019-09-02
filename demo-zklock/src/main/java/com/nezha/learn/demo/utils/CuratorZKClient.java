package com.nezha.learn.demo.utils;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
import java.util.List;

/**
 * @author: nezha <br>
 * @Title: CuratorZKClient <br>
 * @ProjectName: curator-util <br>
 * @Description: 基于curator的客户端 <br>
 * @Date: 2019/6/18 7:51 PM <br>
 */
public class CuratorZKClient {
    protected static final Logger logger = LoggerFactory.getLogger(CuratorZKClient.class);

    static final Charset CHARSET = Charset.forName("UTF-8");
    private final CuratorFramework client;

    public CuratorZKClient() {
        //获取zookeeper的地址
        //String zkAddress = XConfig.getValue(Constants.ZK_ADDRESS_KEY);
        String zkAddress = "127.0.0.1:2181";
        CuratorFrameworkFactory.Builder builder = CuratorFrameworkFactory.builder()
                .connectString(zkAddress)
                .retryPolicy(new RetryNTimes(1, 1000))
                .connectionTimeoutMs(5000);
        client = builder.build();
        client.start();
    }

    public void createPersistent(String path) {
        try {
            client.create().creatingParentContainersIfNeeded().forPath(path);
        } catch (KeeperException.NodeExistsException e) {
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    public void createEphemeral(String path) {
        try {
            client.create().creatingParentContainersIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(path);
        } catch (KeeperException.NodeExistsException e) {
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    public String createEphemeralSequential(String path) {
        try {
            return client.create().creatingParentContainersIfNeeded().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath(path);
        } catch (KeeperException.NodeExistsException e) {
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
        return null;
    }

    public String createPersistentSequential(String path) {
        try {
            return client.create().creatingParentContainersIfNeeded().withMode(CreateMode.PERSISTENT_SEQUENTIAL).forPath(path);
        } catch (KeeperException.NodeExistsException e) {
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
        return null;
    }

    public void createPersistent(String path, String data) {
        byte[] dataBytes = data.getBytes(CHARSET);
        try {
            client.create().creatingParentContainersIfNeeded().forPath(path, dataBytes);
        } catch (KeeperException.NodeExistsException e) {
            try {
                client.setData().forPath(path, dataBytes);
            } catch (Exception e1) {
                throw new IllegalStateException(e.getMessage(), e1);
            }
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    public void createEphemeralSequential(String path, String data) {
        byte[] dataBytes = data.getBytes(CHARSET);
        try {
            client.create().creatingParentContainersIfNeeded().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath(path, dataBytes);
        } catch (KeeperException.NodeExistsException e) {
            try {
                client.setData().forPath(path, dataBytes);
            } catch (Exception e1) {
                throw new IllegalStateException(e.getMessage(), e1);
            }
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    public void createEphemeral(String path, String data) {
        byte[] dataBytes = data.getBytes(CHARSET);
        try {
            client.create().creatingParentContainersIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(path, dataBytes);
        } catch (KeeperException.NodeExistsException e) {
            try {
                client.setData().forPath(path, dataBytes);
            } catch (Exception e1) {
                throw new IllegalStateException(e.getMessage(), e1);
            }
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    public void delete(String path) {
        try {
            client.delete().deletingChildrenIfNeeded().forPath(path);
        } catch (KeeperException.NoNodeException e) {
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    public List<String> getChildren(String path) {
        try {
            return client.getChildren().forPath(path);
        } catch (KeeperException.NoNodeException e) {
            return null;
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    public boolean checkExists(String path) {
        try {
            if (client.checkExists().forPath(path) != null) {
                return true;
            }
        } catch (Exception e) {
        }
        return false;
    }


    public boolean isConnected() {
        return client.getZookeeperClient().isConnected();
    }

    public String doGetContent(String path) {
        try {
            byte[] dataBytes = client.getData().forPath(path);
            return (dataBytes == null || dataBytes.length == 0) ? null : new String(dataBytes, CHARSET);
        } catch (KeeperException.NoNodeException e) {
            // ignore NoNode Exception.
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
        return null;
    }

    public void doClose() {
        client.close();
    }

}
