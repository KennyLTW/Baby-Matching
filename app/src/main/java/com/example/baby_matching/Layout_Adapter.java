package com.example.baby_matching;

import android.app.Activity;
import android.content.Context;
import android.os.Process;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.squareup.picasso.Picasso;

public class Layout_Adapter extends BaseAdapter {

    private Context context;

    public Layout_Adapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 16;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ImageView img_view;

        //todo: get XY
        DisplayMetrics displayMetrics = new DisplayMetrics();
        int w = ((Activity)context).getWindowManager().getDefaultDisplay().getWidth();
        int h = ((Activity)context).getWindowManager().getDefaultDisplay().getHeight();

        if(convertView == null) {
            img_view = new ImageView(this.context);
            img_view.setLayoutParams(new GridView.LayoutParams((w/4)-20, 300));
            img_view.setScaleType(ImageView.ScaleType.FIT_XY);
        } else
            img_view = (ImageView) convertView;
            img_view.setImageResource((R.drawable.back));

        return img_view;

    }


}
