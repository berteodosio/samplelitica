package com.berteodosio.samplelitica.congressman.remote;

import com.berteodosio.samplelitica.congressman.model.CongressmanList;

import retrofit2.http.GET;
import rx.Observable;

public interface CongressmanRemoteService {

    @GET("Deputados.asmx/ObterDeputados")
    Observable<CongressmanList> loadCongressmen();
}
