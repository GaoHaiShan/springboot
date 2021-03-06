package controller.demo.controller;

/**
 * @author 海山
 *
 * @data 2019/05/17
 *
 */

import controller.demo.entity.User;
import controller.demo.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.hypermedia.DiscoveredResource;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.net.URLEncoder;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user")
public class UserController {
    @Resource
    private IUserService userService;

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
            if (userService.getModelByUser(user)>0) {
                return "redirect:/main/startView/?myUsername="+ URLEncoder.encode(user.getUsername(),"UTF-8");
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