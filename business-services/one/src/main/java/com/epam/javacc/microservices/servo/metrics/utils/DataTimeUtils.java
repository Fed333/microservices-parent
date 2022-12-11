package com.epam.javacc.microservices.servo.metrics.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DataTimeUtils {

    public static String getCurrentTimestamp() {
        return new SimpleDateFormat("yyyy-MM-dd hh:mm:ssZ").format(new Date());
    }

}
