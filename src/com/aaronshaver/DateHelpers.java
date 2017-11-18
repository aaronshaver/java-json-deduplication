package com.aaronshaver;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateHelpers {
    public static Date getDateFromUTCString(String date) {
        // see https://docs.oracle.com/javase/7/docs/api/java/text/SimpleDateFormat.html
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX", Locale.ENGLISH);
        Date out = null;
        try {
            out = format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return out;
    }

}
