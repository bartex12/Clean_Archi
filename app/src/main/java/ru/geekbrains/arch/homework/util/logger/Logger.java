package ru.geekbrains.arch.homework.util.logger;


public interface Logger {

    void logException(String tag, String message, Throwable throwable);

}
