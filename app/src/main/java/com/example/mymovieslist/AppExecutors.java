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

    private AppExecutors(Executor diskIo,Executor networkIO,Executor mainThread)
    {
        this.diskIO=diskIo;
        this.networkIO=networkIO;
        this.mainThread=mainThread;
    }

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

    public Executor getDiskIo(){
        return diskIO;
    }

    public Executor getMainThread(){
        return mainThread;
    }

    public Executor getNetworkIO(){
        return networkIO;
    }

    private static class MainThreadExecutor implements Executor{
        private Handler mainThreadHandler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(@NonNull Runnable command) {
            mainThreadHandler.post(command);
        }
    }
}
