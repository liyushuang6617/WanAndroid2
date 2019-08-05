package com.example.mydemo1.presenter;

import com.example.mydemo1.base.BasePresenter;
import com.example.mydemo1.bean.ArticleItemBean;
import com.example.mydemo1.bean.ArticleListData;
import com.example.mydemo1.bean.BannerDataBean;
import com.example.mydemo1.contract.HomePagerContract;
import com.example.mydemo1.model.HomePagerModel;

import java.util.List;

public class HomePagerPresenter<V extends HomePagerContract.HomePagerView>
        extends BasePresenter<V> implements HomePagerContract.HomePagerPresenter, HomePagerContract.HomePagerModel.CallBack {

    private HomePagerContract.HomePagerModel homePagerModel = new HomePagerModel();


    @Override
    public void HomePagerhttp(int page) {
        if (mView != null) {
            homePagerModel.getHomePagerData(this,page);
        }
    }

    @Override
    public void onSuccess(List<ArticleItemBean> articleListData) {
        mView.onSuccess(articleListData);
    }

    @Override
    public void onBannerSuccess(List<BannerDataBean> bannerDataBeans) {
        mView.onBannerSuccess(bannerDataBeans);
    }

    @Override
    public void onArtDataSuccess(ArticleListData datasBean) {
        mView.onArtDataSuccess(datasBean);
    }

    @Override
    public void onFail(String msg) {
        mView.onFail(msg);
    }

}
