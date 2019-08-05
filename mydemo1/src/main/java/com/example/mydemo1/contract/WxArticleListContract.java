package com.example.mydemo1.contract;

import com.example.mydemo1.bean.WxArticleListBean;

public interface WxArticleListContract {

    interface WxArticleListView {

        void onWxArticleListSuccess(WxArticleListBean wxArticleListBean);

        void onWxArticleListFail(String msg);
    }

    interface WxArticleListPresenter {
        void onWxArticleListhttp(int cid);
    }

    interface WxArticleListModel {
        interface CallBack {

            void onWxArticleListSuccess(WxArticleListBean wxArticleListBean);

            void onWxArticleListFail(String msg);
        }

        void getWxArticleListData(CallBack callBack,int cid);
    }
}
