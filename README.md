# GlobalComponent
> 提供适配多个国家的货币展示和输入方案，时间范围选择组件

出海应用必然面临的两个问题：
* 货币的展示和输入：每个国家的货币符号，小数点分隔符，千分位分隔符都不一样
* 时间：时区处理
    使用utc_offset：计算简单，但不精确，一个国家的utc_offset可能跟随令时变化，可使用city_id来表示城市时区


### 各个国家的货币符号和分隔符
| 国家  | 国家码 | 语言 | 货币符 | 千分位符 | 小数点符 |
|-----|-----|----|-----|------|------|
| 澳洲  | AU  | en | $   | ,    | .    |
| 巴西  | BR  | Pt | R$  | .    | ,    |
| 墨西哥 | MX  | es | MX$ | ,    | .    |

同是西语国家中，货币符号和分隔符也不尽相同，感兴趣的可以自行了解：[维基百科](https://wiki.zhonghuashu.com/wiki/%E5%B0%8F%E6%95%B0%E7%82%B9)

## 货币展示和输入方案
### 货币展示
> 货币格式化尽量放到服务端处理，端上仅做展示，但有些场景又绕不过去，比如需要对货币做动画

可以参考 [GlobalAnimateTextView](https://github.com/wangyuetingtao/GlobalComponent/blob/master/app/src/main/java/com/wyttlb/globalcomponent/text/GlobalAnimateTextView.java) 组件，格式化核心使用`Java`的`NumberFormat`类，滚动动画使用属性动画中的自定义估值器，原理参见：

效果如下

![巴西](https://github.com/wangyuetingtao/GlobalComponent/blob/master/screenshot/brazil_text.gif)
![澳洲](https://github.com/wangyuetingtao/GlobalComponent/blob/master/screenshot/australia_text.gif)

### 货币输入
梳理一个国家的货币输入要求，有以下共性需求
1. 都有最大值限制
2. 都需要自动添加千分位分隔符
3. 都有精度要求，小数点保留0位或若干位
4. 不允许输入特殊字符

效果图：

![brazil](https://github.com/wangyuetingtao/GlobalComponent/blob/master/screenshot/brazil_edit_text.gif)
![australia](https://github.com/wangyuetingtao/GlobalComponent/blob/master/screenshot/australia_edit_text.gif)

#### 实现方案
自定义文本输入框，支持按国家配置不同的限制策略，通过多种InputFilter，实现对文本的限制。

过滤器可以分为以下四种：
1. 最大值过滤器
2. 特殊字符过滤器
3. 自动千分位过滤器
4. 小数点过滤器

工作流程图：

![image](https://github.com/wangyuetingtao/GlobalComponent/blob/master/screenshot/filter.png)


几个注意点：

1. 过滤器无法实现对已输入文字的编辑。如果想对已经输入的文字进行变更，在不使用`ontextchangedlistener`的前提下，只能持有`edittext`的引用

2. `setText`时，会出发所有过滤器

3. 不能使用`InputType.TYPE_CLASS_NUMBER` 或`InputType.TYPE_NUMBER_FLAG_DECIMAL`，因为允许用户输入特殊字符`,`,同时又必须默认数字键盘，所以需要使用

4. 针对连续输入`000`时，这样处理：如果小数位不为0，在第二个0前面，自动拼接小数点；否则不允许输入第二个0

## 时间范围选择组件

时间范围选择器在时间边界上比较容易出问题，抽空撸了一个。

效果如图：

![image](https://github.com/wangyuetingtao/GlobalComponent/blob/master/screenshot/time_range_picker.gif)

功能：
 * 自动筛选时间范围外的时间
 * 支持配置最早和最晚时间，精度到分钟
 * 支持配置分钟间隔，幕布文字边框等颜色，字体字号
 * 提升性能：一次滚动即使导致多个时间变化，也仅触发一次OnTimeSelectedListener回调

> 详情参见Demo

