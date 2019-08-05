package com.example.mydemo1.model;

import com.example.mydemo1.api.MyService;
import com.example.mydemo1.base.BaseObsever;
import com.example.mydemo1.base.BaseResponse;
import com.example.mydemo1.bean.ArticleItemBean;
import com.example.mydemo1.bean.ArticleListData;
import com.example.mydemo1.bean.KnowledgeItemBean;
import com.example.mydemo1.contract.KnowledgeActivityContract;
import com.example.mydemo1.http.HttpManager;
import com.example.mydemo1.utils.RxUtils;

import java.util.List;

public class KnowledgeActivityModel implements KnowledgeActivityContract.KnowledgeActivityModel {
    @Override
    public void getKnowledgeActivityData(final CallBack callBack,int cid) {
        HttpManager.getInstance().getApiService(MyService.class).getKnowledgeItem(cid)
                .compose(RxUtils.<BaseResponse<ArticleListData>>rxScheduleThread())
                .compose(RxUtils.<ArticleListData>changeResult())
                .subscribe(new BaseObsever<ArticleListData>() {
                    @Override
                    public void onSuccess(ArticleListData data) {
                        callBack.onKnowledgeItemSuccess(data);
                    }

                    @Override
                    public void onFail(String error) {
                        callBack.onFail(error);
                    }
                });

    }
}
