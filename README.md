# EnergyTree
仿蚂蚁森林收集能量

![这是一张图片](https://github.com/YangsBryant/TeaScreenPopupWindow/blob/master/image/kfgmg-a8c9e.gif)

## 使用方式
取出所需文件：module文件夹下的BallModel和TipsModel实体类，以及EnergyTree控件类，还有所需item布局和资源文件

## 中心点说明
根据EnergyTree控件类的collectAnimator(final View view,boolean isRun)方法，可知道球移动的终点是此布局的中心点，即mHeight/2 andr mWidth/2-60
可以根据自己的需求改变中心点。

## 其他说明
isCollectBall 和 isCollectTips 分别可以控制 点击能量球或者Tips提示，是否可以移动消失

能力球最多可以有七颗

Tips提示框最多4个

## 主要代码
```java
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
```

## 主布局
```java
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:background="@mipmap/background">

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:gravity="center"
        >
        <ImageView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:background="@mipmap/tree" />
    </LinearLayout>
    <com.bryant.EnergyTree
        android:id="@+id/custom_view"
        android:layout_width="match_parent"
        android:layout_height="match_parent"/>
    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:gravity="bottom"
        android:padding="10dp"
        >
        <Button
           android:id="@+id/btn"
           android:text="重置"
           android:layout_width="wrap_content"
           android:layout_height="wrap_content"
        />
    </LinearLayout>
</RelativeLayout>
```
