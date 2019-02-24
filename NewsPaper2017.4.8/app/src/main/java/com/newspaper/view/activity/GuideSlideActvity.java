package com.newspaper.view.activity;

import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.OvershootInterpolator;
import android.widget.TextView;
import android.widget.Toast;

import com.newspaper.R;
import com.newspaper.model.Bean.NewsOther;
import com.newspaper.model.Bean.NewsTop;
import com.newspaper.presenter.IEvent;
import com.newspaper.utils.MyView.AntiAliasTextView;
import com.newspaper.utils.MyView.ImageSlidePanel;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

/**
 * Created by mephisto- on 2016/10/29.
 */

public class GuideSlideActvity extends AppCompatActivity implements IEvent {


    private Handler handler;
    private View.OnClickListener btnListener;

    @BindView(R.id.image_slide_panel)
    ImageSlidePanel slidePanel;
    @BindView(R.id.left_shake)
    View leftShake;
    @BindView(R.id.right_shake)
    View rightShake;
    @BindView(R.id.bottom_shake)
    View bottomShake;
    @BindView(R.id.left_click_area)
    View leftClickArea;
    @BindView(R.id.right_click_area)
    View rightClickArea;
    @BindView(R.id.bottom_click_area)
    View bottomClickArea;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //全屏
        setContentView(R.layout.guide_slide_layout);
        //注册EventBus
        EventBus.getDefault().register(this);

        ButterKnife.bind(this);


        initEvent();

        leftClickArea.setOnClickListener(btnListener);
        rightClickArea.setOnClickListener(btnListener);

        delayShowSlidePanel();
    }


    @Subscribe(threadMode = ThreadMode.MainThread)
    public void receiveMap(HashMap<String, Bitmap> map) {
//更新背景图
        initAliasText(map);
    }

    private void initEvent() {


        bottomClickArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentTo();
            }
        });


        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                slidePanel.startInAnim();
                initAnimations();
            }
        };


        btnListener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int type = 0;
                if (v.getId() == leftClickArea.getId()) {
                    type = -1;
                } else if (v.getId() == rightClickArea.getId()) {
                    type = 1;
                }
                slidePanel.onClickFade(type);
            }
        };
    }


    //执行跳转
    private void IntentTo() {
        Intent intent = new Intent(GuideSlideActvity.this, MainActivity.class);
        startActivity(intent);
        this.overridePendingTransition(R.anim.slide_up, R.anim.scale_down);
        finish();
    }


    @BindViews({R.id.guide_slide_textview1, R.id.guide_slide_textview2,
            R.id.guide_slide_textview3, R.id.guide_slide_textview4,
            R.id.guide_slide_textview5, R.id.guide_slide_textview6,})
    List<AntiAliasTextView> textViewList;

    private void initAliasText(HashMap<String, Bitmap> map) {
        String[] titles = map.keySet().toArray(new String[map.size()]);
        for (int i = 0; i < textViewList.size(); i++) {

            textViewList.get(i).setText(titles[i]);
            BitmapDrawable bd = new BitmapDrawable(getResources(), map.get(titles[i]));
            textViewList.get(i).setBackground(bd);
        }

    }


    private void initAnimations() {
        Animation animationLeft = AnimationUtils.loadAnimation(this,
                R.anim.left_shake);
        Animation animationRight = AnimationUtils.loadAnimation(this,
                R.anim.right_shake);

        animationLeft.setInterpolator(new OvershootInterpolator(3));
        animationRight.setInterpolator(new OvershootInterpolator(3));

        leftShake.startAnimation(animationLeft);
        rightShake.startAnimation(animationRight);

        // 底部的动画使用keyFrame动画
        Keyframe kf0 = Keyframe.ofFloat(0, 0);
        Keyframe kf1 = Keyframe.ofFloat(0.6f, -70);
        Keyframe kf9 = Keyframe.ofFloat(1.0f, -70);
        PropertyValuesHolder pvhTranslateY = PropertyValuesHolder.ofKeyframe(
                View.TRANSLATION_Y, kf0, kf1, kf9);

        Keyframe kf2 = Keyframe.ofFloat(0, 1f);
        Keyframe kf3 = Keyframe.ofFloat(0.4f, 1f);
        Keyframe kf4 = Keyframe.ofFloat(0.6f, 0f);
        PropertyValuesHolder pvhAlpha = PropertyValuesHolder.ofKeyframe(
                View.ALPHA, kf2, kf3, kf4);

        ObjectAnimator bottomAnim = ObjectAnimator.ofPropertyValuesHolder(
                bottomShake, pvhTranslateY, pvhAlpha);
        bottomAnim.setDuration(1500);
        bottomAnim.setRepeatMode(ValueAnimator.REVERSE);
        bottomAnim.setRepeatCount(Animation.INFINITE);
        bottomAnim.start();

        leftShake.setVisibility(View.VISIBLE);
        rightShake.setVisibility(View.VISIBLE);
        bottomShake.setVisibility(View.VISIBLE);
    }

    private void delayShowSlidePanel() {

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                handler.sendEmptyMessage(1);
            }
        }, 1200);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //解绑EventBus
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void getTop(List<NewsTop.ResultBean.DataBean> listTop) {

    }

    @Override
    public void getOther(List<NewsOther.ResultBean.DataBean> listOther) {

    }

    @Override
    public void onError(Throwable e) {

    }


}
