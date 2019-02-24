package com.newspaper.utils;

import android.util.SparseArray;
import android.view.View;

/**
 * Created by mephisto- on 2016/8/4.
 */
public class ViewHolder {

    @SuppressWarnings("unchecked")
    public static <T extends View> T get(View convertView, int id) {
        SparseArray<View> viewHolder = (SparseArray<View>) convertView.getTag();
        if (viewHolder == null) {
            viewHolder = new SparseArray<View>();
            convertView.setTag(viewHolder);
        }
        View childView = viewHolder.get(id);
        if (childView == null) {
            childView = convertView.findViewById(id);
            viewHolder.put(id, childView);
        }
        return (T) childView;
    }
/*
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.banana_phone, parent, false);
        }

        ImageView bananaView = listViewHolder.get(convertView, R.id.banana);

        TextView phoneView = listViewHolder.get(convertView, R.id.phone);

        BananaPhone bananaPhone = getItem(position);
        phoneView.setText(bananaPhone.getPhone());
        bananaView.setImageResource(bananaPhone.getBanana());

        return convertView;
    }

    */
}
