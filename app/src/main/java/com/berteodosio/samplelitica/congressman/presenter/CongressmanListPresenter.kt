package com.berteodosio.samplelitica.congressman.presenter

import android.util.Log
import com.berteodosio.samplelitica.base.BasePresenter
import com.berteodosio.samplelitica.congressman.CongressmanMVP
import com.berteodosio.samplelitica.congressman.model.Congressman
import com.berteodosio.samplelitica.log.ErrorLogger
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.observables.ConnectableObservable
import rx.schedulers.Schedulers

class CongressmanListPresenter(private val mView: CongressmanMVP.View, private val mBO: CongressmanMVP.BO) : BasePresenter(), CongressmanMVP.Presenter {

    private val TAG = CongressmanListPresenter::class.java.simpleName

    override fun loadLocalCongressmen() {
        var emittedItemsCount = 0

        mBO.loadLocalCongressmen()
                .flatMap { congressmanList -> Observable.from(congressmanList) }
                .filter { congressman -> congressman != null }
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext { emittedItemsCount++ }
                .subscribe({ congressman -> onLocalCongressmanLoaded(congressman) }, { error -> onErrorLoadingLocalCongressman(error) }, { onFinishedLoadingLocalCongressman(emittedItemsCount) })

    }

    private fun onFinishedLoadingLocalCongressman(emittedItemsCount: Int) {
        if (emittedItemsCount == 0) {
            mView.displayWaitUntilRemoteCongressmanIsLoadedMessage()
        }
    }

    private fun onErrorLoadingLocalCongressman(error: Throwable?) {
        ErrorLogger.logException(TAG, "An error ocurred while loading local congressman", error)
    }

    private fun onLocalCongressmanLoaded(congressman: Congressman) {
        mView.addCongressman(congressman)
    }

    override fun loadRemoteCongressmen() {
        var emittedItemsCount = 0

        val connectableObservable = mBO.loadRemoteCongressmen()
                .flatMap { congressmanList -> Observable.from(congressmanList) }
                .filter { congressman -> congressman != null }
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext { emittedItemsCount++ }
                .publish()

        connectableObservable
                .first()
                .subscribe({ congressman -> onFirstRemoteCongressmanLoaded(congressman) }, { error -> onErrorLoadingRemoteCongressman(error) })

        connectableObservable
                .subscribe({ congressman -> onRemoteCongressmanLoaded(congressman) }, { error -> onErrorLoadingRemoteCongressman(error) }, { onFinishedLoadingRemoteCongressman(emittedItemsCount) })

        connectableObservable.connect()
    }

    private fun onFirstRemoteCongressmanLoaded(congressman: Congressman) {
        Log.d(TAG, "First remote congressman loaded: ${congressman.name}")
        mView.emptyCongressman()
    }

    private fun onErrorLoadingRemoteCongressman(error: Throwable?) {
        ErrorLogger.logException(TAG, "An error ocurred while loading remote congressman", error)
    }

    private fun onFinishedLoadingRemoteCongressman(emittedItemsCount: Int) {
        if (emittedItemsCount == 0) {
            mView.displayEmptyCongressmanList()
        }

        Log.d(TAG, "Remote emitted items count: $emittedItemsCount")
    }

    private fun onRemoteCongressmanLoaded(congressman: Congressman) {
        Observable.create<Nothing> { mBO.persistCongressman(congressman) }
                .subscribeOn(Schedulers.newThread())
                .subscribe()

        mView.addCongressman(congressman)
    }
}
