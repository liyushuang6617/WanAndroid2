package com.example.mydemo1.presenter;

import com.example.mydemo1.base.BasePresenter;
import com.example.mydemo1.bean.ProjectTreeDataBean;
import com.example.mydemo1.contract.ProjectTreeContrat;
import com.example.mydemo1.model.ProjectTreeModel;

import java.util.List;

public class ProjectTreePresenter<V extends ProjectTreeContrat.ProjectTreeView> extends BasePresenter<V>
        implements ProjectTreeContrat.ProjectTreePresenter, ProjectTreeContrat.ProjectTreeModel.CallBack {

    public ProjectTreeContrat.ProjectTreeModel projectTreeModel = new ProjectTreeModel();

    @Override
    public void ProjectTreehttp() {
        if (mView != null) {
            projectTreeModel.getProjectTreeData(this);
        }
    }

    @Override
    public void onProjectSuccess(List<ProjectTreeDataBean> projectDataBean) {
        mView.onProjectSuccess(projectDataBean);
    }

    @Override
    public void onProjectFail(String msg) {
        mView.onProjectFail(msg);
    }
}
