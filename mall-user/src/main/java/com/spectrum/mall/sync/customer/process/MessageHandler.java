package com.spectrum.mall.sync.customer.process;

import com.spectrum.mall.sync.customer.channel.InputChannel;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.cloud.stream.messaging.Source;

/**
 * @author oe_qinzuopu
 */
@EnableBinding(value={InputChannel.class})
public class MessageHandler {


    public void msgProcess() {

    }
}
