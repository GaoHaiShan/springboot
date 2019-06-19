package controller.demo.entity;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Component
public class Study implements Serializable {
    private int id;
    private String studyname;
    private String studytxt;
    private String username;
    private String type;
    private String photo;

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPhoto() {
        return photo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public String getStudyname() {
        return studyname;
    }

    public String getStudytxt() {
        return studytxt;
    }

    public void setStudyname(String studyname) {
        this.studyname = studyname;
    }

    public void setStudytxt(String studytxt) {
        this.studytxt = studytxt;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "["+getId()+","+getStudyname()+","+getType()+","+getPhoto()+"]";
    }
}
