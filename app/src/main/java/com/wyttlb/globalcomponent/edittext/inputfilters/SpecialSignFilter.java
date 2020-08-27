package com.wyttlb.globalcomponent.edittext.inputfilters;

import android.text.InputFilter;
import android.text.Spanned;

import com.wyttlb.globalcomponent.config.LocaleConfig;

import java.util.ArrayList;

/**
 * 特殊字符过滤器，只允许输入数字和小数点符号
 * @author wyttlb
 */
public class SpecialSignFilter implements InputFilter {
    ArrayList<String> mInputInLaw;

    public SpecialSignFilter(String decimalSep) {
        mInputInLaw = new ArrayList();
        mInputInLaw.add("0");
        mInputInLaw.add("1");
        mInputInLaw.add("2");
        mInputInLaw.add("3");
        mInputInLaw.add("4");
        mInputInLaw.add("5");
        mInputInLaw.add("6");
        mInputInLaw.add("7");
        mInputInLaw.add("8");
        mInputInLaw.add("9");
        mInputInLaw.add(decimalSep);
    }
    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        if ("".equals(source)) {
            return null;
        }
        boolean isValidSign = true;
        for (int i = 0; i < source.length(); i++) {
            String temChar = String.valueOf(source.charAt(i));
            //不在合法字符内，也不是千分位
            if (!mInputInLaw.contains(temChar) && !temChar.equals(LocaleConfig.Companion.getGroupingSeparator())) {
                isValidSign = false;
                break;
            }
        }
        if(isValidSign) {
           return source;
        } else {
            return "";
        }
    }
}
