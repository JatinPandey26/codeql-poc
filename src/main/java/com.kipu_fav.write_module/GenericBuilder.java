package com.kipu_fav.write_module;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Iterator;
import java.util.Objects;


@Getter
@Setter
public class GenericBuilder implements  Serializable {




    /**
     *
     * @param val (val will be like "Hi {0}")
     * @param macro
     * @return
     */
    public static String formate(String val, String... macro){
        return MessageFormat.format(val,macro);
    }

    /**
     *
     * @param values
     * @return string
     */
    public static  <T> String arrayAppender(Iterable<T> values) {
        return arrayAppender(values,false);
    }

    /**
     *
     * @param values
     * @return string
     */
    public static  <T> String arrayAppender(Iterable<T> values,boolean addQ) {
        String defaultValue = "()";
        if (Objects.isNull(values)) {
            return defaultValue;
        }
        Iterator<T> valueIterator = values.iterator();
        if (!valueIterator.hasNext()) {
            return defaultValue;
        }
        StringBuilder builder = new StringBuilder(" ");
        boolean afterFirstFlag = false;
        while (valueIterator.hasNext()) {
            if (afterFirstFlag) {
                if (addQ){
                    builder.append(" '").append(valueIterator.next().toString()).append("' ");
                } else {
                    builder.append(valueIterator.next().toString());
                }
            } else {
                if (addQ){
                    builder.append(" ( '").append(valueIterator.next().toString()).append("' ");
                } else {
                    builder.append(" ( ").append(valueIterator.next().toString());
                }
                afterFirstFlag = true;
            }
            if (valueIterator.hasNext())
                builder.append(" , ");
        }
        builder.append(" ) ");
        return builder.toString();
    }


    /**
     *
     * @param values
     * @return string
     */
    public static  <T> String arrayAppender(Iterable<T> values,boolean addQ,
                                            String openingBrackets,String closingBrackets) {
        String defaultValue = openingBrackets+""+closingBrackets;
        if (Objects.isNull(values)) {
            return defaultValue;
        }
        Iterator<T> valueIterator = values.iterator();
        if (!valueIterator.hasNext()) {
            return defaultValue;
        }
        StringBuilder builder = new StringBuilder(" ");
        boolean afterFirstFlag = false;
        while (valueIterator.hasNext()) {
            if (afterFirstFlag) {
                if (addQ){
                    builder.append(" '").append(valueIterator.next().toString()).append("' ");
                } else {
                    builder.append(valueIterator.next().toString());
                }
            } else {
                if (addQ){
                    builder.append(openingBrackets).append(" '").append(valueIterator.next().toString()).append("' ");
                } else {
                    builder.append(openingBrackets).append(" ").append(valueIterator.next().toString());
                }
                afterFirstFlag = true;
            }
            if (valueIterator.hasNext())
                builder.append(" , ");
        }
        builder.append(" ").append(closingBrackets);
        return builder.toString();
    }

}
