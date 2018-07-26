package net.transino.lms;

import net.transino.lms.comm.service.CommTradeServiceImpl;
import net.transino.lms.modules.comm.cfg.DefaultBody;
import net.transino.lms.modules.comm.client.DefaultMessageAdapter;
import net.transino.lms.modules.comm.client.SocketClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;

@SpringBootApplication
@RunWith(SpringJUnit4ClassRunner.class)

@TestPropertySource("classpath:application.properties")
public class ClientTest {
    @Autowired
    SocketClient client;

//    @Autowired
    CommTradeServiceImpl service;

    @Test
    public void testClientDemo() {
        DemoSendMessage demo = null;
        try {
            DefaultBody body = new DefaultBody();
            demo = new DemoSendMessage("9902", body, this.getLmap(), this.getFmap(), this.service);
        } catch (Exception e) {
            e.printStackTrace();
        }
        client.send(demo);
        System.out.println(demo.getOutput().getHead());
        System.out.println(demo.getOutput().getBody());
        System.out.println(demo.getOutput().getFiles().get("10220180705000006"));
    }

//    @Test
//    public void testClient() {
//        DefaultMessageAdapter adapter = null;
//        DefaultMessageAdapter.commTradeService = this.service;
//        try {
//            adapter = new DefaultMessageAdapter("1001", this.getLmap(), this.getFmap());
////            adapter.init(this.getLmap(),this.getFmap());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        client.send(adapter);
//    }

    private Map<String, List<?>> getFmap() {
        Map<String, List<?>> map = new HashMap<>();
        List<Object> list = new ArrayList<>(2);
        list.add(this.getFmap1());
        list.add(this.getFmap2());
        map.put("1001_file.txt", list);
        list = new ArrayList<>(2);
        list.add(this.getFmap1());
        list.add(this.getFmap2());
        map.put("1002_file.txt", list);
        return map;
    }

    private Map<String, String> getFmap1() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("odr", "BZJ00000001");
        map.put("text", "我的信贷保证金账户1");
        map.put("curType", "CNY");
        map.put("type", "1");
        map.put("amount", "200000.00");
        map.put("status", "0");
        return map;
    }

    private Map<String, String> getFmap2() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("odr", "BZJ00000002");
        map.put("text", "我的信贷保证金账户2");
        map.put("curType", "CNY");
        map.put("type", "1");
        map.put("amount", "300000.00");
        map.put("status", "0");
        return map;
    }

    private Map<String, Object> getLmap() {
        Map<String, Object> map = new HashMap<>();
        map.put("transcode", "9902");
        map.put("occurdate", new Date());
        map.put("occurtime", new Date());
        map.put("operator", "0000");
        map.put("reviewer", "");
        map.put("auditor", "");
        map.put("hostserial", "0");
        map.put("branchserial", "1");
        map.put("openbranch", "000000");
        map.put("openbranch", "000000");
        map.put("respcode", "0000");
        map.put("terminal", " ");
        map.put("fileflag", "0");
        map.put("respinfo", "");
        map.put("contractno", "111111");
        map.put("sequence", "1");
        map.put("customerno", "10000000018");
        map.put("custname", "测试");
        map.put("custtype", "1");
        map.put("currency1", "cny");
        map.put("account1", "hqckzh0000000001");
        map.put("balance3", "10000000.00");
        map.put("balance1", "10000000.00");
        map.put("balance2", "5000000.00");
        map.put("termtype", "1");
        map.put("bizcode", "101101101");
        map.put("amount1", "888.00");
        map.put("abstract", "穷死了");
        return map;
    }
}