package com.bryant;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.bryant.model.BallModel;
import com.bryant.model.TipsModel;
import com.bryant.xhb.customwaterview.R;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class EnergyTree extends FrameLayout {

    private OnBallItemListener mOnBallItemListener;
    private OnTipsItemListener mOnTipsItemListener;
    private float mWidth, mHeight;
    private Random mRandom = new Random();
    private LayoutInflater mLayoutInflater;
    private List<Float> mOffsets = Arrays.asList(5.0f, 4.5f, 4.8f, 5.5f, 5.8f, 6.0f, 6.5f);
    private boolean isBall=true,isTips=true;
    public EnergyTree(@NonNull Context context) {
        this(context, null);
    }

    public EnergyTree(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EnergyTree(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mLayoutInflater = LayoutInflater.from(getContext());
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    //设置小球数量
    public void setModelList(final List<BallModel> ballList, final List<TipsModel> tipsList) {
        if (ballList == null || ballList.isEmpty()) {
            return;
        }
        removeAllViews();
        post(new Runnable() {
            @Override
            public void run() {
                addWaterView(ballList,tipsList);
            }
        });

    }

    @SuppressLint("SetTextI18n")
    private void addWaterView(List<BallModel> ballList,List<TipsModel> tipsList) {
        int[] xRandom = randomCommon(0, 9, ballList.size());
        int[] yRandom = randomCommon2(1, 5, ballList.size());
        if (xRandom == null || yRandom == null) {
            return;
        }
        for (int i = 0; i < ballList.size(); i++) {
            BallModel ballModel = ballList.get(i);
            final  TextView view;
            view = (TextView) mLayoutInflater.inflate(R.layout.item_ball, this, false);
            view.setText(ballList.get(i).getContent()+"\n"+ballList.get(i).getValue());
            view.setX((float) ((mWidth * xRandom[i] * 0.1)));
            view.setY((float) ((mHeight * yRandom[i] * 0.07)));
            view.setTag(ballModel);
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Object tag = v.getTag();
                    if (tag instanceof BallModel) {
                        if (mOnBallItemListener != null) {
                            mOnBallItemListener.onItemClick((BallModel) tag);
                        }
                        collectAnimator(view,isBall);
                    }
                }
            });
            view.setTag(R.string.isUp, mRandom.nextBoolean());
            setOffset(view);
            addView(view);
            addShowViewAnimation(view);
            start(view);
        }
        int[] num ={5,1,1,5};
        float[] num2 ={(float) 5.5,5,6, (float) 6.5};
        for (int i = 0; i < tipsList.size(); i++) {
            TipsModel tipsModel = tipsList.get(i);
            final  TextView view;
            view = (TextView) mLayoutInflater.inflate(R.layout.item_tips, this, false);
            view.setText(tipsList.get(i).getContent());
            view.setX((float) ((mWidth * num[i] * 0.11)));
            view.setY((float) ((mHeight * num2[i] * 0.08)));
            view.setTag(tipsModel);
            if(i==1||i==2){
                view.setBackgroundResource(R.mipmap.tips2);
            }
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Object tag = v.getTag();
                    if (tag instanceof TipsModel) {
                        if (mOnTipsItemListener != null) {
                            mOnTipsItemListener.onItemClick((TipsModel) tag);
                        }
                        collectAnimator(view,isTips);
                    }
                }
            });
            view.setTag(R.string.isUp, mRandom.nextBoolean());
            setOffset(view);
            addView(view);
            addShowViewAnimation(view);
            start(view);
        }
    }

    //设置小球点击事件
    public void setOnBallItemListener(OnBallItemListener onBallItemListener) {
        mOnBallItemListener = onBallItemListener;
    }
    //设置提示点击事件
    public void setOnTipsItemListener(OnTipsItemListener onWaterItemListener) {
        mOnTipsItemListener = onWaterItemListener;
    }

    public void isCollectBall(boolean bl){
        isBall = bl;
    }

    public void isCollectTips(boolean bl){
        isTips = bl;
    }

    public interface OnBallItemListener {
        void onItemClick(BallModel ballModel);
    }

    public interface OnTipsItemListener {
        void onItemClick(TipsModel tipsModel);
    }
    private void collectAnimator(final View view,boolean isRun) {
        if(isRun) {
            ObjectAnimator translatAnimatorY = ObjectAnimator.ofFloat(view, "translationY", mHeight / 2);
            translatAnimatorY.start();

            ObjectAnimator translatAnimatorX = ObjectAnimator.ofFloat(view, "translationX", mWidth / 2 - 60);
            translatAnimatorX.start();

            ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(view, "alpha", 1f, 0f);
            alphaAnimator.start();

            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.play(translatAnimatorY).with(translatAnimatorX).with(alphaAnimator);
            animatorSet.setDuration(1500);
            animatorSet.start();
            animatorSet.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    removeView(view);
                }
            });
        }
    }

    //点击动画
    public void start(View view) {
        boolean isUp = (boolean) view.getTag(R.string.isUp);
        float offset = (float) view.getTag(R.string.offset);
        ObjectAnimator mAnimator;
        if (isUp) {
            mAnimator = ObjectAnimator.ofFloat(view, "translationY", view.getY() - offset, view.getY() + offset, view.getY() - offset);
        } else {
            mAnimator = ObjectAnimator.ofFloat(view, "translationY", view.getY() + offset, view.getY() - offset, view.getY() + offset);
        }
        mAnimator.setDuration(3000);
        mAnimator.setInterpolator(new LinearInterpolator());
        mAnimator.setRepeatMode(ValueAnimator.RESTART);
        mAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mAnimator.start();
    }

    //添加显示动画
    private void addShowViewAnimation(View view) {
        view.setAlpha(0);
        view.setScaleX(0);
        view.setScaleY(0);
        view.animate().alpha(1).scaleX(1).scaleY(1).setDuration(500).start();
    }

    /*
     * 随机指定XY轴不重复的数
     * @param min 指定范围最小值
     * @param max 指定范围最大值
     * @param n   随机数个数
     */
    public static int[] randomCommon(int min, int max, int n) {
        if (n > (max - min + 1) || max < min) {
            return null;
        }
        int[] result = new int[n];
        int count = 0;
        while (count < n) {
            int num = (int) ((Math.random() * (max - min)) + min);
            boolean flag = true;
            for (int j = 0; j < n; j++) {
                if (num == result[j]) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                result[count] = num;
                count++;
            }
        }
        return result;
    }

    public static int[] randomCommon2(int min, int max, int n) {
        int[] result = new int[n];
        int count = 0;
        while (count < n) {
            int num = (int) ((Math.random() * (max - min)) + min);
            result[count] = num;
            count++;
        }
        return result;
    }

    private void setOffset(View view) {
        float offset = mOffsets.get(mRandom.nextInt(mOffsets.size()));
        view.setTag(R.string.offset, offset);
    }
}
