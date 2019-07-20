package dao.controller.daoImpl;

import dao.controller.dao.IUserDao;
import dao.controller.entity.User;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/IUserDao")
@ResponseBody
public class IUserDaoImpl {

    @Resource
    private IUserDao userDao;

    @RequestMapping("/getModelByUser")
    public int getModelByUser(@RequestBody User user){
        return userDao.getModelByUser(user);
    }
    @RequestMapping("/addUser")
    public boolean addUser(@RequestBody User user){
        return userDao.addUser(user);
    }
    @RequestMapping("/getUser")
    public int getUser(@RequestParam("username") String username){
        return userDao.getUser(username);
    }
}
