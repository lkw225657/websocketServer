package net.transino.lms.modules.comm.server;

import com.alibaba.fastjson.JSON;
import net.transino.lms.DemoSendMessage;
import net.transino.lms.modules.comm.cfg.DefaultBody;
import net.transino.lms.modules.comm.client.AbstractMessageAdapter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lee
 * @since 5.0
 */
@Component
public class DefaultMessageProcessor extends AbstractMessageProcessor {
    @Override
    public AbstractMessageAdapter execServiceMethod (String transCode, String body, Map<String,List<?>> files){
        DefaultBody defaultBody = JSON.parseObject(body, DefaultBody.class);
        Map<String, Object> bodyMap = new HashMap<>(4);
        bodyMap.put("ACCNUMBER","10000000024");
        bodyMap.put("CNACCNAME","沿河榕信房地产开发有限");
        bodyMap.put("BALANCE1",1000.8);
        bodyMap.put("RESPCODE","0000");

        try {
            return  new DemoSendMessage(transCode,defaultBody, bodyMap,files,null);
        } catch (Exception e) {

        }
        return null;
    }
}