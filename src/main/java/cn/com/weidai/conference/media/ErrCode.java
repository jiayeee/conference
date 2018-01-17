package cn.com.weidai.conference.media;

/**
 * Created by lwz on 2018/01/14 22:00.
 */
public enum ErrCode {

                     NO_LOGIN(1, "没有登录"), NO_PERMISSION(2, "没有权限"), UNKONW_ERR(3, "未知错误");

    private int    code;
    private String msg;

    public int code() {
        return code;
    }

    public String msg() {
        return msg;
    }

    ErrCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
