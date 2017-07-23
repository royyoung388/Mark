package com.roy.mark.ui;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;

import com.roy.mark.R;
import com.roy.mark.framework.Matter;
import com.roy.mark.provider.DataBaseManager;

import java.util.List;

/**
 * Created by Administrator on 2017/7/19.
 */

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private List<Matter> matters;
    private Context context;
    private OnItemViewClickListener listener;

    public MyAdapter(Context context, List<Matter> matters) {
        this.context = context;
        this.matters = matters;
    }

    //View点击用的接口
    public interface OnItemViewClickListener {
        void OnEndTimeClick(MyViewHolder viewHolder);
        void OnFinishClick(MyViewHolder viewHolder, boolean isChecked);
        void OnPriorityClick(MyViewHolder viewHolder, int priority);
        void OnCardViewLongClick(MyViewHolder viewHolder);
    }

    //设置接口变量
    public void setOnItemViewClickListener(OnItemViewClickListener listener) {
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.setView(matters.get(position));
        holder.endTime.setTag(matters.get(position).id);
        //长按监听 删除
        holder.itemCardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (listener != null) {
                    listener.OnCardViewLongClick(holder);
                }
                return true;
            }
        });
        //截止时间被点击
        holder.endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.OnEndTimeClick(holder);
                }
            }
        });
        //完成被点击
        holder.finish.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (listener != null) {
                    listener.OnFinishClick(holder, isChecked);
                }
            }
        });
        //监听优先级点击
        holder.priority.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int priority = Integer.parseInt(holder.priority.getSelectedItem().toString());
                if (listener != null) {
                    listener.OnPriorityClick(holder, priority);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return matters.size();
    }

    public void update(List<Matter> matters) {
        this.matters.clear();
        this.matters.addAll(matters);
        notifyDataSetChanged();
    }
}
