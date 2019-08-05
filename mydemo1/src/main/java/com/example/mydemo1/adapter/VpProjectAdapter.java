package com.example.mydemo1.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.mydemo1.bean.ProjectTreeDataBean;

import java.util.ArrayList;

public class VpProjectAdapter extends FragmentStatePagerAdapter {

    private ArrayList<Fragment> list;
    private ArrayList<ProjectTreeDataBean> projectTreeDataBeans;

    public VpProjectAdapter(FragmentManager fm, ArrayList<Fragment> list, ArrayList<ProjectTreeDataBean> projectTreeDataBeans) {
        super(fm);
        this.list = list;
        this.projectTreeDataBeans = projectTreeDataBeans;
    }

    @Override
    public Fragment getItem(int i) {
        return list.get(i);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return projectTreeDataBeans.get(position).getName();
    }
}
