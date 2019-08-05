package com.example.mydemo1.model;

import com.example.mydemo1.api.MyService;
import com.example.mydemo1.base.BaseObsever;
import com.example.mydemo1.base.BaseResponse;
import com.example.mydemo1.bean.WxArticleDataTabBean;
import com.example.mydemo1.contract.WxArticleContract;
import com.example.mydemo1.http.HttpManager;
import com.example.mydemo1.utils.RxUtils;

import java.util.List;

public class WxArticleModel implements WxArticleContract.WxArticleModel {
    @Override
    public void getWxArticleData(final CallBack callBack) {
        HttpManager.getInstance().getApiService(MyService.class).getWxArticleTab("wxarticle/chapters/json")
                .compose(RxUtils.<BaseResponse<List<WxArticleDataTabBean>>>rxScheduleThread())
                .compose(RxUtils.<List<WxArticleDataTabBean>>changeResult())
                .subscribe(new BaseObsever<List<WxArticleDataTabBean>>() {
                    @Override
                    public void onSuccess(List<WxArticleDataTabBean> data) {
                        callBack.onWxArticleSuccess(data);
                    }

                    @Override
                    public void onFail(String error) {
                        callBack.onWxArticleFail(error);
                    }
                });
    }
}
