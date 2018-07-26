//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package net.transino.core.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public class Collections3 {
    public Collections3() {
    }




    public static String convertToString(Collection collection, String separator) {
        return StringUtils.join(collection, separator);
    }

    public static String convertToString(Collection collection, String prefix, String postfix) {
        StringBuilder builder = new StringBuilder();
        Iterator var4 = collection.iterator();

        while(var4.hasNext()) {
            Object o = var4.next();
            builder.append(prefix).append(o).append(postfix);
        }

        return builder.toString();
    }

    public static boolean isEmpty(Collection collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isEmpty(Map map) {
        return map == null || map.isEmpty();
    }

    public static boolean isNotEmpty(Collection collection) {
        return collection != null && !collection.isEmpty();
    }

    public static <T> T getFirst(Collection<T> collection) {
        return isEmpty(collection)?null:collection.iterator().next();
    }



    public static <T> List<T> union(Collection<T> a, Collection<T> b) {
        ArrayList result = new ArrayList(a);
        result.addAll(b);
        return result;
    }

    public static <T> List<T> subtract(Collection<T> a, Collection<T> b) {
        ArrayList list = new ArrayList(a);
        Iterator var3 = b.iterator();

        while(var3.hasNext()) {
            Object element = var3.next();
            list.remove(element);
        }

        return list;
    }

    public static <T> List<T> intersection(Collection<T> a, Collection<T> b) {
        ArrayList list = new ArrayList();
        Iterator var3 = a.iterator();

        while(var3.hasNext()) {
            Object element = var3.next();
            if(b.contains(element)) {
                list.add(element);
            }
        }

        return list;
    }
}
