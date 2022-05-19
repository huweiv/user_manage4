package com.huweiv.util;

import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author HUWEIV
 * @version 1.0.0
 * @ClassName DateConvert
 * @Description Convertr<String, Data>  第一个为要转换之前的类型，即原始类型，第二个为要转换成的类型，即目标类型
 * @CreateTime 2021/11/10 16:45
 */
public class DateConvert implements Converter<String, Date> {
    public Date convert(String dateStr) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = format.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
