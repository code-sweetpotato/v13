import org.junit.Test;
import redis.clients.jedis.Jedis;

public class JedisTest {

    @Test
    public void testJedis(){
        Jedis jedis = new Jedis("106.14.57.231",6379);
        jedis.auth("123");
        jedis.set("test","learn jedis");


    }
    @Test
    public void testget(){
        Jedis jedis = new Jedis("106.14.57.231",6379);
        jedis.auth("123");
        System.out.println(jedis.get("target"));


    }
}
