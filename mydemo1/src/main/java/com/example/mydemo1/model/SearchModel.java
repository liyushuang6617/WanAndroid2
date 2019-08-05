package com.example.mydemo1.model;

import com.example.mydemo1.api.MyService;
import com.example.mydemo1.base.BaseObsever;
import com.example.mydemo1.base.BaseResponse;
import com.example.mydemo1.bean.SearchDataBean;
import com.example.mydemo1.contract.SearchContract;
import com.example.mydemo1.http.HttpManager;
import com.example.mydemo1.utils.RxUtils;

import java.util.List;

public class SearchModel implements SearchContract.SearchModel {
    @Override
    public void getSearchData(final CallBack callBack, String key) {
    }
}
