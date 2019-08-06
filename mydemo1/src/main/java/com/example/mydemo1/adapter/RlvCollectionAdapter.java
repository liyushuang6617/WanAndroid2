package com.example.mydemo1.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mydemo1.R;
import com.example.mydemo1.bean.ArticleItemBean;
import com.example.mydemo1.bean.CollectionDataBean;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RlvCollectionAdapter extends RecyclerView.Adapter<RlvCollectionAdapter.ViewHolder> {

    private Context context;
    private ArrayList<CollectionDataBean.DatasBean> list;

    public RlvCollectionAdapter(Context context, ArrayList<CollectionDataBean.DatasBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.item_collection_list, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        if (!TextUtils.isEmpty(list.get(i).getAuthor())) {
            viewHolder.tvArticleAuthor.setText(list.get(i).getAuthor());
        }

        if (!TextUtils.isEmpty(list.get(i).getChapterName())) {
            viewHolder.tvArticleChapterName.setText(list.get(i).getChapterName());
        }

        if (!TextUtils.isEmpty(list.get(i).getEnvelopePic())) {
            viewHolder.ivArticleThumbnail.setVisibility(View.VISIBLE);
            Glide.with(context).load(list.get(i).getEnvelopePic()).into(viewHolder.ivArticleThumbnail);
        } else {
            viewHolder.ivArticleThumbnail.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(list.get(i).getTitle())) {
            viewHolder.tvArticleTitle.setText(list.get(i).getTitle());
        }

//        Glide.with(context).load(R.drawable.ic_like).into(viewHolder.ivArticleLike);

        if (!TextUtils.isEmpty(list.get(i).getNiceDate())) {
            viewHolder.tvArticleNiceDate.setText(list.get(i).getNiceDate());
        }

        viewHolder.ivArticleLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (collection != null) {
                    collection.onClicknoClooection(i, list.get(i));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_article_top)
        TextView tvArticleTop;
        @BindView(R.id.tv_article_fresh)
        TextView tvArticleFresh;
        @BindView(R.id.tv_article_tag)
        TextView tvArticleTag;
        @BindView(R.id.tv_article_author)
        TextView tvArticleAuthor;
        @BindView(R.id.tv_article_chapterName)
        TextView tvArticleChapterName;
        @BindView(R.id.iv_article_thumbnail)
        ImageView ivArticleThumbnail;
        @BindView(R.id.tv_article_title)
        TextView tvArticleTitle;
        @BindView(R.id.iv_article_like)
        ImageView ivArticleLike;
        @BindView(R.id.tv_article_niceDate)
        TextView tvArticleNiceDate;
        @BindView(R.id.item_search_pager_group)
        CardView itemSearchPagerGroup;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    public noCollection collection;

    public void setCollection(noCollection collection) {
        this.collection = collection;
    }

    public interface noCollection {
        void onClicknoClooection(int pos, CollectionDataBean.DatasBean bean);
    }
}
