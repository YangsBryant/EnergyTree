package com.bryant;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.bryant.model.BallModel;
import com.bryant.model.TipsModel;
import com.bryant.xhb.customwaterview.R;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EnergyTree mWaterFlake;
    private List<BallModel> mBallList;
    private List<TipsModel> mTipsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
        mWaterFlake = findViewById(R.id.custom_view);
        Button mBtn = findViewById(R.id.btn);
        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWaterFlake.setModelList(mBallList,mTipsList);
            }
        });
        mBtn.post(new Runnable() {
            @Override
            public void run() {
                mWaterFlake.setModelList(mBallList,mTipsList);
            }
        });

        mWaterFlake.isCollectTips(false);
        mWaterFlake.setOnBallItemListener(new EnergyTree.OnBallItemListener() {
            @Override
            public void onItemClick(BallModel ballModel) {
                Toast.makeText(MainActivity.this,"收取了"+ballModel.getValue()+"能量",Toast.LENGTH_SHORT).show();
            }
        });

        mWaterFlake.setOnTipsItemListener(new EnergyTree.OnTipsItemListener() {
            @Override
            public void onItemClick(TipsModel tipsModel) {
                Toast.makeText(MainActivity.this,tipsModel.getContent(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initData() {
        mBallList = new ArrayList<>();
        mBallList.add(new BallModel("能量","5g"));
        mBallList.add(new BallModel("能量","7g"));
        mBallList.add(new BallModel("能量","15g"));
        mBallList.add(new BallModel("能量","1g"));
        mBallList.add(new BallModel("能量","2g"));
        mBallList.add(new BallModel("能量","9g"));
        mBallList.add(new BallModel("能量","9g"));
        mTipsList = new ArrayList<>();
        mTipsList.add(new TipsModel("Tips:缺水"));
        mTipsList.add(new TipsModel("Tips:风大"));
        mTipsList.add(new TipsModel("Tips:暴雨"));
        mTipsList.add(new TipsModel("Tips:干燥"));
    }

}
