package net.transino.lms.modules.comm.cfg;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author lee
 * @since 5.0
 */
@Getter
@Setter
@ToString
public class DefaultBody {
    /**
     * 交易代码
     */
    @JSONField(name="TRANSCODE")
    private String transCode;

    /**
     * 业务发生日期
     */
    @JSONField(name="OCCURDATE")
    private String occurDate;

    /**
     * 业务发生时间
     */
    @JSONField(name="OCCURTIME")
    private String occurTime;

    /**
     * 操作员
     */
    @JSONField(name="OPERATOR")
    private String operator;

    /**
     * 复核员
     */
    @JSONField(name="REVIEWER")
    private String reviewer;

    /**
     * 审核员
     */
    @JSONField(name="AUDITOR")
    private String auditor;

    /**
     * 主机流水号
     */
    @JSONField(name="HOSTSERIAL")
    private Integer hostSerial;

    /**
     * 网点流水号
     */
    @JSONField(name="BRANCHSERIAL")
    private Integer branchSerial;

    /**
     * 开户机构
     */
    @JSONField(name="OPENBRANCH")
    private String openBranch;

    /**
     * 发送机构
     */
    @JSONField(name="TRADBRANCH")
    private String tradBranch;

    /**
     * 响应代码
     */
    @JSONField(name="RESPCODE")
    private String respCode;

    /**
     * 终端号
     */
    @JSONField(name="TERMINAL")
    private String terminal;

    /**
     * 文件下传标志
     */
    @JSONField(name="FILEFLAG")
    private String fileFlag;

    /**
     * 返回信息
     */
    @JSONField(name="RESPINFO")
    private String respInfo;
}
