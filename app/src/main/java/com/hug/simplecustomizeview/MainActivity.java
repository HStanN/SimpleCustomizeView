package com.hug.simplecustomizeview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.hug.simplecustomizeview.view.ringChart.ChartItem;
import com.hug.simplecustomizeview.view.ringChart.RingChartView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RingChartView ringChartView = (RingChartView) findViewById(R.id.ring_chart);
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
        ringChartView.setChartData(lists);
    }
}
