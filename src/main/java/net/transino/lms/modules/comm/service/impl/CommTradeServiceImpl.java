package net.transino.lms.modules.comm.service.impl;

import net.transino.lms.modules.comm.entity.CommTradeCtrl;
import net.transino.lms.modules.comm.entity.CommTradeTxt;
import net.transino.lms.modules.comm.entity.IOType;
import net.transino.lms.modules.comm.mapper.CommTradeCtrlMapper;
import net.transino.lms.modules.comm.mapper.CommTradeTxtMapper;
import net.transino.lms.modules.comm.service.ICommTradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author veggieg
 * @since 5.0
 */
//@Service("commTradeService")
public class CommTradeServiceImpl implements ICommTradeService {
    @Autowired
    private CommTradeCtrlMapper ctrlMapper;

    @Autowired
    private CommTradeTxtMapper txtMapper;

    @Override
    public List<CommTradeCtrl> selectCtrlByTradeCodeAndIo(String tradeCode, IOType io) {
        return ctrlMapper.selectByTradeCodeAndIo(tradeCode, io);
    }

    @Override
    public List<CommTradeTxt> selectTxtByTradeCodeAndBatchAndIo(String tradeCode, Integer batch, IOType io) {
        return txtMapper.selectByTradeCodeAndBatchAndIo(tradeCode, batch, io);
    }

    @Override
    public void insert(CommTradeCtrl commTradeCtrl) {
//        ctrlMapper.insert(commTradeCtrl);
    }
}
