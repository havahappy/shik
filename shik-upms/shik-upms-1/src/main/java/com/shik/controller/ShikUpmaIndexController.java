/**
 * ━━━━━━神兽出没━━━━━━
 * 　　　┏┓　　　┏┓
 * 　　┏┛┻━━━┛┻┓
 * 　　┃　　　　　　　┃
 * 　　┃　　　━　　　┃
 * 　　┃　┳┛　┗┳　┃
 * 　　┃　　　　　　　┃
 * 　　┃　　　┻　　　┃
 * 　　┃　　　　　　　┃
 * 　　┗━┓　　　┏━┛
 * 　　　　┃　　　┃神兽保佑, 永无BUG!
 * 　　　　┃　　　┃Code is far away from bug with the animal protecting
 * 　　　　┃　　　┗━━━┓
 * 　　　　┃　　　　　　　┣┓
 * 　　　　┃　　　　　　　┏┛
 * 　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　┃┫┫　┃┫┫
 * 　　　　　┗┻┛　┗┻┛
 * ━━━━━━感觉萌萌哒━━━━━━
 */
package com.shik.controller;

import com.google.common.collect.Maps;
import com.shik.client.ShikUpmsClient;
import com.shik.support.util.CookieUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @author gengshikun
 * @date 2017/8/1
 */
@Controller
@RequestMapping
public class ShikUpmaIndexController {

    private static final Logger logger = LoggerFactory.getLogger(ShikUpmaIndexController.class);

    @Autowired
    private ShikUpmsClient shikUpmsClient;

    /**
     * upms-index
     * @return
     */
    @RequestMapping(value = {"", "/index"}, method = RequestMethod.GET)
    public String index() {
        return "index";
    }

    /**
     * upms-login
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping("admin/login")
    @ResponseBody
    public Map aLogin(@RequestParam("username") String username, @RequestParam("password") String password) {
        Map<String, Object> result = Maps.newHashMap();
        result.put("status", Boolean.FALSE);
        Subject currentUser = SecurityUtils.getSubject();

        if (!currentUser.isAuthenticated()) {
            // 把用户名和密码封装为 UsernamePasswordToken 对象
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            // rememberme
            token.setRememberMe(true);
            try {
                System.out.println("1. " + token.hashCode());
                // 执行登录.
                currentUser.login(token);
                result.put("status", Boolean.TRUE);
                result.put("returnUrl", currentUser.getSession().getAttribute("returnUrl"));
            }
            // ... catch more exceptions here (maybe custom ones specific to your application?
            // 所有认证时异常的父类.
            catch (AuthenticationException ae) {
                //unexpected condition?  error?
                System.out.println("登录失败: " + ae.getMessage());
                result.put("msg", "登录失败");
            }
        }

        return result;
    }

}
