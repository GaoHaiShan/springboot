package controller.demo.controller;

/**
 * @author 海山
 *
 * @data 2019/05/17
 *
 */

import controller.demo.entity.User;
import controller.demo.services.IUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@Controller
@RequestMapping("/user")
public class UserController {
    @Resource
    private IUserService userService;

    public static String UserName;

    @RequestMapping("/registered")
    public String Zhuce(){
        return "registered";
    }

    @RequestMapping("/user")//登陆页面请求地址
    public String ShowUser() {
        return "User";
    }

    @RequestMapping("/denglu")//登陆请求地址
    public String Denglu(User user, Model model) {
        try {
            UserName = user.getUsername();
            if (userService.getModelByUser(user)>0) {
                return "redirect:/main/startView";
            }else {
                model.addAttribute("error","用户名或密码输入有误");
                return "User";
            }
        }catch (Exception e){
            return "redirect:/user/user";
        }
    }

    @RequestMapping("/adduser/")
    public String AddUser(User user) {
        try {
            if (userService.addUser(user)) {
                return "redirect:/user/user";
            }
            else {
                return "redirect:/user/registered";
            }
        }catch(Exception e){
            return "redirect:/user/registered";
        }
    }
}