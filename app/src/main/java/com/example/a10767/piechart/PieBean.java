package com.example.a10767.piechart;

public class PieBean {

    private float mData;//数据
    private String mName;//扇性所对应的名字
    private int mColor;//对应的颜色
    private boolean mIsDraw;//是否绘制

    public PieBean(float data, String name, int color, boolean isDraw) {
        mData = data;
        mName = name;
        mColor = color;
        mIsDraw = isDraw;
    }

    public void setData(float data) {
        mData = data;
    }

    public void setColor(int color) {
        mColor = color;
    }

    public void setDraw(boolean draw) {
        mIsDraw = draw;
    }

    public float getData() {
        return mData;
    }

    public int getColor() {
        return mColor;
    }

    public boolean isDraw() {
        return mIsDraw;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }
}