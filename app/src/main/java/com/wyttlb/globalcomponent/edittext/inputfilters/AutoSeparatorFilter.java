package com.wyttlb.globalcomponent.edittext.inputfilters;

import android.text.InputFilter;
import android.text.Spanned;
import android.widget.EditText;

import com.wyttlb.globalcomponent.config.LocaleConfig;
import com.wyttlb.globalcomponent.utils.NumFormatterUtil;

/**
 * 自动千分位过滤器
 * 文本框输入过程中，自动根据locale补全千分位
 * 当输入0时，后续输入数字前自动添加小数点
 * @author wyttlb
 * @since 2020-08-26
 */
public class AutoSeparatorFilter implements InputFilter {

    /**千分位分隔符*/
    String mGroupingSeparator;
    /**小数点分隔符*/
    String mDecimalSeparator;
    /**小数点位数*/
    int mDecimalLength;
    /**文本框*/
    EditText mEditText;
    public AutoSeparatorFilter(EditText editText, String groupingSeparator, String decimalSeparator, int decimalLength) {
        mGroupingSeparator = groupingSeparator;
        mDecimalSeparator = decimalSeparator;
        mDecimalLength = decimalLength;
        mEditText = editText;
    }
    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        String oldStr = "";
        if (source.equals("") && dend > dstart) {
            oldStr = dest.subSequence(0, dstart).toString();
        } else {
            oldStr = dest.toString();
        }
        //是否有小数点
        int decimalSeparatorIndex = oldStr.indexOf(mDecimalSeparator);
        if (decimalSeparatorIndex != -1) {
            return null;
        } else {
            //格式化金额
            if (oldStr != null && oldStr.length() > 2 && !source.equals(mDecimalSeparator)) {
                Double tempNum = NumFormatterUtil.getInstance().parseString2Double(oldStr + source);
                //这里格式化时保持数字原有精度
                String amountStr = NumFormatterUtil.getInstance().parseNumber2String(tempNum, -1);
                setText(amountStr);
                return amountStr;
            }

            //如果当前字符是0开头，则添加小数点后，再添加其他字符
            //排除删除条件，否则0.1 删除0后会自动添加"."
            if (dest.toString().equals("0")
                && !source.equals(mDecimalSeparator)
                    && !source.equals("")
                    && dstart == 1) {
                if (mDecimalLength == 0) {
                    return "";
                } else {
                    return mDecimalSeparator + source;
                }
            }
        }
        return null;
    }

    private void setText(String text) {mEditText.setText(text);}
}
