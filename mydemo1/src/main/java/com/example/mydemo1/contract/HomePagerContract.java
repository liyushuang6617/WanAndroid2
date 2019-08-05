package com.example.mydemo1.contract;

import com.example.mydemo1.bean.ArticleItemBean;
import com.example.mydemo1.bean.ArticleListData;
import com.example.mydemo1.bean.BannerDataBean;

import java.util.List;

public interface HomePagerContract {

    interface HomePagerView {
        void onSuccess(List<ArticleItemBean> articleListData);

        void onBannerSuccess(List<BannerDataBean> bannerDataBeans);

        void onArtDataSuccess(ArticleListData datasBean);

        void onFail(String msg);
    }

    interface HomePagerPresenter {
        void HomePagerhttp(int page);
    }

    interface HomePagerModel {
        interface CallBack {
            void onSuccess(List<ArticleItemBean> articleListData);

            void onBannerSuccess(List<BannerDataBean> bannerDataBeans);

            void onArtDataSuccess(ArticleListData datasBean);

            void onFail(String msg);
        }

        void getHomePagerData(CallBack callBack,int page);
    }
}
