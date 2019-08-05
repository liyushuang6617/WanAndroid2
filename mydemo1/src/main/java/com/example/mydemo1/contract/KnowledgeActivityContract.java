package com.example.mydemo1.contract;

import com.example.mydemo1.bean.ArticleItemBean;
import com.example.mydemo1.bean.ArticleListData;
import com.example.mydemo1.bean.KnowledgeItemBean;

import java.util.List;

public interface KnowledgeActivityContract {

    interface KnowledgeActivityView {
        void onKnowledgeItemSuccess(ArticleListData knowledgeItemBean);

        void onFail(String msg);
    }

    interface KnowledgeActivityPresenter {
        void getKnowledgeActivityhttp(int cid);
    }

    interface KnowledgeActivityModel {
        interface CallBack {
            void onKnowledgeItemSuccess(ArticleListData knowledgeItemBean);

            void onFail(String msg);
        }

        void getKnowledgeActivityData(CallBack callBack,int cid);
    }
}
