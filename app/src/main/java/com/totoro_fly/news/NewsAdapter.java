package com.totoro_fly.news;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by totoro-fly on 2017/2/11.
 */

public class NewsAdapter extends ArrayAdapter {
    public NewsAdapter(Context context, ArrayList<New.ResponseBean.ResultsBean> textViewResourceId) {
        super(context, 0, textViewResourceId);
    }

    private static class ViewHolder {
        private TextView mTitle;
        private TextView mType;
        private TextView mDate;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder viewHolder;
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.new_list_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.mTitle = (TextView) view.findViewById(R.id.title_list);
            viewHolder.mType = (TextView) view.findViewById(R.id.type_list);
            viewHolder.mDate = (TextView) view.findViewById(R.id.date_list);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        New.ResponseBean.ResultsBean n = (New.ResponseBean.ResultsBean) getItem(position);
        String date = n.getWebPublicationDate();
        date = date.replace("T", "");
        date = date.replace("Z", "");
        viewHolder.mTitle.setText(n.getWebTitle());
        viewHolder.mType.setText(n.getType());
        viewHolder.mDate.setText(date);
        return view;
    }
}
