package com.example.mymovieslist;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.logging.LogRecord;

public class AppExecutors {
    //singleton Instantiation
    private static final Object LOCK=new Object();
    private static AppExecutors sIntance;
    private final Executor diskIO;
    private final Executor mainThread;
    private final Executor networkIO;
    //constructors
    private AppExecutors(Executor diskIo,Executor networkIO,Executor mainThread)
    {
        this.diskIO=diskIo;
        this.networkIO=networkIO;
        this.mainThread=mainThread;
    }
    //getInstance method to get insatnce of AppExecutor
    public static AppExecutors getInstance()
    {
        if (sIntance== null){
            synchronized (LOCK){
                sIntance=new AppExecutors(Executors.newSingleThreadExecutor(),
                        Executors.newFixedThreadPool(3),new MainThreadExecutor());
            }
        }
        return sIntance;
    }
    //getter method for diskIO
    public Executor getDiskIo(){
        return diskIO;
    }
    //getter method for MainThread
    public Executor getMainThread(){
        return mainThread;
    }
    //getter method for networkIO
    public Executor getNetworkIO(){
        return networkIO;
    }
    //inner class for MainThreadExecutor
    private static class MainThreadExecutor implements Executor{
        private Handler mainThreadHandler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(@NonNull Runnable command) {
            mainThreadHandler.post(command);
        }
    }
}
