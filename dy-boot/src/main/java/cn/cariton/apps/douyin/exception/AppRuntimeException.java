package cn.cariton.apps.douyin.exception;

import java.io.Serial;

public class AppRuntimeException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 4374306549646878727L;
    private String msg;
    private int code = 500;

    public AppRuntimeException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public AppRuntimeException(int code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public AppRuntimeException(String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
    }

    public AppRuntimeException(int code, String msg, Throwable e) {
        super(msg, e);
        this.code = code;
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
