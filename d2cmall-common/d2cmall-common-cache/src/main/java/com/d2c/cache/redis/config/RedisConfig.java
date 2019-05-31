package com.d2c.cache.redis.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisClusterNode;

public class RedisConfig extends RedisClusterConfiguration {

    private String address;

    /**
     * 解析addr 192.168.0.143:7000
     */
    private RedisClusterNode getClusterNode(String addr) {
        String[] strs = StringUtils.split(addr, ":");
        if (strs.length != 2) {
            return null;
        }
        return new RedisClusterNode(strs[0], Integer.parseInt(strs[1]));
    }

    public String getAddress() {
        return address;
    }

    /**
     * address=192.168.0.143:7000;192.168.0.143:7001
     */
    public void setAddress(String address) {
        this.address = address;
        for (String addr : StringUtils.split(address, ";")) {
            RedisClusterNode node = getClusterNode(addr);
            if (node != null) {
                addClusterNode(node);
            }
        }
    }

}
