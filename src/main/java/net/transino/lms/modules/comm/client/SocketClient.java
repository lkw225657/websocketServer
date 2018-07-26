package net.transino.lms.modules.comm.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;
import net.transino.core.util.PropertiesLoader;
import net.transino.lms.modules.comm.FileDecoderHandler;
import net.transino.lms.modules.comm.FileEncoderHandler;
import net.transino.lms.modules.comm.MessageDecoderHandler;
import net.transino.lms.modules.comm.MessageEncoderHandler;
import net.transino.lms.server.BytePrintHandler;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author veggieg
 * @since 5.0
 */
@Component
@Slf4j
public class SocketClient {
    private static final String HOST = "127.0.0.1";//"11.6.8.15";
    private static final int PORT = 60000;//55004;
    private static final int GOURP_PARENT = 1;

    public int send(AbstractMessageAdapter adapter) {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup(GOURP_PARENT);

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventLoopGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new MessageEncoderHandler());
                            pipeline.addLast(new FileEncoderHandler());
                            pipeline.addLast(new SocketClientHandler(adapter));
//                            pipeline.addLast(new MessageDecoderHandler(adapter));
//                            pipeline.addLast(new FileDecoderHandler(adapter));
                        }
                    });

            ChannelFuture channelFuture = bootstrap.connect(HOST, PORT).sync();

            channelFuture.channel().closeFuture().sync();
            adapter.end();
            log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>> 服务器连接结束");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            eventLoopGroup.shutdownGracefully();
        }
        return 0;
    }

//    public static void main(String[] args)  {
//        ApplicationContext ctx = null;
//        try {
//            ctx = new ClassPathXmlApplicationContext("spring-context.xml");
//            ICommTradeService service = BeanFactory.getBean(ICommTradeService.class);
//
//            AbstractMessageAdapter adapter = new DefaultMessageAdapter("1001", getLmap(), getFmap());
//            System.out.println(adapter.getOutput());
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            ((ClassPathXmlApplicationContext) ctx).close();
//        }
//
//
//    }

    private Map<String, String> getLmap() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("transcode", "1001");
        map.put("occurdate", "20180425");
        map.put("occurtime", "091800");
        map.put("operator", "0000");
        map.put("reviewer", "");
        map.put("auditor", "");
        map.put("hostserial", "0");
        map.put("branchserial", "1");
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

    private Map<String, List<Object>> getFmap() {
        Map<String, List<Object>> map = new HashMap<>();
        List<Object> list = new ArrayList<>(2);
        list.add(getFmap1());
        list.add(getFmap2());
        map.put("1001_file.txt", list);
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
}
