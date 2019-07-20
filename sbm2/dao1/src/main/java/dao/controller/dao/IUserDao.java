package dao.controller.dao;


import dao.controller.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserDao {
    int getModelByUser(User user);
    boolean addUser(User user);
    int getUser(String userName);
}