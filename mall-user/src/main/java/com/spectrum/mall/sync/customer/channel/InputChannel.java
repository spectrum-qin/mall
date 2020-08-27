package com.spectrum.mall.sync.customer.channel;

import com.spectrum.mall.sync.customer.constance.InputChannelConstance;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * @author oe_qinzuopu
 */
public interface InputChannel {

    @Input(InputChannelConstance.INPUT_CHANNEL)
    SubscribableChannel inputChannel();
}
