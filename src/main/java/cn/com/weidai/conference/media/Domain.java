package cn.com.weidai.conference.media;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by lwz on 2018/01/14 17:42.
 */
public class Domain {

    /**
     * 登录请求（http）
     */
    @Getter
    @Setter
    public static class HttpLoginReq implements Serializable {

        private String mobile;
        private String verifyCode;
    }

    /**
     * 登录响应（http）
     */
    @Getter
    @Setter
    public static class HttpLoginResp implements Serializable {

        private Long   uid;
        private String token;
    }

    // ----------------------------------------------------------

    /**
     * websocket连接请求（websock）
     */
    @Getter
    @Setter
    public static class WSConnReq implements Serializable {

        private Long    uid;
        private Integer roomId;
        private String  token;
    }

    // ----------------------------------------------------------

    /**
     * 弹幕请求（websock）
     */
    @Getter
    @Setter
    public static class BarrageReq implements Serializable {
        private int    cmd;
        private Long   uid;    // 后台添加
        private String message;
    }

    /**
     * 弹幕响应（websock）
     */
    @Getter
    @Setter
    public static class BarrageResp implements Serializable {

        private final int cmd = CMDs.CMD_WS_BARRAGE;
        private Long      uid;
        private String    avatar;                   // 头像
        private String    message;                  // 弹幕内容
        private long      ts  = 0L;
    }

    /**
     * 点赞请求（websock）
     */
    @Getter
    @Setter
    public static class ThumbsUpReq implements Serializable {
        private int  cmd;
        private Long uid; // 后台添加
    }

    /**
     * 点赞响应（websock）
     */
    @Getter
    @Setter
    public static class ThumbsUpResp implements Serializable {

        private final int cmd      = CMDs.CMD_WS_THUMBS_UP;
        private Long      uid;
        private String    avatar;                          // 头像
        private int       progress = 0;                    // 进度
        private long      ts       = 0L;
    }

    /**
     * 抽奖请求（websock）
     */
    @Getter
    @Setter
    public static class HttpLuckyDrawReq implements Serializable {
        private int     cmd;
        private Long    uid;
        private String  token;
        private Integer roomId;
    }

    /**
     * 抽奖响应（websock）
     */
    @Getter
    @Setter
    public static class LuckyDrawResp implements Serializable {

        private final int cmd  = CMDs.CMD_WS_LUCKY_DRAW;
        private Long      uid;
        private String    avatar;                       // 头像
        private boolean   flag = false;                 // 是否中奖
        private long      ts   = 0L;
    }
}
