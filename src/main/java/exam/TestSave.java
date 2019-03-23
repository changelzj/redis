package exam;

import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.HashSet;

public class TestSave {

    @Test
    public void test() {
        Jedis jedis = new Jedis("192.168.228.10",6379);

        jedis.ping();
        jedis.set("hello", "world");
        jedis.set("可爱", "晓璐");


    }
}


