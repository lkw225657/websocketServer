package net.transino.lms.modules.comm.mapper;


import net.transino.lms.modules.comm.entity.CommTradeCtrl;
import net.transino.lms.modules.comm.entity.IOType;



import java.util.List;

/**
 * @author veggieg
 * @since 5.0
 */

public interface CommTradeCtrlMapper  {
    List<CommTradeCtrl> selectByTradeCodeAndIo( String tradeCode,  IOType io);
}
