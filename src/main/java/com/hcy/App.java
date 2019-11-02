package com.hcy;

import redis.clients.jedis.Jedis;

public class App {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        jedis.set("name", "tom");
        String value = jedis.get("name");
        System.out.println(value);
    }
}
