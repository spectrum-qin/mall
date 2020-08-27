package com.spectrum.mall.sync.customer.channel;

import com.spectrum.mall.sync.customer.constance.OutputChannelConstance;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * @author oe_qinzuopu
 */
public interface OutputChannel {

    @Output(OutputChannelConstance.OUTPUT_CHANNEL)
    MessageChannel outputChannel();
}
