package com.wyttlb.globalcomponent.edittext.inputfilters;

import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;

/**
 * 最大值过滤器
 * @author wyttlb
 */
public class MaxmumFilter implements InputFilter {
    //整数最长位数
    int mMaxIntLength;
    //小数位最长位数
    int mDecimalLength;
    //小数点分隔符
    String mDecimalSeperator;

    public MaxmumFilter(int maxIntLength, int maxDecimalLength, String decimalSeperator) {
        mMaxIntLength = maxIntLength;
        mDecimalLength = maxDecimalLength;
        mDecimalSeperator = decimalSeperator;
    }
    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        if ("".equals(source)){
            return null;
        }
        //当输入是小数点，放行
        if (source.equals(mDecimalSeperator)) {
            return source;
        }
        //只过滤单个输入字符
        if (source.length() == 1) {
            int keep = 0;
            //是否包含小数点分隔符
            boolean isContainSep = dest.toString().indexOf(mDecimalSeperator) != -1;
            String headStr = dest.toString().replace(",", "").replace(".", "");
            if (mDecimalLength != 0 && isContainSep) {
                keep = mMaxIntLength + mDecimalLength - (TextUtils.isEmpty(headStr) ? 0 : headStr.length());
            } else {
                //只看整数部分
                keep = mMaxIntLength - (TextUtils.isEmpty(headStr) ? 0 : headStr.length());
            }
            if (keep <= 0) {
                return ""; //拦截输入
            } else if (keep >= end - start) {
                return null; //保持原样
            }
        }
        return null;
    }
}
