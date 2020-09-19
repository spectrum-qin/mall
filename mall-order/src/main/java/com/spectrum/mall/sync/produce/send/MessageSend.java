package com.spectrum.mall.sync.produce.send;

import com.spectrum.mall.common.ServiceException;
import com.spectrum.mall.common.user.UserExceptionCode;
import com.spectrum.mall.sync.produce.channel.OutputChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;

/**
 * @author oe_qinzuopu
 */
@Slf4j
@EnableBinding({OutputChannel.class})
public class MessageSend {

    @Autowired
    private OutputChannel outputChannel;

    public void msgSend(String msg) {

        try {
            outputChannel.outputChannel().send(MessageBuilder.withPayload(msg).build());
            log.info("推送消息成功");
        } catch (Exception e) {
            throw new ServiceException(UserExceptionCode.USER_UPDATE);
        }

    }
}
