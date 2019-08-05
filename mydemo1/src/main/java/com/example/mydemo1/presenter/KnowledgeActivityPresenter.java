package com.example.mydemo1.presenter;

import com.example.mydemo1.base.BasePresenter;
import com.example.mydemo1.bean.ArticleItemBean;
import com.example.mydemo1.bean.ArticleListData;
import com.example.mydemo1.bean.KnowledgeItemBean;
import com.example.mydemo1.contract.KnowledgeActivityContract;
import com.example.mydemo1.contract.KnowledgeContract;
import com.example.mydemo1.model.KnowledgeActivityModel;
import com.example.mydemo1.model.KnowledgeModel;

import java.util.List;

public class KnowledgeActivityPresenter<V extends KnowledgeActivityContract.KnowledgeActivityView>
        extends BasePresenter<V> implements KnowledgeActivityContract.KnowledgeActivityPresenter,
        KnowledgeActivityContract.KnowledgeActivityModel.CallBack {

    public KnowledgeActivityContract.KnowledgeActivityModel knowledgeModel = new KnowledgeActivityModel();

    @Override
    public void getKnowledgeActivityhttp(int cid) {
        if (mView != null) {
            knowledgeModel.getKnowledgeActivityData(this,cid);
        }
    }

    @Override
    public void onKnowledgeItemSuccess(ArticleListData knowledgeItemBean) {
        mView.onKnowledgeItemSuccess(knowledgeItemBean);
    }

    @Override
    public void onFail(String msg) {
        mView.onFail(msg);
    }
}
