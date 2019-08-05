package com.example.mydemo1.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.mydemo1.R;
import com.example.mydemo1.adapter.RlvWxArticleItemAdapter;
import com.example.mydemo1.api.MyService;
import com.example.mydemo1.base.BaseResponse;
import com.example.mydemo1.bean.ArticleItemBean;
import com.example.mydemo1.bean.WxArticleListBean;
import com.example.mydemo1.constant.Constants;
import com.example.mydemo1.http.HttpManager;
import com.example.mydemo1.utils.HomePagerUtils;
import com.example.mydemo1.utils.RxUtils;

import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class WxArticleListFragment extends Fragment {

    private View view;
    private RecyclerView mWxArticleListRecycleView;
    private String cid;
    private ArrayList<ArticleItemBean> wxArticleListBeans;
    private RlvWxArticleItemAdapter rlvWxArticleItemAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_wx_article_list, null);

        initView(inflate);
        initData();
        return inflate;
    }

    private void initData() {
        HttpManager.getInstance().getApiService(MyService.class).getWxArticleList(Integer.parseInt(cid))
                .compose(RxUtils.<BaseResponse<WxArticleListBean>>rxScheduleThread())
                .compose(RxUtils.<WxArticleListBean>changeResult())
                .subscribe(new Observer<WxArticleListBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(WxArticleListBean wxArticleListBean) {
                        wxArticleListBeans.addAll(wxArticleListBean.getDatas());
                        rlvWxArticleItemAdapter.notifyDataSetChanged();
                    }

                    private static final String TAG = "WxArticleListFragment";

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void initView(View inflate) {
        cid = getArguments().getString(Constants.ARTICLE_ID);
        mWxArticleListRecycleView = (RecyclerView) inflate.findViewById(R.id.wx_article_list_recycle_view);
        mWxArticleListRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        wxArticleListBeans = new ArrayList<>();
        rlvWxArticleItemAdapter = new RlvWxArticleItemAdapter(R.layout.item_artice_list, wxArticleListBeans);
        rlvWxArticleItemAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startArticleActivity(view,position);
            }
        });
        mWxArticleListRecycleView.setHasFixedSize(true);
        mWxArticleListRecycleView.setAdapter(rlvWxArticleItemAdapter);
    }

    private void startArticleActivity(View view, int position) {
        if (rlvWxArticleItemAdapter.getData().size() <= 0 || rlvWxArticleItemAdapter.getData().size() < position) {
            return;
        }
        HomePagerUtils.startArticleDetailActivity(getActivity()
                , rlvWxArticleItemAdapter.getData().get(position).getId()
                , rlvWxArticleItemAdapter.getData().get(position).getTitle()
                , rlvWxArticleItemAdapter.getData().get(position).getLink()
                , rlvWxArticleItemAdapter.getData().get(position).isCollect()
                , true, position, "link");
    }

    public void jumpToTheTop() {
    }
}
