package com.berteodosio.samplelitica.congressman.remote;

import com.berteodosio.samplelitica.BuildConfig;
import com.berteodosio.samplelitica.congressman.model.Congressman;
import com.berteodosio.samplelitica.congressman.model.CongressmanList;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;
import rx.Observable;

public class CongressmanRemoteClient {

    private static final String TAG = CongressmanRemoteClient.class.getSimpleName();

    public static Observable<List<Congressman>> getCongressmen() {
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .baseUrl(BuildConfig.API_ENDPOINT)
                .build();

        CongressmanRemoteService service = retrofit.create(CongressmanRemoteService.class);
        return service.loadCongressmen().map(CongressmanList::list);
    }
}
