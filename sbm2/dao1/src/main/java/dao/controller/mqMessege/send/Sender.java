package dao.controller.mqMessege.send;

import dao.controller.mqMessege.entity.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;

@Component
public class Sender {

    private static final Logger LOGGER = LoggerFactory.getLogger(Sender.class);

    @Autowired
    RabbitTemplate rabbitTemplate;

    /**
     *     序列化对象
     */

    public void send(Message message){
        byte[] oos = null;

        //申请内存地址存放io输出流
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            //将objectOutputStream输出流绑定到bos内存中
            ObjectOutputStream objectOutputStream =  new ObjectOutputStream(bos);
            //将student序列化
            objectOutputStream.writeObject(message);
            //写入刷新
            objectOutputStream.flush();
            //获取值
            oos = bos.toByteArray();
            //关闭io
            objectOutputStream.close();
            bos.close();
            rabbitTemplate.convertAndSend("SPRING_FANOUT1","",oos);
            rabbitTemplate.convertAndSend("SPRING_FANOUT","",oos);
        }catch (Exception e){
            System.out.println("序列化出错");
        }
    }
}
