package cn.com.weidai.conference.gate.websocket;

import cn.com.weidai.conference.media.Cmds;
import cn.com.weidai.conference.media.Domain;
import cn.com.weidai.conference.media.Remote;
import org.springframework.stereotype.Service;

/**
 * Created by lwz on 2018/01/16 12:32.
 */
@Service
public class BarrageService {

    @Remote(cmd = Cmds.CMD_WS_BARRAGE, ws = true)
    public Domain.BarrageResp barrage(Domain.BarrageReq barrageReq) {

        Domain.BarrageResp barrageResp = new Domain.BarrageResp();
        barrageResp.setUid(barrageReq.getUid());
        barrageResp.setAvatar("https://www.baidu.com/img/bd_logo1.png");
        barrageResp.setMessage(barrageReq.getMessage());
        barrageResp.setTs(System.currentTimeMillis());
        return barrageResp;
    }
}