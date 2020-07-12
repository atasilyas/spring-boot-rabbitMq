package com.atasilyas.rabbitmq.config;

import com.atasilyas.rabbitmq.model.User;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

import java.io.*;

@Component
public class JMSReceiver implements ChannelAwareMessageListener {

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        try {
            byte[] byteArray = message.getBody();
            User user = (User) getUserObjectFromArray(byteArray);

        /*
        act gonderdikten sonra bu mesajin producer tarafindan alindigi bilgisi
        queue ye gonerilir ve bu mesaj silinir eger gonderilmese silinmez.
         */
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);

            System.out.println("Consumed object : " + user);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private Object getUserObjectFromArray(byte[] byteArray) throws IOException, ClassNotFoundException {

        ByteArrayInputStream byteArrayInputStream =  new ByteArrayInputStream(byteArray);
        ObjectInput in =  new ObjectInputStream(byteArrayInputStream);
        return in.readObject();
    }
}
