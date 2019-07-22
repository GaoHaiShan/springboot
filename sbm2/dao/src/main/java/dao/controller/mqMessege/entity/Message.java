package dao.controller.mqMessege.entity;

import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Map;

@Component
public class Message  implements Serializable {

    private String ip;
    private String modelName;
    private Map<String,Object> param;

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getId() {
        return ip;
    }

    public Map<String, Object> getParam() {
        return param;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public void setParam(Map<String, Object> param) {
        this.param = param;
    }
}
