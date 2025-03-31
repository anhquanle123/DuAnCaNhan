/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edusys.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class DateHelper {

    static SimpleDateFormat formater = new SimpleDateFormat("MM/dd/yyyy");

    public static Date toDate(String date, String... pattern) {
        try {
            if (pattern.length > 0) {
                formater.applyPattern(pattern[0]);
            }
            if (date == null) {
                return DateHelper.now();
            }
            return formater.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static Date now() {
        return new Date();
    }

    public static String toString(Date date, String... pattern) {
        try {
            if (pattern.length > 0) {
                formater.applyPattern(pattern[0]);
            }
            if (date == null) {
                date = DateHelper.now();
            }
            return formater.format(date);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public static Date addDays(Date date, int days) {
        date.setTime(date.getTime() + days * 24 * 60 * 60 * 1000);
        return date;
    }

    public static Date add(int days) {
        Date now = DateHelper.now();
        now.setTime(now.getTime() + days * 24 * 60 * 60 * 1000);
        return now;
    }
}
