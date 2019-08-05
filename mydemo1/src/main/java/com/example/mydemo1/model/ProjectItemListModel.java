package com.example.mydemo1.model;

import com.example.mydemo1.api.MyService;
import com.example.mydemo1.base.BaseObsever;
import com.example.mydemo1.base.BaseResponse;
import com.example.mydemo1.bean.ProjectListDataBean;
import com.example.mydemo1.contract.ProjectListItemContract;
import com.example.mydemo1.http.HttpManager;
import com.example.mydemo1.utils.RxUtils;

public class ProjectItemListModel implements ProjectListItemContract.ProjectListItemModel {
    @Override
    public void getProjectListItemData(final CallBack callBack,int page, int cid) {
        HttpManager.getInstance().getApiService(MyService.class).getProjectListItem(page,cid)
                .compose(RxUtils.<BaseResponse<ProjectListDataBean>>rxScheduleThread())
                .compose(RxUtils.<ProjectListDataBean>changeResult())
                .subscribe(new BaseObsever<ProjectListDataBean>() {
                    @Override
                    public void onSuccess(ProjectListDataBean data) {
                        callBack.onProjectListItemSuccess(data);
                    }

                    @Override
                    public void onFail(String error) {
                        callBack.onProjectListItemFail(error);
                    }
                });
    }
}
