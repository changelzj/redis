package exam;

import org.junit.Test;
import redis.clients.jedis.Jedis;

public class TestSave {

    @Test
    public void test() {
        Jedis jedis = new Jedis("127.0.0.1");
        jedis.ping();
        jedis.set("hello", "world");
        jedis.set("可爱", "晓璐");
    }
}


