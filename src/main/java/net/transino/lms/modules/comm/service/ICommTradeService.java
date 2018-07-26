package net.transino.lms.modules.comm.service;

import net.transino.lms.modules.comm.entity.CommTradeCtrl;
import net.transino.lms.modules.comm.entity.CommTradeTxt;
import net.transino.lms.modules.comm.entity.IOType;

import java.util.List;

/**
 * @author veggieg
 * @since 5.0
 */
public interface ICommTradeService {
    List<CommTradeCtrl> selectCtrlByTradeCodeAndIo(String tradeCode, IOType io);

    List<CommTradeTxt> selectTxtByTradeCodeAndBatchAndIo(String tradeCode, Integer batch, IOType io);

    void insert(CommTradeCtrl commTradeCtrl);
}
