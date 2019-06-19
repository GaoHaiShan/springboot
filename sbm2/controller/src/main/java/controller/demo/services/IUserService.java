package controller.demo.services;


import controller.demo.entity.User;

public interface IUserService {
    int getModelByUser(User user);
    boolean addUser(User user);
}
