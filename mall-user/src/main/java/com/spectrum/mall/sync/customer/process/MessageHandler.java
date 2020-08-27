package com.spectrum.mall.sync.customer.process;

import com.spectrum.mall.sync.customer.channel.InputChannel;
import com.spectrum.mall.sync.customer.constance.InputChannelConstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.Message;

/**
 * @author oe_qinzuopu
 */
@EnableBinding({Sink.class, InputChannel.class})
public class MessageHandler {

    @StreamListener(InputChannelConstance.INPUT_CHANNEL)
    public void msgProcess(Message<String> message) {

        System.out.println(message.getPayload());
    }
}
