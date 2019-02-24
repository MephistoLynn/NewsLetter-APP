package com.newspaper.view.adapter;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.newspaper.R;
import com.newspaper.model.Bean.NewsOther;
import com.newspaper.model.Bean.NewsTop;
import com.newspaper.presenter.IEvent;
import com.newspaper.presenter.NewsPresenter;
import com.newspaper.view.fragment.TopFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by str on 2016/10/21.
 */

public class StaggeredApdapter extends RecyclerView.Adapter<StaggeredApdapter.MyViewHolder> implements IEvent {
    private List<NewsTop.ResultBean.DataBean> listTop;
    private Context context;
    private List<Integer> height;
    private waterfallDeliver waterfallDeliver;
    private RecyclerView viewForSnack;

    public interface waterfallDeliver {
        void deliver(List<NewsTop.ResultBean.DataBean> listTop);

        void onitemclick(View view, int position, List<NewsTop.ResultBean.DataBean> listTop);

    }

    NewsPresenter newsPresenter = NewsPresenter.getInstance(this);

    public StaggeredApdapter(TopFragment topFragment) {
        this.context = topFragment.getActivity();
        waterfallDeliver = topFragment;
        viewForSnack = topFragment.getViewForSnack();

    }

    public void requestTopData() {
        newsPresenter.getTop();
    }

    @Override
    public void getTop(List<NewsTop.ResultBean.DataBean> listTop) {
        initAdapterData(listTop);
        waterfallDeliver.deliver(listTop);
    }

    public void initAdapterData(List<NewsTop.ResultBean.DataBean> listTop) {
        this.listTop = listTop;
        this.notifyDataSetChanged();
        height = new ArrayList<>();
        for (int i = 0; i < listTop.size(); i++) {
            height.add((int) (500 + Math.random() * 300));
        }
    }

    @Override
    public void getOther(List<NewsOther.ResultBean.DataBean> listOther) {

    }

    @Override
    public void onError(Throwable e) {
        showError(viewForSnack);
    }

    @Override
    public int getItemCount() {
        return listTop == null ? 0 : listTop.size();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        MyViewHolder myViewHolder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.waterfall_item, parent, false));


        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        LayoutParams layoutParams = holder.staggeredll1.getLayoutParams();
        layoutParams.height = height.get(position);
        holder.staggeredll1.setLayoutParams(layoutParams);
        Glide.with(context).load(listTop.get(position).getThumbnail_pic_s()).crossFade().into(holder.staggerediv1);

        holder.staggeredtv1.setText(listTop.get(position).getTitle());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            int position = holder.getLayoutPosition();

            @Override
            public void onClick(View view) {
                waterfallDeliver.onitemclick(holder.itemView, position, listTop);
            }
        });


    }

    public void setWaterfallDeliver(waterfallDeliver waterfallDeliver) {
        this.waterfallDeliver = waterfallDeliver;
    }

    private void showError(View view) {
        Snackbar.make(view, "网络异常", Snackbar.LENGTH_LONG).setAction("刷新", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refresh();
            }
        }).show();
    }
//执行刷新
    public void refresh() {
        newsPresenter.getTop();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView staggerediv1;
        private TextView staggeredtv1;
        private LinearLayout staggeredll1;

        public MyViewHolder(View itemView) {
            super(itemView);
            staggerediv1 = (ImageView) itemView.findViewById(R.id.staggerdiv1);
            staggeredtv1 = (TextView) itemView.findViewById(R.id.staggerdtv1);
            staggeredll1 = (LinearLayout) itemView.findViewById(R.id.staggerdll1);
        }
    }

}

