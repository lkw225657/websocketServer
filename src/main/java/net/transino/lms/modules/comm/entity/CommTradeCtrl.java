package net.transino.lms.modules.comm.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * @author veggieg
 * @since 5.0
 */
@Getter
@Setter
@ToString
public class CommTradeCtrl  {
    private static final long serialVersionUID = -8025317916289118845L;

    private String tradeCode;

    private IOType io;

    /**
     * 属性名称
     */
    private String fieldName;

    private String ffield;

    /**
     * @see java.util.Formatter
     */
    private String ftype;

    private Integer fprecision;

    private Integer fscale;

    private String tfield;

    private String ttype;

    private Integer tprecision;

    private Integer tscale;

    private String bnullable;

    private String dataDefault;

    private String param;
}
