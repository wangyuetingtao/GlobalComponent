package com.wyttlb.globalcomponent.utils;

import android.text.TextUtils;
import com.wyttlb.globalcomponent.config.LocaleConfig;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Locale;

public class NumFormatterUtil {
    //拆分国家码和语言
    String[] splitResult;

    private NumFormatterUtil() {
        splitResult = LocaleConfig.Companion.getLanguage().split("-");
    }

    private static final class Singleton {
        private static NumFormatterUtil INSTANCE = new NumFormatterUtil();
    }

    public static NumFormatterUtil getInstance() {
        return Singleton.INSTANCE;
    }

    public NumberFormat getNamFormatter() {
       return NumberFormat.getInstance(new Locale(splitResult[0], splitResult[1]));
    }

    /**
     * 将带local符号的string转为double
     * ex. 1.863,34 -> 1863.34
     * @param valueStr
     * @return
     */
    public Double parseString2Double(String valueStr) {
        double value = 0.0;
        if (TextUtils.isEmpty(valueStr)) {
            return value;
        }
        try{
            NumberFormat format = getNamFormatter();
            Number number = format.parse(valueStr);
            value = number.doubleValue();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    public String parseNumber2String(Number value, int scale) {
        try {
            NumberFormat format = getNamFormatter();
            if (scale >= 0) {
                format.setMaximumFractionDigits(scale);
                format.setRoundingMode(RoundingMode.UP);
                format.setMinimumFractionDigits(scale);
            }
            return  format.format(value);
        } catch (Exception e){
            e.printStackTrace();
        }
        return "";

    }
}
