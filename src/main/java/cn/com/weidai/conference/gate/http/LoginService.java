package cn.com.weidai.conference.gate.http;

import cn.com.weidai.conference.media.CMDs;
import cn.com.weidai.conference.media.Domain;
import cn.com.weidai.conference.media.Remote;
import org.springframework.stereotype.Service;

/**
 * Created by lwz on 2018/01/14 22:48.
 */
@Service
public class LoginService {

    @Remote(cmd = CMDs.CMD_HTTP_LOGIN, ws = false, needsLogin = false)
    public Domain.HttpLoginResp doLogin(Domain.HttpLoginReq httpLoginReq) {

        System.out.println("");

        Domain.HttpLoginResp httpLoginResp = new Domain.HttpLoginResp();
        httpLoginResp.setUid(111L);
        httpLoginResp.setToken("43606811c7305ccc6abb2be116579bfd");
        return httpLoginResp;
    }
}
