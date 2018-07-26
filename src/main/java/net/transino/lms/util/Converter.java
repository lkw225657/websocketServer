package net.transino.lms.util;

import jodd.bean.BeanUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Converter<T> {
    protected List<String> list = new ArrayList<>();
    protected char _beforeChar;
    protected char _quoteChar;
    protected char _endChar;
    protected  List<T> obj;
    protected Map<String, OutPut> putMap;
    protected String[] keys;

    public Converter(List<T> o) {
        this.obj = o;
        this.keys = new String[o.size()];
        this.putMap = new HashMap<>(o.size());
        this.createPutMat();
    }

    protected abstract void createPutMat();

    public abstract void put(Object p) throws Exception;

    public void release(){
        this.list.clear();
    }

    public String convertToString(){
        int i;
        StringBuilder sb = new StringBuilder(1024);
        if (this._beforeChar > 0) {
            sb.append(this._beforeChar);
        }
        for (i = 0; i < this.list.size()-1; i++) {
            sb.append(this.list.get(i));
            if (this._quoteChar > 0) {
                sb.append(this._quoteChar);
            }
        }
        sb.append(this.list.get(i));
        if (this._endChar > 0) {
            sb.append(this._endChar);
        }
        this.release();
        return sb.toString();
    }

    protected Object getProperty(Object bean, String name, OutPut outPut) throws Exception {
        Object val = BeanUtil.pojo.getProperty(bean, name);
        if (val == null) {
            if (outPut.getBnullable() != null && !"0".equals(outPut.getBnullable())){
                throw new NullPointerException("Field " + outPut.getLfield() + " is NULL!");
            }
            return outPut.getDataDefault();
        }
        try {
            if (outPut.getCls() == Integer.class) {
                val = Integer.parseInt(val.toString());
            } else if (outPut.getCls() == Float.class) {
                val = Float.parseFloat(val.toString());
            }
        } catch (NumberFormatException nfe){
            throw new Exception("Field " + outPut.getLfield() + " format Exception!");
        }
        return val;
    }

    protected class OutPut{
        public String getLfield() {
            return lfield;
        }

        public void setLfield(String lfield) {
            this.lfield = lfield;
        }

        public String getLtype() {
            return ltype;
        }

        public void setLtype(String ltype) {
            this.ltype = ltype;
        }

        public BigDecimal getLprecision() {
            return lprecision;
        }

        public void setLprecision(BigDecimal lprecision) {
            this.lprecision = lprecision;
        }

        public BigDecimal getLscale() {
            return lscale;
        }

        public void setLscale(BigDecimal lscale) {
            this.lscale = lscale;
        }

        public String getCfield() {
            return cfield;
        }

        public void setCfield(String cfield) {
            this.cfield = cfield;
        }

        public String getCtype() {
            return ctype;
        }

        public void setCtype(String ctype) {
            this.ctype = ctype;
        }

        public BigDecimal getCprecision() {
            return cprecision;
        }

        public void setCprecision(BigDecimal cprecision) {
            this.cprecision = cprecision;
        }

        public BigDecimal getCscale() {
            return cscale;
        }

        public void setCscale(BigDecimal cscale) {
            this.cscale = cscale;
        }

        public String getBnullable() {
            return bnullable;
        }

        public void setBnullable(String bnullable) {
            this.bnullable = bnullable;
        }

        public String getDataDefault() {
            return dataDefault;
        }

        public void setDataDefault(String dataDefault) {
            this.dataDefault = dataDefault;
        }

        public String getParam() {
            return param;
        }

        public void setParam(String param) {
            this.param = param;
        }

        public Class getCls() {
            return cls;
        }

        public void setCls(Class cls) {
            this.cls = cls;
        }

        public String getFormat() {
            return format;
        }

        public void setFormat(String format) {
            this.format = format;
        }

        private Class cls;

        private String format;

        private String lfield;

        private String ltype;

        private BigDecimal lprecision;

        private BigDecimal lscale;

        private String cfield;

        private String ctype;

        private BigDecimal cprecision;

        private BigDecimal cscale;

        private String bnullable;

        private String dataDefault;

        private String param;
    }
}