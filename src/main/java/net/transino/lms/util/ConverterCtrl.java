package net.transino.lms.util;


import net.transino.lms.modules.comm.entity.CommTradeCtrl;

import java.util.List;

public class ConverterCtrl extends Converter<CommTradeCtrl> {
    public ConverterCtrl(List<CommTradeCtrl> o) {
        super(o);
        this._beforeChar = 123;
        this._quoteChar = 44;
        this._endChar = 125;
    }

    @Override
    protected void createPutMat() {
        OutPut outPut;
        CommTradeCtrl ctrl;

//        for (int i = 0; i < this.obj.size(); i++) {
//            ctrl = this.obj.get(i);
//            outPut = new OutPut();
//            this.keys[i] = ctrl.getCfield();
//            outPut.setLfield(ctrl.getLfield());
//            outPut.setCfield(ctrl.getCfield());
//            outPut.setCprecision(ctrl.getCprecision());
//            outPut.setCscale(ctrl.getCscale());
//            switch (ctrl.getCtype()){
//                case "D":
//                    outPut.setFormat("\"%s\":%d");
//                    outPut.setCls(Integer.class);
//                    break;
//                case "F":
//                    outPut.setCls(Float.class);
//                    outPut.setFormat("\"%s\":%"+ ctrl.getCprecision().toString() + "." + ctrl.getCscale().toString() +"f");
//                    break;
//                default:
//                    outPut.setFormat("\"%s\":\"%s\"");
//                    outPut.setCls(String.class);
//            }
//            this.putMap.put(ctrl.getCfield(),outPut);
//        }
    }

    public void put(Object p) throws Exception {
        String key;
        OutPut outPut;
        Object val;
        for (int i = 0; i < this.keys.length; i++) {
            key = this.keys[i];
            outPut = this.putMap.get(key);
            val = this.getProperty(p, outPut.getLfield(), outPut);
            list.add(String.format(outPut.getFormat(), outPut.getCfield(), val));
        }
    }
}