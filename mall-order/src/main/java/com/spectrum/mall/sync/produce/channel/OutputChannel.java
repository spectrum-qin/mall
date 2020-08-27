package com.spectrum.mall.sync.produce.channel;

import com.spectrum.mall.sync.produce.constance.OutputChannelConstance;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Component;

/**
 * @author oe_qinzuopu
 */
public interface OutputChannel {

    @Output(OutputChannelConstance.OUTPUT_CHANNEL)
    MessageChannel outputChannel();
}
