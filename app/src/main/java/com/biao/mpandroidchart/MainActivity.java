package com.biao.mpandroidchart;

import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnChartValueSelectedListener, OnChartGestureListener {

    private static final String TAG = "MainActivity";
    private LineChart lineChart;
    private BarChart mBarChart;
    private PieChart mPieChart;
    private CombinedChart combinedChart;
    private ArrayList<Entry> values = new ArrayList<Entry>();
    private ArrayList<BarEntry> barEntries = new ArrayList<BarEntry>();
    private ArrayList<PieEntry> entries = new ArrayList<PieEntry>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lineChart = findViewById(R.id.mLineChar);
        mBarChart = findViewById(R.id.mBarChart);
        mPieChart = findViewById(R.id.mPieChart);
        combinedChart = findViewById(R.id.combined_chart);

        initData();
        initLineChart();
        initBarChart();
        initPieChart();
        initCombinedChart();
        showLineChart();
    }

    private void initData() {
        //这里我模拟一些数据(折线)
        values.add(new Entry(5, 50));
        values.add(new Entry(10, 66));
        values.add(new Entry(15, 120));
        values.add(new Entry(20, 30));
        values.add(new Entry(35, 10));
        values.add(new Entry(40, 110));
        values.add(new Entry(45, 30));
        values.add(new Entry(50, 160));
        values.add(new Entry(100, 30));
        values.add(new Entry(110, 30));
        values.add(new Entry(120, 30));
        values.add(new Entry(150, 80));
        values.add(new Entry(160, 150));
        //这里我模拟一些数据（柱状）
        barEntries.add(new BarEntry(5, 50));
        barEntries.add(new BarEntry(10, 66));
        barEntries.add(new BarEntry(15, 120));
        barEntries.add(new BarEntry(20, 30));
        barEntries.add(new BarEntry(35, 10));
        barEntries.add(new BarEntry(40, 110));
        barEntries.add(new BarEntry(45, 30));
        barEntries.add(new BarEntry(50, 160));
        barEntries.add(new BarEntry(100, 30));
        barEntries.add(new BarEntry(110, 30));
        barEntries.add(new BarEntry(120, 30));
        barEntries.add(new BarEntry(150, 80));
        barEntries.add(new BarEntry(160, 150));
        //饼状模拟数据
        entries.add(new PieEntry(20, "满分"));
        entries.add(new PieEntry(5, "非常优秀"));
        entries.add(new PieEntry(40, "优秀"));
        entries.add(new PieEntry(30, "及格"));
        entries.add(new PieEntry(5, "不及格"));
    }

    /**
     * 初始化折线图
     */
    private void initLineChart() {
        //设置手势滑动事件
        lineChart.setOnChartGestureListener(this);
//        设置数值选择监听
        lineChart.setOnChartValueSelectedListener(this);
//        后台绘制
        lineChart.setDrawGridBackground(false);
        //设置描述文本
        lineChart.getDescription().setEnabled(false);
        //设置支持触控手势
        lineChart.setTouchEnabled(true);
        //设置缩放
        lineChart.setDragEnabled(true);
        //设置推动
        lineChart.setScaleEnabled(true);
        //如果禁用,扩展可以在x轴和y轴分别完成
        lineChart.setPinchZoom(true);
        // 得到这个文字
        Legend l = lineChart.getLegend();
        // 修改文字 ...
        l.setForm(Legend.LegendForm.LINE);//文字前的标识符号是（线）
    }

    private void showLineChart() {//显示折线图
        hideView();
        lineChart.setVisibility(View.VISIBLE);
        setData(values);
        //刷新
        lineChart.invalidate();
        //默认动画
        lineChart.animateX(1500);
    }

    //折线传递数据集
    private void setData(ArrayList<Entry> values) {
        LineDataSet set1;
        if (lineChart.getData() != null && lineChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) lineChart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            lineChart.getData().notifyDataChanged();
            lineChart.notifyDataSetChanged();
        } else {
            // 创建一个数据集,并给它一个类型
            set1 = new LineDataSet(values, "年度总结报告");

//             在这里设置线
            set1.enableDashedLine(10f, 5f, 0f);//虚线
            set1.enableDashedHighlightLine(10f, 5f, 0f);
            set1.setColor(Color.BLACK);//线颜色
            set1.setCircleColor(Color.BLACK);//数据点颜色
            set1.setLineWidth(1f);//线大小
            set1.setCircleRadius(3f);//点大小
            set1.setDrawCircleHole(false);
            set1.setValueTextSize(9f);//点文字大小
            set1.setDrawFilled(true);//图形填充
            set1.setFormLineWidth(1f);
            set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
            set1.setFormSize(15.f);
            set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);//折线这是为圆滑线

            if (Utils.getSDKInt() >= 18) {
                // 填充背景只支持18以上
                Drawable drawable = ContextCompat.getDrawable(this, R.mipmap.ic_launcher);
                set1.setFillDrawable(drawable);
                set1.setFillColor(Color.YELLOW);
            } else {
                set1.setFillColor(Color.BLACK);
            }
            ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
            //添加数据集
            dataSets.add(set1);

            //创建一个数据集的数据对象
            LineData data = new LineData(dataSets);

            //谁知数据
            lineChart.setData(data);
        }
    }

    /**
     * 初始化柱状图
     */
    private void initBarChart() {
        //设置表格上的点，被点击的时候，的回调函数
        mBarChart.setOnChartValueSelectedListener(this);
        mBarChart.setDrawBarShadow(false);
        mBarChart.setDrawValueAboveBar(true);
        mBarChart.getDescription().setEnabled(false);
        // 如果60多个条目显示在图表,drawn没有值
        mBarChart.setMaxVisibleValueCount(60);
        // 扩展现在只能分别在x轴和y轴
        mBarChart.setPinchZoom(true);
        //是否显示表格颜色
        mBarChart.setDrawGridBackground(true);
        //左右两边间隙
        mBarChart.setFitBars(true);

        XAxis xAxis = mBarChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);//x轴网格线
        xAxis.setGranularity(1f);//x轴最小单位
        xAxis.setLabelCount(7);//x轴网格数量
//        xAxis.setAxisMaximum(200f);//x轴最大值
        xAxis.setAxisMinimum(0f);
        xAxis.setDrawAxisLine(true);
//        xAxis.setValueFormatter(xAxisFormatter);


        YAxis leftAxis = mBarChart.getAxisLeft();
        leftAxis.setStartAtZero(true);
//        leftAxis.setLabelCount(8, false);
//        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
//        leftAxis.setSpaceTop(15f);
//        //这个替换setStartAtZero(true)
//        leftAxis.setAxisMinimum(0f);
//        leftAxis.setAxisMaximum(200f);
//
        YAxis rightAxis = mBarChart.getAxisRight();
        rightAxis.setEnabled(false);//隐藏右测竖线
//        rightAxis.setDrawGridLines(false);
//        rightAxis.setLabelCount(8, false);
//        rightAxis.setSpaceTop(15f);
//        rightAxis.setAxisMinimum(0f);
//        rightAxis.setAxisMaximum(50f);
    }

    /**
     * 显示柱状图
     */
    private void showBarChart() {
        hideView();
        mBarChart.setVisibility(View.VISIBLE);
        //设置数据
        setDataBar(barEntries);
        mBarChart.invalidate();
        mBarChart.animateXY(2000, 1500);
    }

    //条形设置数据
    private void setDataBar(ArrayList yVals1) {
        float start = 1f;
        BarDataSet set1;
        if (mBarChart.getData() != null && mBarChart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) mBarChart.getData().getDataSetByIndex(0);
            set1.setValues(yVals1);
            mBarChart.getData().notifyDataChanged();
            mBarChart.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(yVals1, "2017年工资涨幅");
            //设置有四种颜色
            set1.setColors(ColorTemplate.MATERIAL_COLORS);
            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
            dataSets.add(set1);
            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);
            data.setBarWidth(0.9f);
            //设置数据
            mBarChart.setData(data);
            mBarChart.setVisibleXRangeMaximum(50f);//设置x轴最多显示数据条数
            mBarChart.setVisibleYRangeMaximum(200f, YAxis.AxisDependency.LEFT);//设置Y轴左边最多显示数据条数
        }
    }

    /**
     * 初始化饼状图
     */
    private void initPieChart() {
        mPieChart.setUsePercentValues(true);
        mPieChart.getDescription().setEnabled(false);
        mPieChart.setExtraOffsets(5, 10, 5, 5);//图像偏移量

        mPieChart.setDragDecelerationFrictionCoef(0.95f);//摩擦力
        //设置中间文件
        mPieChart.setCenterText("成绩分布表");
        mPieChart.setCenterTextSize(40f);

        mPieChart.setDrawHoleEnabled(true);//图标中心区域是否为空
        mPieChart.setHoleColor(Color.WHITE);//图标中心区域颜色

        mPieChart.setTransparentCircleColor(Color.WHITE);//透明圈颜色
        mPieChart.setTransparentCircleAlpha(110);//透明圈透明度

        mPieChart.setHoleRadius(58f);//中心孔的半径
        mPieChart.setTransparentCircleRadius(61f);//中心孔加透明圈的半径-中心空半径（透明圈大小）

        mPieChart.setDrawCenterText(true);//是否显示中心文字

        mPieChart.setRotationAngle(0);//绘图偏移量（最大270）

        // 触摸旋转
        mPieChart.setRotationEnabled(true);//是否允许触摸旋转
        mPieChart.setHighlightPerTapEnabled(true);//点击突出高亮

        //变化监听
        mPieChart.setOnChartValueSelectedListener(this);

        //表旁文字说明格式设置
        Legend legend = mPieChart.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);
        legend.setDrawInside(false);
        legend.setXEntrySpace(7f);
        legend.setYEntrySpace(0f);
        legend.setYOffset(0f);

        // 输入标签样式
        mPieChart.setEntryLabelColor(Color.WHITE);
        mPieChart.setEntryLabelTextSize(30f);
    }

    /**
     * 显示饼状图
     */
    private void showPieChart() {
        hideView();
        mPieChart.setVisibility(View.VISIBLE);
        //设置数据
        setDataPie(entries);
        mPieChart.invalidate();
        mPieChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
    }

    //饼装设置数据
    private void setDataPie(ArrayList<PieEntry> entries) {
        PieDataSet dataSet = new PieDataSet(entries, "三年级一班");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);

        //数据和颜色
        ArrayList<Integer> colors = new ArrayList<Integer>();
        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);
        colors.add(ColorTemplate.getHoloBlue());
        dataSet.setColors(colors);
        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(30f);
        data.setValueTextColor(Color.WHITE);
//        data.setDrawValues(false);
        mPieChart.setData(data);
        mPieChart.highlightValues(null);
        //刷新
        mPieChart.invalidate();
    }

    /**
     * 初始化折线+柱状
     */
    private void initCombinedChart() {
        /**
         * 柱状+折线
         */
        combinedChart.setDrawBorders(false); // 显示边界
        combinedChart.getDescription().setEnabled(false);  // 不显示备注信息
        combinedChart.setPinchZoom(false); // 比例缩放

        XAxis xAxisC = combinedChart.getXAxis();
        xAxisC.setDrawGridLines(false);
        xAxisC.setAxisMinValue(0f);
        xAxisC.setAxisMaxValue(200f);
        /*解决左右两端柱形图只显示一半的情况 只有使用CombinedChart时会出现，如果单独使用BarChart不会有这个问题*/
//        xAxisC.setAxisMinimum(-0.5f);
//        xAxisC.setAxisMaximum(barEntries.size() - 0.5f);

//        xAxisC.setLabelCount(suppliers.length); // 设置X轴标签数量
        xAxisC.setPosition(XAxis.XAxisPosition.BOTTOM); // 设置X轴标签位置，BOTTOM在底部显示，TOP在顶部显示
//        xAxisC.setValueFormatter(new IAxisValueFormatter() { // 转换要显示的标签文本，value值默认是int从0开始
//            @Override
//            public String getFormattedValue(float value, AxisBase axis) {
//                return suppliers[(int) value];
//            }
//        });


        YAxis axisLeft = combinedChart.getAxisLeft(); // 获取左边Y轴操作类
        axisLeft.setAxisMinimum(0); // 设置最小值
        axisLeft.setGranularity(10); // 设置Label间隔
        axisLeft.setLabelCount(10);// 设置标签数量
        axisLeft.setValueFormatter(new IAxisValueFormatter() { // 在左边Y轴标签文本后加上%号
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return value + "%";
            }
        });

        YAxis axisRight = combinedChart.getAxisRight(); // 获取右边Y轴操作类
        axisRight.setDrawGridLines(false); // 不绘制背景线，上面左边Y轴并没有设置此属性，因为不设置默认是显示的
        axisRight.setGranularity(10); // 设置Label间隔
        axisRight.setAxisMinimum(0); // 设置最小值
        axisRight.setLabelCount(20); // 设置标签个数
        axisRight.setValueFormatter(new IAxisValueFormatter() { // 在右边Y轴标签文本后加上%号
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return value + "%";
            }
        });
        axisRight.setEnabled(false);
    }

    /**
     * 显示折线+柱状
     */
    private void showCombinedChart() {
        hideView();
        combinedChart.setVisibility(View.VISIBLE);
        setCombinedData(barEntries, values);
        combinedChart.invalidate();
        combinedChart.animateY(1500);
    }

    private void setCombinedData(ArrayList<BarEntry> barEntries, ArrayList<Entry> values) {
        /**
         * 初始化柱形图的数据
         * 此处用suppliers的数量做循环，因为总共所需要的数据源数量应该和标签个数一致
         * 其中BarEntry是柱形图数据源的实体类，包装xy坐标数据
         */
        /******************BarData start********************/
//        List<BarEntry> barEntriesC = new ArrayList<>();
//        for (int i = 0; i < suppliers.length; i++) {
//            barEntriesC.add(new BarEntry(i, getRandom(100, 50)));
//        }
        BarDataSet barDataSet = new BarDataSet(barEntries, "LAR");  // 新建一组柱形图，"LAR"为本组柱形图的Label
        barDataSet.setColor(Color.parseColor("#0288d1")); // 设置柱形图颜色
        barDataSet.setValueTextColor(Color.parseColor("#0288d1")); //  设置柱形图顶部文本颜色
        BarData barData = new BarData();
        barData.addDataSet(barDataSet);// 添加一组柱形图，如果有多组柱形图数据，则可以多次addDataSet来设置
        /******************BarData end********************/

        /**
         * 初始化折线图数据
         * 说明同上
         */
        /******************LineData start********************/
//        List<Entry> lineEntries = new ArrayList<>();
//        for (int i = 0; i < suppliers.length; i++) {
//            lineEntries.add(new Entry(i, getRandom(100, 0)));
//        }
        LineDataSet lineDataSet = new LineDataSet(values, "不良率");
        lineDataSet.setColor(Color.parseColor("#b71c1c"));
        lineDataSet.setCircleColor(Color.parseColor("#b71c1c"));
        lineDataSet.setValueTextColor(Color.parseColor("#f44336"));
        lineDataSet.setLineWidth(2f);
        lineDataSet.setCircleRadius(3f);//点大小
        lineDataSet.setHighlightEnabled(false);
        lineDataSet.setDrawValues(false);//是否绘制曲线上的文字
        LineData lineData = new LineData();
        lineData.addDataSet(lineDataSet);
        /******************LineData end********************/

        CombinedData combinedData = new CombinedData(); // 创建组合图的数据源
        combinedData.setData(barData);  // 添加柱形图数据源
        combinedData.setData(lineData); // 添加折线图数据源
        combinedChart.setData(combinedData); // 为组合图设置数据源

        combinedChart.setVisibleXRangeMaximum(50f);
//        combinedChart.setVisibleYRangeMaximum(200f, YAxis.AxisDependency.LEFT);
    }

    /**
     * 隐藏所有布局
     */
    private void hideView() {
        lineChart.setVisibility(View.GONE);
        mBarChart.setVisibility(View.GONE);
        mPieChart.setVisibility(View.GONE);
        combinedChart.setVisibility(View.GONE);
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        Log.e(TAG, "onValueSelected");
    }

    @Override
    public void onNothingSelected() {
        Log.e(TAG, "onNothingSelected");
    }

    @Override
    public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
        Log.e(TAG, "onChartGestureStart");
    }

    @Override
    public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
        Log.e(TAG, "onChartGestureEnd");
    }

    @Override
    public void onChartLongPressed(MotionEvent me) {
        Log.e(TAG, "onChartLongPressed");
    }

    @Override
    public void onChartDoubleTapped(MotionEvent me) {
        Log.e(TAG, "onChartDoubleTapped");
    }

    @Override
    public void onChartSingleTapped(MotionEvent me) {
        Log.e(TAG, "onChartSingleTapped");
    }

    @Override
    public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {
        Log.e(TAG, "onChartFling");
    }

    @Override
    public void onChartScale(MotionEvent me, float scaleX, float scaleY) {
        Log.e(TAG, "onChartScale");
    }

    @Override
    public void onChartTranslate(MotionEvent me, float dX, float dY) {
        Log.e(TAG, "onChartTranslate");
    }

    public void showLineChart(View view) {
        showLineChart();
    }

    public void showBarChart(View view) {
        showBarChart();
    }

    public void showPieChart(View view) {
        showPieChart();
    }

    public void showCombinedChart(View view) {
        showCombinedChart();
    }
}
