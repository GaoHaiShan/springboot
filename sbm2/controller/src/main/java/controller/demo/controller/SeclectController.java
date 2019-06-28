package controller.demo.controller;

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


    @RequestMapping("/select")
    public String view(@RequestParam("myUsername")String myUsername,Model model){
        model.addAttribute("myUsername",myUsername);
        return "select";
    }

    @RequestMapping("/userName")
    public String getOtherStudy(@RequestParam("username")String username,@RequestParam("myUsername")String myUsername) throws IOException {
        return "redirect:/main/startView/?otherUsername="+ URLEncoder.encode(username,"UTF-8")+
                "&myUsername="+URLEncoder.encode(myUsername,"UTF-8");
    }
}
