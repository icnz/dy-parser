package cn.cariton.apps.douyin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.cariton.apps.douyin.domain.Comment;
import cn.cariton.apps.douyin.service.CommentService;
import cn.cariton.apps.douyin.mapper.CommentMapper;
import org.springframework.stereotype.Service;

/**
* @author CARITON-DEV
* @description 针对表【comment】的数据库操作Service实现
* @createDate 2023-12-23 12:12:57
*/
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment>
    implements CommentService{

}




