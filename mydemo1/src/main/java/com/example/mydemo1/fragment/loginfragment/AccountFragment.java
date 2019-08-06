package com.example.mydemo1.fragment.loginfragment;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mydemo1.R;
import com.example.mydemo1.bean.AccountDataBean;
import com.example.mydemo1.contract.AccountContract;
import com.example.mydemo1.fragment.BaseFragment;
import com.example.mydemo1.presenter.AccountPresenter;
import com.example.mydemo1.utils.MD5Utils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends BaseFragment<AccountContract.AccountView, AccountPresenter<AccountContract.AccountView>>
        implements AccountContract.AccountView {


    @BindView(R.id.account_et_name)
    EditText accountEtName;
    @BindView(R.id.account_et_pwd)
    EditText accountEtPwd;
    @BindView(R.id.account_et_repwd)
    EditText accountEtRepwd;
    @BindView(R.id.account_btn)
    Button accountBtn;
    @BindView(R.id.account_tv_not)
    TextView accountTvNot;
    Unbinder unbinder;
    private FragmentManager manager;
    private String accountName;
    private String accountPwd;
    private String accountRepwd;
    private String nickname;
    private String password;

    @Override
    protected void initViewAndData() {

    }

    private void account() {
        accountName = accountEtName.getText().toString();
        accountPwd = accountEtPwd.getText().toString();
        accountRepwd = accountEtRepwd.getText().toString();
        accountEtName.setText(nickname);
        accountEtPwd.setText(password);
        if (TextUtils.isEmpty(accountName)) {
            Toast.makeText(getActivity(), "用户名不能为空", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(accountPwd)) {
            Toast.makeText(getActivity(), "密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(accountRepwd)) {
            Toast.makeText(getActivity(), "重复密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        } else if (accountEtPwd.equals(accountEtRepwd)) {
            Toast.makeText(getActivity(), "两次密码不一致", Toast.LENGTH_SHORT).show();
            return;
        } else if (isExistUserName(accountName)) {
            Toast.makeText(getActivity(), "用户已经存在", Toast.LENGTH_SHORT).show();
            return;
        } else {
            Toast.makeText(getActivity(), "注册成功", Toast.LENGTH_SHORT).show();
            mPresenter.getAccounthttp(accountName, accountPwd, accountRepwd);
        }
    }
    //布局  P层

    @Override
    protected int createLayout() {
        return R.layout.fragment_account;
    }

    @Override
    protected AccountPresenter<AccountContract.AccountView> createPresenter() {
        return new AccountPresenter<>();
    }

    @Override
    public void onAccountSuccess(AccountDataBean accountDataBean) {
        LoginFragment loginFragment = new LoginFragment();
        Bundle bundle = new Bundle();
        bundle.putString("loginname", accountName);
        loginFragment.setArguments(bundle);
    }

    private static final String TAG = "AccountFragment";

    @Override
    public void onAccountFail(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    @OnClick({R.id.account_tv_not, R.id.account_btn})
    public void getAccOunt(View view) {
        LoginFragment loginFragment = new LoginFragment();
        switch (view.getId()) {
            case R.id.account_tv_not:
                manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.login_fragment, loginFragment);
                transaction.commit();
                break;
            case R.id.account_btn:
                account();
                manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction1 = manager.beginTransaction();
                transaction1.replace(R.id.login_fragment, loginFragment);
                transaction1.commit();
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getString(String title) {
    }

    private boolean isExistUserName(String userName) {
        boolean has_userName = false;
        //mode_private SharedPreferences sp = getSharedPreferences( );
        // "loginInfo", MODE_PRIVATE
        SharedPreferences sp = getActivity().getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        //获取密码
        String spPsw = sp.getString(userName, "");//传入用户名获取密码
        //如果密码不为空则确实保存过这个用户名
        if (!TextUtils.isEmpty(spPsw)) {
            has_userName = true;
        }
        return has_userName;
    }

    private void saveRegisterInfo(String userName, String psw) {
        String md5Psw = MD5Utils.encryptMD5(psw);//把密码用MD5加密
        //loginInfo表示文件名, mode_private SharedPreferences sp = getSharedPreferences( );
        SharedPreferences sp = getActivity().getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        //获取编辑器， SharedPreferences.Editor  editor -> sp.edit();
        SharedPreferences.Editor editor = sp.edit();
        //以用户名为key，密码为value保存在SharedPreferences中
        //key,value,如键值对，editor.putString(用户名，密码）;
        editor.putString(userName, md5Psw);
        //提交修改 editor.commit();
        editor.commit();
    }
}
