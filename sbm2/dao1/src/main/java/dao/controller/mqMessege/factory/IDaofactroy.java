package dao.controller.mqMessege.factory;

import dao.controller.dao.IDao;
import dao.controller.entity.Study;
import dao.controller.mqMessege.entity.Message;
import org.springframework.stereotype.Component;


import javax.annotation.Resource;
@Component
public class IDaofactroy {

    @Resource
    IDao iDao;

    private Message message1;

    public void setMessage(Message message) {
        this.message1 = message;
    }

    public IDaofactroy(){}
    public IDaofactroy(Message message) {
        this.message1 = message;
    }

    public void performance() {
        String param1;
        String param2;
        String model0="addModelByStudy";
        String model1="deleteModelByStudyname";
        String model2 = "addType";
        String model3="addPhoto";
        if (model0.equals(message1.getModelName())) {
            iDao.addModelByStudy((Study) message1.getParam().get("study"));
        }

        if (model1.equals(message1.getModelName())) {
            param1 = message1.getParam().get("id").toString();
            try {
                iDao.deleteModelByStudyname(Integer.valueOf(param1));
            }catch (Exception e){
                System.out.println("删除知识点失败");
            }
        }

        if (model2.equals(message1.getModelName())) {
            param1=message1.getParam().get("username").toString();
            param2 = message1.getParam().get("type").toString();
            System.out.println(param1+param2);
            try {
                iDao.addType(param1,param2);
            }catch (Exception e){
                System.out.println("类型添加失败");
            }
        }

        if (model3.equals(message1.getModelName())){
            param1 = message1.getParam().get("imagePath").toString();
            param2 =message1.getParam().get("id").toString();
            if ("".equals(param1)||param1==null){
                param1 = "";
            }
            try {
                iDao.addPhoto(param1, Integer.valueOf(param2));
            }catch (Exception e){
                System.out.println("图片添加失败");
            }
        }

    }
}
