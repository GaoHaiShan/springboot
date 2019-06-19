package controller.demo.dao;

import controller.demo.entity.User;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@FeignClient(name = "spring-cloud-dao")
public interface IUserDao {

    @RequestMapping("/IUserDao/getModelByUser")
    int getModelByUser(@RequestBody User user);

    @RequestMapping("/IUserDao/addUser")
    boolean addUser(@RequestBody User user);
}
