package com.huweiv.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.huweiv.domain.KaptchaImageVO;
import com.huweiv.domain.PageBean;
import com.huweiv.domain.Result;
import com.huweiv.domain.User;
import com.huweiv.service.UserService;
import com.huweiv.util.Code;
import com.huweiv.util.StringUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @author HUWEIV
 * @version 1.0.0
 * @ClassName UserController
 * @Description TODO
 * @CreateTime 2022/4/10 10:28
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource(name = "userService")
    private UserService userService;
    @Resource(name = "kaptchaProducer")
    DefaultKaptcha kaptchaProducer;
    @Resource(name = "jackJson")
    ObjectMapper objectMapper;

    @PostMapping("/{currentPage}/{pageSize}")
    public Result list(@RequestBody String params, @PathVariable int currentPage, @PathVariable int pageSize) throws IOException {
        JsonNode jsonNode = objectMapper.readTree(params);
        User user = objectMapper.readValue(jsonNode.get("user").toString(), User.class);
        Long[] roleIds = objectMapper.readValue(jsonNode.get("roleIds").toString(), Long[].class);
        PageBean<User> userPageBean =  userService.list(user, roleIds, currentPage, pageSize);
        return new Result(userPageBean, Code.GET_PAGE_OK);
    }

    @PostMapping
    public Result add(@RequestBody String params) throws IOException {
        JsonNode jsonNode = objectMapper.readTree(params);
        User user = objectMapper.readValue(jsonNode.get("user").toString(), User.class);
        String pwd1 = objectMapper.readValue(jsonNode.get("pwd1").toString(), String.class);
        String pwd2 = objectMapper.readValue(jsonNode.get("pwd2").toString(), String.class);
        Long[] roleIds = objectMapper.readValue(jsonNode.get("roleIds").toString(), Long[].class);
        if (user.getUsername() == null || user.getUsername().length() == 0)
            return new Result(Code.SAVE_ERR, "用户名不能为空");
        if (!StringUtil.isEmail(user.getEmail()))
            return new Result(Code.SAVE_ERR, "邮件格式错误");
        if (!StringUtil.isPhoneNum(user.getPhoneNum()))
            return new Result(Code.SAVE_ERR, "电话号码格式错误");
        if (pwd1 == null || pwd1.length() == 0 || !pwd1.equals(pwd2))
            return new Result(Code.SAVE_ERR, "两次密码输入不一致");
        user.setPassword(pwd1);
        return userService.add(user, roleIds);
    }

    @PutMapping
    public Result edit(@RequestBody String params) throws IOException {
        JsonNode jsonNode = objectMapper.readTree(params);
        User user = objectMapper.readValue(jsonNode.get("user").toString(), User.class);
        String pwd1 = objectMapper.readValue(jsonNode.get("pwd1").toString(), String.class);
        String pwd2 = objectMapper.readValue(jsonNode.get("pwd2").toString(), String.class);
        Long[] roleIds = objectMapper.readValue(jsonNode.get("roleIds").toString(), Long[].class);
        int isUpdateUserName = jsonNode.get("isUpdateUserName").asInt();
        int isUpdateUserPwd = jsonNode.get("isUpdateUserPwd").asInt();
        if (user.getUsername() == null || user.getUsername().length() == 0)
            return new Result(Code.UPDATE_ERR, "用户名不能为空");
        if (!StringUtil.isEmail(user.getEmail()))
            return new Result(Code.UPDATE_ERR, "邮件格式错误");
        if (!StringUtil.isPhoneNum(user.getPhoneNum()))
            return new Result(Code.UPDATE_ERR, "电话号码格式错误");
        if (isUpdateUserPwd == 1) {
            if (pwd1 == null || pwd1.length() == 0 || !pwd1.equals(pwd2))
                return new Result(Code.UPDATE_ERR, "两次密码输入不一致");
            user.setPassword(pwd1);
        }
        return userService.edit(user, roleIds, isUpdateUserName, isUpdateUserPwd);
    }

    @DeleteMapping
    public Result batchDelete(@RequestBody Long[] selectIds) {
        return userService.batchDelete(selectIds);
    }

    @DeleteMapping("/{userId}")
    public Result delete(@PathVariable Long userId) {
        return userService.delete(userId);
    }

    @GetMapping("/kaptcha")
    public void getKaptchaImage(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        response.setDateHeader("Expires",0);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/png");
        String code = kaptchaProducer.createText();
        BufferedImage image = kaptchaProducer.createImage(code);
        KaptchaImageVO kaptchaImageVO = new KaptchaImageVO(code, 2 * 60);
        session.setAttribute("kaptcha", kaptchaImageVO);
        try {
            ServletOutputStream outputStream = response.getOutputStream();
            ImageIO.write(image, "png", outputStream);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/login")
    public Result login(@RequestBody String params, HttpSession session) throws IOException {
        JsonNode jsonNode = objectMapper.readTree(params);
        User user = objectMapper.readValue(jsonNode.get("user").toString(), User.class);
        String kaptchaCode = objectMapper.readValue(jsonNode.get("kaptchaCode").toString(), String.class);
        KaptchaImageVO kaptcha = (KaptchaImageVO) session.getAttribute("kaptcha");
        if (kaptcha.isExpired())
            return new Result(Code.LOGIN_ERR, "验证码已过期");
        if (!kaptcha.getCode().equals(kaptchaCode))
            return new Result(Code.LOGIN_ERR, "验证码错误");
        User userInfo = userService.login(user);
        System.out.println(userInfo);
        if (userInfo == null)
            return new Result(Code.LOGIN_ERR, "账号或密码错误");
        if (userInfo.getStatus() == 0)
            return new Result(Code.LOGIN_ERR, "账号被禁用");
        session.setAttribute("user", userInfo);
        return new Result(Code.LOGIN_OK, "登录成功");
    }

    @PutMapping("/forgetPwd")
    public Result forgetPwd(@RequestBody String params, HttpSession session) throws IOException {
        JsonNode jsonNode = objectMapper.readTree(params);
        User user = objectMapper.readValue(jsonNode.get("user").toString(), User.class);
        String kaptchaCode = objectMapper.readValue(jsonNode.get("kaptchaCode").toString(), String.class);
        String pwd1 = objectMapper.readValue(jsonNode.get("pwd1").toString(), String.class);
        String pwd2 = objectMapper.readValue(jsonNode.get("pwd2").toString(), String.class);
        KaptchaImageVO kaptcha = (KaptchaImageVO) session.getAttribute("kaptcha");
        if (kaptcha.isExpired())
            return new Result(Code.FORGET_PWD_ERR, "验证码已过期");
        if (!kaptcha.getCode().equals(kaptchaCode))
            return new Result(Code.FORGET_PWD_ERR, "验证码错误");
        if (user.getUsername() == null || user.getUsername().length() == 0)
            return new Result(Code.FORGET_PWD_ERR, "用户名不能为空");
        if (pwd1 == null || pwd1.length() == 0 || !pwd1.equals(pwd2))
            return new Result(Code.FORGET_PWD_ERR, "两次密码输入不一致");
        user.setPassword(pwd1);
        return userService.forgetPwd(user);
    }

    @GetMapping
    public Result selectLoginUser(HttpSession session) {
        User user =  (User) session.getAttribute("user");
        return new Result(user, Code.GET_OK);
    }

    @GetMapping("/quit")
    public Result quit(HttpSession session) {
        session.invalidate();
        return new Result(Code.QUIT_OK, "注销成功");
    }

    @PutMapping("/editPwd")
    public Result editPwd(@RequestBody String params, HttpSession session) throws IOException {
        JsonNode jsonNode = objectMapper.readTree(params);
        String pwd = objectMapper.readValue(jsonNode.get("pwd").toString(), String.class);
        String pwd1 = objectMapper.readValue(jsonNode.get("pwd1").toString(), String.class);
        String pwd2 = objectMapper.readValue(jsonNode.get("pwd2").toString(), String.class);
        User user = (User) session.getAttribute("user");
        if (pwd1 == null || pwd1.length() == 0 || !pwd1.equals(pwd2))
            return new Result(Code.UPDATE_ERR, "两次密码输入不一致");
        user.setPassword(pwd);
        return userService.editPwd(user, pwd1);
    }
}
