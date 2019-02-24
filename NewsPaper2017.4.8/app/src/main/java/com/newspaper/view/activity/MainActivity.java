package com.newspaper.view.activity;

import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ToxicBakery.viewpager.transforms.CubeOutTransformer;
import com.newspaper.R;
import com.newspaper.model.HttpDetail.HttpParams;
import com.newspaper.utils.indicator_ext.titles.ScaleTransitionPagerTitleView;
import com.newspaper.view.adapter.FragmentAdapter;
import com.newspaper.view.dialog.chooseHeadDialog;
import com.newspaper.view.fragment.OtherFragment;
import com.newspaper.view.fragment.TopFragment;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.BezierPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.main_viewpager)
    ViewPager viewPager;
    @BindView(R.id.main_magic_indicator)
    MagicIndicator magicIndicator;
    @BindView(R.id.main_toolbar_title)
    TextView toolbar_title;
    private List<String> titles = Arrays.asList(HttpParams.categories);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        initAutoView();

        initView();

        setStatus();
//        if (otherFragment != null)
//            Toast.makeText(this, "成功", Toast.LENGTH_LONG).show();
//        Toast.makeText(this, "再试试", Toast.LENGTH_LONG).show();

    }

    //沉浸式状态栏
    public void setStatus() {

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT)
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

    }

    private ArrayList<Fragment> fragmentList;

    private void initView() {


        fragmentList = new ArrayList<>();
        Bundle bundleType;
        for (int i = 0; i < titles.size(); i++) {
            if (i == 4) {
                fragmentList.add(new TopFragment());
                continue;
            }

            OtherFragment otherFragment = new OtherFragment();
            bundleType = new Bundle();
            bundleType.putString(HttpParams.paramTYPE, HttpParams.paramTypes[i]);
            otherFragment.setArguments(bundleType);
            fragmentList.add(otherFragment);
        }
        FragmentAdapter adapter =
                new FragmentAdapter(getFragmentManager(), fragmentList, titles);
        viewPager.setAdapter(adapter);
        viewPager.setPageTransformer(true, new CubeOutTransformer());

        initMagicIndicator();

        //导航栏初始化
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        LinearLayout headerView = (LinearLayout) navigationView.getHeaderView(0);
        //头像
        // ImageView head_Img = (ImageView) headerView.findViewById(R.id.Nav_Head_Img);
        ImageView head_Img = ButterKnife.findById(headerView, R.id.Nav_Head_Img);
        head_Img.setOnClickListener(this);
    }

    //初始化标题栏
    private void initMagicIndicator() {

        magicIndicator.setBackgroundColor(Color.WHITE);
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return titles == null ? 0 : titles.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new ScaleTransitionPagerTitleView(context);
                simplePagerTitleView.setText(titles.get(index));
                simplePagerTitleView.setTextSize(18);
                simplePagerTitleView.setNormalColor(Color.GRAY);
                simplePagerTitleView.setSelectedColor(Color.BLACK);
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewPager.setCurrentItem(index);
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                BezierPagerIndicator indicator = new BezierPagerIndicator(context);
                indicator.setColors(Color.parseColor("#ff4a42"), Color.parseColor("#fcde64"), Color.parseColor("#73e8f4"), Color.parseColor("#76b0ff"), Color.parseColor("#c683fe"));
                return indicator;
            }
        });
        magicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magicIndicator, viewPager);
    }


    @BindView(R.id.fab)
    FloatingActionButton fab;

    private void initAutoView() {

        setSupportActionBar(toolbar);
        //设置Title
        // getSupportActionBar().setTitle("NewsLetter");
        toolbar_title.setText("NewsLetter");

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //部署刷新
                refreshPlan();
                Snackbar.make(view, "已刷新", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    //执行具体刷新页面
    private void refreshPlan() {
        int postion = viewPager.getCurrentItem();
        if (postion == 4) {
            TopFragment fragment = (TopFragment) fragmentList.get(postion);
            fragment.getAdapter().refresh();
        } else {
            OtherFragment fragment = (OtherFragment) fragmentList.get(postion);
            fragment.refresh();
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_collection) {

            startActivity(new Intent().setClass(MainActivity.this, EnshrineActivity.class));


            // Handle the camera action
        } else if (id == R.id.nav_about) {
            //关于窗口
//            Dialog dialog =new AboutDialog(this);
//            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//            dialog.show();
        } else if (id == R.id.nav_tool) {

        } else if (id == R.id.nav_setting) {

        } else if (id == R.id.nav_exit) {
            finish();

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Nav_Head_Img:
                new chooseHeadDialog(this).show();
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && data != null) {
            {
/*
                Intent intent = new Intent(this, PreviewActivity.class);
                intent.setDataAndType(uri, "image*//*");
                startActivityForResult(intent, 3);*/

            }
        }
    }
}
