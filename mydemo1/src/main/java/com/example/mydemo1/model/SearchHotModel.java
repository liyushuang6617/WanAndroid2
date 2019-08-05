package com.example.mydemo1.model;

import com.example.mydemo1.api.MyService;
import com.example.mydemo1.base.BaseObsever;
import com.example.mydemo1.base.BaseResponse;
import com.example.mydemo1.bean.SearchHotBean;
import com.example.mydemo1.contract.SearchHotContract;
import com.example.mydemo1.http.HttpManager;
import com.example.mydemo1.utils.RxUtils;

import java.util.List;

public class SearchHotModel implements SearchHotContract.SearchHotModel {
    @Override
    public void getSearchHotData(final CallBack callBack) {
        HttpManager.getInstance().getApiService(MyService.class).getSearchHotBean("hotkey/json")
                .compose(RxUtils.<BaseResponse<List<SearchHotBean>>>rxScheduleThread())
                .compose(RxUtils.<List<SearchHotBean>>changeResult())
                .subscribe(new BaseObsever<List<SearchHotBean>>() {
                    @Override
                    public void onSuccess(List<SearchHotBean> data) {
                        callBack.onSearchHotSucces(data);
                    }

                    @Override
                    public void onFail(String error) {
                        callBack.onSearchHotFail(error);
                    }
                });
    }
}
