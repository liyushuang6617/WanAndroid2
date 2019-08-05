package com.example.mydemo1.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mydemo1.R;
import com.example.mydemo1.bean.ArticleItemBean;
import com.example.mydemo1.bean.SearchDataBean;

import java.util.ArrayList;
import java.util.Timer;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RlvSearchAdapter extends RecyclerView.Adapter<RlvSearchAdapter.ViewHolder> {

    private Context context;
    private ArrayList<ArticleItemBean> list;

    public RlvSearchAdapter(Context context, ArrayList<ArticleItemBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.item_resule_list, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.tvResult1.setText(list.get(i).getAuthor());
        viewHolder.tvResult2.setText(list.get(i).getChapterName() + "/" + list.get(i).getSuperChapterName());
        if (!TextUtils.isEmpty(list.get(i).getEnvelopePic())) {
            viewHolder.ivResult1.setVisibility(View.VISIBLE);
            Glide.with(context).load(list.get(i).getEnvelopePic()).into(viewHolder.ivResult1);
        } else {
            viewHolder.ivResult1.setVisibility(View.GONE);
        }
        viewHolder.tvResultTitle.setText(list.get(i).getTitle());
//        if (list.get(i).isCollect()) {
//            Glide.with(context).load(R.drawable.ic_like).into(viewHolder.ivResult2);
//        } else {
//            Glide.with(context).load(R.drawable.ic_like_not).into(viewHolder.ivResult2);
//        }
        viewHolder.tvResult3.setText(list.get(i).getNiceDate());
        if (list.get(i).getTags().size() > 0) {
            viewHolder.tvResult4.setText(list.get(i).getTags().get(0).getName());
            viewHolder.tvResult4.setVisibility(View.VISIBLE);
        } else {
            viewHolder.tvResult4.setVisibility(View.GONE);
        }
        if (list.get(i).getType() == 1) {
            viewHolder.tvResultTop.setVisibility(View.VISIBLE);
        } else {
            viewHolder.tvResultTop.setVisibility(View.GONE);
        }

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (a != null) {
                    a.onItemClick(i, list.get(i));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_result_top)
        TextView tvResultTop;
        @BindView(R.id.tv_result_4)
        TextView tvResult4;
        @BindView(R.id.tv_result_1)
        TextView tvResult1;
        @BindView(R.id.tv_result_2)
        TextView tvResult2;
        @BindView(R.id.resule_re1)
        RelativeLayout resuleRe1;
        @BindView(R.id.iv_result_1)
        ImageView ivResult1;
        @BindView(R.id.tv_result_title)
        TextView tvResultTitle;
        @BindView(R.id.iv_result_2)
        ImageView ivResult2;
        @BindView(R.id.tv_result_3)
        TextView tvResult3;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private A a;

    public void setA(A a) {
        this.a = a;
    }

    public interface A {
        void onItemClick(int pos, ArticleItemBean bean);
    }
}
