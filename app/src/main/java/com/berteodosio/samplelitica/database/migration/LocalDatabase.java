package com.berteodosio.samplelitica.database.migration;

import com.raizlabs.android.dbflow.annotation.Database;

@Database(name = LocalDatabase.NAME, version = LocalDatabase.VERSION)
public class LocalDatabase {

    public static final String NAME = "SampleliticaLocal";

    public static final int VERSION = 1;

}
