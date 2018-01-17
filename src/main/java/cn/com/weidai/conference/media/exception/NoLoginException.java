package cn.com.weidai.conference.media.exception;

/**
 * Created by lwz on 2018/01/15 14:37.
 */
public class NoLoginException extends RuntimeException {

    public NoLoginException() {
    }

    public NoLoginException(String message) {
        super(message);
    }

    public NoLoginException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoLoginException(Throwable cause) {
        super(cause);
    }

    public NoLoginException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
