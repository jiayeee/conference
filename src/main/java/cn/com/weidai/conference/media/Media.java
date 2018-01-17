package cn.com.weidai.conference.media;

import cn.com.weidai.conference.entity.Client;
import cn.com.weidai.conference.media.exception.NoLoginException;
import cn.com.weidai.conference.media.exception.UnknownException;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.apache.commons.codec.binary.Base64;

import java.util.Map;

/**
 * Created by lwz on 2018/01/14 18:13.
 */
public class Media {

    public static void wsExe(Client client, final String req) {
        try {
            if (!Cache.ROOM_CHANNEL_GROUP.containsKey(client.getRoomId())) {
                return;
            }
            ChannelGroup channels = Cache.ROOM_CHANNEL_GROUP.get(client.getRoomId());

            Response response = new Response();

            String res = new String(Base64.decodeBase64(req.getBytes(Const.CHARSET)));
            JSONObject jsonObject = JSON.parseObject(res);
            jsonObject.put("uid", client.getUid());

            Object o = App.getInstance().execute(jsonObject.toJSONString());
            if (o != null) {
                Map map = JSON.parseObject(JSON.toJSONString(o));
                response.setData(map);
            }
            String resp = JSON.toJSONString(response);

            Integer cmd = jsonObject.getInteger("cmd");
            switch (cmd) {
                case Cmds.CMD_WS_BARRAGE:
                    channels.writeAndFlush(new TextWebSocketFrame(resp));
                    break;
                case Cmds.CMD_WS_THUMBS_UP:
                    channels.writeAndFlush(new TextWebSocketFrame(resp));
                    break;
                case Cmds.CMD_WS_LUCKY_DRAW:
                    break;
                default:
                    break;
            }
        } catch (NoLoginException e) {
            e.printStackTrace();
        } catch (UnknownException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String exe(final String req) {
        Response response;

        try {
            response = new Response();

            String res = new String(Base64.decodeBase64(req.getBytes(Const.CHARSET)));
            Object o = App.getInstance().execute(res);
            if (o != null) {
                Map map = JSON.parseObject(JSON.toJSONString(o));
                response.setData(map);
            }
        } catch (NoLoginException e) {
            e.printStackTrace();

            response = new Response(ErrCode.NO_LOGIN.code(), ErrCode.NO_LOGIN.msg());
        } catch (UnknownException e) {
            e.printStackTrace();

            response = new Response(ErrCode.UNKONW_ERR.code(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();

            response = new Response(ErrCode.UNKONW_ERR.code(), ErrCode.UNKONW_ERR.msg());
        }

        return JSON.toJSONString(response);
    }
}