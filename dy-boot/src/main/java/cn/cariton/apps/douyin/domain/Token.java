package cn.cariton.apps.douyin.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 
 * @TableName token
 */
@TableName(value ="token")
@Data
public class Token implements Serializable {
    /**
     * ID
     */
    @TableId
    private Long id;

    /**
     * 
     */
    private String token;

    /**
     * 
     */
    private String csrfSessionId;

    /**
     * 
     */
    private String toutiaoSsoUser;

    /**
     * 
     */
    private String ssoUidTt;

    /**
     * 
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}