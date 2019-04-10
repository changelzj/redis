package exam;

import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Response;
import redis.clients.jedis.Transaction;

import java.util.Set;

public class TestSave {

    @Test
    public void testPing() {
        Jedis jedis = new Jedis("192.168.228.12",6379);
        System.out.println(jedis.ping());
    }

    @Test
    public void testSet() {
        Jedis jedis = new Jedis("192.168.228.12",6379);
        for (int i=0; i<100; i++) {
            jedis.set("k"+i, "v"+i);
        }

    }

    @Test
    public void testGet() {
        Jedis jedis = new Jedis("192.168.228.12",6379);
        String s = jedis.get("2");
        System.out.println(s);

        Set<String> keys = jedis.keys("*");

    }



    @Test
    public void testTx() {
        Jedis jedis = new Jedis("192.168.228.12",6379);
        Transaction tx = jedis.multi();
        tx.set("", "");
        tx.set("", "");
        tx.set("", "");
        //执行
        tx.exec();
        //放弃
        tx.discard();
    }



    @Test
    public void testWacch() {
        Jedis jedis = new Jedis("192.168.228.12",6379);
        jedis.watch("balance");
        String balance = jedis.get("balance");
        jedis.unwatch();
    }

    @Test
    public void testMS() {
        Jedis master = new Jedis("192.168.228.12",6379);

        Jedis slave = new Jedis("192.168.228.12",6380);
        slave.slaveof("192.168.228.12",6379);

        master.set("t1", "hello");
        slave.get("t1");

    }


}











