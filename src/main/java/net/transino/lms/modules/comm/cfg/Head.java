package net.transino.lms.modules.comm.cfg;

import lombok.Getter;
import lombok.Setter;

/**
 * <pre>
 * 1	5	交易代码（右补空格）
 * 6	15	空格
 * 21	1	空格
 * 22	5	空格
 * 27	4	终端号（无，给空格）
 * 31	15	终端ip(可空格)
 * 46	1	交易模式,‘0’ 正常 ‘1’ 复核申请 ‘2’ 复核
 * 47	1	‘0’ 正常交易 ‘1’ 系统异常
 * 48	1	空格
 * 49	1	通讯类型‘T’  tcp
 * 50	1	空格
 * 51	1	渠到类型。 ‘0’，前台交易；‘4’中间业务；
 * 52	1	交易包类型。  ‘0’、‘ ’表示普通交易；
 * 53	4	空格
 * 57	4	报文体(c)的长度
 * </pre>
 *
 * @author veggieg
 * @since 5.0
 */
@Getter
@Setter
public class Head {
    private String tradeCode;
    private String clientNo;
    private String clientIP;
    private String tradeMode;
    private String tradeStatus;
    private String type;
    private String tradeType;
    private String bodyType;

    @Override
    public String toString() {
        String head = "";
        
        head = head + String.format("%-5s", this.tradeCode);
        head = head + String.format("%-21s", "");
        head = head + String.format("%-4s", this.clientNo);
        head = head + String.format("%-15s", this.clientIP);
        head = head + String.format("%-1s", this.tradeMode);
        head = head + String.format("%-1s", this.tradeStatus);
        head = head + String.format("%-1s", "");
        head = head + String.format("%-1s", this.type);
        head = head + String.format("%-1s", "");
        head = head + String.format("%-1s", this.tradeType);
        head = head + String.format("%-1s", this.bodyType);
        head = head + String.format("%-4s", "");

        return head;
    }
}
