package com.berteodosio.samplelitica.log;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import java.util.Arrays;


public final class ErrorLogger {

    private static final String DEFAULT_TAG = ErrorLogger.class.getSimpleName();
    private static final String DEFAULT_MESSAGE = "No error message";

    public static void log(@NonNull final String tag, @NonNull final String message) {
        Log.e(tag, message);
    }

    public static void logException(@Nullable String tag, @Nullable String message,
                                    @Nullable final Throwable throwable) {
        if (TextUtils.isEmpty(tag)) {
            tag = DEFAULT_TAG;
        }

        if (TextUtils.isEmpty(message)) {
            message = DEFAULT_MESSAGE;
        }

        Log.e(tag, message, throwable);
    }
}

