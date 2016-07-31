package com.berteodosio.samplelitica.database.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.berteodosio.samplelitica.database.entity.CongressmanEntity;
import com.raizlabs.android.dbflow.sql.language.SQLite;

public class CongressmanProvider extends ContentProvider {

    public static final String PROVIDER_AUTHORITY = "com.berteodosio.samplelitica.provider.congressman";
    public static final String URL = "content://" + PROVIDER_AUTHORITY + "/congressman";
    public static final Uri CONTENT_URI = Uri.parse(URL);

    private static final int CONGRESSMAN = 1;
    private static final int CONGRESSMAN_ID = 2;

    private UriMatcher mUriMatcher;

    @Override
    public boolean onCreate() {
        mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        mUriMatcher.addURI(PROVIDER_AUTHORITY, "congressman", CONGRESSMAN);
        mUriMatcher.addURI(PROVIDER_AUTHORITY, "congressman/#", CONGRESSMAN_ID);

        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull final Uri uri, final String[] projection, final String selection, final String[] selectionArgs, final String sortOrder) {
        switch (mUriMatcher.match(uri)) {
            case CONGRESSMAN:
                return SQLite.select().from(CongressmanEntity.class).query();
        }

        return null;
    }

    @Nullable
    @Override
    public String getType(final Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(final Uri uri, final ContentValues values) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int delete(final Uri uri, final String selection, final String[] selectionArgs) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int update(final Uri uri, final ContentValues values, final String selection, final String[] selectionArgs) {
        throw new UnsupportedOperationException();
    }
}
