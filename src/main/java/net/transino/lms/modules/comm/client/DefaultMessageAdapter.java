package net.transino.lms.modules.comm.client;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import jodd.bean.BeanUtil;
import lombok.extern.slf4j.Slf4j;
//import net.transino.core.BeanFactory;
import net.transino.core.Consts;
import net.transino.core.util.DateUtil;
import net.transino.core.util.Util;
//import net.transino.core.util.bean.BeanUtil;
import net.transino.lms.modules.comm.CommException;
import net.transino.lms.modules.comm.cfg.DefaultBody;
import net.transino.lms.modules.comm.cfg.Head;
import net.transino.lms.modules.comm.entity.CommTradeCtrl;
import net.transino.lms.modules.comm.entity.CommTradeTxt;
import net.transino.lms.modules.comm.entity.IOType;
import net.transino.lms.modules.comm.service.ICommTradeService;

import javax.xml.stream.events.Comment;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author veggieg
 * @since 5.0
 */
@Slf4j
public class DefaultMessageAdapter extends AbstractMessageAdapter {
//    public static ICommTradeService commTradeService = BeanFactory.getBean(ICommTradeService.class);
public static ICommTradeService commTradeService;
    public DefaultMessageAdapter(String tradeCode, DefaultBody defaultBody, Object bodyObject, Map<String, List<?>> fileList) throws Exception {
        super(tradeCode, defaultBody, bodyObject, fileList);
    }

    @Override
    protected void makeReqHead() {
        Head head = new Head();
        // 交易代码（右补空格）
        head.setTradeCode(this.tradeCode);
        // 终端号（无，给空格）
        head.setClientNo(Consts.SPACE);
        // 终端ip(可空格)
        head.setClientIP(Consts.SPACE);
        // 交易模式,‘0’ 正常 ‘1’ 复核申请 ‘2’ 复核
        head.setTradeMode("0");
        // ‘0’ 正常交易 ‘1’ 系统异常
        head.setTradeStatus("0");
        // 通讯类型‘T’  tcp
        head.setType("T");
        // 渠到类型。 ‘0’，前台交易；‘4’中间业务；
        head.setTradeType("0");
        // 交易包类型。  ‘0’、‘ ’表示普通交易；
        head.setBodyType("0");

        String headc = head.toString();
        this.getInput().setHead(headc);
        log.debug("构建报文头: [{}]", headc);
    }

    @Override
    protected void makeReqBody(DefaultBody defaultBody, Object bodyObject) throws Exception {
        Map<String, Object> bodyMap = JSON.parseObject(JSON.toJSONString(defaultBody), new TypeReference<HashMap<String, Object>>() {
        });

        List<CommTradeCtrl> ctrlList = commTradeService.selectCtrlByTradeCodeAndIo(tradeCode, IOType.IN);
        for (CommTradeCtrl ctrl : ctrlList) {
            Object property = BeanUtil.pojo .getProperty(bodyObject, ctrl.getFfield());

            /// 规则
            switch (ctrl.getFtype()) {
                case "T": {
                    if (!(property instanceof Date)) {
                        throw new CommException(ctrl.getFfield() + " 字段不是有效的日期类型");
                    }

                    if (Util.textIsBlank(ctrl.getParam())) {
                        throw new CommException(ctrl.getFfield() + " 字段日期格式没有配置");
                    }

                    bodyMap.put(ctrl.getTfield(), DateUtil.toString((Date) property, ctrl.getParam()));

                    break;
                }
                default: {
                    bodyMap.put(ctrl.getTfield(), property);

                    break;
                }
            }
        }

        String bodyc = JSON.toJSONString(bodyMap);
        this.getInput().setBody(bodyc);
        log.debug("构建报文体: [{}]", bodyc);
    }

    @Override
    protected void makeReqFiles(Map<String, List<?>> fileList) throws Exception {
        List<String> lineList = null;

        if (fileList == null) {
            return;
        }

        List<CommTradeTxt> txtList = commTradeService.selectTxtByTradeCodeAndBatchAndIo(this.tradeCode, 1, IOType.IN);
        for (Map.Entry<String, List<?>> entry : fileList.entrySet()) {
            lineList = new LinkedList<>();

            List<String> propertyList = null;

            String fileName = entry.getKey();
            int i = 0;
            for (Object object : entry.getValue()) {
                propertyList = new LinkedList<>();

                for (CommTradeTxt txt : txtList) {
                    Object property = BeanUtil.pojo.getProperty(object, txt.getField());
                    switch (txt.getType()) {
                        case "T": {
                            if (!(property instanceof Date)) {
                                throw new CommException("数据不是有效的日期类型");
                            }

                            if (Util.textIsBlank(txt.getParam())) {
                                throw new CommException("日期格式没有配置");
                            }

                            propertyList.add(DateUtil.toString((Date) property, txt.getParam()));

                            break;
                        }
                        default: {
                            propertyList.add(property.toString());

                            break;
                        }
                    }
                }

                /// 字段分割
                String line = Util.join(propertyList, Consts.PIPE) + Consts.PIPE;
                if (i++ == 0) {
                    lineList.add(line);
                } else {
                    /// 行分割
                    lineList.add(Consts.CRLF + line);
                }
            }

            this.getInput().getFiles().put(fileName, lineList);
        }
    }
}
