package com.example.mydemo1.presenter;

import com.example.mydemo1.base.BasePresenter;
import com.example.mydemo1.bean.KnowledgeItemBean;
import com.example.mydemo1.bean.KnowledgeTreeBean;
import com.example.mydemo1.contract.KnowledgeContract;
import com.example.mydemo1.fragment.BaseFragment;
import com.example.mydemo1.model.KnowledgeModel;

import java.util.List;

public class KnowledgePresenter<V extends KnowledgeContract.KnowledgeView> extends BasePresenter<V>
        implements KnowledgeContract.KnowledgePresenter, KnowledgeContract.KnowledgeModel.CallBack {

    public KnowledgeContract.KnowledgeModel knowledgeModel = new KnowledgeModel();


    @Override
    public void Knowledgehttp() {
        if (mView != null) {
            knowledgeModel.getKnowledgeData(this);
        }
    }

    @Override
    public void onTreeSuccess(List<KnowledgeTreeBean> knowledgeTreeBean) {
        mView.onTreeSuccess(knowledgeTreeBean);
    }

    @Override
    public void onItemSuccess(KnowledgeItemBean knowledgeItemBean) {
        mView.onItemSuccess(knowledgeItemBean);
    }

    @Override
    public void onFail(String msg) {
        mView.onFail(msg);
    }
}
