package dao.controller.dao;

import dao.controller.entity.Study;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IDao {

    Boolean addModelByStudy(Study study);

    Study getModelByStudyTxt(@Param("studyname") String studyname, @Param("type") String type, @Param("username") String username);

    List<Study> getModelByStudy(@Param("username") String username, @Param("type") String type);

    int getModelByStudyName(int id);

    Boolean deleteModelByStudyname(int id);

    List<String> getModelType(String userName);

    List<Study> getModelAllByStudy(String userName);

    int getModelById();

    boolean addType(@Param("username") String username, @Param("type") String type);

    boolean addPhoto(@Param("imagePath") String imagePath, @Param("id") int id);

    String getModelImagePath(int id);
}
