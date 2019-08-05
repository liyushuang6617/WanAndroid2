package com.example.mydemo1.fragment.mfragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.mydemo1.R;
import com.example.mydemo1.activity.KnowledgeActivity;
import com.example.mydemo1.adapter.RlvKnowledgeTreeAdapter;
import com.example.mydemo1.bean.KnowledgeItemBean;
import com.example.mydemo1.bean.KnowledgeTreeBean;
import com.example.mydemo1.constant.Constants;
import com.example.mydemo1.contract.KnowledgeContract;
import com.example.mydemo1.fragment.BaseFragment;
import com.example.mydemo1.presenter.KnowledgePresenter;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class KnowledgeFragment extends BaseFragment<KnowledgeContract.KnowledgeView, KnowledgePresenter<KnowledgeContract.KnowledgeView>>
        implements KnowledgeContract.KnowledgeView {

    @BindView(R.id.knowledge_recycle_view)
    RecyclerView knowledgeRecycleView;
    private ArrayList<KnowledgeTreeBean> knowledgeTreeBeans;
    private RlvKnowledgeTreeAdapter adapter;

    public static KnowledgeFragment newInstance() {
        return new KnowledgeFragment();
    }

    @Override
    protected void initViewAndData() {
        initRecycleLaout();
    }

    private void initRecycleLaout() {
        knowledgeRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        knowledgeTreeBeans = new ArrayList<>();
        adapter = new RlvKnowledgeTreeAdapter(R.layout.item_knowledge_tree_list, knowledgeTreeBeans);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startKnowledgeActivity(position);
            }
        });

        knowledgeRecycleView.setHasFixedSize(true);
        knowledgeRecycleView.setAdapter(adapter);
        mPresenter.Knowledgehttp();
    }

    private void startKnowledgeActivity(int position) {
        Intent intent = new Intent(getActivity(), KnowledgeActivity.class);
        intent.putExtra(Constants.KNOWLEDGE_DATA, adapter.getData().get(position));
        startActivity(intent);
    }

    @Override
    protected int createLayout() {
        return R.layout.fragment_knowledge;
    }

    @Override
    protected KnowledgePresenter<KnowledgeContract.KnowledgeView> createPresenter() {
        return new KnowledgePresenter<>();
    }

    @Override
    public void onTreeSuccess(List<KnowledgeTreeBean> knowledgeTreeBean) {
        knowledgeTreeBeans.addAll(knowledgeTreeBean);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemSuccess(KnowledgeItemBean knowledgeItemBean) {

    }

    private static final String TAG = "KnowledgeFragment";

    @Override
    public void onFail(String msg) {
        Log.e(TAG, "onFail: " + msg);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getData(String title) {

    }

    public void jumpToTheTop() {
        if (knowledgeRecycleView != null) {
            knowledgeRecycleView.smoothScrollToPosition(0);
        }
    }
}
