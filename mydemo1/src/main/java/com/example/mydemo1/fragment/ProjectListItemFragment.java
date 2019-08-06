package com.example.mydemo1.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.mydemo1.R;
import com.example.mydemo1.adapter.RlvProjectListItemAdapter;
import com.example.mydemo1.api.MyService;
import com.example.mydemo1.base.BaseResponse;
import com.example.mydemo1.bean.ProjectListDataBean;
import com.example.mydemo1.bean.ProjectListItemBean;
import com.example.mydemo1.constant.Constants;
import com.example.mydemo1.contract.ProjectListItemContract;
import com.example.mydemo1.http.HttpManager;
import com.example.mydemo1.presenter.ProjectItemListPresenter;
import com.example.mydemo1.utils.HomePagerUtils;
import com.example.mydemo1.utils.RxUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProjectListItemFragment extends BaseFragment<ProjectListItemContract.ProjectListItemView, ProjectItemListPresenter<ProjectListItemContract.ProjectListItemView>>
        implements ProjectListItemContract.ProjectListItemView {

    @BindView(R.id.project_list_item_recycle_view)
    RecyclerView projectListItemRecycleView;
    Unbinder unbinder;
    @BindView(R.id.pproject_list_smart_refresh)
    SmartRefreshLayout pprojectListSmartRefresh;
    Unbinder unbinder1;
    private int cid;
    private int page = 1;
    private RlvProjectListItemAdapter adapter;
    private ArrayList<ProjectListItemBean> projectListItemBeans;

    public ProjectListItemFragment() {
        // Required empty public constructor
    }

    @Override
    protected void initViewAndData() {
        cid = getArguments().getInt(Constants.PROJECT_CID, -1);
        projectListItemRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        projectListItemBeans = new ArrayList<>();
        adapter = new RlvProjectListItemAdapter(R.layout.item_project_list, projectListItemBeans);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startArticleDetailPager(view, position);
            }
        });
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                collectClickEvent(position);
            }
        });
        projectListItemRecycleView.setHasFixedSize(true);
        projectListItemRecycleView.setAdapter(adapter);
        mPresenter.onProjectListItemhttp(page, cid);

        initSmartRefresh();
    }

    private void initSmartRefresh() {
        pprojectListSmartRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page++;
                mPresenter.onProjectListItemhttp(page, cid);
            }
        });
        pprojectListSmartRefresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.onProjectListItemhttp(page, cid);
            }
        });
    }

    private void startArticleDetailPager(View view, int position) {
        HomePagerUtils.startArticleDetailActivity(getActivity(),
                adapter.getData().get(position).getId(),
                adapter.getData().get(position).getTitle(),
                adapter.getData().get(position).getLink(),
                adapter.getData().get(position).isCollect(),
                true, position, "link");
    }

    //布局  P层

    @Override
    protected int createLayout() {
        return R.layout.fragment_project_list_item;
    }

    @Override
    protected ProjectItemListPresenter<ProjectListItemContract.ProjectListItemView> createPresenter() {
        return new ProjectItemListPresenter<>();
    }

    @Override
    public void onProjectListItemSuccess(ProjectListDataBean projectListItemBean1) {
        projectListItemBeans.addAll(projectListItemBean1.getDatas());
        adapter.notifyDataSetChanged();

    }

    private static final String TAG = "ProjectListItemFragment";

    @Override
    public void onProjectListItemFail(String msg) {
        Log.e(TAG, "onProjectListItemFail: " + msg);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getData(String title) {
    }

    public void jumpToTheTop() {
        if (projectListItemRecycleView != null) {
            projectListItemRecycleView.smoothScrollToPosition(0);
        }
    }

    private void collectClickEvent(int position) {//8655
        int id = projectListItemBeans.get(position).getId();
        if (projectListItemBeans.get(position).isCollect()) {
            HttpManager.getInstance().getApiService(MyService.class).getNoCollection("lg/uncollect_originId/" + id + "" + "/json")
                    .compose(RxUtils.<BaseResponse>rxScheduleThread())
                    .subscribe(new Observer<BaseResponse>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(BaseResponse baseResponse) {
                            if (baseResponse.getErrorMsg() == "") {
                                Toast.makeText(getActivity(), "已取消收藏", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        } else {
            HttpManager.getInstance().getApiService(MyService.class).getCollectionItem("lg/collect/" + id + "" + "/json")
                    .compose(RxUtils.<BaseResponse>rxScheduleThread())
                    .subscribe(new Observer<BaseResponse>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(BaseResponse baseResponse) {
                            if (baseResponse.getErrorMsg() == "") {
                                Toast.makeText(getActivity(), "收藏成功", Toast.LENGTH_SHORT).show();
                                View view = View.inflate(getActivity(), R.layout.item_artice_list, null);
                                ImageView viewById = view.findViewById(R.id.iv_article_like);
                                viewById.setImageResource(R.drawable.ic_like);
                            } else {
                                Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e(TAG, "onError: " + e.getMessage());
                        }

                        @Override
                        public void onComplete() {

                        }
                    });

        }
    }
}
