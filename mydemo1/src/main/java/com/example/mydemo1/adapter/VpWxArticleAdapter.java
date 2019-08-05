package com.example.mydemo1.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.mydemo1.bean.WxArticleDataTabBean;

import java.util.ArrayList;

public class VpWxArticleAdapter extends FragmentStatePagerAdapter {

    private ArrayList<Fragment> fragments;
    private ArrayList<WxArticleDataTabBean> wxArticleDataBeans;

    public VpWxArticleAdapter(FragmentManager fm, ArrayList<Fragment> fragments, ArrayList<WxArticleDataTabBean> wxArticleDataBeans) {
        super(fm);
        this.fragments = fragments;
        this.wxArticleDataBeans = wxArticleDataBeans;
    }

    @Override
    public Fragment getItem(int i) {
        return fragments.get(i);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return wxArticleDataBeans.get(position).getName();
    }
}
