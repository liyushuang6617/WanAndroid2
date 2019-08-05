package com.example.mydemo1.contract;

import com.example.mydemo1.bean.KnowledgeItemBean;
import com.example.mydemo1.bean.KnowledgeTreeBean;

import java.util.List;

public interface KnowledgeContract {
    interface KnowledgeView {
        void onTreeSuccess(List<KnowledgeTreeBean> knowledgeTreeBean);

        void onItemSuccess(KnowledgeItemBean knowledgeItemBean);

        void onFail(String msg);
    }

    interface KnowledgePresenter {
        void Knowledgehttp();
    }

    interface KnowledgeModel {
        interface CallBack {
            void onTreeSuccess(List<KnowledgeTreeBean> knowledgeTreeBean);

            void onItemSuccess(KnowledgeItemBean knowledgeItemBean);

            void onFail(String msg);
        }

        void getKnowledgeData(CallBack callBack);
    }
}
