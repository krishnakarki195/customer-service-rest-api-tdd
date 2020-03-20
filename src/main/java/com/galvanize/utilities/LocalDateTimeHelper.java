package com.galvanize.utilities;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeHelper {

    public static String getLocalDateTimeInString(){
        LocalDateTime localDateTime = LocalDateTime.now();
        String DATE_FORMATTER= "yyyy-MM-dd HH:mm:ss";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMATTER);
        String localDateTimeString = localDateTime.format(formatter);
        return localDateTimeString;
    }

}
