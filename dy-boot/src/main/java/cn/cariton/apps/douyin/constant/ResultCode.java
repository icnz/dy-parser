package cn.cariton.apps.douyin.constant;

/**
 * API返回码封装类
 *
 * @author icnz
 * @date 2023-10-19 14:52
 */
public enum ResultCode {
    FAIL(500, "fail"),
    OK(200, "success"),
    ARG_ERR(400, "参数错误"),
    ARG_VAL_ERR(400, "参数值错误"),
    ARG_MISS(400, "缺少参数"),
    ARG_NOT_READ(400, "参数解析失败"),
    ARG_INVALID(400, "参数无效"),
    ARG_BIND_FAIL(400, "参数绑定失败"),
    ARG_VIOLATE(400, "参数违规"),
    ARG_VALID_FAIL(400, "参数验证失败"),
    METHOD_NOT_ALLOWED(405, "请求方式不支持"),
    MEDIA_TYPE_UNSUPPORTED(405, "媒体类型不支持"),
    BIZ_ERR(500, "业务逻辑异常"),
    DATA_VIOLATE(500, "数据违规"),
    DUP_KEY(500, "主键重复"),
    SERVER_EXP(500, "服务器异常"),
    SERVER_ERR(500, "服务器出错"),
    UNKNOWN(500, "系统内部错误"),
    FORBIDDEN(500, "没有相关权限"),
    UNSUPPORTED(500, "业务不支持"),
    CAPTCHA_INVALID(500, "验证码无效"),
    CAPTCHA_EXPIRED(500, "验证码已失效"),
    USER_NOT_EXIST(500, "用户不存在"),
    USER_DISABLED(500, "用户已停用"),
    PWD_ERR(500, "密码错误"),
    AUTH_EXCEPTION(500, "身份验证异常"),
    NO_PERMISSION(500, "没有权限"),
    UNAUTHORIZED(701, "未登录或token已经失效"),
    NON_TOKEN(701, "未携带token信息"),
    TOKEN_INVALID(701, "token无效"),
    TOKEN_EXPIRED(701, "token已失效"),
    USER_EXPIRED(701, "用户状态已失效"),
    ;
    private final int code;
    private final String message;

    private ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
