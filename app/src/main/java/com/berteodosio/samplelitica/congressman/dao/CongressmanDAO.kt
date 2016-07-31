package com.berteodosio.samplelitica.congressman.dao

import com.berteodosio.samplelitica.congressman.CongressmanMVP
import com.berteodosio.samplelitica.congressman.model.Congressman
import com.berteodosio.samplelitica.congressman.remote.CongressmanRemoteClient
import com.berteodosio.samplelitica.database.entity.CongressmanEntity
import com.raizlabs.android.dbflow.sql.language.SQLite
import rx.Observable
import java.util.*

class CongressmanRemoteDAO : CongressmanMVP.DAO {

    override fun saveCongressman(congressman: Congressman) {

    }

    override fun loadCongressmen(): Observable<MutableList<Congressman>> {
        return CongressmanRemoteClient.getCongressmen()
    }

}

class CongressmanLocalDAO: CongressmanMVP.DAO {

    override fun saveCongressman(congressman: Congressman) {
        CongressmanEntity.fromModel(congressman).save()
    }

    override fun loadCongressmen(): Observable<MutableList<Congressman>> {
        val congressmanEntityList = SQLite.select().from(CongressmanEntity::class.java).queryList()
        val congressmanList = ArrayList<Congressman>()

        for (congressmanEntity in congressmanEntityList) {
            congressmanList.add(congressmanEntity.toModel())
        }

        return Observable.just(congressmanList)
    }

}