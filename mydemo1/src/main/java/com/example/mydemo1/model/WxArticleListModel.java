package com.example.mydemo1.model;

import com.example.mydemo1.api.MyService;
import com.example.mydemo1.base.BaseObsever;
import com.example.mydemo1.base.BaseResponse;
import com.example.mydemo1.bean.WxArticleListBean;
import com.example.mydemo1.contract.WxArticleListContract;
import com.example.mydemo1.http.HttpManager;
import com.example.mydemo1.utils.RxUtils;

public class WxArticleListModel implements WxArticleListContract.WxArticleListModel {
    @Override
    public void getWxArticleListData(final CallBack callBack, int cid) {
        HttpManager.getInstance().getApiService(MyService.class).getWxArticleList(cid)
                .compose(RxUtils.<BaseResponse<WxArticleListBean>>rxScheduleThread())
                .compose(RxUtils.<WxArticleListBean>changeResult())
                .subscribe(new BaseObsever<WxArticleListBean>() {
                    @Override
                    public void onSuccess(WxArticleListBean data) {
                        callBack.onWxArticleListSuccess(data);
                    }

                    @Override
                    public void onFail(String error) {
                        callBack.onWxArticleListFail(error);
                    }
                });
    }
}
