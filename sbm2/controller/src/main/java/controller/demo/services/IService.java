package controller.demo.services;


import controller.demo.entity.Study;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.util.List;

public interface IService {
    List<Study> getModelByStudy(String userName, String tableName);

    Study getModelByStudyTxt(String studyname, String tablename, String username);

    Boolean addModelByStudy(Study stud);

    int getModelByStudyName(int id);

    Boolean deleteModelByStudyname(int id);

    List<String> getModelType(String userName);

    List<Study> getModelAllByStudy(String userName);

    int getModelById();

    boolean addType(String username, String type);

    boolean addPhoto(CommonsMultipartFile image, int id);

    boolean deleteModelImage(int id);
}
