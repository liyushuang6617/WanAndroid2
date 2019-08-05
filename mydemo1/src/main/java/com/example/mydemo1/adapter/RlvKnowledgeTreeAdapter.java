package com.example.mydemo1.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.mydemo1.R;
import com.example.mydemo1.bean.KnowledgeTreeBean;

import java.util.List;

public class RlvKnowledgeTreeAdapter extends BaseQuickAdapter<KnowledgeTreeBean, BaseViewHolder> {
    public RlvKnowledgeTreeAdapter(int layoutResId, @Nullable List<KnowledgeTreeBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, KnowledgeTreeBean item) {
        if (item.getName() == null) {
            return;
        }
        helper.setText(R.id.knowledge_title, item.getName());

        if (item.getChildren() == null) {
            return;
        }
        StringBuilder childTitles = new StringBuilder();
        for (KnowledgeTreeBean knowledgeTreeBean : item.getChildren()) {
            childTitles.append(knowledgeTreeBean.getName()).append("  ");
        }
        helper.setText(R.id.title_child, childTitles.toString());
    }
}
