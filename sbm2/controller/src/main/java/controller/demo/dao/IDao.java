package controller.demo.dao;

import controller.demo.entity.Study;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * name为获取的服务名
 */
@FeignClient(name = "spring-cloud-dao")
public interface IDao {
    @RequestMapping("/IDao/addModelByStudy")
    Boolean addModelByStudy(@RequestBody Study study);

    @RequestMapping("/IDao/getModelByStudyTxt")
    Study getModelByStudyTxt(@RequestParam(value = "studyname") String studyname,
                             @RequestParam(value = "type") String type, @RequestParam(value = "username") String username);

    @RequestMapping("/IDao/getModelByStudy")
    List<Study> getModelByStudy(@RequestParam(value = "username") String username,
                                @RequestParam(value = "type") String type,
                                @RequestParam(value = "f") boolean f);

    @RequestMapping("/IDao/getModelByStudyName")
    Integer getModelByStudyName(@RequestParam(value = "id") int id);

    @RequestMapping("/IDao/deleteModelByStudyname")
    Boolean deleteModelByStudyname(@RequestParam(value = "id") int id);

    @RequestMapping("/IDao/getModelType")
    List<String> getModelType(@RequestParam(value = "userName") String userName);

    @RequestMapping("/IDao/getModelAllByStudy")
    List<Study> getModelAllByStudy(@RequestParam(value = "userName") String username,@RequestParam(value = "f")boolean f);

    @RequestMapping("/IDao/getModelById")
    Integer getModelById();

    @RequestMapping("/IDao/addType")
    boolean addType(@RequestParam(value = "username") String username, @RequestParam("type") String type);

    @RequestMapping("/IDao/addPhoto")
    boolean addPhoto(@RequestParam(value = "imagePath") String imagePath, @RequestParam(value = "id") int id);

    @RequestMapping("/IDao/getModelImagePath")
    String getModelImagePath(@RequestParam(value = "id") int id);

    @RequestMapping("/IDao/getModelByStudy1")
    Study getModelByStudy1(@RequestParam(value = "id") int id);
}
