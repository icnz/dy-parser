package cn.cariton.apps.douyin.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @TableName comment
 */
@TableName(value = "comment")
@Data
public class Comment implements Serializable {
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
     * 评论ID
     */
    private String cid;

    /**
     * 回复ID
     */
    private String rid;

    /**
     * 评论内容
     */
    private String text;

    /**
     * 评论图片
     */
    private String images;

    /**
     * 评论视频ID
     */
    private String vid;

    /**
     * 评论时间
     */
    private Long stamp;

    /**
     * 地区
     */
    private String area;

    /**
     * 用户ID
     */
    private String uid;

    /**
     * 抖音号
     */
    private String sid;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像
     */
    private String avatar;

    /**
     * sec_uid
     */
    private String suid;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(exist = false)
    private List<Comment> replies;
    @TableField(exist = false)
    private LocalDateTime released;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}