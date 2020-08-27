package com.wyttlb.globalcomponent.text;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.wyttlb.globalcomponent.R;
import com.wyttlb.globalcomponent.config.LocaleConfig;
import com.wyttlb.globalcomponent.utils.NumFormatterUtil;
import java.math.BigDecimal;

/**
 * 支持多个国家货币展示的TextView
 * 支持滚动动画
 * @author wyttlb
 * @since 2020-08-26
 */
@SuppressLint("AppCompatCustomView")
public class GlobalAnimateTextView extends TextView {
    //起始值 默认0
    private float mNumStart = 0;
    //结束值
    private float mNumEnd;
    //动画总时间 默认 1000 毫秒
    private long mDuration = 1000;
    //前缀
    private String mPrefixString = "";
    //后缀
    private String mPostfixString = "";
    //是否开启动画
    private boolean mIsEnableAnim = true;
    //是否使用千分位分隔符，格式化数字，默认使用
    private boolean mIsGroupingUsed;
    //是否当内容有改变时才使用动画，默认是
    private boolean mRunWhenChange;
    //显示数字最少要达到这个数字才滚动
    private float mMinNum;
    //上一次缓存的数据
    private float mPreNum = 0;
    //是否动画中
    private boolean mIsAnimating = false;
    ValueAnimator animator;
    private NumFormatterUtil mFormatter;

    public GlobalAnimateTextView(Context context) {
        this(context, null);
    }

    public GlobalAnimateTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, android.R.attr.textViewStyle);
    }

    public GlobalAnimateTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.GlobalAnimateTextView);
        mDuration = ta.getInt(R.styleable.GlobalAnimateTextView_duration, 1000);
        mMinNum = ta.getFloat(R.styleable.GlobalAnimateTextView_minNum, 0.00f);
        mRunWhenChange = ta.getBoolean(R.styleable.GlobalAnimateTextView_runWhenChange, true);
        mIsEnableAnim = ta.getBoolean(R.styleable.GlobalAnimateTextView_isEnableAnim, true);
        mNumStart = ta.getFloat(R.styleable.GlobalAnimateTextView_numStart, 0.00f);
        mNumEnd = ta.getFloat(R.styleable.GlobalAnimateTextView_numEnd, 0.00f);
        mPrefixString = ta.getString(R.styleable.GlobalAnimateTextView_prefixString);
        if (TextUtils.isEmpty(mPrefixString)) {
            mPrefixString = "";
        }
        mPostfixString = ta.getString(R.styleable.GlobalAnimateTextView_postfixString);
        if (TextUtils.isEmpty(mPostfixString)) {
            mPostfixString = "";
        }
        mIsGroupingUsed = ta.getBoolean(R.styleable.GlobalAnimateTextView_groupingUsed, true);
        mFormatter = NumFormatterUtil.getInstance();
    }

    //generate setter start
    public void setNumStart(float mNumStart) {
        this.mNumStart = mNumStart;
    }

    public void setNumEnd(float mNumEnd) {
        this.mNumEnd = mNumEnd;
    }

    public void setDuration(long mDuration) {
        this.mDuration = mDuration;
    }

    public void setPrefixString(String mPrefixString) {
        this.mPrefixString = mPrefixString;
    }

    public void setPostfixString(String mPostfixString) {
        this.mPostfixString = mPostfixString;
    }

    public void setIsEnableAnim(boolean mIsEnableAnim) {
        this.mIsEnableAnim = mIsEnableAnim;
    }

    public void setRunWhenChange(boolean mRunWhenChange) {
        this.mRunWhenChange = mRunWhenChange;
    }

    public void setMinNum(float mMinNum) {
        this.mMinNum = mMinNum;
    }

    //generate setter end

    public void setNumber(float numberStart, float numberEnd) {
        mNumStart = numberStart;
        mNumEnd = numberEnd;

        if (numberEnd > mMinNum) {
            //数字合法，开始数字动画
            start();
        } else {
            //数字不合法，直接settext 设置最终值
            setText(mNumEnd);
        }
    }

    private void start() {
        //禁止动画
        if (!mIsEnableAnim) {
            setText(mNumEnd);
            return;
        }

        if (mRunWhenChange) {
            if (mPreNum == mNumEnd) {
                //两次内容一致，则不做处理
                return;
            }
            //两次数字不一致，记录最新当数字
            mPreNum = mNumEnd;
        }
        animator = ValueAnimator.ofObject(new BigDecimalEvaluator(), new BigDecimal(String.valueOf(mNumStart)), new BigDecimal(String.valueOf(mNumEnd)));
        animator.setDuration(mDuration);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mIsAnimating = false;
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                mIsAnimating = false;
            }
        });
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                BigDecimal value = (BigDecimal)valueAnimator.getAnimatedValue();
                setText(value);
            }
        });
        animator.start();
    }

    private void setText(float number) {
        setText(new BigDecimal(String.valueOf(number)));
    }

    private void setText(BigDecimal value) {
        StringBuilder sb = new StringBuilder(mPrefixString).append(format(value)).append(mPostfixString);
        setText(sb.toString());
    }

    private String format(BigDecimal bd) {
        try {
            //设置是否使用千分符
            mFormatter.getNamFormatter().setGroupingUsed(mIsGroupingUsed);
            String num = mFormatter.parseNumber2String(bd, LocaleConfig.Companion.getDecimalLength());
            return num;
        } catch (Exception e) {
            e.printStackTrace();
            return String.valueOf(bd);
        }
    }

    private static class BigDecimalEvaluator implements TypeEvaluator {
        @Override
        public Object evaluate(float fraction, Object startValue, Object endValue) {
            BigDecimal start = (BigDecimal)startValue;
            BigDecimal end = (BigDecimal)endValue;
            BigDecimal result = end.subtract(start);
            return result.multiply(new BigDecimal("" + fraction)).add(start);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        animator.pause();
        animator.addListener(null);
        animator.addUpdateListener(null);
        animator = null;
    }
}
