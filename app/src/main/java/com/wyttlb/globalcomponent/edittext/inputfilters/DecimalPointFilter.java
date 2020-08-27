package com.wyttlb.globalcomponent.edittext.inputfilters;

import android.text.InputFilter;
import android.text.Spanned;

/**
 * 小数点输入过滤器
 * @author wyttlb
 */
public class DecimalPointFilter implements InputFilter {
    /**小数点分隔符*/
    String mDecimalSeparator;
    /**小数位长度*/
    int mDecimalLength;

    public DecimalPointFilter(String decimalSep, int decimalLength) {
        mDecimalLength = decimalLength;
        mDecimalSeparator = decimalSep;
    }
    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        //屏蔽删除
        if ("".equals(source)) {
            return null;
        }
        if (mDecimalLength == 0) {
            //小数位为0，又输入了小数点
            if (mDecimalSeparator.equals(source.toString())) {
                return "";
            }
        }
        if (mDecimalLength != 0) {
            //已经输入过小数点了，不允许再次输入
            if (dest.toString().contains(mDecimalSeparator)) {
                if (mDecimalSeparator.equals(source.toString())) {
                    return "";
                } else {
                    //判断是否到了小数位最大值
                    String decimalString = dest.toString().substring(dest.toString().indexOf(mDecimalSeparator) + 1);
                    if (decimalString.length() >= mDecimalLength) {
                        return "";
                    }
                }
            }
            //第一位输入小数点，自动拼接0
            if (mDecimalSeparator.equals(source.toString()) && dstart == 0){
                return "0" + mDecimalSeparator;
            }
        }
        return source;
    }
}
