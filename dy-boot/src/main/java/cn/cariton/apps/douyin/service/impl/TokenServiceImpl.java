package cn.cariton.apps.douyin.service.impl;

import cn.cariton.apps.douyin.domain.Token;
import cn.cariton.apps.douyin.mapper.TokenMapper;
import cn.cariton.apps.douyin.service.TokenService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
* @author CARITON-DEV
* @description 针对表【token】的数据库操作Service实现
* @createDate 2023-12-23 12:12:57
*/
@Service
public class TokenServiceImpl extends ServiceImpl<TokenMapper, Token>
    implements TokenService{
    @Resource
    private TokenMapper tokenMapper;

    @Override
    public Token findByToken(String token) {
        QueryWrapper<Token> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Token::getToken, token);
        return tokenMapper.selectOne(queryWrapper);
    }
}




