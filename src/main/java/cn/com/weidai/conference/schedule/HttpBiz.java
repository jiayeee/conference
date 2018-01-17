package cn.com.weidai.conference.schedule;

import cn.com.weidai.conference.media.Cache;
import cn.com.weidai.conference.media.Domain;
import cn.com.weidai.conference.media.Response;
import com.alibaba.fastjson.JSON;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by lwz on 2018/01/16 17:16.
 */
@Repository
public class HttpBiz {

    private ExecutorService                tp      = Executors.newSingleThreadExecutor();

    private static final Map<Long, Person> PERSONS = new HashMap<>();

    @Getter
    @Setter
    private static class Person {
        private Long   uid;
        private String avatar = "https://www.baidu.com/img/bd_logo1.png";

        public Person(Long uid) {
            this.uid = uid;
        }
    }

    static {
        for (long i = 0; i < 50; ++i) {
            PERSONS.put(i, new Person(i));
        }
    }

    public void luckDraw(Task task) throws InterruptedException {
        final ChannelGroup channels = Cache.ROOM_CHANNEL_GROUP.get(task.getRoomId());

        for (Map.Entry<Long, Person> entry : PERSONS.entrySet()) {
            TimeUnit.MILLISECONDS.sleep(10);

            Long uid = entry.getKey();
            Person person = entry.getValue();

            Domain.LuckyDrawResp luckyDrawResp = new Domain.LuckyDrawResp();
            luckyDrawResp.setAvatar(person.avatar);
            luckyDrawResp.setUid(uid);
            luckyDrawResp.setFlag(false);
            luckyDrawResp.setTs(System.currentTimeMillis());

            submit(channels, luckyDrawResp);
        }

        int size = PERSONS.size();
        long l = new Random().nextLong();
        long id = l % size;
        Person person = PERSONS.get(id);

        Domain.LuckyDrawResp luckyDrawResp = new Domain.LuckyDrawResp();
        luckyDrawResp.setAvatar(person.getAvatar());
        luckyDrawResp.setUid(person.getUid());
        luckyDrawResp.setFlag(true);
        luckyDrawResp.setTs(System.currentTimeMillis());

        submit(channels, luckyDrawResp);
    }

    private void submit(final ChannelGroup channels, Domain.LuckyDrawResp luckyDrawResp) {

        Response response = new Response();
        Map map = JSON.parseObject(JSON.toJSONString(luckyDrawResp));
        response.setData(map);
        final String s = JSON.toJSONString(response);

        tp.submit(new Runnable() {
            @Override
            public void run() {
                channels.writeAndFlush(new TextWebSocketFrame(s));
            }
        });
    }
}
