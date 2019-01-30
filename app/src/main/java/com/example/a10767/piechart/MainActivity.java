package com.example.a10767.piechart;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private PieChart mPieChart;
    private List<PieBean> mPieBeanList;//数据列表
    private boolean mIsDestroy = false;//是否销毁

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();
        initThread();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        mPieBeanList = new ArrayList<>();
        Random random = new Random();
        mPieBeanList.add(new PieBean(random.nextInt(20) + 5, "赵田", 0xFF7AD4FD, true));
        mPieBeanList.add(new PieBean(random.nextInt(20) + 5, "孙李", 0xFFFFF58C, true));
        mPieBeanList.add(new PieBean(random.nextInt(20) + 5, "李四", 0xFFFF84F9, true));
        mPieBeanList.add(new PieBean(random.nextInt(20) + 5, "王五", 0xFFA4FF7B, true));
        mPieBeanList.add(new PieBean(random.nextInt(20) + 5, "赵六", 0xFFFF9898, true));
    }

    private void initView() {
        mPieChart = findViewById(R.id.pieChart);
        mPieChart.setDataList(mPieBeanList);//将数据设置进去
    }

    /**
     * 初始化线程
     */
    private void initThread() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!mIsDestroy) {
                    try {
                        Thread.sleep(3000);//睡3秒
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Random random = new Random();
                    for (int i = 0; i < mPieBeanList.size(); i++) {
                        mPieBeanList.get(i).setData(random.nextInt(20) + 5);
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mPieChart.setDataList(mPieBeanList);//重新设一下数据
                        }
                    });
                }
            }
        }).start();
    }

    @Override
    protected void onDestroy() {
        mIsDestroy = false;//表示当前activity 已经被销毁了
        super.onDestroy();
    }
}
