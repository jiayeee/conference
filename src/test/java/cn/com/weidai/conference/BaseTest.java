package cn.com.weidai.conference;

import cn.com.weidai.conference.media.Domain;
import com.alibaba.fastjson.JSON;
import org.apache.commons.codec.binary.Base64;
import org.junit.Test;

/**
 * Created by lwz on 2018/01/13 21:07.
 */
public class BaseTest {

    /**
     * 1    eyJjbWQiOjEsInVpZCI6MSwicmlkIjoyMiwidG9rZW4iOiI0MzYwNjgxMWM3MzA1Y2NjNmFiYjJiZTExNjU3OWJmZCJ9
     * 2    eyJjbWQiOjIsInVpZCI6MSwicm9vbUlkIjoyMiwidG9rZW4iOiI0MzYwNjgxMWM3MzA1Y2NjNmFiYjJiZTExNjU3OWJmZCJ9
     *
     *
     */
    @Test
    public void test1() {
        String request = "{\"cmd\":2,\"uid\":1,\"roomId\":22,\"token\":\"43606811c7305ccc6abb2be116579bfd\"}";
        String res = new String(Base64.encodeBase64(request.getBytes()));
        System.out.println(res);
    }

    @Test
    public void testLogin() {
        String s = "{\"cmd\":1,\"mobile\":\"17682312532\",\"verifyCode\":\"111111\"}";
        String res = new String(Base64.encodeBase64(s.getBytes()));
        System.out.println(res);
    }
}
