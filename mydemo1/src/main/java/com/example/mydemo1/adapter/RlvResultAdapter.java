package com.example.mydemo1.adapter;

import android.support.annotation.Nullable;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.mydemo1.R;
import com.example.mydemo1.bean.SearchDataBean;
import com.example.mydemo1.utils.GlideImageLoader;

import java.util.List;

public class RlvResultAdapter extends BaseQuickAdapter<SearchDataBean, BaseViewHolder> {
    public RlvResultAdapter(int layoutResId, @Nullable List<SearchDataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SearchDataBean item) {
        if (!TextUtils.isEmpty(item.getAuthor())) {
            helper.setText(R.id.tv_result_1, Html.fromHtml(item.getAuthor()));
        }
        if (!TextUtils.isEmpty(item.getChapterName()) && !TextUtils.isEmpty(item.getSuperChapterName())) {
            String name = item.getChapterName() + "/" + item.getSuperChapterName();
            helper.setText(R.id.tv_result_2, name);
        }
        helper.setImageResource(R.id.iv_result_2, item.isCollect() ? R.drawable.ic_like_not : R.drawable.ic_like);
        if (!TextUtils.isEmpty(item.getTitle())) {
            helper.setText(R.id.tv_result_title, item.getTitle());
        }
        if (!TextUtils.isEmpty(item.getNiceDate())) {
            helper.setText(R.id.tv_result_3, item.getNiceDate());
        }
        if (item.getTags().size() > 0) {
            helper.setText(R.id.tv_result_top, item.getTags().get(0).getName())
                    .getView(R.id.tv_result_top).setVisibility(View.VISIBLE);
        } else {
            helper.getView(R.id.tv_result_top).setVisibility(View.GONE);
        }
        helper.getView(R.id.tv_result_top).setVisibility(item.getType() == 1 ? View.VISIBLE : View.GONE);
        if (!TextUtils.isEmpty(item.getEnvelopePic())) {
            helper.getView(R.id.iv_result_1).setVisibility(View.VISIBLE);
            GlideImageLoader.load(mContext, item.getEnvelopePic(), (ImageView) helper.getView(R.id.iv_article_thumbnail));
        } else {
            helper.getView(R.id.iv_result_1).setVisibility(View.GONE);
        }
    }
}
