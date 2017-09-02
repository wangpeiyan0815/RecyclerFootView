package com.wpy.recycler.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wpy.recycler.R;

import java.util.List;

/**
 * Created by dell on 2017/9/2.
 */

public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<String> list;
    private int NORML_ITEM = 0;
    private int NORML_FOOT = 1;

    public MyAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    public void addDatas(List<String> items) {
        list.addAll(items);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == NORML_ITEM) {
            View view = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
            return new ViewHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.foot_view, parent, false);
            return new FootViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        // 避开LoadingMoreV
        if (position < list.size()) {
            String item = list.get(position);
            ((ViewHolder) holder).name.setText(item);
        }
    }

    @Override
    public int getItemCount() {
        return list.size()+1 ;
    }

    @Override
    public int getItemViewType(int position) {
        int type = NORML_ITEM;
        if (position == list.size()) {
            return NORML_FOOT;
        }
        return type;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.city_name);
        }
    }

    class FootViewHolder extends RecyclerView.ViewHolder {

        public FootViewHolder(View itemView) {
            super(itemView);
        }
    }

    public interface OnScrollReachedBottomListener {
        void onScrollReachedBottom();
    }
}
