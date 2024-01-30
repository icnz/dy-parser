package cn.cariton.apps.douyin.interceptor;

import cn.cariton.apps.douyin.bean.Result;
import cn.cariton.apps.douyin.domain.Token;
import cn.cariton.apps.douyin.service.TokenService;
import cn.cariton.apps.douyin.utils.ApplicationContextUtil;
import cn.cariton.apps.douyin.utils.StringUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.util.Objects;

/**
 * @author icnz
 * @date 2023-12-22 12:02
 */
public class AuthCookieInterceptor implements HandlerInterceptor {

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String cookies = request.getParameter("cookies");
        if (StringUtils.isEmpty(cookies)) {
            falseResult(response, "no cookie");
            return false;
        }
        if (!cookies.contains("csrf_session_id") && !cookies.contains("toutiao_sso_user") && !cookies.contains(
                "sso_uid_tt")) {
            falseResult(response, "error cookie");
            return false;
        }
        String authorization = request.getHeader("Authorization");
        if (StringUtils.isEmpty(authorization) || !authorization.startsWith("Bearer ")) {
            falseResult(response, "no token");
            return false;
        }
        String tokenStr = authorization.substring(7);
        TokenService tokenService = ApplicationContextUtil.getBean(TokenService.class);
        Token token = tokenService.findByToken(tokenStr);
        if (Objects.isNull(token)) {
            falseResult(response, "error token");
            return false;
        }
        request.getSession().setAttribute("TOKEN", tokenStr);
        return true;
    }

    private void falseResult(HttpServletResponse response, String message) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        ObjectMapper objectMapper = new ObjectMapper();
        response.getWriter().println(objectMapper.writeValueAsString(Result.fail(message)));
    }
}
