package com.berteodosio.samplelitica.congressman.presenter;

import android.support.annotation.NonNull;
import android.util.Log;

import com.berteodosio.samplelitica.base.BasePresenter;
import com.berteodosio.samplelitica.congressman.CongressmanMVP;
import com.berteodosio.samplelitica.congressman.model.Congressman;
import com.berteodosio.samplelitica.log.ErrorLogger;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.observables.ConnectableObservable;
import rx.schedulers.Schedulers;

public class CongressmanListPresenterJava extends BasePresenter implements CongressmanMVP.Presenter {

    private static final String TAG = CongressmanListPresenterJava.class.getSimpleName();

    private final CongressmanMVP.View mView;
    private final CongressmanMVP.BO mBO;

    // should be class member variable
    private int loadLocalCongressmanEmittedItemsCount = 0;
    private int loadRemoteCongressmanEmittedItemsCount = 0;

    public CongressmanListPresenterJava(@NonNull final CongressmanMVP.View view,
                                        @NonNull final CongressmanMVP.BO BO) {
        mView = view;
        mBO = BO;
    }

    @Override
    public void loadLocalCongressmen() {
        mBO.loadLocalCongressmen()
                .flatMap(new Func1<List<Congressman>, Observable<?>>() {
                    @Override
                    public Observable<?> call(final List<Congressman> congressmanList) {
                        return Observable.from(congressmanList);
                    }
                })
                .filter(new Func1<Object, Boolean>() {
                    @Override
                    public Boolean call(final Object o) {
                        return (Congressman) o != null;
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Action1<Object>() {
                    @Override
                    public void call(final Object o) {
                        // should be declared in class because java doesn't support method variables
                        // access from inner class
                        loadLocalCongressmanEmittedItemsCount++;
                    }
                })
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(final Object o) {
                        onLocalCongressmanLoaded((Congressman) o);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(final Throwable throwable) {
                        onErrorLoadingLocalCongressman(throwable);
                    }
                }, new Action0() {
                    @Override
                    public void call() {
                        onFinishedLoadingLocalCongressman(loadLocalCongressmanEmittedItemsCount);
                    }
                });
    }

    private void onFinishedLoadingLocalCongressman(final int loadLocalCongressmanEmittedItemsCount) {
        if (loadLocalCongressmanEmittedItemsCount == 0) {
            mView.displayWaitUntilRemoteCongressmanIsLoadedMessage();
        }
    }

    private void onErrorLoadingLocalCongressman(final Throwable error) {
        ErrorLogger.logException(TAG, "An error ocurred while loading local congressman", error);
    }

    private void onLocalCongressmanLoaded(final Congressman congressman) {
        mView.addCongressman(congressman);
    }

    @Override
    public void loadRemoteCongressmen() {
        final ConnectableObservable<Object> connectableObservable = mBO.loadRemoteCongressmen()
                .flatMap(new Func1<List<Congressman>, Observable<?>>() {
                    @Override
                    public Observable<?> call(final List<Congressman> congressmanList) {
                        return Observable.from(congressmanList);
                    }
                })
                .filter(new Func1<Object, Boolean>() {
                    @Override
                    public Boolean call(final Object o) {
                        return (Congressman) o != null;
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Action1<Object>() {
                    @Override
                    public void call(final Object o) {
                        loadRemoteCongressmanEmittedItemsCount++;
                    }
                })
                .publish();

        connectableObservable.first()
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(final Object o) {
                        onFirstRemoteCongressmanLoaded((Congressman) o);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(final Throwable throwable) {
                        onErrorLoadingLocalCongressman(throwable);
                    }
                });

        connectableObservable
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(final Object o) {
                        onRemoteCongressmanLoaded((Congressman) o);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(final Throwable throwable) {
                        onErrorLoadingLocalCongressman(throwable);
                    }
                }, new Action0() {
                    @Override
                    public void call() {
                        onFinishedLoadingLocalCongressman(loadRemoteCongressmanEmittedItemsCount);
                    }
                });

        connectableObservable.connect();
    }

    private void onRemoteCongressmanLoaded(final Congressman o) {
        
    }

    private void onFirstRemoteCongressmanLoaded(final Congressman congressman) {
        Log.d(TAG, "First remote congressman loaded: " + congressman.getName());
        mView.emptyCongressman();
    }
}
