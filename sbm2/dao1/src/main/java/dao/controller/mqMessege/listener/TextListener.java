package dao.controller.mqMessege.listener;

import dao.controller.mqMessege.conf.RabbitMQConfig;
import dao.controller.mqMessege.entity.Message;
import dao.controller.mqMessege.factory.IDaofactroy;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;

@Component
public class TextListener {

    @Autowired
    IDaofactroy iDaofactroy;
    @RabbitListener(queues = {RabbitMQConfig.QUEUE_A})
    public void queueCListener(byte[] bytes){
        String addr = null;
        try {
            addr = InetAddress.getLocalHost().getHostAddress()+"8001";
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        Message message = getMessage(bytes);
        if(!addr.equals(message.getId())){
            iDaofactroy.setMessage(message);
            iDaofactroy.performance();
        }
    }

    public Message getMessage(byte[] bytes){
        Message message = null;
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        ObjectInputStream objectInputStream = null;
        try {
            objectInputStream = new ObjectInputStream(byteArrayInputStream);
            message = (Message) objectInputStream.readObject();
            byteArrayInputStream.close();
            objectInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return message;
    }
}
