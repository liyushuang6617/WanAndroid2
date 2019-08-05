package com.example.mydemo1.model;

import com.example.mydemo1.api.MyService;
import com.example.mydemo1.base.BaseObsever;
import com.example.mydemo1.base.BaseResponse;
import com.example.mydemo1.bean.KnowledgeTreeBean;
import com.example.mydemo1.contract.KnowledgeContract;
import com.example.mydemo1.http.HttpManager;
import com.example.mydemo1.utils.RxUtils;

import java.util.List;

public class KnowledgeModel implements KnowledgeContract.KnowledgeModel {
    @Override
    public void getKnowledgeData(final CallBack callBack) {
        HttpManager.getInstance().getApiService(MyService.class).getKnowledgeTree("tree/json")
                .compose(RxUtils.<BaseResponse<List<KnowledgeTreeBean>>>rxScheduleThread())
                .compose(RxUtils.<List<KnowledgeTreeBean>>changeResult())
                .subscribe(new BaseObsever<List<KnowledgeTreeBean>>() {
                    @Override
                    public void onSuccess(List<KnowledgeTreeBean> data) {
                        callBack.onTreeSuccess(data);
                    }

                    @Override
                    public void onFail(String error) {
                        callBack.onFail(error);
                    }
                });
    }
}
