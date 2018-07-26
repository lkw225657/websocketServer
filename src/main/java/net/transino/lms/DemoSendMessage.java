package net.transino.lms;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import lombok.extern.slf4j.Slf4j;
import net.transino.core.Consts;
import net.transino.core.util.Util;
import net.transino.lms.modules.comm.cfg.DefaultBody;
import net.transino.lms.modules.comm.cfg.Head;
import net.transino.lms.modules.comm.client.AbstractMessageAdapter;
import net.transino.lms.modules.comm.service.ICommTradeService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static net.transino.core.Consts.CRLF;

@Slf4j
public class DemoSendMessage extends AbstractMessageAdapter {
    public DemoSendMessage(String tradeCode, DefaultBody defaultBody, Object bodyBean, Map<String, List<?>> fileBean, ICommTradeService service) throws Exception {
//        super(tradeCode, bodyBean, fileBean);
        super(tradeCode,defaultBody, bodyBean,fileBean);
    }

    public void setHead(String head){
        this.getInput().setHead(head);
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
    protected void makeReqBody(DefaultBody body,Object bodyBean) throws Exception {
        Map<String, Object> bodyMap = JSON.parseObject(JSON.toJSONString(body), new TypeReference<HashMap<String, Object>>() {
        });
        bodyMap.putAll((Map<? extends String, ?>) bodyBean);
        String strBody = JSON.toJSONString(bodyMap);
//        String strBody = "{\n" +
//                "\"TRANSCODE\":\"1001\",\n" +
//                "\"OCCURDATE\":\"20180425\",\n" +
//                "\"OCCURTIME\":\"091800\",\n" +
//                "\"OPERATOR\": \"0000\",\n" +
//                "\"REVIEWER\": \"\", \n" +
//                "\"AUDITOR\":  \"\",\n" +
//                "\"HOSTSERIAL\":   0,\n" +
//                "\"BRANCHSERIAL\": 1,\n" +
//                "\"OPENBRANCH\":   \"000000\",\n" +
//                "\"OPENBRANCH\":   \"000000\",\n" +
//                "\"RESPCODE\": \"0000\",\n" +
//                "\"TERMINAL\": \" \",\n" +
//                "\"FILEFLAG\": \"0\",\n" +
//                "\"RESPINFO\": \"\",\n" +
//                "\"CONTRACTNO\":   \"111111\",\n" +
//                "\"SEQUENCE\": 1,\n" +
//                "\"CUSTOMERNO\":   \"10000000018\",\n" +
//                "\"CUSTNAME\": \"测试\",\n" +
//                "\"CUSTTYPE\": \"1\",\n" +
//                "\"CURRENCY1\":\"CNY\",\n" +
//                "\"ACCOUNT1\":\"HQCKZH0000000001\",\n" +
//                "\"BALANCE3\": 10000000.00,\n" +
//                "\"BALANCE1\": 10000000.00,\n" +
//                "\"BALANCE2\": 5000000.00,\n" +
//                "\"TERMTYPE\": \"1\",\n" +
//                "\"BIZCODE\":  \"101101101\",\n" +
//                "\"AMOUNT1\":  888.00,\n" +
//                "\"ABSTRACT\": \"穷死了\"\n" +
//                "}";
//        String strBody = "{\"TERMINAL\":\"9999\",\"CUSTTYPE\":\"1\",\"INTEREST3\":0.0,\"INTEREST2\":0.0,\"OCCURDATE\":\"20180425\",\"CURRENCY1\":\"CNY\",\"INTEREST1\":3.625" +
//                ",\"CONTRACTNO\":\"租赁还款计划测试1\",\"BIZDATE2\":\"20190704\",\"BIZDATE1\":\"20180705\",\"SEQUENCE\":1,\"CUSTNAME\":\"沿河榕信房地产开发有限公司\",\"PERIODNUM\":0,\"OCCURTIME\":\"181454\",\"BALANCE2\":10000.0,\"DUTYFREE\":\"2\",\"BALANCE1\":10000.0,\"TRANSC" +
//                "ODE\":\"1002\",\"BIZCODE\":\"101401001\",\"SUBJECT1\":\"\",\"TERMTYPE\":\"1\",\"INTMETHOD\":\"12\",\"OPERATOR\":\"0000\",\"CUSTOMERNO\":\"10000000024\",\"FILEFLAG\":\"1\"}";
        this.getInput().setBody(strBody);
    }

    @Override
    protected void makeReqFiles(Map<String, List<?>> fileBean) throws Exception {
        if (fileBean == null) {return;};
        List<String> list = new ArrayList<String>(2);
        list.add("1|2|12121212|21212121|200000.00|00000000000000000000|2018-08-08|\n");
        list.add("3|4|34343434|43434343|300000.00|88888888888888888888|2018-08-08|");
//        list.add("1|2313.0|12.0|20180720|20180810|");
//        for (Map.Entry entry: fileBean.entrySet()){
//
//            this.getInput().getFiles().put(entry.getKey().toString(), (List<String>) entry.getValue());
//        }
        this.getInput().getFiles().put("100003456",list);
    }
}