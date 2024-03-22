package org.example;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Unit test for simple App.
 */
@SpringBootTest
public class AppApplicationTest {

    private final long maxRequire = 10l;

    @Resource
    private RedisTemplate<String, String> redisTemplate;


    @Test
    public void transfer() {
        int[] nums = new int[]{4, 3, 4};
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(nums[i])) {
                map.put(nums[i], map.getOrDefault(nums[i], nums[i]) + 1);
            } else {
                map.put(nums[i], 1);
            }
        }
        Map.Entry<Integer, Integer> majorityEntry = null;
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if (majorityEntry == null || entry.getValue() > majorityEntry.getValue()) {
                majorityEntry = entry;
            }
        }
        System.out.println(majorityEntry.getKey());

    }

    private int[] prices = {7,1,5,3,6,4};

    @Test
    public void maxProfit() {
        int ans = 0;
        int n = prices.length;
        for (int i = 1; i < n; i++) {
            ans += Math.max(0, prices[i] - prices[i - 1]);
            System.out.println(i);
        }
        System.out.println(ans);
    }


    @Test
    public void testRedis(){
//        redisTemplate.opsForValue().set(UUID.randomUUID().toString().replace("-",""), "k1");
        Jedis jedis = new Jedis("121.40.166.14",6379);
        jedis.auth("redis123");
        String set = jedis.set("aa", "bb");
        System.out.println(set);
    }


}
