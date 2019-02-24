package com.newspaper.view.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.newspaper.R;
import com.newspaper.model.Bean.NewsOther;
import com.newspaper.model.Bean.NewsTop;
import com.newspaper.presenter.IEvent;
import com.newspaper.presenter.NewsPresenter;
import com.newspaper.view.activity.GuideTopActivity;

import java.util.List;


public class GuidePagerAdapter extends PagerAdapter implements IEvent {

    private List<NewsTop.ResultBean.DataBean> listTop;
    private Context context;
    private IPagerDetail iPagerDetail;

    public GuidePagerAdapter(GuideTopActivity activity) {
        this.context = activity;
        this.iPagerDetail = activity;
        NewsPresenter newsPresenter = NewsPresenter.getInstance(this);
        newsPresenter.getTop();
    }


    @Override
    public void getTop(List<NewsTop.ResultBean.DataBean> listTop) {
        this.listTop = listTop;
        this.notifyDataSetChanged();

        iPagerDetail.modifyText(listTop);
    }

    @Override
    public void getOther(List<NewsOther.ResultBean.DataBean> listOther) {

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public int getCount() {

        return listTop == null ? 0 : 9;

    }

    @Override
    public boolean isViewFromObject(View view, Object object)

    {
        return view == object;
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Viewholder viewholder;
        viewholder = new Viewholder();

        View view = LayoutInflater.from(context).inflate(R.layout.pageradapter_guide, null);

        viewholder.imageView1 = (ImageView) view.findViewById(R.id.imageView1);
        Glide.with(context).load(listTop.get(position).getThumbnail_pic_s03()).crossFade().into(viewholder.imageView1);

        container.addView(view);


        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
/*        if(cachesView.size() > 0){
            cachesView.clear();

        }
        container.removeView((View)object);
        cachesView.add((View)object);*/

    }

    @Override
    public int getItemPosition(Object object) {
        TextView textView = (TextView) object;
        String text = textView.getText().toString();
        int index = listTop.indexOf(text);

        if (index >= 0) {
            return index;
        }
        return POSITION_NONE;
    }


//    @Override
//    public CharSequence getPageTitle(int position) {
//        return listTop.get(position).getTitle();
//    }


    public static class Viewholder {


        ImageView imageView1;

    }

    public interface IPagerDetail {
        void modifyText(List<NewsTop.ResultBean.DataBean> listTop);
    }

}
