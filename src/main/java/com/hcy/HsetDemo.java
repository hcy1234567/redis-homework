package com.hcy;

import redis.clients.jedis.Jedis;
import java.util.HashMap;
import java.util.Map;

public class HsetDemo {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("127.0.0.1",6379);
        Post post = new Post();
        post.setTitle("Hash blog");
        post.setAuthor("Hcy");
        post.setContent("My Hash blog");
        Long postId = savePost(jedis, post);
        getPost(jedis,postId);
        updatePost(jedis,postId,"修改后的标题");
        Post post1=getPost(jedis,postId);
        System.out.println(post1);
        deleteBlog(postId,jedis);
    }
    static Post getPost(Jedis jedis, Long postId) {
        Map<String, String> myBlog = jedis.hgetAll("post:" + postId);
        Post post = new Post();
        post.setContent(myBlog.get("content"));
        post.setAuthor(myBlog.get("author"));
        post.setTitle(myBlog.get("title"));
        return post;
    }

    static Long savePost(Jedis jedis, Post post) {
        Long postId = jedis.incr("posts");
        Map<String, String> myPost = new HashMap<String, String>();
        myPost.put("title", post.getTitle());
        myPost.put("author", post.getAuthor());
        myPost.put("content", post.getContent());
        jedis.hmset("post:" + postId, myPost);
        System.out.println(myPost);
        return postId;
    }

    static boolean updatePost(Jedis jedis, Long postId,String title) {
        Long isSuccess = jedis.hset("post:" + postId, "title", title);
        if (isSuccess==0){
            return true;
        }
        return false;
    }

    //删除文章
    static void deleteBlog(Long postId,Jedis jedis){
        jedis.hdel("post:" + postId,"title","author","contet");
        System.out.println("删除成功");
    }


}
