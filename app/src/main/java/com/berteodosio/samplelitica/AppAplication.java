package com.berteodosio.samplelitica;

import android.app.Application;

import com.raizlabs.android.dbflow.config.FlowManager;

public class AppAplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        FlowManager.init(this);
    }
}
