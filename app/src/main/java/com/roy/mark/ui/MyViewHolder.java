package com.roy.mark.ui;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

import com.roy.mark.R;
import com.roy.mark.framework.Matter;

/**
 * Created by Administrator on 2017/7/19.
 */

public class MyViewHolder extends RecyclerView.ViewHolder {

    public TextView title, content, beginTime, endTime;
    public CheckBox finish;
    public Spinner priority;
    public CardView itemCardView;

    public MyViewHolder(View itemView) {
        super(itemView);
        itemCardView = (CardView) itemView.findViewById(R.id.item_cardview);
        title = (TextView) itemView.findViewById(R.id.item_title);
        content = (TextView) itemView.findViewById(R.id.item_content);
        beginTime = (TextView) itemView.findViewById(R.id.item_begin_time);
        endTime = (TextView) itemView.findViewById(R.id.item_end_time_text);
        finish = (CheckBox) itemView.findViewById(R.id.item_finish);
        priority = (Spinner) itemView.findViewById(R.id.item_priority);
    }

    public void setView(Matter matter) {
        title.setText(matter.title);
        content.setText(matter.content);
        beginTime.setText(matter.beginTime);
        endTime.setText(matter.endTime);
        finish.setChecked(matter.finish);
        priority.setSelection(matter.priority);
    }
}
