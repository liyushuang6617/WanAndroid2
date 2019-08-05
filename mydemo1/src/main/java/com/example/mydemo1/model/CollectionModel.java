package com.example.mydemo1.model;

import com.example.mydemo1.api.MyService;
import com.example.mydemo1.base.BaseObsever;
import com.example.mydemo1.base.BaseResponse;
import com.example.mydemo1.bean.CollectionDataBean;
import com.example.mydemo1.contract.CollectionContract;
import com.example.mydemo1.http.HttpManager;
import com.example.mydemo1.utils.RxUtils;

public class CollectionModel implements CollectionContract.CollectionModel {
    @Override
    public void getCollectionData(final CallBack callBack) {
        HttpManager.getInstance().getApiService(MyService.class).getCollectionData("lg/collect/list/0/json")
                .compose(RxUtils.<BaseResponse<CollectionDataBean>>rxScheduleThread())
                .compose(RxUtils.<CollectionDataBean>changeResult())
                .subscribe(new BaseObsever<CollectionDataBean>() {
                    @Override
                    public void onSuccess(CollectionDataBean data) {
                        callBack.onCollectionSuccess(data);
                    }

                    @Override
                    public void onFail(String error) {
                        callBack.onCollectionFail(error);
                    }
                });
    }
}
