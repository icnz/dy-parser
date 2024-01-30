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
 * @TableName video
 */
@TableName(value ="video")
@Data
public class Video implements Serializable {
    /**
     * ID
     */
    @TableId
    private Long id;

    /**
     * 当前用户token
     */
    private String token;

    /**
     * 视频ID
     */
    private String vid;

    /**
     * 视频描述
     */
    private String descp;

    /**
     * 上传时间
     */
    private Long stamp;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(exist = false)
    private String  released;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}