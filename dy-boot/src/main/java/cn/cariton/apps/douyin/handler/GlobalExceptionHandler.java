package cn.cariton.apps.douyin.handler;

import cn.cariton.apps.douyin.bean.Result;
import cn.cariton.apps.douyin.constant.ResultCode;
import cn.cariton.apps.douyin.exception.AppRuntimeException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Set;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private final static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 401 - Bad Request
     */
    @ExceptionHandler(IllegalArgumentException.class)
    // @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result badArgumentHandler(IllegalArgumentException e) {
        logger.error("URL参数错误:\n", e);
        return Result.fail(/*ResultCode.ARG_ERR.getCode(), */ResultCode.ARG_ERR.getMessage());
    }

    /**
     * 402 - Bad Request
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    // @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Object badArgumentHandler(MethodArgumentTypeMismatchException e) {
        logger.error("URL参数值错误:\n", e);
        return Result.fail(/*ResultCode.ARG_VAL_ERR.getCode(), */ResultCode.ARG_VAL_ERR.getMessage());
    }

    /**
     * 400 - Bad Request
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    // @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        logger.error("URL缺少请求参数:\n", e);
        return Result.fail(/*ResultCode.ARG_MISS.getCode(), */ResultCode.ARG_MISS.getMessage());
    }

    /**
     * 400 - Bad Request
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    // @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        logger.error("URL参数解析失败:\n", e);
        return Result.fail(/*ResultCode.ARG_NOT_READ.getCode(), */ResultCode.ARG_NOT_READ.getMessage());
    }

    /**
     * 400 - Bad Request
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    // @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        logger.error("方法参数无效:\n", e);
        BindingResult result = e.getBindingResult();
        FieldError error = result.getFieldError();
        // String field = null;
        // if (error != null) {
        //     field = error.getField();
        // }
        String msg = null;
        if (error != null) {
            msg = error.getDefaultMessage();
        }
        // String message = String.format("%s:%s", field, msg);
        // return Result.fail(/*ResultCode.ARG_INVALID.getCode(), */ResultCode.ARG_INVALID.getMessage() + ":" +
        // message);
        return Result.fail(msg);
    }

    /**
     * 400 - Bad Request
     */
    @ExceptionHandler(BindException.class)
    // @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result handleBindException(BindException e) {
        logger.error("URL参数绑定失败:\n", e);
        BindingResult result = e.getBindingResult();
        FieldError error = result.getFieldError();
        // String field = null;
        // if (error != null) {
        //     field = error.getField();
        // }
        String msg = null;
        if (error != null) {
            msg = error.getDefaultMessage();
        }
        // String message = String.format("%s:%s", field, msg);
        // return Result.fail(/*ResultCode.ARG_BIND_FAIL.getCode(),
        //  */ResultCode.ARG_BIND_FAIL.getMessage() + ":" + message);
        return Result.fail(msg);
    }

    /**
     * 400 - Bad Request
     */
    @ExceptionHandler(ConstraintViolationException.class)
    // @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result handleServiceException(ConstraintViolationException e) {
        logger.error("URL参数验证失败(违反约束):\n", e);
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        ConstraintViolation<?> violation = violations.iterator().next();
        String message = violation.getMessage();
        return Result.fail(/*ResultCode.ARG_VIOLATE.getCode(), */ResultCode.ARG_VIOLATE.getMessage() + ":" + message);
    }

    /**
     * 400 - Bad Request
     */
    @ExceptionHandler(ValidationException.class)
    // @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result handleValidationException(ValidationException e) {
        logger.error("URL参数验证失败:\n", e);
        return Result.fail(/*ResultCode.ARG_VALID_FAIL.getCode(), */ResultCode.ARG_VALID_FAIL.getMessage());
    }

    /**
     * 405 - Method Not Allowed
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    // @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public Result handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        logger.error("URL不支持当前请求方法:\n", e);
        return Result.fail(/*ResultCode.METHOD_NOT_ALLOWED.getCode(), */ResultCode.METHOD_NOT_ALLOWED.getMessage());
    }

    /**
     * 415 - Unsupported Media Type
     */
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    // @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    public Result handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e) {
        logger.error("URL不支持当前媒体类型:\n", e);
        return Result.fail(/*ResultCode.MEDIA_TYPE_UNSUPPORTED.getCode(), */ResultCode.MEDIA_TYPE_UNSUPPORTED
                .getMessage());
    }

    /**
     * AppRuntimeException
     */
    @ExceptionHandler(AppRuntimeException.class)
    // @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result handleRHException(AppRuntimeException e) {
        logger.error(e.getMessage() == null ? "服务器业务逻辑异常:\n" : e.getMessage() + ":\n", e);
        // return Result.fail(/*ResultCode.BIZ_ERR.getCode(), */ResultCode.BIZ_ERR.getMessage());
        return Result.fail(e.getCode(), e.getMsg());
    }

    /**
     * 操作数据库出现异常:名称重复或外键关联
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    // @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result handleException(DataIntegrityViolationException e) {
        logger.error("更新(INSERT或UPDATE)数据库时，违反了完整性约束(如：外键关联或主键重复):\n", e);
        return Result.fail(/*ResultCode.DATA_VIOLATE.getCode(), */ResultCode.DATA_VIOLATE.getMessage());
    }

    /**
     * 数据库主键重复
     */
    @ExceptionHandler(DuplicateKeyException.class)
    // @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result handleDuplicateKeyException(DuplicateKeyException e) {
        logger.error("数据库中已存在该记录(主键重复):\n", e);
        return Result.fail(/*ResultCode.DUP_KEY.getCode(), */ResultCode.DUP_KEY.getMessage());
    }

    /**
     * 500 - Internal Server Error
     */
    @ExceptionHandler(Exception.class)
    // @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result handleException(Exception e) {
        logger.error("服务器异常:\n", e);
        return Result.fail(/*ResultCode.SERVER_EXP.getCode(), */ResultCode.SERVER_EXP.getMessage());
    }

    /**
     * 500 - Internal Server Error
     */
    @ExceptionHandler(Throwable.class)
    // @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result handleException() {
        logger.error("服务器出错.\n");
        return Result.fail(/*ResultCode.SERVER_ERR.getCode(), */ResultCode.SERVER_ERR.getMessage());
    }
}
