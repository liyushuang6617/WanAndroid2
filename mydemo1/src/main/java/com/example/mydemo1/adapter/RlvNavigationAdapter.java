package com.example.mydemo1.adapter;

import android.support.annotation.Nullable;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.mydemo1.R;
import com.example.mydemo1.bean.ArticleItemBean;
import com.example.mydemo1.bean.NavigtionDataBean;
import com.example.mydemo1.constant.Constants;
import com.example.mydemo1.utils.HomePagerUtils;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;
import java.util.zip.Inflater;

public class RlvNavigationAdapter extends BaseQuickAdapter<NavigtionDataBean, BaseViewHolder> {
    public RlvNavigationAdapter(int layoutResId, @Nullable List<NavigtionDataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, NavigtionDataBean item) {
        if (!TextUtils.isEmpty(item.getName())) {
            helper.setText(R.id.tv_navigation_name, Html.fromHtml(item.getName()));
        }

        final TagFlowLayout mtagFlowLayout = helper.getView(R.id.item_navigation_flow_layout);
        final List<ArticleItemBean> articles = item.getArticles();
        mtagFlowLayout.setAdapter(new TagAdapter<ArticleItemBean>(articles) {
            @Override
            public View getView(FlowLayout parent, int position, ArticleItemBean articleItemBean) {
                TextView tv = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.flow_layout_tv, mtagFlowLayout, false);
                if(articleItemBean == null){
                    return null;
                }
                String name = articleItemBean.getTitle();
                tv.setText(name);
                tv.setTextColor(HomePagerUtils.getRandomColor());
                mtagFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
                    @Override
                    public boolean onTagClick(View view, int position, FlowLayout parent) {
                        HomePagerUtils.startArticleDetailActivity(parent.getContext(),
                                articles.get(position).getId(),
                                articles.get(position).getTitle().trim(),
                                articles.get(position).getLink().trim(),
                                articles.get(position).isCollect(),
                                true, -1, Constants.TAG_DEFAULT);
                        return true;
                    }
                });
                return tv;
            }
        });
    }
}
