package com.example.mydemo1.adapter;

import android.support.annotation.Nullable;
import android.text.Html;
import android.text.TextUtils;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.mydemo1.R;
import com.example.mydemo1.bean.ProjectListDataBean;
import com.example.mydemo1.bean.ProjectListItemBean;
import com.example.mydemo1.utils.GlideImageLoader;

import java.util.List;

public class RlvProjectListItemAdapter extends BaseQuickAdapter<ProjectListItemBean, BaseViewHolder> {
    public RlvProjectListItemAdapter(int layoutResId, @Nullable List<ProjectListItemBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ProjectListItemBean item) {
        helper.setText(R.id.item_project_list_title_tv, Html.fromHtml(item.getTitle()))
                .setText(R.id.item_project_list_content_tv, item.getDesc())
        .setImageResource(R.id.item_project_list_iv,item.isCollect() ? R.drawable.ic_like : R.drawable.ic_like_not);

        if(!TextUtils.isEmpty(item.getDesc())){
            helper.setText(R.id.item_project_list_author_tv, item.getAuthor());
        }
        if(!TextUtils.isEmpty(item.getNiceDate())){
            helper.setText(R.id.item_project_list_time_tv, item.getNiceDate());
        }

        if(!TextUtils.isEmpty(item.getEnvelopePic())){
            ImageView view = helper.getView(R.id.item_project_list_iv);
            GlideImageLoader.load(mContext,item.getEnvelopePic(),view);
        }
        helper.addOnClickListener(R.id.item_project_list_like_iv);
    }
}
