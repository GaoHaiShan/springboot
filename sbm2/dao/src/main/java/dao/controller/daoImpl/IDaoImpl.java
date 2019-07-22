package dao.controller.daoImpl;

import dao.controller.dao.IDao;
import dao.controller.entity.Study;
import dao.controller.mqMessege.entity.Message;
import dao.controller.mqMessege.send.Sender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/IDao")
@ResponseBody
public class IDaoImpl {

    @Resource
   private IDao dao;
    @Autowired
    private Sender sender;
    @Autowired
    private Message message;

    @RequestMapping("/addModelByStudy")
    public Boolean addModelByStudy(@RequestBody Study study) {
        try {
            Map<String, Object> map = new HashMap<>(16);
            map.put("study", study);
            sender.send(getMessage("addModelByStudy", map, message));
        }catch (Exception e){
            System.out.println("消息发送失败");
        }
        return dao.addModelByStudy(study);
    }
    @RequestMapping("/updateModelByStudy")
    public Boolean updateModelByStudy(@RequestParam("id") int id, @RequestParam("studytxt") String studytxt) {
        try {
            Map<String, Object> map = new HashMap<>(16);
            map.put("id", id);
            map.put("studytxt",studytxt);
            sender.send(getMessage("updateModelByStudy", map, message));
        }catch (Exception e){
            System.out.println("消息发送失败");
        }
        return dao.updateModelByStudy(id,studytxt);
    }
    @RequestMapping("/getModelByStudyTxt")
    public Study getModelByStudyTxt(@RequestParam("id") int id){
        return dao.getModelByStudyTxt(id);
    }

    @RequestMapping("/getModelByStudy")
    public List<Study> getModelByStudy(@RequestParam("username") String username, @RequestParam("type") String type,
                                       @RequestParam("f")boolean f){
        return dao.getModelByStudy(username,type,f);
    }

    @RequestMapping("/getModelByStudyName")
    public int getModelByStudyName(@RequestParam int id){
        return dao.getModelByStudyName(id);
    }

    @RequestMapping("/deleteModelByStudyname")
    public Boolean deleteModelByStudyname(@RequestParam int id){
        try {
            Map<String, Object> map = new HashMap<>(16);
            map.put("id", id);
            sender.send(getMessage("deleteModelByStudyname", map, message));
        }catch (Exception e){
            System.out.println("消息发送失败");
        }
        return dao.deleteModelByStudyname(id);
    }

    @RequestMapping("/getModelType")
    public List<String> getModelType(@RequestParam String userName){
        return dao.getModelType(userName);
    }

    @RequestMapping("/getModelAllByStudy")
    public List<Study> getModelAllByStudy(@RequestParam("userName") String userName,@RequestParam("f")boolean f){
        System.out.println("11111111");
        return dao.getModelAllByStudy(userName,f);
    }

    @RequestMapping("/getModelById")
    public int getModelById(){
        return dao.getModelById();
    }

    @RequestMapping("/addType")
    public boolean addType(@RequestParam("username") String username,@RequestParam("type") String type){
        try {
            Map<String, Object> map = new HashMap<>(16);
            map.put("username", username);
            map.put("type",type);
            sender.send(getMessage("addType", map, message));
        }catch (Exception e){
            System.out.println("消息发送失败");
        }
        return dao.addType(username,type);
    }

    @RequestMapping("/addPhoto")
    public boolean addPhoto(@RequestParam("imagePath") String imagePath,@RequestParam("id") int id){
        try {
            Map<String, Object> map = new HashMap<>(16);
            map.put("imagePath", imagePath);
            map.put("id",id);
            sender.send(getMessage("addPhoto", map, message));
        }catch (Exception e){
            System.out.println("消息发送失败");
        }
        return dao.addPhoto(imagePath,id);
    }

    @RequestMapping("/getModelImagePath")
    public String getModelImagePath(@RequestParam("id") int id){
        return dao.getModelImagePath(id);
    }

    @RequestMapping("/getModelByStudy1")
    public Study getModelByStudy1(@RequestParam("id") int id){
        return dao.getModelByStudy1(id);
    }

    private static Message getMessage(String modelName, Map<String,Object> param,Message message) throws UnknownHostException {
        message.setIp(InetAddress.getLocalHost().getHostAddress()+"9001");
        message.setModelName(modelName);
        message.setParam(param);
        return message;
    }
}
