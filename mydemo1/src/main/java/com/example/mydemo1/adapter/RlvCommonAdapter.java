package com.example.mydemo1.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mydemo1.R;
import com.example.mydemo1.bean.CommonDataBean;
import com.example.mydemo1.utils.HomePagerUtils;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

public class RlvCommonAdapter extends RecyclerView.Adapter<RlvCommonAdapter.ViewHolder> {

    private Context context;
    private ArrayList<CommonDataBean> commonDataBeans;

    public RlvCommonAdapter(Context context, ArrayList<CommonDataBean> commonDataBeans) {
        this.context = context;
        this.commonDataBeans = commonDataBeans;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.item_search_flow, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        final TagFlowLayout tagFlowLayout = viewHolder.tagFlowLayout;

        tagFlowLayout.setAdapter(new TagAdapter<CommonDataBean>(commonDataBeans) {
            @Override
            public View getView(FlowLayout parent, int position, CommonDataBean commonDataBean) {
                TextView view = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.flow_layout_tv, tagFlowLayout, false);
                if (commonDataBean == null) {
                    return null;
                }
                view.setText(commonDataBean.getName());
                view.setTextColor(HomePagerUtils.getRandomColor());
                return view;
            }
        });
    }

    @Override
    public int getItemCount() {
        return commonDataBeans.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TagFlowLayout tagFlowLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tagFlowLayout = itemView.findViewById(R.id.flow_layout);
        }
    }
}
