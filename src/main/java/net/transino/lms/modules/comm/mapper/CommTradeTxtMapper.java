package net.transino.lms.modules.comm.mapper;


import net.transino.lms.modules.comm.entity.CommTradeTxt;
import net.transino.lms.modules.comm.entity.IOType;



import java.util.List;

/**
 * @author veggieg
 */

public interface CommTradeTxtMapper  {
    List<CommTradeTxt> selectByTradeCodeAndBatchAndIo(String tradeCode,  Integer batch,  IOType io);
}
