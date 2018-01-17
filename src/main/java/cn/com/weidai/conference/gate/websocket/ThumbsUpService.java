package cn.com.weidai.conference.gate.websocket;

import cn.com.weidai.conference.media.CMDs;
import cn.com.weidai.conference.media.Domain;
import cn.com.weidai.conference.media.Remote;
import org.springframework.stereotype.Service;

/**
 * Created by lwz on 2018/01/16 14:12.
 */
@Service
public class ThumbsUpService {

    @Remote(cmd = CMDs.CMD_WS_THUMBS_UP, ws = true)
    public Domain.ThumbsUpResp barrage(Domain.ThumbsUpReq thumbsUpReq) {

        Domain.ThumbsUpResp thumbsUpResp = new Domain.ThumbsUpResp();
        thumbsUpResp.setUid(thumbsUpReq.getUid());
        thumbsUpResp.setAvatar("https://www.baidu.com/img/bd_logo1.png");
        thumbsUpResp.setProgress(10);
        thumbsUpResp.setTs(System.currentTimeMillis());
        return thumbsUpResp;
    }
}
