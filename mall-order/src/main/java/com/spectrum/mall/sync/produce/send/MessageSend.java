package com.spectrum.mall.sync.produce.send;

import com.spectrum.mall.sync.produce.channel.OutputChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

/**
 * @author oe_qinzuopu
 */
@Component
@EnableBinding({Source.class, OutputChannel.class})
public class MessageSend {

    @Autowired
    private OutputChannel outputChannel;

    public void msgSend(String msg) {

        outputChannel.outputChannel().send(MessageBuilder.withPayload(msg).build());
    }
}
