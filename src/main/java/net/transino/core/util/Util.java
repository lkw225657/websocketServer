package net.transino.core.util;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import net.transino.core.Consts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;
import com.google.common.base.Splitter;
import com.google.common.base.Joiner;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author lee
 * @ver 5.0
 */
public class Util {
    private static final Logger log = LoggerFactory.getLogger(Util.class);
    private static final String INDENT = "    ";
    private static final Pattern PASSWORD = Pattern.compile("(password|credential)", 2);

    private Util() {
        throw new AssertionError("No " + this.getClass().getName() + " instances for you!");
    }

    public static boolean isSuccess(int numEdits) {
        return numEdits > 0;
    }

    public static boolean textHasContent(String text) {
        return text != null && text.trim().length() > 0;
    }

    public static boolean textIsBlank(CharSequence cs) {
        int strLen;
        if(cs != null && (strLen = cs.length()) != 0) {
            for(int i = 0; i < strLen; ++i) {
                if(!Character.isWhitespace(cs.charAt(i))) {
                    return false;
                }
            }

            return true;
        } else {
            return true;
        }
    }

    public static String format(String messagePattern, Object... args) {
        FormattingTuple formattingTuple = MessageFormatter.arrayFormat(messagePattern, args);
        return formattingTuple.getMessage();
    }
    public static String join(Iterable<?> parts, String separator) {
        return Joiner.on(separator).skipNulls().join(parts);
    }

    public static <T> T defaultIfNull(T object, T defaultValue) {
        return object != null?object:defaultValue;
    }

    public static String trimPossiblyNull(String text) {
        return text == null?null:text.trim();
    }

    public static String trimToEmpty(String text) {
        return text == null?"":text.trim();
    }

    public static boolean isInRange(int number, int low, int high) {
        if(low > high) {
            throw new IllegalArgumentException("Low: " + low + " is greater than High: " + high);
        } else {
            return low <= number && number <= high;
        }
    }
    public static List<String> splitToList(CharSequence sequence, String separator) {
        return Splitter.on(separator).trimResults().omitEmptyStrings().splitToList(sequence);
    }

    public static Boolean parseBoolean(String booleanAsText) {
        Boolean result = null;
        String value = trimPossiblyNull(booleanAsText);
        if(value != null) {
            if(!value.equalsIgnoreCase("false") && !value.equalsIgnoreCase("no") && !value.equalsIgnoreCase("off")) {
                if(!value.equalsIgnoreCase("true") && !value.equalsIgnoreCase("yes") && !value.equalsIgnoreCase("on")) {
                    throw new IllegalArgumentException("Cannot parse into Boolean: " + quote(booleanAsText) + ". Accepted values are: true/false/yes/no/on/off");
                }

                result = Boolean.TRUE;
            } else {
                result = Boolean.FALSE;
            }
        }

        return result;
    }

    public static Boolean nullMeansFalse(Boolean bool) {
        return bool == null?Boolean.FALSE:bool;
    }

    public static final Pattern getPatternFromList(List<?> list) {
        if(list.isEmpty()) {
            throw new IllegalArgumentException();
        } else {
            StringBuilder regex = new StringBuilder("(");
            Iterator iter = list.iterator();

            while(iter.hasNext()) {
                Object item = iter.next();
                regex.append(item.toString());
                if(iter.hasNext()) {
                    regex.append("|");
                }
            }

            regex.append(")");
            return Pattern.compile(regex.toString());
        }
    }

    public static boolean isZeroMoney(BigDecimal money) {
        BigDecimal ZERO_MONEY = new BigDecimal("0");
        BigDecimal ZERO_MONEY_WITH_DECIMAL = new BigDecimal("0.00");
        return money.equals(ZERO_MONEY) || money.equals(ZERO_MONEY_WITH_DECIMAL);
    }

    public static boolean matches(Pattern pattern, String text) {
        if(text == null) {
            return false;
        } else {
            Matcher matcher = pattern.matcher(text);
            return matcher.matches();
        }
    }

    public static boolean contains(Pattern pattern, String text) {
        if(text == null) {
            return false;
        } else {
            Matcher matcher = pattern.matcher(text);
            return matcher.find();
        }
    }

    public static <E> E replaceIfNull(E possiblyNullItem, E replacement) {
        return possiblyNullItem == null?replacement:possiblyNullItem;
    }

    public static String quote(Object object) {
        String result = "EXCEPTION OCCURED";

        try {
            result = "\'" + String.valueOf(object) + "\'";
        } catch (Throwable var3) {
            ;
        }

        return result;
    }

    public static String doubleQuote(Object object) {
        String result = "EXCEPTION OCCURED";

        try {
            result = "\"" + String.valueOf(object) + "\"";
        } catch (Throwable var3) {
            ;
        }

        return result;
    }

}