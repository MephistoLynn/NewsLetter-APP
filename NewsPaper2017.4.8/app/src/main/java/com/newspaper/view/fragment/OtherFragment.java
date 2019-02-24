package com.newspaper.view.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.newspaper.R;
import com.newspaper.model.Bean.DeliverBean;
import com.newspaper.model.Bean.NewsOther;
import com.newspaper.model.Bean.NewsTop;
import com.newspaper.model.Constant.IntentKey;
import com.newspaper.model.HttpDetail.HttpParams;
import com.newspaper.presenter.IEvent;
import com.newspaper.presenter.NewsPresenter;
import com.newspaper.utils.Animations.RecyclerAnimation.ScaleInAnimatorAdapter;
import com.newspaper.view.activity.WebDetailActivity;
import com.newspaper.view.adapter.RecyclerViewAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class OtherFragment extends Fragment implements IEvent {


    @BindView(R.id.other_recycler_list)
    RecyclerView recycler;


    private ViewGroup layout;
    private RecyclerViewAdapter recycleAdapter;
    private NewsPresenter presenter;
    //暂存的已请求的数据
    private List<NewsOther.ResultBean.DataBean> savedListOther = null;
    //请求数据的类型
    private String paramType = "";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            paramType = bundle.getString(HttpParams.paramTYPE);
        }
        // Inflate the layout for this fragment
        layout = (ViewGroup) inflater.inflate(R.layout.fragment_tab_info, container, false);
        presenter = NewsPresenter.getInstance(this);
        ButterKnife.bind(this, layout);
        return layout;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        initView();
        if (savedListOther != null) {
            updateAdapter(savedListOther);
        } else {
            presenter.getOther(paramType);
        }
    }

    private void initView() {


        initRecyclerView();
    }


    private void initRecyclerView() {
        //初始化Recycler
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recycler.setLayoutManager(layoutManager);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        recycler.setItemAnimator(new DefaultItemAnimator());

        recycleAdapter = new RecyclerViewAdapter(getActivity());
        //recyclerView 项目添加动画
        ScaleInAnimatorAdapter scaleInRecyclerViewAnimationAdapter = new ScaleInAnimatorAdapter(recycleAdapter, recycler);
        recycler.setAdapter(scaleInRecyclerViewAnimationAdapter);
        //点击事件
        recycleAdapter.setItemClickListener(new RecyclerViewAdapter.RecylerItemClickListener() {
            @Override
            public void onItemClick(View view) {
                Intent intent = new Intent(getActivity(), WebDetailActivity.class);
                DeliverBean deliverBean = (DeliverBean) view.getTag();

                intent.putExtra(IntentKey.URL_TO_WEB, deliverBean.getUrl());
                intent.putExtra(IntentKey.TITLE_TO_WEB, deliverBean.getTitle());
                startActivity(intent);
            }
        });
    }


    @Override
    public void getOther(List<NewsOther.ResultBean.DataBean> listOther) {

        savedListOther = listOther;
        updateAdapter(listOther);
    }

    @Override
    public void onError(Throwable e) {
        showError(recycler);
    }


    @Override
    public void getTop(List<NewsTop.ResultBean.DataBean> listTop) {

    }


    private void updateAdapter(List<NewsOther.ResultBean.DataBean> listOther) {
        recycleAdapter.updateData(listOther);
        recycleAdapter.notifyDataSetChanged();
        recycleAdapter.startBanner();
    }

    private void showError(View view) {
        Snackbar.make(view, "网络异常", Snackbar.LENGTH_LONG).setAction("刷新", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refresh();
            }
        }).show();
    }

    public void refresh() {
        if (presenter != null)
            presenter.getOther(paramType);
        refreshBanner();
    }

    @Override
    public void onStart() {
        super.onStart();
        //开始轮播
        recycleAdapter.startBanner();
    }


    //刷新
    public boolean refreshBanner() {
        //刷新Banner
        if (recycleAdapter != null)
            recycleAdapter.resetBanner();
        return true;
    }


    @Override
    public void onPause() {
        super.onPause();
        //结束轮播
        recycleAdapter.stopBanner();
    }


}
