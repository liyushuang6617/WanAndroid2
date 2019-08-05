package com.example.mydemo1.fragment.mfragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.classic.common.MultipleStatusView;
import com.example.mydemo1.R;
import com.example.mydemo1.activity.SearchActivity;
import com.example.mydemo1.adapter.RlvNavigationAdapter;
import com.example.mydemo1.bean.NavigtionDataBean;
import com.example.mydemo1.contract.NavigationContract;
import com.example.mydemo1.fragment.BaseFragment;
import com.example.mydemo1.presenter.NavigationPresenter;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import q.rorbin.verticaltablayout.VerticalTabLayout;
import q.rorbin.verticaltablayout.adapter.TabAdapter;
import q.rorbin.verticaltablayout.widget.ITabView;
import q.rorbin.verticaltablayout.widget.TabView;

/**
 * A simple {@link Fragment} subclass.
 */
public class NavigationFragment extends BaseFragment<NavigationContract.NavigationView, NavigationPresenter<NavigationContract.NavigationView>>
        implements NavigationContract.NavigationView {

    @BindView(R.id.navigation_vertical_tab_layout)
    VerticalTabLayout navigationVerticalTabLayout;
    @BindView(R.id.navigation_divier)
    View navigationDivier;
    @BindView(R.id.navigation_recycle_view)
    RecyclerView navigationRecycleView;
    @BindView(R.id.custom_multiple_status_view)
    MultipleStatusView customMultipleStatusView;
    Unbinder unbinder;
    private RlvNavigationAdapter adapter;
    private ArrayList<NavigtionDataBean> navigtionDataBeans;

    private boolean needScroll;
    private int index;
    private boolean isClickTab;
    private LinearLayoutManager linearLayoutManager;

    public static NavigationFragment newInstance() {
        return new NavigationFragment();
    }

    @Override
    protected void initViewAndData() {
        navigtionDataBeans = new ArrayList<>();
        adapter = new RlvNavigationAdapter(R.layout.item_navigation, navigtionDataBeans);

        linearLayoutManager = new LinearLayoutManager(getActivity());
        navigationRecycleView.setLayoutManager(linearLayoutManager);
        navigationRecycleView.setHasFixedSize(true);
        navigationRecycleView.setAdapter(adapter);

        mPresenter.Navigationhttp();

    }

    //布局 P层

    @Override
    protected int createLayout() {
        return R.layout.fragment_navigation;
    }

    @Override
    protected NavigationPresenter<NavigationContract.NavigationView> createPresenter() {
        return new NavigationPresenter<>();
    }

    private void showNavigationListData(final ArrayList<NavigtionDataBean> navigtionDataBeans) {

        navigationVerticalTabLayout.setTabAdapter(new TabAdapter() {
            @Override
            public int getCount() {
                return navigtionDataBeans == null ? 0 : navigtionDataBeans.size();
            }

            @Override
            public ITabView.TabBadge getBadge(int position) {
                return null;
            }

            @Override
            public ITabView.TabIcon getIcon(int position) {
                return null;
            }

            @Override
            public ITabView.TabTitle getTitle(int position) {
                return new TabView.TabTitle.Builder()
                        .setContent(navigtionDataBeans.get(position).getName())
                        .setTextColor(ContextCompat.getColor(getActivity(), R.color.colorAccent)
                                , ContextCompat.getColor(getActivity(), R.color.Grey500))
                        .build();
            }

            @Override
            public int getBackground(int position) {
                return -1;
            }
        });
    }


    @Override
    public void onNavigationSuccess(List<NavigtionDataBean> navigtionDataBean1) {
        adapter.replaceData(navigtionDataBean1);
        navigtionDataBeans.addAll(navigtionDataBean1);
        showNavigationListData(navigtionDataBeans);
        leftRightLinkage();
    }

    private static final String TAG = "NavigationFragment";

    @Override
    public void onNavigationFail(String msg) {
        Log.e(TAG, "onNavigationFail: " + msg);
    }

    //回到顶部
    public void jumpToTheTop() {
        if (navigationRecycleView != null) {
            navigationRecycleView.smoothScrollToPosition(0);
        }
    }

    private void leftRightLinkage() {
        //监听recycleview的滚动事件
        navigationRecycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                //recyclerView: 当前在滚动的RecyclerView
                //newState: 当前滚动状态.
                super.onScrollStateChanged(recyclerView, newState);
                //newState == RecyclerView.SCROLL_STATE_IDLE  静止没有滚动
                if (needScroll && (newState == RecyclerView.SCROLL_STATE_IDLE)) {
                    //recycleview  滑动
                    scrollRecyclerView();
                }
                rightLinkageLeft(newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                //recyclerView : 当前滚动的view
                //dx : 水平滚动距离
                //dy : 垂直滚动距离
                super.onScrolled(recyclerView, dx, dy);
                if (needScroll) {
                    scrollRecyclerView();
                }
            }
        });

        navigationVerticalTabLayout.addOnTabSelectedListener(new VerticalTabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabView tab, int position) {
                isClickTab = true;
                selectTag(position);
            }

            @Override
            public void onTabReselected(TabView tab, int position) {

            }
        });
    }


    //RecycleView滑动
    private void scrollRecyclerView() {
        needScroll = false;
        //获取RecyclerView当前屏幕显示的第一个条目的position位置=findFirstVisibleItemPosition();
        int indexDistance = index - linearLayoutManager.findFirstVisibleItemPosition();
        //navigationRecycleView.getChildCount() getChildCount（）方法返回的是直接子元素的个数，不包含子元素内部包含的元素个数
        if (indexDistance >= 0 && indexDistance < navigationRecycleView.getChildCount()) {

            int top = navigationRecycleView.getChildAt(indexDistance).getTop();
            //smoothScroll会触发多次onScrolled，还会触发onScrollStateChanged
            navigationRecycleView.smoothScrollBy(0, top);
        }
    }

    private void rightLinkageLeft(int newState) {
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            if (isClickTab) {
                isClickTab = false;
                return;
            }
            int firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
            if (index != firstVisibleItemPosition) {
                index = firstVisibleItemPosition;
                setChecked(index);
            }
        }
    }

    private void selectTag(int position) {
        index = position;
        navigationRecycleView.stopScroll();
        smoothScrollToPosition(position);
    }

    private void setChecked(int position) {
        if (isClickTab) {
            isClickTab = false;
        } else {
            if (navigationVerticalTabLayout == null) {
                return;
            }
            navigationVerticalTabLayout.setTabSelected(index);
        }
        index = position;
    }

    private void smoothScrollToPosition(int position) {
        int firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
        //获取RecyclerView当前屏幕显示的第一个条目的position位置=findLastVisibleItemPosition();
        int lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();
        if (position <= firstVisibleItemPosition) {
            navigationRecycleView.smoothScrollToPosition(position);
        } else if (position <= lastVisibleItemPosition) {
            int top = navigationRecycleView.getChildAt(position - firstVisibleItemPosition).getTop();
            navigationRecycleView.smoothScrollBy(0, top);
        } else {
            navigationRecycleView.smoothScrollToPosition(position);
            needScroll = true;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getData(String title) {
    }

}
