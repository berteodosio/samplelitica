package com.berteodosio.samplelitica.congressman.business

import android.util.Log
import com.berteodosio.samplelitica.congressman.CongressmanMVP
import com.berteodosio.samplelitica.congressman.dao.CongressmanLocalDAO
import com.berteodosio.samplelitica.congressman.dao.CongressmanRemoteDAO
import com.berteodosio.samplelitica.congressman.model.Congressman
import rx.Observable

class CongressmanListBO(private val mLocalDAO: CongressmanLocalDAO, private val mRemoteDAO: CongressmanRemoteDAO) : CongressmanMVP.BO {

    companion object {
        val TAG = CongressmanListBO::class.java.simpleName
    }

    override fun loadLocalCongressmen(): Observable<MutableList<Congressman>> {
        return mLocalDAO.loadCongressmen()
    }

    override fun loadRemoteCongressmen(): Observable<MutableList<Congressman>> {
        return mRemoteDAO.loadCongressmen()
    }

    override fun persistCongressman(congressman: Congressman) {
        synchronized(mLocalDAO) {
            try {
                mLocalDAO.saveCongressman(congressman)
            } catch (e: Exception) {
                Log.e(TAG, "Error saving ${congressman.name}")
            }
        }
    }

}