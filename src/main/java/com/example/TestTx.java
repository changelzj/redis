package com.example;

import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.List;

public class TestTx {
    /**
     * 事务操作
     */
    @Test
    public void testTx() {
        Jedis jedis = new Jedis("192.168.228.101",6379);
        jedis.select(7);
        jedis.watch("balance");
        Transaction tx = jedis.multi();
        tx.set("balance", "300");
        tx.set("balance", "400");
        List<Object> exec = tx.exec();
        System.out.println(exec);
    }


    /**
     * 修改被监控的KEY
     */
    @Test
    public void set() {
        Jedis jedis = new Jedis("192.168.228.101",6379);
        jedis.select(7);
        jedis.set("balance", "10");
    }
}
