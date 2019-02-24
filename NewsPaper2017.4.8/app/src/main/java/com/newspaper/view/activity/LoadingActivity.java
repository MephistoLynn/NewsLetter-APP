package com.newspaper.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.newspaper.R;
import com.newspaper.model.Bean.NewsOther;
import com.newspaper.model.Bean.NewsTop;
import com.newspaper.presenter.IEvent;
import com.newspaper.presenter.NewsPresenter;
import com.newspaper.utils.MyView.TasksProgressView;
import com.newspaper.view.Task.progressTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.greenrobot.event.EventBus;


/**
 * @author naiyu(http://snailws.com)
 * @version 1.0
 */
public class LoadingActivity extends Activity implements IEvent, progressTask.ISend {

    private TasksProgressView mTasksView;

    private int mTotalProgress;
    private int mCurrentProgress;
    private NewsPresenter newsPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //全屏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_loading);

        newsPresenter = NewsPresenter.getInstance(this);
        newsPresenter.getTop();
        initVariable();
        initView();


//        new Thread(new ProgressRunable()).start();
    }

    private void initVariable() {
        mTotalProgress = 100;
        mCurrentProgress = 0;
    }

    private void initView() {
        mTasksView = (TasksProgressView) findViewById(R.id.loading_progress_view);
    }


    @Override
    public void getTop(List<NewsTop.ResultBean.DataBean> listTop) {

        String[] urls = getUrls(listTop);
        if (urls.length > 0) {
            //开启加载任务
            new progressTask(getTitles(listTop), mTasksView, LoadingActivity.this).execute(urls);
        } else {

            startActivity(new Intent(LoadingActivity.this, MainActivity.class));
        }


    }


    private String[] getTitles(List<NewsTop.ResultBean.DataBean> listTop) {
        ArrayList<String> titlesList = new ArrayList<>();
        String[] array = new String[listTop.size()];
        for (NewsTop.ResultBean.DataBean bean : listTop) {

            titlesList.add(bean.getTitle());
        }
        titlesList.toArray(array);
        return array;
    }

    private String[] getUrls(List<NewsTop.ResultBean.DataBean> listTop) {
        ArrayList<String> UrlsList = new ArrayList<>();
        String[] array = new String[listTop.size()];
        for (NewsTop.ResultBean.DataBean bean : listTop) {
            if (bean.getThumbnail_pic_s03() == null) {
                continue;
            }
            UrlsList.add(bean.getThumbnail_pic_s03());
        }
        UrlsList.toArray(array);
        return array;
    }

    @Override
    public void getOther(List<NewsOther.ResultBean.DataBean> listOther) {

    }

    @Override
    public void onError(Throwable e) {
        showError(findViewById(R.id.loading_progress_view));
    }


    HashMap<String, Bitmap> map;


    @Override
    public void sendBitmap(HashMap<String, Bitmap> map) {

        this.map = map;
        Intent intent = new Intent(this, GuideSlideActvity.class);
//            SerializableMap mapSerializable = new SerializableMap();
//            mapSerializable.setMap(map);
//            Bundle bundle = new Bundle();
//            bundle.putSerializable("data", mapSerializable);
//            intent.putExtra("data", bundle);
        startActivity(intent);
        finish();

    }


    @Override
    protected void onStop() {
        super.onStop();
        if (map != null) {
            EventBus.getDefault().post(map);
        }
    }

    //传递数据
    private void postBitmapByBus(HashMap<String, Bitmap> map) {
        EventBus.getDefault().post(map);
    }


    private void showError(View view) {
        Snackbar.make(view, "网络异常", Snackbar.LENGTH_INDEFINITE).setAction("刷新", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newsPresenter.getTop();
            }
        }).show();
    }


}
