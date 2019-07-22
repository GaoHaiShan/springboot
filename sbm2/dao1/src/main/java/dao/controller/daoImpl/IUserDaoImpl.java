package dao.controller.daoImpl;

import dao.controller.dao.IUserDao;
import dao.controller.entity.User;
import dao.controller.mqMessege.entity.Message;
import dao.controller.mqMessege.send.Sender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/IUserDao")
@ResponseBody
public class IUserDaoImpl {

    @Resource
    private IUserDao userDao;

    @Autowired
    private Sender sender;
    @Autowired
    private Message message;

    @RequestMapping("/getModelByUser")
    public int getModelByUser(@RequestBody User user){
        return userDao.getModelByUser(user);
    }
    @RequestMapping("/addUser")
    public boolean addUser(@RequestBody User user){
        try {
            Map<String, Object> map = new HashMap<>(16);
            map.put("user", user);
            sender.send(getMessage("addUser", map, message));
        }catch (Exception e){
            System.out.println("消息发送失败");
        }
        return userDao.addUser(user);
    }
    @RequestMapping("/getUser")
    public int getUser(@RequestParam("username") String username){
        return userDao.getUser(username);
    }
    private static Message getMessage(String modelName, Map<String,Object> param,Message message) throws UnknownHostException {
        message.setIp(InetAddress.getLocalHost().getHostAddress()+"8001");
        message.setModelName(modelName);
        message.setParam(param);
        return message;
    }
}
