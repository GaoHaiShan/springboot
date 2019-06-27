package controller.demo.services.serviceImpl;

import controller.demo.dao.IDao;
import controller.demo.entity.Study;
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

    /**
     *   获取某一个类型的知识点，并将知识点插入缓存区
     */

    @Override
    public List<Study> getModelByStudy(String userName, String type, int[] index) {
        List<Study> studys;
        String key = "getModelByStudy"+type+userName;
        // 缓存存在
        ListOperations<String,Study> operations = redisTemplate.opsForList();
        index[1] = operations.range(key,0,-1).size();
        if(operations.range(key,0,-1).size() > 0){
            return getStudy(key,index);
        }
        else {
            //用户同时访问某个列表没有缓存时，只允许一个用户访问数据，其他用户读取已更改的缓存内容
            synchronized (this) {
                studys = dao.getModelByStudy(userName,type);
                operations.rightPushAll(key, studys);
                LOGGER.info(key+":"+key+" 插入缓存 ");
                if(studys.size()>10) {
                    return studys.subList(0, 10);
                }
                else {
                    return studys;
                }
            }
        }
    }

    /**
     *     获取某个知识点的内容
     */

    @Override
    public Study getModelByStudyTxt(String studyname, String type, String username) {
        return dao.getModelByStudyTxt(studyname,type,username);
    }

    /**
     *添加知识点
     */
    @Override
    public Boolean addModelByStudy(Study study) {
        String key = "getModelByStudy"+study.getType()+study.getUsername();
        String key1 = "getModelAllByStudy" + study.getUsername();
        ListOperations<String,Study> operations = redisTemplate.opsForList();
        if (dao.getModelByStudyName(study.getId()) > 0) {
           dao.deleteModelByStudyname(study.getId());
        }
        if (dao.addModelByStudy(study)) {
            operations.rightPush(key, study);
            System.out.println(key+"添加："+study.toString()+"成功");
            operations.rightPush(key1, study);
            System.out.println(key1+"添加："+study.toString()+"成功");
            return true;
        }else{
            return false;
        }
    }
    /**
     *在添加知识点的时候判断知识点是否存在
     */
    @Override
    public int getModelByStudyName(int id) {
        return dao.getModelByStudyName(id);
    }

    /**
     *删除知识点
     */
    @Override
    public Boolean deleteModelByStudyname(Study study) {
        String key = "getModelByStudy"+study.getType()+study.getUsername();
        String key1 = "getModelAllByStudy" + study.getUsername();
        String fileName =  dao.getModelImagePath(study.getId());
        if(fileName!=null&&fileName!="") {
            String file = fileName.substring(fileName.lastIndexOf("/") + 1);
            new File("D:/javaText/sbm2/controller/src/main/resources/image/" + file).delete();
        }
        deleteModelByStudyName(study.getId(),key);
        deleteModelByStudyName(study.getId(),key1);
        return dao.deleteModelByStudyname(study.getId());
    }

    /**
     *查询知识点所有类型
     */
    @Override
    public List<String> getModelType(String userName) {
        return dao.getModelType(userName);
    }

    /**
     *获取所有知识点，并返回十个，将全部知识点插入缓存
     */
    @Override
    public List<Study> getModelAllByStudy(String userName,int[] index) {
        String key = "getModelAllByStudy"+userName;
        List<Study> studys;
        ListOperations<String,Study> operations = redisTemplate.opsForList();
        index[1] = operations.range(key,0,-1).size();
        // 缓存存在
        if(operations.range(key,0,-1).size() > 0){
            return getStudy(key,index);
        }
        else {
            //用户同时访问某个列表没有缓存时，只允许一个用户访问数据，其他用户读取已更改的缓存内容
            synchronized (this) {
                studys = dao.getModelAllByStudy(userName);
                operations.rightPushAll(key, studys);
                LOGGER.info(key+":"+key+" 插入缓存 ");
                if(studys.size()>10) {
                    return studys.subList(0, 10);
                }
                else {
                    return studys;
                }
            }
        }
    }

    /**
     * 获取最大id号
     */
    @Override
    public int getModelById() {
        return dao.getModelById();
    }

    /**
     *添加知识点类型
     */
    @Override
    public boolean addType( String username,String type) {
        return dao.addType(username,type);
    }

    /**
     *添加图片，并修改缓存区
     */
    @Override
    public boolean addPhoto(CommonsMultipartFile image,Study study)  {
        String imageName=null;
        String key = "getModelByStudy"+study.getType()+study.getUsername();
        String key1 = "getModelAllByStudy" + study.getUsername();
        try {
            //添加图片
            if(image.getSize()==0) {
                return false;
            }
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss");
            File f = new File("D:/javaText/sbm2/controller/src/main/resources/image/");
            String suffix = image.getOriginalFilename().
                    substring(image.getOriginalFilename().lastIndexOf(".") + 1);
            imageName = simpleDateFormat.format(new Date())+"."+suffix;
            FileUtils.copyInputStreamToFile(image.getInputStream(),new File(f,imageName));
            //修改缓存区
            addPhoto("/" +imageName,study.getId(),key);
            addPhoto("/" +imageName,study.getId(),key1);
        }catch (IOException e){
            System.out.println("eorrs1");
        }
        return dao.addPhoto("/" +imageName,study.getId());
    }

    /**
     *删除图片并修改缓存区
     */
    @Override
    public boolean deleteModelImage(Study study) {
        String key = "getModelByStudy"+study.getType()+study.getUsername();
        String key1 = "getModelAllByStudy" + study.getUsername();
        boolean f = false;
        //删除图片
       String fileName = dao.getModelImagePath(study.getId());
       if(fileName!=null&&fileName!="") {
           String file = fileName.substring(fileName.lastIndexOf("/") + 1);
           dao.addPhoto("", study.getId());
           File file1 = new File("D:/javaText/sbm2/controller/src/main/resources/image/" + file);
           f = file1.delete();
       }
        //修改缓存区
        addPhoto("" ,study.getId(),key);
        addPhoto("" ,study.getId(),key1);
        return f;
    }

    /**
     *得到某个id知识点的基本信息，不返回知识点内容
     */
    @Override
    public Study getModelByStudy1(int id) {
        return dao.getModelByStudy1(id);
    }

    /**
     *修改缓存区图片地址
     */
    private void addPhoto(String path,int id,String key){
        ListOperations<String,Study> operations = redisTemplate.opsForList();
        Study study1;
        int count;
        for (int i = 0;i < operations.range(key,0,-1).size();i++){
            count = operations.range(key,0,-1).get(i).getId();
            if(count==id){
                study1=operations.range(key,0,-1).get(i);
                operations.remove(key,0,study1);
                study1.setPhoto(path);
                operations.leftPush(key,study1);
                System.out.println("更改"+key+"成功");
                break;
            }
        }
    }

    /**
     *修改缓存区图片地址
     */
    private void deleteModelByStudyName(int id,String key){
        ListOperations<String,Study> operations = redisTemplate.opsForList();
        Study study1;
        int count;
        for (int i = 0;i < operations.range(key,0,-1).size();i++){
            count = operations.range(key,0,-1).get(i).getId();
            if(count==id){
                study1=operations.range(key,0,-1).get(i);
                operations.remove(key,0,study1);
                System.out.println("删除"+key+"成功");
                break;
            }
        }
    }
    /**
     *查询缓存区对应的知识点列表
     */
    private List<Study> getStudy(String key, int[] index){
        List<Study> studys;
        int count = 0;
        // 缓存存在
        ListOperations<String,Study> operations = redisTemplate.opsForList();
        studys = new LinkedList<>();
        for (Study study:operations.range(key,0,-1)) {
            if(count>=index[0]&&count<index[0]+10) {
                studys.add(study);
                LOGGER.info(key + ": 从缓存中获取了知识 >> " + study.toString());
            }
            count++;
        }
        return studys;
    }
}
