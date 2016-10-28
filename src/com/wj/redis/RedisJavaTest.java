package com.wj.redis;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import redis.clients.jedis.Jedis;

public class RedisJavaTest {

  private Jedis jedis;

  @Before
  public void init() {
    jedis = new Jedis("172.17.211.119");
    jedis.auth("w3cschool.cc");
    System.out.println("Connection to server sucessfully");
    System.out.println("Server is running: " + jedis.ping());
  }

  @Test
  public void testRedisString() {
    jedis.set("w3ckey", "wujing");
    System.out.println("string in redis :" + jedis.get("w3ckey"));
  }

  @Test
  public void testRedisList() {
    jedis.lpush("tutorial-list", "Redis");
    jedis.lpush("tutorial-list", "Mongodb");
    jedis.lpush("tutorial-list", "MySql");

    List<String> list = jedis.lrange("tutorial-list", 0, 5);
    for (String str : list) {
      System.out.println(str);
    }
  }

}
