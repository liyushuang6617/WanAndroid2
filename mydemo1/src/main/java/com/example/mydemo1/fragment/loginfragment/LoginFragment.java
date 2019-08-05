package com.example.mydemo1.fragment.loginfragment;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mydemo1.R;
import com.example.mydemo1.activity.LoginActivity;
import com.example.mydemo1.activity.ShouYeActivity;
import com.example.mydemo1.bean.LoginDataBean;
import com.example.mydemo1.contract.LoginActivityContract;
import com.example.mydemo1.fragment.BaseFragment;
import com.example.mydemo1.presenter.LoginActivityPresenter;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.ResponseBody;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends BaseFragment<LoginActivityContract.LoginActivityView, LoginActivityPresenter<LoginActivityContract.LoginActivityView>>
        implements LoginActivityContract.LoginActivityView {

    @BindView(R.id.login_et_name)
    EditText loginEtName;
    @BindView(R.id.login_et_pwd)
    EditText loginEtPwd;
    @BindView(R.id.login_btn)
    Button loginBtn;
    @BindView(R.id.login_tv_not)
    TextView loginTvNot;
    private String loginName;
    private String loginPwd;
    private FragmentManager manager;
    private String nickname;
    private String password;

    @Override
    protected int createLayout() {
        return R.layout.fragment_login;
    }

    @Override
    protected LoginActivityPresenter<LoginActivityContract.LoginActivityView> createPresenter() {
        return new LoginActivityPresenter<>();
    }

    @Override
    protected void initViewAndData() {

    }

    @Override
    public void onLoginActivitySuccess(LoginDataBean responseBody) {
        Toast.makeText(getActivity(), "成功", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getActivity(), ShouYeActivity.class);
        intent.putExtra("loginuser", loginName);
        getActivity().startActivity(intent);
        SharedPreferences sp = getActivity().getSharedPreferences("login", MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("status", loginName);
        edit.commit();
    }

    @OnClick({R.id.login_tv_not, R.id.login_btn})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_btn:
                login();
                break;
            case R.id.login_tv_not:
                replaceFrament();
                break;
        }
    }

    private void login() {
        loginName = loginEtName.getText().toString();
        loginPwd = loginEtPwd.getText().toString();
        if (TextUtils.isEmpty(loginName)) {
            Toast.makeText(getActivity(), "用户名不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(loginPwd)) {
            Toast.makeText(getActivity(), "密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        } else {
            mPresenter.onLoginActivityhttp(loginName, loginPwd);
        }

    }

    private void replaceFrament() {
        AccountFragment accountFragment = new AccountFragment();
        manager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.login_fragment, accountFragment);
        transaction.commit();
    }


    @Override
    public void onLoginActivityFail(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getString(String title) {
    }
}
