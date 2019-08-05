package com.example.mydemo1.model;

import android.annotation.SuppressLint;

import com.example.mydemo1.api.MyService;
import com.example.mydemo1.base.BaseObsever;
import com.example.mydemo1.base.BaseResponse;
import com.example.mydemo1.bean.ArticleItemBean;
import com.example.mydemo1.bean.ArticleListData;
import com.example.mydemo1.bean.BannerDataBean;
import com.example.mydemo1.contract.HomePagerContract;
import com.example.mydemo1.http.HttpManager;
import com.example.mydemo1.utils.RxUtils;

import java.util.List;

public class HomePagerModel implements HomePagerContract.HomePagerModel {
    @SuppressLint("CheckResult")
    @Override
    public void getHomePagerData(final CallBack callBack,int page) {
        HttpManager.getInstance().getApiService(MyService.class).getTopArticles("article/top/json")
                .compose(RxUtils.<BaseResponse<List<ArticleItemBean>>>rxScheduleThread())
                .compose(RxUtils.<List<ArticleItemBean>>changeResult())
                .subscribe(new BaseObsever<List<ArticleItemBean>>() {
                    @Override
                    public void onSuccess(List<ArticleItemBean> data) {
                        if (data != null) {
                            callBack.onSuccess(data);
                        }
                    }

                    @Override
                    public void onFail(String error) {
                        callBack.onFail(error);
                    }
                });
        HttpManager.getInstance().getApiService(MyService.class).getBanner("banner/json")
                .compose(RxUtils.<BaseResponse<List<BannerDataBean>>>rxScheduleThread())
                .compose(RxUtils.<List<BannerDataBean>>changeResult())
                .subscribe(new BaseObsever<List<BannerDataBean>>() {
                    @Override
                    public void onSuccess(List<BannerDataBean> data) {
                        callBack.onBannerSuccess(data);
                    }

                    @Override
                    public void onFail(String error) {

                    }
                });

        HttpManager.getInstance().getApiService(MyService.class).getArticleList(page)
                .compose(RxUtils.<BaseResponse<ArticleListData>>rxScheduleThread())
                .compose(RxUtils.<ArticleListData>changeResult())
                .subscribe(new BaseObsever<ArticleListData>() {
                    @Override
                    public void onSuccess(ArticleListData data) {
                        if (data != null) {
                            callBack.onArtDataSuccess(data);
                        }
                    }

                    @Override
                    public void onFail(String error) {
                        callBack.onFail(error);
                    }
                });
    }
}
