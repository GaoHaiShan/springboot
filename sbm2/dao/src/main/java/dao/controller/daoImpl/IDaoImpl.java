package dao.controller.daoImpl;

import dao.controller.dao.IDao;
import dao.controller.entity.Study;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/IDao")
@ResponseBody
public class IDaoImpl {

    @Resource
   private IDao dao;

    @RequestMapping("/addModelByStudy")
    public Boolean addModelByStudy(@RequestBody Study study){
        return dao.addModelByStudy(study);
    }

    @RequestMapping("/getModelByStudyTxt")
    public Study getModelByStudyTxt(@RequestParam("studyname") String studyname,
                                    @RequestParam("type") String type,@RequestParam("username") String username){
        return dao.getModelByStudyTxt(studyname,type,username);
    }

    @RequestMapping("/getModelByStudy")
    public List<Study> getModelByStudy(@RequestParam("username") String username, @RequestParam("type") String type){
        return dao.getModelByStudy(username,type);
    }

    @RequestMapping("/getModelByStudyName")
    public int getModelByStudyName(@RequestParam int id){
        return dao.getModelByStudyName(id);
    }

    @RequestMapping("/deleteModelByStudyname")
    public Boolean deleteModelByStudyname(@RequestParam int id){
        return dao.deleteModelByStudyname(id);
    }

    @RequestMapping("/getModelType")
    public List<String> getModelType(@RequestParam String userName){
        return dao.getModelType(userName);
    }

    @RequestMapping("/getModelAllByStudy")
    public List<Study> getModelAllByStudy(@RequestParam String userName){
        return dao.getModelAllByStudy(userName);
    }

    @RequestMapping("/getModelById")
    public int getModelById(){
        return dao.getModelById();
    }

    @RequestMapping("/addType")
    public boolean addType(@RequestParam("username") String username,@RequestParam("type") String type){
        return dao.addType(username,type);
    }

    @RequestMapping("/addPhoto")
    public boolean addPhoto(@RequestParam("imagePath") String imagePath,@RequestParam("id") int id){
        return dao.addPhoto(imagePath,id);
    }

    @RequestMapping("/getModelImagePath")
    public String getModelImagePath(@RequestParam("id") int id){
        return dao.getModelImagePath(id);
    }
}
