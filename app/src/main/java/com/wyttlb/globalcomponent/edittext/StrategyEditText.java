package com.wyttlb.globalcomponent.edittext;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatEditText;
import com.wyttlb.globalcomponent.config.LocaleConfig;
import com.wyttlb.globalcomponent.edittext.inputfilters.AutoSeparatorFilter;
import com.wyttlb.globalcomponent.edittext.inputfilters.DecimalPointFilter;
import com.wyttlb.globalcomponent.edittext.inputfilters.MaxmumFilter;
import com.wyttlb.globalcomponent.edittext.inputfilters.SpecialSignFilter;

/**
 * 支持多种输入过滤策略的数字输入框
 * 适配多个国家的货币输入
 * 策略支持：
 *   千分位分隔符：根据locale自动添加千分位
 *   小数点分隔符：根据locale自动添加正确的小数点
 *   最大值：限制不会超过最大值，最大值定位为：整数长度加小数点位数，如999.99
 *   特殊字符：屏蔽特殊字符输入,只允许输入数字和小数点
 *
 * @author wyttlb
 * @since 2020-08-26
 */
@SuppressLint("AppCompatCustomView")
public class StrategyEditText extends AppCompatEditText {
    public StrategyEditText(Context context) {
        this(context, null);
    }

    public StrategyEditText(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public StrategyEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        //不要用下面这种方式设置edittext type类型，这种类型允许输入数字和小数点，但不允许输入","
        //而有些国家的小数点是","
//        setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        //下面这种方式需要对字母进行过滤
        setRawInputType(Configuration.KEYBOARD_QWERTY);
        LocaleConfig.Companion config = LocaleConfig.Companion;
        setFilters(new InputFilter[] {
                new SpecialSignFilter(config.getDecimalSeparator()),
                new MaxmumFilter(String.valueOf(config.getMaxFeeLimit()).length(), config.getDecimalLength(), config.getDecimalSeparator()),
                new AutoSeparatorFilter(this, config.getGroupingSeparator(), config.getDecimalSeparator(), config.getDecimalLength()),
                new DecimalPointFilter(config.getDecimalSeparator(), config.getDecimalLength())
        });

    }

    @Override
    protected void onSelectionChanged(int selStart, int selEnd) {
        super.onSelectionChanged(selStart, selEnd);
        //保证光标始终在最后面
        if (selStart == selEnd) { //防止不能多选
            setSelection(TextUtils.isEmpty(getText()) ? 0 : getText().length());
        }
    }
}
