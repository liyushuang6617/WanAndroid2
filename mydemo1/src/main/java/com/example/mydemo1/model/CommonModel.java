package com.example.mydemo1.model;

import com.example.mydemo1.api.MyService;
import com.example.mydemo1.base.BaseObsever;
import com.example.mydemo1.base.BaseResponse;
import com.example.mydemo1.bean.CommonDataBean;
import com.example.mydemo1.contract.CommonContract;
import com.example.mydemo1.http.HttpManager;
import com.example.mydemo1.utils.RxUtils;

import java.util.List;

public class CommonModel implements CommonContract.CommonModel {
    @Override
    public void getData(final CallBack callBack) {
        HttpManager.getInstance().getApiService(MyService.class).getCommonData("friend/json")
                .compose(RxUtils.<BaseResponse<List<CommonDataBean>>>rxScheduleThread())
                .compose(RxUtils.<List<CommonDataBean>>changeResult())
                .subscribe(new BaseObsever<List<CommonDataBean>>() {
                    @Override
                    public void onSuccess(List<CommonDataBean> data) {
                        callBack.onCommonSuccess(data);
                    }

                    @Override
                    public void onFail(String error) {
                        callBack.onCommonFail(error);
                    }
                });
    }
}
