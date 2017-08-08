### RingChartView
仿支付宝月账单的环形图

![Source](https://github.com/HStanN/SimpleCustomizeView/blob/master/image/ringchartview.png)

#### 属性说明:
 - bigRingRadius : 外圆半径
 - smallCircleRadius : 内圆半径
 - ringTextSize : 字体大小

#### xml中使用:
```xml
    <com.hug.simplecustomizeview.view.ringChart.RingChartView
        android:id="@+id/ring_chart"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:layout_marginTop="50dp"
        />
<!-- default value
    app:bigRingRadius="100dp"
    app:smallCircleRadius="30dp"
    app:ringTextSize="15sp"
-->
```
#### 添加数据:
```java
        ChartItem chartItem1 = new ChartItem();
        chartItem1.setPercent(0.10);
        chartItem1.setAmount(1000);
        chartItem1.setName("购物");
        chartItem1.setColor(R.color.color_red);
        ChartItem chartItem2 = new ChartItem();
        chartItem2.setPercent(0.20);
        chartItem2.setAmount(2000);
        chartItem2.setName("生活缴费");
        chartItem2.setColor(R.color.color_blue);
        ChartItem chartItem3 = new ChartItem();
        chartItem3.setPercent(0.40);
        chartItem3.setName("手机");
        chartItem3.setAmount(4000);
        chartItem3.setColor(R.color.color_green);
        ChartItem chartItem4 = new ChartItem();
        chartItem4.setPercent(0.10);
        chartItem4.setAmount(1000);
        chartItem4.setName("转账");
        chartItem4.setColor(R.color.colorAccent);
        ChartItem chartItem5 = new ChartItem();
        chartItem5.setPercent(0.20);
        chartItem5.setAmount(2000);
        chartItem5.setName("其他");
        chartItem5.setColor(R.color.color_orange);

        List<ChartItem> lists = new ArrayList<>();
        lists.add(chartItem1);
        lists.add(chartItem2);
        lists.add(chartItem3);
        lists.add(chartItem4);
        lists.add(chartItem5);
        //ringChartView.setRingRadius(200);
        //ringChartView.setCenterRadius(60);
        //ringChartView.setTextSize(30);    皆为像素单位
        ringChartView.setChartData(lists);
```
