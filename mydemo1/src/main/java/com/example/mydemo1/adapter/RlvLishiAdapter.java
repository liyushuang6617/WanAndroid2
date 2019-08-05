package com.example.mydemo1.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mydemo1.R;
import com.example.mydemo1.bean.ResuleSearchBean;

import java.util.ArrayList;

public class RlvLishiAdapter extends RecyclerView.Adapter<RlvLishiAdapter.ViewHolder> {

    private Context context;
    private ArrayList<ResuleSearchBean> list;

    public RlvLishiAdapter(Context context, ArrayList<ResuleSearchBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.item_null, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.tv.setText(list.get(i).getTitle());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.item_tv);
        }
    }

}
