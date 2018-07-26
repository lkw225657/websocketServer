package net.transino.lms.modules.comm.server;

import net.transino.lms.modules.comm.client.AbstractMessageAdapter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author lee
 * @since 5.0
 */
@Component
public abstract class AbstractMessageProcessor {
    public abstract AbstractMessageAdapter execServiceMethod (String transCode, String body, Map<String,List<?>> files);

    public static AbstractMessageProcessor newInstance(String transCode) {
        AbstractMessageProcessor processor = null;
        if ("9902".equals(transCode)){
            processor = new DefaultMessageProcessor();
        }
        return processor;
    }
}