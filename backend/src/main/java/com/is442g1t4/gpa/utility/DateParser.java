package com.is442g1t4.gpa.utility;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateParser {
    public static Date parseDateString(String dateStr, String pattern) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        try {
            Date date = dateFormat.parse(dateStr);
            return date;
        } catch (Exception e) {
            // TODO: handle exception
            System.out.print("Invalid Date format");
            return null;
        }

    }

}
