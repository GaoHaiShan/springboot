package controller.demo.controller;

import controller.demo.services.IService;
import controller.demo.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.net.URLEncoder;

@Controller
@RequestMapping("/select")
public class SeclectController {

    @Autowired
    IUserService userService;

    @RequestMapping("/select")
    public String view(@RequestParam("myUsername")String myUsername,
                       @RequestParam(value = "c",required = true,defaultValue = "")String c,
                       Model model){
        model.addAttribute("c",c);
        model.addAttribute("myUsername",myUsername);
        return "select";
    }

    @RequestMapping("/userName")
    public String getOtherStudy(@RequestParam("username")String username,@RequestParam("myUsername")String myUsername) throws IOException {

        if(userService.getUser(username)>0){
        return "redirect:/main/startView/?otherUsername="+ URLEncoder.encode(username,"UTF-8")+
                "&myUsername="+URLEncoder.encode(myUsername,"UTF-8");
        }else{
            return "redirect:/select/select/?myUsername="+URLEncoder.encode(myUsername,"utf-8")+"&c="+
                    URLEncoder.encode("没有找到用户","UTF-8");
        }
    }
}
