package dao.controller.mqMessege.listener;

import dao.controller.mqMessege.conf.RabbitMQConfig;
import dao.controller.mqMessege.entity.Message;
import dao.controller.mqMessege.factory.IDaofactroy;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.net.InetAddress;

@Component
public class TextListener {

    @Autowired
    IDaofactroy iDaofactroy;
    @RabbitHandler
    @RabbitListener(queues = {RabbitMQConfig.QUEUE_A})
    public void queueCListener(byte[] bytes) throws Exception{
        String addr = InetAddress.getLocalHost().getHostAddress()+"9001";
        Message message = getMessage(bytes);
        if(!addr.equals(message.getId())){
            iDaofactroy.setMessage(message);
            iDaofactroy.performance();
        }
    }

    public Message getMessage(byte[] bytes)throws Exception{
        Message message = null;
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        message = (Message) objectInputStream.readObject();
        byteArrayInputStream.close();
        objectInputStream.close();
        return message;
    }
}
