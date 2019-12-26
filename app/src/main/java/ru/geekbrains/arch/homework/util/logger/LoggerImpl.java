package ru.geekbrains.arch.homework.util.logger;

import android.util.Log;

public class LoggerImpl implements Logger {

    @Override
    public void logException(String tag, String message, Throwable throwable) {
        Log.w(tag, message, throwable);
    }
}
