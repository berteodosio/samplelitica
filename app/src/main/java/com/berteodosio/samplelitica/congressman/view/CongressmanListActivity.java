package com.berteodosio.samplelitica.congressman.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.berteodosio.samplelitica.R;
import com.berteodosio.samplelitica.base.BaseActivity;
import com.berteodosio.samplelitica.congressman.CongressmanMVP;
import com.berteodosio.samplelitica.congressman.adapter.CongressmanListAdapter;
import com.berteodosio.samplelitica.congressman.business.CongressmanListBO;
import com.berteodosio.samplelitica.congressman.dao.CongressmanLocalDAO;
import com.berteodosio.samplelitica.congressman.dao.CongressmanRemoteDAO;
import com.berteodosio.samplelitica.congressman.model.Congressman;
import com.berteodosio.samplelitica.congressman.presenter.CongressmanListPresenter;
import com.berteodosio.samplelitica.database.entity.CongressmanEntity;
import com.berteodosio.samplelitica.log.ErrorLogger;
import com.berteodosio.samplelitica.toolkit.itemdecoration.VerticalSpacingItemDecoration;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

public class CongressmanListActivity extends BaseActivity implements CongressmanMVP.View {

    private static final String TAG = CongressmanListActivity.class.getSimpleName();

    private CongressmanMVP.Presenter mPresenter;

    private RecyclerView mRecyclerView;
    private CongressmanListAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_congressman);

        bindViews();

        mPresenter.loadLocalCongressmen();
        mPresenter.loadRemoteCongressmen();
    }

    private void bindViews() {
        mAdapter = new CongressmanListAdapter(this);

        mRecyclerView = (RecyclerView) findViewById(R.id.congressman_recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.addItemDecoration(new VerticalSpacingItemDecoration(16, getResources()));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void createPresenter() {
        mPresenter = new CongressmanListPresenter(this, new CongressmanListBO(new CongressmanLocalDAO(), new CongressmanRemoteDAO()));
    }

    @Override
    public void displayNewCongressmanList(@NonNull final List<Congressman> congressmanList) {
        // *flatmap* operator takes an operator, modifies it and return another operator
        // if we had Observable<List<String>>, we could have used a flatmap operator to convert it
        // to an Observable.from(List<String>) operator, which would emit each string by a time

        // we use *from* method to iterate over a collection
        Observable.from(congressmanList)
                .subscribe(congressman -> {
                    mAdapter.addCongressman(congressman);
                    CongressmanEntity.fromModel(congressman).save();
                });
    }

    @Override
    public void displayEmptyCongressmanList() {
        ErrorLogger.log(TAG, "Congressman list is null");
    }

    @Override
    public void addCongressman(@NonNull final Congressman congressman) {
        mAdapter.addCongressman(congressman);
    }

    @Override
    public void displayWaitUntilRemoteCongressmanIsLoadedMessage() {

    }

    @Override
    public void emptyCongressman() {
        mAdapter.updateCongressmanList(new ArrayList<>());
    }
}
