package cn.com.weidai.conference.gate.websocket;

import cn.com.weidai.conference.media.Domain;
import cn.com.weidai.conference.entity.Client;
import com.alibaba.fastjson.JSON;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

/**
 * WebSocket连接
 */
public class WSConnService {

    /**
     * 根据客户端的请求生成 Client
     *
     * @param request 例如 {uid:1;roomId:21;token:'43606811c7305ccc6abb2be116579bfd'}
     * @return
     */
    public static Client clientRegister(String request) {
        Client client = new Client();

        try {
            String res = new String(Base64.decodeBase64(request));
            Domain.WSConnReq wsConnReq = JSON.parseObject(res, Domain.WSConnReq.class);

            Long uid = wsConnReq.getUid();
            Integer roomId = wsConnReq.getRoomId();
            String token = wsConnReq.getToken();

            if (roomId == null) {
                return client;
            }
            if (uid == null || StringUtils.isBlank(token)) {
                return client;
            }

            if (!checkToken(uid, token)) {
                return client;
            }

            client.setUid(uid);
            client.setRoomId(roomId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return client;
    }

    /**
     * 从 redis 里根据 id 获取 token 与之对比
     *
     * @param id
     * @param token
     * @return
     */
    private static boolean checkToken(Long uid, String token) {
        // TODO
        return true;
    }
}
