package net.transino.lms.modules.comm.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * @author veggig
 * @since 5.0
 */
@Getter
@Setter
@ToString
public class CommTradeTxt  {
    private static final long serialVersionUID = 6615194409148396056L;

    private String tradeCode;

    private int batch;

    private IOType io;

    private String fieldName;

    private String field;

    private String type;

    private Integer precision;

    private Integer scale;

    private String param;

    private int odr;
}
