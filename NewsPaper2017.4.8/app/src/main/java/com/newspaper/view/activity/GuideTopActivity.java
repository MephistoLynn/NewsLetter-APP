package com.newspaper.view.activity;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ToxicBakery.viewpager.transforms.ZoomOutTranformer;
import com.newspaper.R;
import com.newspaper.model.Bean.NewsTop;
import com.newspaper.utils.indicator_ext.navigator.ScaleCircleNavigator;
import com.newspaper.view.adapter.GuidePagerAdapter;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GuideTopActivity extends AppCompatActivity implements GuidePagerAdapter.IPagerDetail {


    @BindView(R.id.fab1)
    FloatingActionButton floatingActionButton;
    @BindView(R.id.guide_view_pager)
    ViewPager mViewPager;
    @BindView(R.id.title1)
    TextView title1;
    @BindView(R.id.date1)
    TextView date1;
    @BindView(R.id.author_name1)
    TextView author_name1;
    @BindView(R.id.guide_linear_anim)
    LinearLayout linearLayout;


    private List<NewsTop.ResultBean.DataBean> listTop;
    private GuidePagerAdapter mGuidePagerAdapter;
    private static final int[] COLORS = new int[]{0xFF33B5E5, 0xFFAA66CC, 0xFF99CC00, 0xFFFFBB33, 0xFFFF4444, 0xff17324d, 0xff994c52, 0xffd9752a, 0xffe6b451};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guide_pager);

        ButterKnife.bind(this);

        initView();
        initMagicIndicator();
        initEvent();

    }

    private void initView() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        mGuidePagerAdapter = new GuidePagerAdapter(GuideTopActivity.this);

        mViewPager.setAdapter(mGuidePagerAdapter);
        mViewPager.setPageTransformer(true, new ZoomOutTranformer());

        // initAnimation(linearLayout);
    }


    private void initEvent() {

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


            }

            @Override
            public void onPageSelected(int position) {
                if (position == 8) {
                    Snackbar.make(mViewPager, "最后一页", Snackbar.LENGTH_SHORT).setAction("HOME", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            IntentTo();
                        }
                    }).show();

                }
                title1.setText(listTop.get(position).getTitle());
                date1.setText(listTop.get(position).getDate());
                author_name1.setText(listTop.get(position).getAuthor_name());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }

        });


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentTo();
            }
        });


    }

    //执行跳转
    private void IntentTo() {
        startActivity(new Intent(GuideTopActivity.this, MainActivity.class));
        this.overridePendingTransition(R.anim.slide_up, R.anim.scale_down);
    }


    private void initMagicIndicator() {

        MagicIndicator magicIndicator = (MagicIndicator) findViewById(R.id.magic_indicator3);
        ScaleCircleNavigator scaleCircleNavigator = new ScaleCircleNavigator(this);
        scaleCircleNavigator.setCircleCount(9);
        scaleCircleNavigator.setNormalCircleColor(Color.LTGRAY);
        scaleCircleNavigator.setSelectedCircleColor(Color.DKGRAY);
        scaleCircleNavigator.setCircleClickListener(new ScaleCircleNavigator.OnCircleClickListener() {
            @Override
            public void onClick(int index) {
                mViewPager.setCurrentItem(index);
            }
        });
        magicIndicator.setNavigator(scaleCircleNavigator);
        ViewPagerHelper.bind(magicIndicator, mViewPager);
    }

    private ValueAnimator colorAnim;

    private void initAnimation(View view) {
        colorAnim = ObjectAnimator.ofInt(view, "backgroundColor", 0xff17324d, 0xff994c52, 0xffd9752a, 0xffe6b451);
        colorAnim.setDuration(4000);
        colorAnim.setEvaluator(new ArgbEvaluator());
        colorAnim.setRepeatCount(ValueAnimator.INFINITE);
        colorAnim.setRepeatMode(ValueAnimator.REVERSE);
        colorAnim.start();
    }

    @Override
    public void modifyText(List<NewsTop.ResultBean.DataBean> listTop) {
        this.listTop = listTop;
        title1.setText(listTop.get(0).getTitle());
        date1.setText(listTop.get(0).getDate());
        author_name1.setText(listTop.get(0).getAuthor_name());
    }

}
