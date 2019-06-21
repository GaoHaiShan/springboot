package controller.demo.services.serviceImpl;


import com.sun.org.apache.bcel.internal.generic.NEW;
import controller.demo.dao.IDao;
import controller.demo.entity.Study;
import controller.demo.entity.User;
import controller.demo.services.IService;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
@Service
public class ServiceImpl implements IService {

    @Resource
    private IDao dao;

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceImpl.class);

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public List<Study> getModelByStudy(String userName, String type) {

        String key = "getModelByStudy"+type;
        List<Study> studys;
        int count;
        // 缓存存在
        ListOperations<String,Study> operations = redisTemplate.opsForList();
        count = operations.range(key,0,-1).size();
        if(count > 0){
            System.out.println(count);
            studys = new LinkedList<>();
            for (int i = 0; i < count; i++) {
                if (operations.index(key, i) == null) {
                    break;
                }
                studys.add(operations.index(key, i));
                LOGGER.info(key + ": 从缓存中获取了知识 >> " + studys.get(i).toString());
            }
            return studys;
        }
        else {
            //用户同时访问某个列表没有缓存时，只允许一个用户访问数据，其他用户读取已更改的缓存内容
            synchronized (this) {
                studys = dao.getModelByStudy(userName,type);
                operations.rightPushAll(key, studys);
                LOGGER.info(key+":"+key+" 插入缓存 ");
                return  studys;
            }
        }
    }

    @Override
    public Study getModelByStudyTxt(String studyname, String tablename, String username) {
        return dao.getModelByStudyTxt(studyname,tablename,username);
    }

    @Override
    public Boolean addModelByStudy(Study study) {
        if (dao.getModelByStudyName(study.getId()) > 0) {
           dao.deleteModelByStudyname(study.getId());
        }
        return dao.addModelByStudy(study);
    }

    @Override
    public int getModelByStudyName(int id) {
        return dao.getModelByStudyName(id);
    }

    @Override
    public Boolean deleteModelByStudyname(int id) {
        String fileName =  dao.getModelImagePath(id);
        String file = fileName.substring(fileName.lastIndexOf("/")+1);
        new File("D:/javaText/sbm2/controller/src/main/resources/image/"+file).delete();
        return dao.deleteModelByStudyname(id);
    }

    @Override
    public List<String> getModelType(String userName) {
        return dao.getModelType(userName);
    }

    @Override
    public List<Study> getModelAllByStudy(String userName) {
        return dao.getModelAllByStudy(userName);
    }

    @Override
    public int getModelById() {
        return dao.getModelById();
    }

    @Override
    public boolean addType( String username,String type) {
        return dao.addType(username,type);
    }

    @Override
    public boolean addPhoto(CommonsMultipartFile image,int id)  {
        String imageName="";
        try {
            if(image.getSize()==0) {
                return false;
            }
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss");
            File f = new File("D:/javaText/sbm2/controller/src/main/resources/image/");
            String suffix = image.getOriginalFilename().
                    substring(image.getOriginalFilename().lastIndexOf(".") + 1);
            imageName = simpleDateFormat.format(new Date())+"."+suffix;
            FileUtils.copyInputStreamToFile(image.getInputStream(),new File(f,imageName));
        }catch (IOException e){
            System.out.println("eorrs1");
        }
        return dao.addPhoto("/" +imageName,id);
    }

    @Override
    public boolean deleteModelImage(int id) {
        String fileName =  dao.getModelImagePath(id);
        String file = fileName.substring(fileName.lastIndexOf("/")+1);
        dao.addPhoto("",id);
        File file1 = new File("D:/javaText/sbm2/controller/src/main/resources/image"+file);
        return file1.delete();
    }
}
