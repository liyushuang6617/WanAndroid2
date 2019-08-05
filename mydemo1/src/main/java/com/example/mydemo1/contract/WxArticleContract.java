package com.example.mydemo1.contract;

import com.example.mydemo1.bean.WxArticleDataTabBean;

import java.util.List;

public interface WxArticleContract {

    interface WxArticleView {

        void onWxArticleSuccess(List<WxArticleDataTabBean> wxArticleDataBean);

        void onWxArticleFail(String msg);
    }

    interface WxArticlePresenter {
        void onWxArticlehttp();
    }

    interface WxArticleModel {

        interface CallBack {
            void onWxArticleSuccess(List<WxArticleDataTabBean> wxArticleDataBean);

            void onWxArticleFail(String msg);
        }

        void getWxArticleData(CallBack callBack);
    }
}
