package cn.cariton.apps.douyin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.cariton.apps.douyin.domain.Video;
import cn.cariton.apps.douyin.service.VideoService;
import cn.cariton.apps.douyin.mapper.VideoMapper;
import org.springframework.stereotype.Service;

/**
* @author CARITON-DEV
* @description 针对表【video】的数据库操作Service实现
* @createDate 2023-12-23 12:12:57
*/
@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video>
    implements VideoService{

}




