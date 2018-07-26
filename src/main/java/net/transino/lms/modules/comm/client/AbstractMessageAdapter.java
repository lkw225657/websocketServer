package net.transino.lms.modules.comm.client;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import lombok.extern.slf4j.Slf4j;
import net.transino.core.Consts;
import net.transino.core.util.Collections3;
import net.transino.core.util.PropertiesLoader;
import net.transino.lms.modules.comm.cfg.DefaultBody;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author veggieg
 * @since 5.0
 */
@Slf4j
public abstract class AbstractMessageAdapter {
    private static final String SND_DIR = "C:\\word\\send";
    private static final String RSV_DIR = "C:\\word\\receive";
    private static final String RESP_CODE = "RESPCODE";

//    static {
//        String archivePath = PropertiesLoader.getProperty(Consts.CFG_ARCHIVE_PATH);
//        SND_DIR = new File(archivePath).getAbsolutePath() + Consts.FILE_SEPARATOR + "snd";
//        RSV_DIR = new File(archivePath).getAbsolutePath() + Consts.FILE_SEPARATOR + "rsv";
//    }

    protected String tradeCode;
    private String respCode;

    /**
     * 输入
     */
    private Packet input = new Packet();

    /**
     * 输出
     */
    private Packet output = new Packet();

    /**
     * @param bodyObject 包体
     * @param fileList   文件名->文件内容实体
     * @throws Exception
     */
    protected AbstractMessageAdapter(String tradeCode, DefaultBody defaultBody, Object bodyObject, Map<String, List<?>> fileList) throws Exception {
        this.tradeCode = tradeCode;

        this.init(defaultBody, bodyObject, fileList);
    }

    protected void init(DefaultBody defaultBody, Object bodyObject, Map<String, List<?>> fileList) throws Exception {
        this.makeReqHead();
        this.makeReqBody(defaultBody, bodyObject);
        this.makeReqFiles(fileList);
        this.saveReqFiles();
    }

    /**
     * 构建 请求报文头
     */
    protected abstract void makeReqHead();

    /**
     * 构建 请求报文体
     *
     * @param defaultBody DefaultBody
     * @param bodyObject  object
     * @throws Exception
     */
    protected abstract void makeReqBody(DefaultBody defaultBody, Object bodyObject) throws Exception;

    /**
     * 构建 请求文件
     *
     * @param fileList
     * @throws Exception
     */
    protected abstract void makeReqFiles(Map<String, List<?>> fileList) throws Exception;

    public Packet getInput() {
        return this.input;
    }

    public Packet getOutput() {
        return this.output;
    }

    public String getRespCode() {
        return this.respCode;
    }

    /**
     *  保存请求报文文件
     */
    private void saveReqFiles() {
        Map<String, List<String>> files = this.getInput().getFiles();
        if (Collections3.isEmpty(files)) {
            return;
        }
        log.info("---> 保存请求报文文件");
        files.forEach((String k, List<String> v) -> {
            File file = new File(SND_DIR + Consts.FILE_SEPARATOR + k);
            writeFile(v, file);
        });
    }

    /**
     *  保存返回报文文件
     */
    private void saveRespFiles() {
        Map<String, List<String>> files = this.getOutput().getFiles();
        if (Collections3.isEmpty(files)) {
            return;
        }
        log.info("---> 保存返回报文文件");
        files.forEach((String k, List<String> v) -> {
            File file = new File(RSV_DIR + Consts.FILE_SEPARATOR + k);
            writeFile(v, file);
        });
    }

    private void writeFile(List<String> v, File file) {
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
            for (String txt : v) {
                randomAccessFile.writeBytes(txt);
            }
            randomAccessFile.close();
        } catch (java.io.IOException e) {
            log.warn(e.getLocalizedMessage());
        }
    }

    /**
     *  数据传输完毕
     */
    void end() {
        this.input = new Packet();
        this.respCode = "";
        Map<String, Object> map = this.output.getBodyMap();
        if (map != null && map.containsKey(RESP_CODE)) {
            this.respCode = String.valueOf(map.get(RESP_CODE));
        }
        log.info("---> 返回报文响应代码:{}", this.getRespCode());
        this.saveRespFiles();
    }

    /**
     * 数据包
     */
    public class Packet {
        private String head;
        private String body;
        private Map<String, List<String>> files = new HashMap<>();

        public String getHead() {
            return head;
        }

        public void setHead(String head) {
            this.head = head;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

        public Map<String, List<String>> getFiles() {
            return files;
        }

        public void setFiles(Map<String, List<String>> files) {
            this.files = files;
        }

        public Map<String, Object> getBodyMap() {
            try {
                return JSON.parseObject(this.body, new TypeReference<HashMap<String, Object>>() {
                });
            }catch (Exception e){
                return null;
            }
        }
    }
}
