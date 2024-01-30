package cn.cariton.apps.douyin.controller;

import cn.cariton.apps.douyin.bean.Result;
import cn.cariton.apps.douyin.constant.Consts;
import cn.cariton.apps.douyin.domain.Token;
import cn.cariton.apps.douyin.service.TokenService;
import cn.cariton.apps.douyin.utils.HttpContextUtils;
import cn.cariton.apps.douyin.utils.StringUtils;
import com.alibaba.fastjson2.JSONObject;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
import java.util.UUID;

/**
 * @author icnz
 * @date 2023-12-21 15:18
 */
@RestController
@RequestMapping("/auth")
@Validated
public class AuthController {

    @Resource
    private TokenService tokenService;

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @GetMapping("/token")
    public Result token(@RequestParam(value = "cookies") String cookies) {
        if (StringUtils.isEmpty(cookies)) {
            return Result.fail("cookie为空");
        }
        String csrf_session_id = null;
        String toutiao_sso_user = null;
        String sso_uid_tt = null;
        if (cookies.contains("csrf_session_id")) {
            int index = cookies.indexOf("csrf_session_id") + 16;
            csrf_session_id = cookies.substring(index, index + 32);
        }
        if (cookies.contains("toutiao_sso_user")) {
            int index = cookies.indexOf("toutiao_sso_user") + 17;
            toutiao_sso_user = cookies.substring(index, index + 32);
        }
        if (cookies.contains("sso_uid_tt")) {
            int index = cookies.indexOf("sso_uid_tt") + 11;
            sso_uid_tt = cookies.substring(index, index + 32);
        }
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        String authorization = request.getHeader("Authorization");
        String tokenStr = null;
        if (!StringUtils.isEmpty(authorization) && authorization.startsWith("Bearer ")) {
            tokenStr = authorization.substring(7);
        }
        Token token = tokenService.findByToken(tokenStr);
        String uuid = null;
        if (Objects.isNull(token)) {
            uuid = UUID.randomUUID().toString().replaceAll("-", "");
            token = new Token();
            token.setToken(uuid);
            token.setCsrfSessionId(csrf_session_id);
            token.setToutiaoSsoUser(toutiao_sso_user);
            token.setSsoUidTt(sso_uid_tt);
            boolean flag = tokenService.save(token);
            if (!flag) {
                return Result.fail("token创建失败，请稍后重试");
            }
        } else {
            uuid = token.getToken();
            boolean flag1 = token.getCsrfSessionId() == null ? Objects.equals(null, csrf_session_id) :
                    token.getCsrfSessionId().equals(csrf_session_id);
            boolean flag2 = token.getToutiaoSsoUser().equals(toutiao_sso_user);
            boolean flag3 = token.getSsoUidTt().equals(sso_uid_tt);
            if (!flag1 || !flag2 || !flag3) {
                token.setCsrfSessionId(csrf_session_id);
                token.setToutiaoSsoUser(toutiao_sso_user);
                token.setSsoUidTt(sso_uid_tt);
                tokenService.updateById(token);
            }
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("token", uuid);
        return Result.ok().put(Consts.DATA, jsonObject);
    }
}
