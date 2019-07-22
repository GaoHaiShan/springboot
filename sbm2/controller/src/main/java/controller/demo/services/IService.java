package controller.demo.services;


import controller.demo.entity.Study;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.util.List;

public interface IService {

    Boolean updateModelByStudy( int id, String studytxt);

    List<Study> getModelByStudy(String userName, String tableName,int[] index,boolean f);

    Study getModelByStudyTxt(int id);

    Boolean addModelByStudy(Study stud);

    int getModelByStudyName(int id);

    Boolean deleteModelByStudyname(Study study);

    List<String> getModelType(String userName);

    List<Study> getModelAllByStudy(String userName,int[] index,boolean f);

    int getModelById();

    boolean addType(String username, String type);

    boolean addPhoto(CommonsMultipartFile image, Study study);

    boolean deleteModelImage(Study study);

    Study getModelByStudy1(int id);
}
