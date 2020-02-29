package com.example;

import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.Set;

public class TestJedis {

    @Test
    public void testPing() {
        Jedis jedis = new Jedis("192.168.228.101",6379);
        System.out.println(jedis.ping());
    }
    
    @Test
    public void keys() {
        Jedis jedis = new Jedis("192.168.228.101",6379);
        Set<String> keys = jedis.keys("*");
        System.out.println(keys);
    }
    
    @Test
    public void testSet() {
        Jedis jedis = new Jedis("192.168.228.101",6379);
        jedis.select(2);
        for (int i=0; i<100; i++) {
            jedis.set("k"+i, "v"+i);
        }
    }

    @Test
    public void testGet() {
        Jedis jedis = new Jedis("192.168.228.101",6379);
        String s = jedis.get("2");
        System.out.println(s);
    }
    
    @Test
    public void list() {
        Jedis jedis = new Jedis("192.168.228.101",6379);
        jedis.lpush("list", "item1", "item2");
    }
    
    
    


    /**
     * 设置主从复制
     */
    @Test
    public void testMasterSlave() {
        Jedis master = new Jedis("192.168.228.101",6379);
        Jedis slave1 = new Jedis("192.168.228.101",6380);
        slave1.slaveof("192.168.228.101",6379);
        master.set("t1", "hello");
        System.out.println(slave1.get("t1"));
    }


}











