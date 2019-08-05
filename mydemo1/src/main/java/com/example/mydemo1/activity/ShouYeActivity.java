package com.example.mydemo1.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mydemo1.R;
import com.example.mydemo1.app.MyApp;
import com.example.mydemo1.base.SimpleActivity;
import com.example.mydemo1.constant.Constants;
import com.example.mydemo1.contract.ShouYeContract;
import com.example.mydemo1.fragment.ProjectListItemFragment;
import com.example.mydemo1.fragment.WxArticleListFragment;
import com.example.mydemo1.fragment.mfragment.HomePagerFragment;
import com.example.mydemo1.fragment.mfragment.KnowledgeFragment;
import com.example.mydemo1.fragment.mfragment.NavigationFragment;
import com.example.mydemo1.fragment.mfragment.ProjectFragment;
import com.example.mydemo1.fragment.mfragment.WxArticleFragment;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShouYeActivity extends SimpleActivity {

    @BindView(R.id.activity_toolbar)
    Toolbar toolbar;
    @BindView(R.id.bottom_navig)
    BottomNavigationView bottomNavig;
    @BindView(R.id.shouye_content_fl)
    FrameLayout shouyeContentFl;
    @BindView(R.id.shouye_navig)
    NavigationView shouyeNavig;
    @BindView(R.id.shouye_dl)
    public DrawerLayout shouyeDl;
    @BindView(R.id.shou_ye_float_btn)
    FloatingActionButton shoyefloatbtn;


    private HomePagerFragment mHomePagerFragment;
    private KnowledgeFragment mKnowledgeFragment;
    private NavigationFragment mNavigationFragment;
    private WxArticleFragment mWxArticleFragment;
    private ProjectFragment mProjectFragment;
    private WxArticleListFragment mWxArticleListFragment;
    private ProjectListItemFragment mProjectListItemFragment;

    private int mCurrentFgIndex = 0;
    private int mLastFgIndex = -1;
    private String loginName;
    private String loginPwd;
    private TextView login;
    private String loginuser;

    @Override
    protected void initViewAndData() {

        initToolbar();
        initDrawerLayout();
        showFragment(mCurrentFgIndex);
        initNavigationView();
        initBottomNavigationView();
        initHeadLayoutId();
        logintext();
    }

    private void initHeadLayoutId() {

        View view = shouyeNavig.inflateHeaderView(R.layout.navig_head_layout);
        login = view.findViewById(R.id.login_name);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ShouYeActivity.this, "123", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ShouYeActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        SharedPreferences sp = getSharedPreferences("login", MODE_PRIVATE);
        String status = sp.getString("status", "");
        login.setText(status);
    }

    private void logintext() {
        loginuser = getIntent().getStringExtra("loginuser");
        if (loginuser == null) {
            login.setText("登录");
        } else {
            login.setText(loginuser);
        }
    }


    private void initBottomNavigationView() {
        bottomNavig.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.tab_main_pager:
                        showFragment(Constants.TYPE_HOME_PAGER);
                        break;
                    case R.id.tab_knowledge_hierarchy:
                        showFragment(Constants.TYPE_KNOWLEDGE);
                        break;
                    case R.id.tab_navigation:
                        showFragment(Constants.TYPE_NAVIGATION);
                        break;
                    case R.id.tab_wx_article:
                        showFragment(Constants.TYPE_WX_ARTICLE);
                        break;
                    case R.id.tab_project:
                        showFragment(Constants.TYPE_PROJECT);
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }

    private void initNavigationView() {
        shouyeNavig.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.nav_item_my_collect:
                        startActivity(new Intent(ShouYeActivity.this, CollectionActivity.class));
                        break;
                    case R.id.nav_item_todo:
                        break;
                    case R.id.nav_item_night_mode:
                        if (MyApp.isNaightMode) {
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                            menuItem.setTitle(R.string.nav_day_mode);
                        } else {
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                            menuItem.setTitle(R.string.nav_night_mode);
                        }
                        recreate();
                        break;
                    case R.id.nav_item_setting:
                        break;
                    case R.id.nav_item_about_us:
                        break;
                    case R.id.nav_item_logout:
                        login.setText("登录");
                        menuItem.setVisible(false);
                        break;

                }
                return true;
            }
        });
    }

    private void showFragment(int index) {
        mCurrentFgIndex = index;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        hideFragment(transaction);
        mLastFgIndex = index;
        switch (index) {
            case Constants.TYPE_HOME_PAGER:
                toolbar.setTitle(R.string.home_pager);
                if (mHomePagerFragment == null) {
                    mHomePagerFragment = HomePagerFragment.newInstance();
                    transaction.add(R.id.shouye_content_fl, mHomePagerFragment);
                }
                transaction.show(mHomePagerFragment);
                break;
            case Constants.TYPE_KNOWLEDGE:
                toolbar.setTitle(R.string.knowledge_hierarchy);
                if (mKnowledgeFragment == null) {
                    mKnowledgeFragment = KnowledgeFragment.newInstance();
                    transaction.add(R.id.shouye_content_fl, mKnowledgeFragment);
                }
                transaction.show(mKnowledgeFragment);
                break;
            case Constants.TYPE_NAVIGATION:
                toolbar.setTitle(R.string.navigation);
                if (mNavigationFragment == null) {
                    mNavigationFragment = NavigationFragment.newInstance();
                    transaction.add(R.id.shouye_content_fl, mNavigationFragment);
                }
                transaction.show(mNavigationFragment);
                break;
            case Constants.TYPE_WX_ARTICLE:
                toolbar.setTitle(R.string.wx_article);
                if (mWxArticleFragment == null) {
                    mWxArticleFragment = WxArticleFragment.newInstance();
                    transaction.add(R.id.shouye_content_fl, mWxArticleFragment);
                }
                transaction.show(mWxArticleFragment);
                break;
            case Constants.TYPE_PROJECT:
                toolbar.setTitle(R.string.project);
                if (mProjectFragment == null) {
                    mProjectFragment = ProjectFragment.newInstance();
                    transaction.add(R.id.shouye_content_fl, mProjectFragment);
                }
                transaction.show(mProjectFragment);
                break;
            default:
                break;
        }
        transaction.commit();
    }

    private void hideFragment(FragmentTransaction transaction) {
        switch (mLastFgIndex) {
            case Constants.TYPE_HOME_PAGER:
                if (mHomePagerFragment != null) {
                    transaction.hide(mHomePagerFragment);
                }
                break;
            case Constants.TYPE_KNOWLEDGE:
                if (mKnowledgeFragment != null) {
                    transaction.hide(mKnowledgeFragment);
                }
                break;
            case Constants.TYPE_NAVIGATION:
                if (mNavigationFragment != null) {
                    transaction.hide(mNavigationFragment);
                }
                break;
            case Constants.TYPE_WX_ARTICLE:
                if (mWxArticleFragment != null) {
                    transaction.hide(mWxArticleFragment);
                }
                break;
            case Constants.TYPE_PROJECT:
                if (mProjectFragment != null) {
                    transaction.hide(mProjectFragment);
                }
                break;
            default:
                break;
        }
    }

    private void initToolbar() {
        toolbar.setTitle("主页");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
    }

    private void initDrawerLayout() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, shouyeDl, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
        shouyeDl.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    protected int createLayout() {
        return R.layout.activity_shou_ye;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.shouye_option_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                Toast.makeText(this, "搜索", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(ShouYeActivity.this, SearchActivity.class));
                break;
            case R.id.action_usage:
                Toast.makeText(this, "常用网站", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ShouYeActivity.this, Main2Activity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getData(String title) {
    }

    @OnClick({R.id.shou_ye_float_btn})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.shou_ye_float_btn:
                jumpToTheTop();
                break;
            default:
                break;
        }
    }

    private void jumpToTheTop() {
        switch (mCurrentFgIndex) {
            case Constants.TYPE_HOME_PAGER:
                if (mHomePagerFragment != null) {
                    mHomePagerFragment.jumpToTheTop();
                }
                break;
            case Constants.TYPE_KNOWLEDGE:
                if (mKnowledgeFragment != null) {
                    mKnowledgeFragment.jumpToTheTop();
                }
                break;
            case Constants.TYPE_WX_ARTICLE:
                if (mWxArticleListFragment != null) {
                    mWxArticleListFragment.jumpToTheTop();
                }
            case Constants.TYPE_NAVIGATION:
                if (mNavigationFragment != null) {
                    mNavigationFragment.jumpToTheTop();
                }
                break;
            case Constants.TYPE_PROJECT:
                if (mProjectListItemFragment != null) {
                    mProjectListItemFragment.jumpToTheTop();
                }
                break;
            default:
                break;
        }
    }

}
