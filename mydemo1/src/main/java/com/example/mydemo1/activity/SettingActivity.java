package com.example.mydemo1.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mydemo1.R;
import com.example.mydemo1.base.SimpleActivity;
import com.example.mydemo1.utils.CacheDataManager;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingActivity extends SimpleActivity {

    @BindView(R.id.status_bar_view)
    View statusBarView;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.activity_toolbar)
    Toolbar activityToolbar;
    @BindView(R.id.clear_cache)
    TextView clearCache;

    @Override
    protected void initViewAndData() {
        toolbarSetting();
        try {
            clearCache.setText(CacheDataManager.getTotalCacheSize(this));
        } catch (Exception e) {
            e.printStackTrace();
        }
        clearCache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 清除缓存
                 */
                try {
                    String data = CacheDataManager.getTotalCacheSize(SettingActivity.this);
                    clearCache.setText(data);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                CacheDataManager.clearAllCache(SettingActivity.this);
                clearCache.setText("0.0M");
                Toast.makeText(SettingActivity.this, "缓存已清理", Toast.LENGTH_SHORT).show();

//                Toast.makeText(SettingActivity.this, "123", Toast.LENGTH_SHORT).show();
//                new AlertDialog.Builder(SettingActivity.this)
//                        .setMessage("清除缓存提醒,是否确认清除缓存?")
//                        .setPositiveButton("是", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                CacheDataManager.clearAllCache(SettingActivity.this);
//                                clearCache.setText("0.0M");
//                                Toast.makeText(SettingActivity.this, "缓存已清理", Toast.LENGTH_SHORT).show();
//                            }
//                        })
//                        .setNegativeButton("否", null)
//                        .create().show();
            }
        });
    }

    @Override
    protected int createLayout() {
        return R.layout.activity_setting;
    }

    private void toolbarSetting() {

        setSupportActionBar(activityToolbar);
        toolbarTitle.setText(R.string.setting);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar == null) {
            supportActionBar.setDisplayShowHomeEnabled(true);
            supportActionBar.setDisplayShowTitleEnabled(false);
        }
        activityToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getData(String title) {
    }
}
