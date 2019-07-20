package dao.controller.entity;

import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class User  implements Serializable {
    private String username;
    private String password;

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
