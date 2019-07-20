package controller.demo.services.serviceImpl;

/**
 * @author 海山
 *
 * @data 2019/5/17
 *
 */


import controller.demo.dao.IUserDao;
import controller.demo.entity.User;
import controller.demo.services.IUserService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import javax.annotation.Resource;

@Service
public class UserServiceImpl  implements IUserService {
    @Resource
    private IUserDao userDao;

    @Override
    public int getModelByUser(User user) {

        return userDao.getModelByUser(user);
    }

    @Override
    public boolean addUser(User user) {
        return userDao.addUser(user);
    }

    @Override
    public int getUser(String username) {
        return userDao.getUser(username);
    }
}
