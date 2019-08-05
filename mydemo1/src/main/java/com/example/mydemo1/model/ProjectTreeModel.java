package com.example.mydemo1.model;

import com.example.mydemo1.api.MyService;
import com.example.mydemo1.base.BaseObsever;
import com.example.mydemo1.base.BaseResponse;
import com.example.mydemo1.bean.ProjectTreeDataBean;
import com.example.mydemo1.contract.ProjectTreeContrat;
import com.example.mydemo1.http.HttpManager;
import com.example.mydemo1.utils.RxUtils;

import java.util.List;

public class ProjectTreeModel implements ProjectTreeContrat.ProjectTreeModel {
    @Override
    public void getProjectTreeData(final CallBack callBack) {
        HttpManager.getInstance().getApiService(MyService.class).getProjectTree("project/tree/json")
                .compose(RxUtils.<BaseResponse<List<ProjectTreeDataBean>>>rxScheduleThread())
                .compose(RxUtils.<List<ProjectTreeDataBean>>changeResult())
                .subscribe(new BaseObsever<List<ProjectTreeDataBean>>() {
                    @Override
                    public void onSuccess(List<ProjectTreeDataBean> data) {
                        callBack.onProjectSuccess(data);
                    }

                    @Override
                    public void onFail(String error) {
                        callBack.onProjectFail(error);
                    }
                });
    }
}
