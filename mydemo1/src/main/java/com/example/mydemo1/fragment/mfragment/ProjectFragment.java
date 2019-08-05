package com.example.mydemo1.fragment.mfragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mydemo1.R;
import com.example.mydemo1.adapter.VpProjectAdapter;
import com.example.mydemo1.bean.ProjectTreeDataBean;
import com.example.mydemo1.constant.Constants;
import com.example.mydemo1.contract.ProjectTreeContrat;
import com.example.mydemo1.fragment.BaseFragment;
import com.example.mydemo1.fragment.ProjectListItemFragment;
import com.example.mydemo1.presenter.ProjectTreePresenter;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProjectFragment extends BaseFragment<ProjectTreeContrat.ProjectTreeView, ProjectTreePresenter<ProjectTreeContrat.ProjectTreeView>>
        implements ProjectTreeContrat.ProjectTreeView {


    @BindView(R.id.project_tablayout)
    TabLayout projectTablayout;
    @BindView(R.id.project_view_pager)
    ViewPager projectViewPager;
    Unbinder unbinder;
    private ArrayList<ProjectTreeDataBean> projectTreeDataBeans;
    private ArrayList<Fragment> fragments;
    private VpProjectAdapter adapter;

    public static ProjectFragment newInstance() {
        return new ProjectFragment();
    }

    @Override
    protected void initViewAndData() {
        projectTreeDataBeans = new ArrayList<>();
        mPresenter.ProjectTreehttp();
    }

    //P层  布局

    @Override
    protected int createLayout() {
        return R.layout.fragment_project;
    }

    @Override
    protected ProjectTreePresenter<ProjectTreeContrat.ProjectTreeView> createPresenter() {
        return new ProjectTreePresenter<>();
    }


    @Override
    public void onProjectSuccess(List<ProjectTreeDataBean> projectDataBean1) {
        projectTreeDataBeans.addAll(projectDataBean1);
        fragments = new ArrayList<>();
        for (int i = 0; i < projectTreeDataBeans.size(); i++) {
//            projectTablayout.addTab(projectTablayout.newTab().setText(projectTreeDataBeans.get(i).getName()));
            ProjectListItemFragment projectListItemFragment = new ProjectListItemFragment();
            Bundle bundle = new Bundle();
            int id = projectTreeDataBeans.get(i).getId();
            bundle.putInt(Constants.PROJECT_CID, id);
            projectListItemFragment.setArguments(bundle);
            fragments.add(projectListItemFragment);
        }
        adapter = new VpProjectAdapter(getChildFragmentManager(), fragments,projectTreeDataBeans);
        projectViewPager.setAdapter(adapter);
        projectTablayout.setupWithViewPager(projectViewPager);
    }

    @Override
    public void onProjectFail(String msg) {

    }


    public void jumpToTheTop() {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getData(String title) {
    }
}
