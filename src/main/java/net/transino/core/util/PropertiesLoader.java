//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package net.transino.core.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.Map.Entry;
import net.transino.core.util.Collections3;
import net.transino.core.util.DESUtil;
import net.transino.core.util.Util;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

public class PropertiesLoader extends PropertyPlaceholderConfigurer {
    private static Map<String, String> propertiesMap;
    private List<String> decryptProperties;

    public PropertiesLoader() {
    }

    public void setDecryptProperties(List<String> decryptProperties) {
        this.decryptProperties = decryptProperties;
    }

    protected void loadProperties(Properties props) throws IOException {
        super.loadProperties(props);
        propertiesMap = new HashMap();

        String key;
        String value;
        for(Iterator var2 = props.entrySet().iterator(); var2.hasNext(); propertiesMap.put(key, value)) {
            Entry entry = (Entry)var2.next();
            key = entry.getKey().toString();
            value = entry.getValue().toString();
            if(Collections3.isNotEmpty(this.decryptProperties) && this.decryptProperties.contains(key)) {
                value = DESUtil.getDecryptString(value);
                props.setProperty(key, value);
            }
        }

    }

    private static String getValue(String key) {
        String systemProperty = System.getProperty(key);
        return systemProperty != null?systemProperty:(String)propertiesMap.get(key);
    }

    public static String getProperty(String key) {
        String value = getValue(key);
        if(value == null) {
            throw new NoSuchElementException();
        } else {
            return value;
        }
    }

    public static String getProperty(String key, String defaultValue) {
        String value = getValue(key);
        return value != null?value:defaultValue;
    }

    public static Integer getInteger(String key) {
        String value = getValue(key);
        if(value == null) {
            throw new NoSuchElementException();
        } else {
            return Integer.valueOf(value);
        }
    }

    public static Integer getInteger(String key, Integer defaultValue) {
        String value = getValue(key);
        return value != null?Integer.valueOf(value):defaultValue;
    }

    public static Double getDouble(String key) {
        String value = getValue(key);
        if(value == null) {
            throw new NoSuchElementException();
        } else {
            return Double.valueOf(value);
        }
    }

    public static Double getDouble(String key, Integer defaultValue) {
        String value = getValue(key);
        return Double.valueOf(value != null?Double.valueOf(value).doubleValue():(double)defaultValue.intValue());
    }

    public static Boolean getBoolean(String key) {
        String value = getValue(key);
        if(value == null) {
            throw new NoSuchElementException();
        } else {
            return Util.parseBoolean(value);
        }
    }

    public static Boolean getBoolean(String key, boolean defaultValue) {
        String value = getValue(key);
        return Boolean.valueOf(value != null?Util.parseBoolean(value).booleanValue():defaultValue);
    }
}
