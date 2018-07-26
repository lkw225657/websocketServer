package net.transino.lms.util;


import net.transino.lms.modules.comm.entity.CommTradeTxt;

import java.util.List;

public class ConverterTxt extends Converter<CommTradeTxt> {
    public ConverterTxt(List<CommTradeTxt> o) {
        super(o);
        this._quoteChar = 124;
        this._endChar = 124;
    }

    @Override
    protected void createPutMat() {
        OutPut outPut;
        CommTradeTxt txt;

//        for (int i = 0; i < this.obj.size(); i++) {
//            txt = this.obj.get(i);
//            outPut = new OutPut();
//            this.keys[i] = txt.getField();
//            outPut.setLfield(txt.getField());
//            outPut.setCprecision(txt.getPrecision());
//            outPut.setCscale(txt.getScale());
//            switch (txt.getType()){
//                case "D":
//                    outPut.setFormat("%d");
//                    outPut.setCls(Integer.class);
//                    break;
//                case "F":
//                    outPut.setCls(Float.class);
//                    outPut.setFormat("%"+ txt.getPrecision().toString() + "." + txt.getScale().toString() +"f");
//                    break;
//                default:
//                    outPut.setFormat("%s");
//                    outPut.setCls(String.class);
//            }
//            this.putMap.put(txt.getField(),outPut);
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
            list.add(String.format(outPut.getFormat(), val));
        }
    }
}