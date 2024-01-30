package cn.cariton.apps.douyin.bean;

import cn.cariton.apps.douyin.constant.Consts;
import cn.cariton.apps.douyin.constant.ResultCode;
import cn.cariton.apps.douyin.utils.SystemClock;

import java.io.Serial;
import java.util.HashMap;

/**
 * 通用返回结果封装类
 *
 * @author icnz
 * @date 2023-10-19 14:45
 */

public class Result extends HashMap<String, Object> {

    @Serial
    private static final long serialVersionUID = 1L;

    public Result() {
        put(Consts.CODE, ResultCode.OK.getCode());
        put(Consts.MESSAGE, ResultCode.OK.getMessage());
        put(Consts.TIMESTAMP, SystemClock.timestamp());
    }

    /**
     * 正确信息：{"code":0，"msg":"success"}
     *
     * @return Result
     */
    public static Result ok() {
        return new Result();
    }

    /**
     * 正确信息：{"code":0，"msg":msg}
     *
     * @param msg 消息内容<STRING>
     * @return Result
     */
    public static Result ok(String msg) {
        Result r = new Result();
        r.put(Consts.MESSAGE, msg);
        return r;
    }

    /**
     * 正确信息：{"code":0，"msg":msg}
     *
     * @param msg 消息内容<OBJECT>
     * @return Result
     */
    public static Result ok(Object msg) {
        Result r = new Result();
        r.put(Consts.MESSAGE, msg);
        return r;
    }

    /**
     * 错误信息：{"code":-1，"msg":"fail"}
     *
     * @return Result
     */
    public static Result fail() {
        return fail(ResultCode.FAIL.getCode(), ResultCode.FAIL.getMessage());
    }

    /**
     * 错误信息：{"code":-1，"msg":msg}
     *
     * @param msg 消息内容
     * @return Result
     */
    public static Result fail(String msg) {
        return fail(ResultCode.FAIL.getCode(), msg);
    }

    /**
     * 错误信息：{"code":code，"msg":msg}
     *
     * @param code 错误编码
     * @param msg  消息内容<STRING>
     * @return Result
     */
    public static Result fail(int code, String msg) {
        Result r = new Result();
        r.put(Consts.CODE, code);
        r.put(Consts.MESSAGE, msg);
        return r;
    }

    /**
     * 错误信息：{"code":code，"msg":msg}
     *
     * @param code 错误编码
     * @param msg  消息内容<OBJECT>
     * @return Result
     */
    public static Result fail(int code, Object msg) {
        Result r = new Result();
        r.put(Consts.CODE, code);
        r.put(Consts.MESSAGE, msg);
        return r;
    }

    @Override
    public Result put(String key, Object value) {
        super.put(key, value);
        return this;
    }

}
