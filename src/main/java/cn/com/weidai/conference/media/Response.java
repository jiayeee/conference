package cn.com.weidai.conference.media;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * websocket、http统一返回格式
 * 
 */
@Getter
@Setter
public class Response {

    private int                 code = 0; // 成功时 0 ,如果大于 0 则表示则显示error_msg
    private String              msg;
    private Map<String, Object> data;

    public Response() {
        data = new HashMap<>();
    }

    public Response(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
