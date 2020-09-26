package com.zilong.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
public class JumpPageController {
    @RequestMapping("/main.action")
    public String main(HttpSession session) {
        //获取登录成功时存入的用户信息
        if (session.getAttribute("user") == null) {
            //没有登录   login.jsp
            // 不能让她走  视图解析器
            return "redirect:/login.jsp";
        }
        return "systemJsp/" + "main";
    }

    @RequestMapping("/userInfo.action")
    public String index(HttpSession session) {  // 跳转到用户管理界面
        //获取登录成功时存入的用户信息
        if (session.getAttribute("user") == null) {
            //没有登录   login.jsp
            // 不能让她走  视图解析器
            return "redirect:/login.jsp";
        }
        return "systemJsp/" + "userInfo";
    }

    @RequestMapping("/addUserInfoWin.action")
    public String addUserInfoWin(HttpSession session) {// 跳转到添加用户页面
        //获取登录成功时存入的用户信息
        if (session.getAttribute("user") == null) {
            //没有登录   login.jsp
            // 不能让她走  视图解析器
            return "redirect:/login.jsp";
        }
        return "systemJsp/" + "addUserInfoWin";
    }

    @RequestMapping("/uptUserInfoWin.action")
    public String uptUserInfoWin(HttpSession session) {// 跳转到添加用户页面
        //获取登录成功时存入的用户信息
        if (session.getAttribute("user") == null) {
            //没有登录   login.jsp
            // 不能让她走  视图解析器
            return "redirect:/login.jsp";
        }
        return "systemJsp/" + "uptUserInfoWin";
    }

    // 封装货商界面
    @RequestMapping("/supplierMain.action")
    public String supplierMain(HttpSession session,String url) {// 跳转到添加用户页面
        //获取登录成功时存入的用户信息
        if (session.getAttribute("supplier") == null) {
            //没有登录   login.jsp
            // 不能让她走  视图解析器
            return "redirect:/login.jsp";
        }
        return "otherJsp/" + url;
    }
    // 封装客户购买界面
    @RequestMapping("/salesMain.action")
    public String salesMainMain(HttpSession session,String url) {// 跳转到添加用户页面
        //获取登录成功时存入的用户信息
        if (session.getAttribute("connection") == null) {
            //没有登录   login.jsp
            // 不能让她走  视图解析器
            return "redirect:/login.jsp";
        }
        return "salesJsp/" + url;
    }
    // 封装客户购买界面
    @RequestMapping("/salesJsp.action")
    public String salesJsp(HttpSession session,String url) {// 跳转到添加用户页面
        //获取登录成功时存入的用户信息
        if (session.getAttribute("user") == null) {
            //没有登录   login.jsp
            // 不能让她走  视图解析器
            return "redirect:/login.jsp";
        }
        return "salesJsp/" + url;
    }

    // 封装客户购买界面
    @RequestMapping("/mysalesJsp.action")
    public String mysalesJsp(HttpSession session,String url) {// 跳转到添加用户页面
        //获取登录成功时存入的用户信息
        if (session.getAttribute("connection") == null) {
            //没有登录   login.jsp
            // 不能让她走  视图解析器
            return "redirect:/login.jsp";
        }
        return "salesJsp/" + url;
    }

    // 封装一个跳转方法
    // 系统管理跳转
    @RequestMapping("/systemIndex.action")
    public String indexs(HttpSession session, String url) {
        //获取登录成功时存入的用户信息
        if (session.getAttribute("user") == null) {
            //没有登录   login.jsp
            // 不能让她走  视图解析器
            return "redirect:/login.jsp";
        }
        return "systemJsp/" + url;
    }

    // 封装一个跳转方法
    // 系统管理跳转
    @RequestMapping("/dataIndex.action")
    public String dataIndex(HttpSession session, String url) {
        //获取登录成功时存入的用户信息
        if (session.getAttribute("user") == null) {
            //没有登录   login.jsp
            // 不能让她走  视图解析器
            return "redirect:/login.jsp";
        }
        return "dataJsp/" + url;
    }

    // 封装一个跳转方法
    // 库存管理管理跳转
    @RequestMapping("/inventoryIndex.action")
    public String inventory(HttpSession session, String url) {
        //获取登录成功时存入的用户信息
        if (session.getAttribute("user") == null) {
            //没有登录   login.jsp
            // 不能让她走  视图解析器
            return "redirect:/login.jsp";
        }
        return "inventoryJsp/" + url;
    }

    //purchaseJspIndex
    // 封装一个跳转方法
    // 库存管理管理跳转
    @RequestMapping("/purchaseIndex.action")
    public String purchase(HttpSession session, String url) {
        //获取登录成功时存入的用户信息
        if (session.getAttribute("user") == null) {
            //没有登录   login.jsp
            // 不能让她走  视图解析器
            return "redirect:/login.jsp";
        }
        return "purchaseJsp/" + url;
    }
}
