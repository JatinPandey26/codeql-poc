package com.kipu_fav.write_module;


import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Setter
@Getter

public class SecuredLoggerSanitizer {

    /*  To do
        Remove comment
    */
    //@Value("${securedLogger.maxLength}")
    private static int MAX_LENGTH = Integer.MAX_VALUE;

    public static <T> String sanitize(T logValue) {

        if (logValue == null) {
            return null;
        }

        String inputString = String.valueOf(logValue);

        return sanitizeNewlines(sanitizeForHTML(inputString));
    }

    public static String sanitizeForHTML(String logValue) {
//        return  StringEscapeUtils .escapeHtml4(logValue);
//        return  StringEscapeUtils
        return org.apache.commons.lang3.StringEscapeUtils.escapeHtml4(logValue);
    }

    public static String sanitizeNewlines(String logValue) {
        return logValue.replaceAll("[\r\n]", ""); // Remove newline characters
    }

    public static String limitInputLength(String logValue) {
        if (logValue.length() > MAX_LENGTH) {
            return logValue.substring(0, MAX_LENGTH);
        }
        return logValue;
    }

}