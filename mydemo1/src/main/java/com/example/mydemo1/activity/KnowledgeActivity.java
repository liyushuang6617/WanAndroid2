package com.example.mydemo1.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mydemo1.R;
import com.example.mydemo1.base.SimpleActivity;
import com.example.mydemo1.bean.KnowledgeItemBean;
import com.example.mydemo1.bean.KnowledgeTreeBean;
import com.example.mydemo1.constant.Constants;
import com.example.mydemo1.fragment.KnowledgeListFragment;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class KnowledgeActivity extends SimpleActivity {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.knowledge_tab)
    TabLayout knowledgeTab;
    @BindView(R.id.knowledge_viewpager)
    ViewPager knowledgeViewpager;
    @BindView(R.id.knowledge_floating_action_bt)
    FloatingActionButton knowledgeFloatingActionBt;
    @BindView(R.id.activity_toolbar)
    Toolbar toolbar;
    private ArrayList<KnowledgeItemBean> datasBeans;
    private List<KnowledgeTreeBean> children;
    private KnowledgeListFragment knowledgeListFragment;

    private SparseArray<KnowledgeListFragment> fragmentSparseArray = new SparseArray<>();
    private KnowledgeTreeBean knowledgeTreeBean;

    @Override
    protected void initViewAndData() {
        knowledgeTreeBean = (KnowledgeTreeBean) getIntent().getSerializableExtra(Constants.KNOWLEDGE_DATA);
        if (knowledgeTreeBean == null && knowledgeTreeBean.getName() == null) {
            return;
        }
        toolbarTitle.setText(knowledgeTreeBean.getName().trim());
        children = knowledgeTreeBean.getChildren();
        if (children == null) {
            return;
        }
        initViewPagerAndTabLayout();
        initToolbar();

    }

    private void initToolbar() {

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void initViewPagerAndTabLayout() {
        knowledgeViewpager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                KnowledgeListFragment knowledgeListFragment = fragmentSparseArray.get(i);
                if (knowledgeListFragment != null) {
                    return knowledgeListFragment;
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putInt(Constants.KNOWLEDGE_CID, children.get(i).getId());
                    knowledgeListFragment = KnowledgeListFragment.newInstance(bundle);
                    fragmentSparseArray.put(i, knowledgeListFragment);
                    return knowledgeListFragment;
                }
            }

            @Override
            public int getCount() {
                return children == null ? 0 : children.size();
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return Html.fromHtml(children.get(position).getName());
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                super.destroyItem(container, position, object);
            }
        });

        knowledgeViewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(knowledgeTab));
        knowledgeTab.setupWithViewPager(knowledgeViewpager);
        knowledgeTab.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(knowledgeViewpager));
        knowledgeTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                knowledgeViewpager.setCurrentItem(tab.getPosition(), false);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @OnClick({R.id.knowledge_floating_action_bt})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.knowledge_floating_action_bt:
                jumpToTheTop();
                break;
            default:
                break;
        }
    }

    private void jumpToTheTop() {
        if (knowledgeListFragment != null) {
            knowledgeListFragment.jumpToTheTop();
        }
    }


    @Override
    protected int createLayout() {
        return R.layout.activity_knowledge;
    }

    @Override
    protected void onDestroy() {
        if (fragmentSparseArray != null) {
            fragmentSparseArray.clear();
            fragmentSparseArray = null;
        }
        if (children != null) {
            children.clear();
            children = null;
        }
        super.onDestroy();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getData(String title) {
    }
}
