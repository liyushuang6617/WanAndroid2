package com.example.mydemo1.model;

import com.example.mydemo1.api.MyService;
import com.example.mydemo1.base.BaseObsever;
import com.example.mydemo1.base.BaseResponse;
import com.example.mydemo1.bean.NavigtionDataBean;
import com.example.mydemo1.contract.NavigationContract;
import com.example.mydemo1.http.HttpManager;
import com.example.mydemo1.utils.RxUtils;

import java.util.List;

public class NavigationModel implements NavigationContract.NavigationModel {
    @Override
    public void getNavigationData(final CallBack callBack) {
        HttpManager.getInstance().getApiService(MyService.class).getNavigtion("navi/json")
                .compose(RxUtils.<BaseResponse<List<NavigtionDataBean>>>rxScheduleThread())
                .compose(RxUtils.<List<NavigtionDataBean>>changeResult())
                .subscribe(new BaseObsever<List<NavigtionDataBean>>() {
                    @Override
                    public void onSuccess(List<NavigtionDataBean> data) {
                        callBack.onNavigationSuccess(data);
                    }

                    @Override
                    public void onFail(String error) {
                        callBack.onNavigationFail(error);
                    }
                });
    }
}
