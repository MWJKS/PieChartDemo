package com.example.a10767.piechart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.List;

public class PieChart extends View {

    private Paint mPiePaint;//用来扇性的画笔
    private TextPaint mTextPaint;//用来绘制文字的画笔
    private List<PieBean> mPieBeanList;//存放数据的集合

    public PieChart(Context context) {
        super(context);
        init();
    }

    /**
     * 只有实现这个构造方法，才能在xml文件中，使用自定义View
     */
    public PieChart(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        mPiePaint = new Paint();
        mPiePaint.setAntiAlias(true);//抗锯齿
        mTextPaint = new TextPaint();//文字画笔
        mTextPaint.setTextSize(16);//设置文字大小
    }

    /**
     * 绘制的方法
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mPieBeanList == null)
            return;

        int width = getWidth();//View的宽
        int height = getHeight();//View的高

        int radius = (int) (Math.min(width, height) * 0.3f);//确定圆的半径

        int centerX = width / 2;
        int centerY = height / 2;

        float sum = 0;

        for (PieBean pieBean : mPieBeanList) {
            if (pieBean.isDraw())
                sum += pieBean.getData();//加入到总和当中
        }

        RectF rectF = new RectF(centerX - radius, centerY - radius,
                centerX + radius, centerY + radius);//扇性的绘制区域
        float startAngle = 0;//绘制扇性的起始角度

        for (PieBean pieBean : mPieBeanList) {
            if (pieBean.isDraw()) {
                mPiePaint.setColor(pieBean.getColor());//设置画笔颜色
                float percent = pieBean.getData() / sum;//计算单个数据展总和的百分比
                canvas.drawArc(rectF, startAngle, percent * 360, true, mPiePaint);//绘制扇性
                startAngle += percent * 360;//继续向后移
            }
        }

        drawLegend(canvas);

    }

    /**
     * 绘制图例
     *
     * @param canvas
     */
    private void drawLegend(Canvas canvas) {
        int width = getWidth();//View的宽
        int height = getHeight();//View的高
        int centerX = width / 2;
        int centerY = (int) (height * 0.9f);//图例的中心y

        for (int i = 0; i < mPieBeanList.size(); i++) {
            PieBean pieBean = mPieBeanList.get(i);
            if (pieBean.isDraw()) {//如果绘制的话
                mPiePaint.setColor(pieBean.getColor());
                mTextPaint.setColor(0xFF888888);
            } else {
                mPiePaint.setColor(0xffaaaaaa);
                mTextPaint.setColor(0xffaaaaaa);
            }
            int x = (int) (centerX - 100 * (mPieBeanList.size() * 0.5f - i));//计算图例x的位置
            RectF rectF = new RectF(x, centerY - 10, x + 30, centerY + 10);
            canvas.drawRect(rectF, mPiePaint);//绘制矩形
            canvas.drawText(pieBean.getName(), x + 40, centerY + 5, mTextPaint);//绘制文字
        }

    }

    /**
     * 触摸事件
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mPieBeanList == null)
            return super.onTouchEvent(event);
        if (event.getAction() == MotionEvent.ACTION_DOWN) {//如果用户按下

            float downX = event.getX();//用户点击的X位置
            float downY = event.getY();//用户点击的Y位置

            int centerX = getWidth() / 2;
            int centerY = (int) (getHeight() * 0.9f);//图例的中心y

            int left = (int) (centerX - 100 * (mPieBeanList.size() * 0.5f));//每个图例占100像素
            int top = centerY - 15;
            int right = (int) (centerX + 100 * (mPieBeanList.size() * 0.5f));//右边
            int bottom = centerY + 15;

            if (downX > left && downX < right && downY > top && downY < bottom) {
                int index = (int) ((downX - left) / 100);//计算用户点击的是哪个图例
                mPieBeanList.get(index).setDraw(!mPieBeanList.get(index).isDraw());
                invalidate();//重绘
            }
        }

        return super.onTouchEvent(event);
    }

    /**
     * 设置这个饼状图的数据
     *
     * @param pieBeanList
     */
    public void setDataList(List<PieBean> pieBeanList) {
        mPieBeanList = pieBeanList;

        invalidate();//重绘
    }
}