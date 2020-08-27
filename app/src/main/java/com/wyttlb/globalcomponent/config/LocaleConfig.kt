package com.wyttlb.globalcomponent.config

import com.wyttlb.globalcomponent.BuildConfig

/**
 * 国家语言相关配置，方便演示使用，项目中可server下发
 * 注意：货币符号，小数点和千分位分隔符是每个国家固定的，不可更改
 * 保留几位小数和最大货币值可以随便改，
 * @author wyttlb
 * @since 2020-08-26
 */
class LocaleConfig {
    companion object {
        private var mConfig: Config = load(BuildConfig.LANG)
        var currencySymbol = mConfig.currencySymbol
        var decimalSeparator = mConfig.decimalSeparator
        var groupingSeparator = mConfig.groupingSeparator
        var decimalLength = mConfig.decimalLength
        var maxFeeLimit = mConfig.maxFeeLimit
        var language = mConfig.language

        fun load(language: String?): Config {
            when(language?.toUpperCase()) {
                "PT-BR" -> {
                    //巴西
                    mConfig = Config("BR", "pt-BR")
                    mConfig.currencySymbol = "R$"
                    mConfig.decimalSeparator = ","
                    mConfig.groupingSeparator = "."
                    mConfig.decimalLength = 1
                    mConfig.maxFeeLimit = 999999

                }
                "EN-AU","EN-US"  -> {
                    //澳洲
                    mConfig = Config("AU", "en-AU")
                    mConfig.currencySymbol = "$"
                    mConfig.decimalSeparator = "."
                    mConfig.groupingSeparator = ","
                    mConfig.decimalLength = 3
                    mConfig.maxFeeLimit = 9999
                }
                else -> {
                    //墨西哥
                    mConfig = Config("MX", "es-MX")
                    mConfig.currencySymbol = "MX$"
                    mConfig.decimalSeparator = "."
                    mConfig.groupingSeparator = ","
                    mConfig.decimalLength = 2
                    mConfig.maxFeeLimit = 999
                }
            }
            return mConfig
        }
    }

    data class Config(
            val country: String,
            //默认语言
            val language: String
    ) {
        init {
            LocaleConfig.language = language
        }

        /**
         * 货币符号
         */
        var currencySymbol: String = ""
            set(value) {
                LocaleConfig.currencySymbol = value
                field = value;
            }

        /**
         * 小数点分隔符
         */
        var decimalSeparator: String = "."
            set(value) {
                LocaleConfig.decimalSeparator = value
                field = value
            }

        /**
         * 千分位分隔符
         */
        var groupingSeparator: String = ","
            set(value) {
                LocaleConfig.groupingSeparator = value;
                field = value
            }

        /**
         * 小数点后保留几位
         */
        var decimalLength: Int = 2
            set(value) {
                LocaleConfig.decimalLength = value;
                field = value
            }
        /**
         * 最大数字限制
         */
        var maxFeeLimit: Int = 999
            set(value) {
                LocaleConfig.maxFeeLimit = value
                field = value
            }
    }
}