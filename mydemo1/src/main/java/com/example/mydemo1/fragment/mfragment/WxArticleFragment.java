package com.example.mydemo1.fragment.mfragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.SparseArray;

import com.example.mydemo1.R;
import com.example.mydemo1.adapter.VpWxArticleAdapter;
import com.example.mydemo1.bean.WxArticleDataTabBean;
import com.example.mydemo1.constant.Constants;
import com.example.mydemo1.contract.WxArticleContract;
import com.example.mydemo1.fragment.BaseFragment;
import com.example.mydemo1.fragment.ListFragment;
import com.example.mydemo1.fragment.WxArticleListFragment;
import com.example.mydemo1.presenter.WxArticlePresenter;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class WxArticleFragment extends BaseFragment<WxArticleContract.WxArticleView, WxArticlePresenter<WxArticleContract.WxArticleView>>
        implements WxArticleContract.WxArticleView {

    @BindView(R.id.wx_article_tab_layout)
    TabLayout wxArticleTabLayout;
    @BindView(R.id.wx_article_view_page)
    ViewPager wxArticleViewPage;
    Unbinder unbinder;
    private ArrayList<WxArticleDataTabBean> wxArticleDataBeans;
    private VpWxArticleAdapter adapter;
    private ArrayList<Fragment> list;

    public static WxArticleFragment newInstance() {
        return new WxArticleFragment();
    }

    @Override
    protected void initViewAndData() {
        wxArticleDataBeans = new ArrayList<>();
        mPresenter.onWxArticlehttp();
    }

    //布局 P层

    @Override
    protected int createLayout() {
        return R.layout.fragment_wx_article;
    }

    @Override
    protected WxArticlePresenter<WxArticleContract.WxArticleView> createPresenter() {
        return new WxArticlePresenter<>();
    }

    @Override
    public void onWxArticleSuccess(List<WxArticleDataTabBean> wxArticleDataBean) {
        wxArticleDataBeans.addAll(wxArticleDataBean);
        list = new ArrayList<>();
        for (int i = 0; i < wxArticleDataBean.size(); i++) {
            WxArticleListFragment fragment = new WxArticleListFragment();
            Bundle bundle = new Bundle();
            bundle.putString(Constants.ARTICLE_ID, wxArticleDataBean.get(i).getId()+"");
            fragment.setArguments(bundle);
            list.add(fragment);
        }
        adapter = new VpWxArticleAdapter(getChildFragmentManager(), list, wxArticleDataBeans);
        wxArticleViewPage.setAdapter(adapter);
        wxArticleTabLayout.setupWithViewPager(wxArticleViewPage);
    }


    private static final String TAG = "WxArticleFragment";

    @Override
    public void onWxArticleFail(String msg) {
        Log.e(TAG, "onWxArticleFail: " + msg);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getData(String title) {
    }

    public void jumpToTheTop() {

    }
}
