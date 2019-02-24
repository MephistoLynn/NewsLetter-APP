package com.newspaper.view.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.newspaper.R;
import com.newspaper.model.Bean.DeliverBean;
import com.newspaper.model.Bean.NewsOther;
import com.newspaper.utils.bannerLoader.GlideImageLoader;
import com.youth.banner.Banner;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class RecyclerViewAdapter extends RecyclerView.Adapter implements View.OnClickListener {

    private final int TYPE_BANNER = 0;
    private final int TYPE_NORMAL = 1;
    private RecylerItemClickListener itemClickListener = null;

    private List<NewsOther.ResultBean.DataBean> listOther = null;


    private Context context;

    public RecyclerViewAdapter(Context context) {
        this.context = context;
        images = context.getResources().getStringArray(R.array.url);
    }

    public void updateData(List<NewsOther.ResultBean.DataBean> listOther) {
        this.listOther = listOther;
    }

    public void setItemClickListener(RecylerItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }


    //告诉创建什么类型的viewHolder
    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_BANNER;
        } else {
            return TYPE_NORMAL;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        if (viewType == TYPE_BANNER) {
            //此处要创建顶部banner的viewHolder
            holder = new BannerViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_banner_card, parent, false));

        }
        //此处要创建顶部list的viewHolder
        else if (viewType == TYPE_NORMAL) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_card, parent, false);
            view.setOnClickListener(this);
            holder = new listViewHolder(view);
        }
        return holder;
    }


    private DeliverBean deliverBean;

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof BannerViewHolder) {
            bannerHolder = (BannerViewHolder) holder;
            images = context.getResources().getStringArray(R.array.url);
            bannerHolder.banner.setImages(Arrays.asList(images)).setImageLoader(new GlideImageLoader()).start();

        } else if (holder instanceof listViewHolder) {
            int realPosition = position - 1;
            deliverBean = new DeliverBean(listOther.get(realPosition).getTitle(), listOther.get(realPosition).getUrl());
            listViewHolder viewholder = (listViewHolder) holder;
            viewholder.cardView.setTag(deliverBean);
            viewholder.title.setText(listOther.get(realPosition).getTitle());
            viewholder.author.setText(listOther.get(realPosition).getAuthor_name());
            viewholder.author.setText(listOther.get(realPosition).getAuthor_name());
            //解决图片不存在问题  ： 3 》2 》1
            String Path_Image =
                    listOther.get(realPosition).getThumbnail_pic_s03() == null
                            ?
                            (listOther.get(realPosition).getThumbnail_pic_s02() == null ?
                                    listOther.get(realPosition).getThumbnail_pic_s() :
                                    listOther.get(realPosition).getThumbnail_pic_s02())
                            :
                            (listOther.get(realPosition).getThumbnail_pic_s03());

            Glide.with(context).load(Path_Image).crossFade().into(viewholder.img);

        }
    }

    private BannerViewHolder bannerHolder = null;
    private String[] images;

    //开启轮播
    public void startBanner() {
        if (bannerHolder != null)
            bannerHolder.banner.startAutoPlay();
    }

    public void resetBanner() {
        images = context.getResources().getStringArray(R.array.url);
        if (bannerHolder != null) {
            bannerHolder.banner.setImages(Arrays.asList(images)).setImageLoader(new GlideImageLoader()).start();
        }
    }

    //关闭轮播
    public void stopBanner() {
        if (bannerHolder != null)
            bannerHolder.banner.stopAutoPlay();
    }

    @Override
    public int getItemCount() {

        return listOther == null ? 0 : listOther.size() + 1;
    }

    @Override
    public void onClick(View view) {


        itemClickListener.onItemClick(view);
    }


    public static class listViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_card_title)
        TextView title;
        @BindView(R.id.item_card_author)
        TextView author;
        @BindView(R.id.item_card_img)
        ImageView img;
        @BindView(R.id.item_cardview)
        CardView cardView;

        public listViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public static class BannerViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.other_banner)
        Banner banner;

        public BannerViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public interface RecylerItemClickListener {
        void onItemClick(View view);
    }
}


