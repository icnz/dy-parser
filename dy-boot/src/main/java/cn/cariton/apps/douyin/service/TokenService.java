package cn.cariton.apps.douyin.service;

import cn.cariton.apps.douyin.domain.Token;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author CARITON-DEV
 * @description 针对表【token】的数据库操作Service
 * @createDate 2023-12-23 12:12:57
 */
public interface TokenService extends IService<Token> {
    Token findByToken(String token);
}
