package cn.com.weidai.conference.gate.http;

import cn.com.weidai.conference.media.*;
import cn.com.weidai.conference.schedule.Schedule;
import cn.com.weidai.conference.schedule.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by lwz on 2018/01/16 15:30.
 */
@Service
public class LuckyDrawService {

    @Autowired
    private Schedule schedule;

    @Remote(cmd = CMDs.CMD_HTTP_LUCKY_DRAW, ws = false, needsLogin = true)
    public void luckDraw(Domain.HttpLuckyDrawReq httpLuckyDrawReq) {
        schedule.addTask(new Task(CMDs.CMD_HTTP_LUCKY_DRAW, httpLuckyDrawReq.getRoomId()));
    }
}
