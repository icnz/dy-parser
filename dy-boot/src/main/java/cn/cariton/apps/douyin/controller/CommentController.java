package cn.cariton.apps.douyin.controller;

import cn.cariton.apps.douyin.bean.Result;
import cn.cariton.apps.douyin.constant.Consts;
import cn.cariton.apps.douyin.domain.Comment;
import cn.cariton.apps.douyin.service.CommentService;
import cn.cariton.apps.douyin.utils.HttpContextUtils;
import cn.cariton.apps.douyin.utils.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author icnz
 * @date 2023-12-21 15:18
 */
@RestController
@RequestMapping("/man/comment")
@Validated
public class CommentController {

    @Resource
    private CommentService commentService;

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @GetMapping("page")
    public Result page(@RequestParam(value = "page", defaultValue = "1") int page,
                       @RequestParam(value = "limit", defaultValue = "50") int limit,
                       @RequestParam(value = "keyword", required = false) String keyword,
                       @RequestParam(value = "date", required = false) String date) {
        String token = (String) HttpContextUtils.getHttpServletRequest().getSession().getAttribute("TOKEN");
        IPage<Comment> iPage = new Page<>(page, limit);
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        if (keyword != null) {
            String[] keywords = keyword.split(",");
            queryWrapper.lambda().like(StringUtils.isNotBlank(keywords[0]), Comment::getText, keywords[0]);
            if (keywords.length > 1) {
                for (int i = 1; i < keywords.length; i++) {
                    queryWrapper.lambda().or().like(StringUtils.isNotBlank(keywords[i]), Comment::getText, keywords[i]);
                }
            }
        }
        queryWrapper.lambda()
                .eq(StringUtils.isNotEmpty(token),Comment::getToken,token)
                .apply(StringUtils.isNotEmpty(date),"DATE_FORMAT(create_time,'%Y-%m-%d') = {0}",date)
                .orderByDesc(Comment::getStamp);
        IPage<Comment> paged = commentService.page(iPage, queryWrapper);
        List<Comment> result = paged.getRecords();
        result.forEach(comment -> {
            comment.setAvatar("https://p3-pc.douyinpic.com/aweme/100x100/" + comment.getAvatar());
            comment.setSuid("https://www.douyin.com/user/" + comment.getSuid());
            comment.setVid("https://www.douyin.com/video/" + comment.getVid());
            comment.setReleased(new Timestamp(comment.getStamp() * 1000L).toLocalDateTime());
        });
        return Result.ok().put(Consts.DATA, result).put(Consts.TOTALS, paged.getTotal());
    }

}
