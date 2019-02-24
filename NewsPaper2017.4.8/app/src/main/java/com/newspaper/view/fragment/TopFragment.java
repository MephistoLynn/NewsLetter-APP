package com.newspaper.view.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.newspaper.R;
import com.newspaper.model.Bean.NewsTop;
import com.newspaper.model.Constant.IntentKey;
import com.newspaper.utils.Decoration.SpacesItemDecoration;
import com.newspaper.view.activity.WebDetailActivity;
import com.newspaper.view.adapter.StaggeredApdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mephisto- on 2016/10/22.
 */

public class TopFragment extends Fragment implements StaggeredApdapter.waterfallDeliver {
    @BindView(R.id.topfragment_recycler)
    RecyclerView recyclerView;

    private ViewGroup layout;
    private StaggeredApdapter staggeredApdapter;


    private List<NewsTop.ResultBean.DataBean> savedListTop;


    public RecyclerView getViewForSnack() {

        return recyclerView;

    }

    public StaggeredApdapter getAdapter() {

        return staggeredApdapter;

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        layout = (ViewGroup) inflater.inflate(R.layout.waterfall_layout, container, false);
        ButterKnife.bind(this, layout);
        return layout;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initEvent();
    }


    private void initEvent() {

        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));


        staggeredApdapter = new StaggeredApdapter(this);
        if (savedListTop == null) {
            staggeredApdapter.requestTopData();
        } else {
            staggeredApdapter.initAdapterData(savedListTop);
        }

        recyclerView.setAdapter(staggeredApdapter);
        recyclerView.addItemDecoration(new SpacesItemDecoration(10));

    }


    @Override
    public void deliver(List<NewsTop.ResultBean.DataBean> listTop) {
        savedListTop = listTop;
    }

    @Override
    public void onitemclick(View view, int position, List<NewsTop.ResultBean.DataBean> listTop) {
        Intent i = new Intent();
        i.putExtra(IntentKey.URL_TO_WEB, listTop.get(position).getUrl());
        i.putExtra(IntentKey.TITLE_TO_WEB, listTop.get(position).getTitle());
        i.setClass(getActivity(), WebDetailActivity.class);
        startActivity(i);
    }
}
