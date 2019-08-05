package com.example.mydemo1.presenter;

import com.example.mydemo1.base.BasePresenter;
import com.example.mydemo1.bean.ProjectListDataBean;
import com.example.mydemo1.contract.ProjectListItemContract;
import com.example.mydemo1.model.ProjectItemListModel;

public class ProjectItemListPresenter<V extends ProjectListItemContract.ProjectListItemView> extends BasePresenter<V>
        implements ProjectListItemContract.ProjectListItemPresenter, ProjectListItemContract.ProjectListItemModel.CallBack {

    public ProjectListItemContract.ProjectListItemModel projectListItemModel = new ProjectItemListModel();


    @Override
    public void onProjectListItemhttp(int page,int cid) {
        if (mView != null) {
            projectListItemModel.getProjectListItemData(this,page,cid);
        }
    }

    @Override
    public void onProjectListItemSuccess(ProjectListDataBean projectListItemBean) {
        mView.onProjectListItemSuccess(projectListItemBean);
    }

    @Override
    public void onProjectListItemFail(String msg) {
        mView.onProjectListItemFail(msg);
    }
}
