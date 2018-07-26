package net.transino.lms.modules.comm.entity;

//import net.transino.core.support.mybatis.handler.IEnum;

/**
 * 输入输出类型
 *
 * @author veggieg
 * @since 5.0
 */
public enum IOType  {
    IN("I", "输入"), OUT("O", "输出");

    private String value;
    private String info;

    private IOType(String value, String info) {
        this.value = value;
        this.info = info;
    }


    public String getInfo() {
        return info;
    }


    public String getValue() {
        return value;
    }
}
