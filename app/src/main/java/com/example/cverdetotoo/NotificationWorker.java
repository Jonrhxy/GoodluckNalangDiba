package com.example.cverdetotoo;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class NotificationWorker extends Worker {

    public NotificationWorker(@NonNull Context context, @NonNull WorkerParameters params) {
        super(context, params);
    }

    @NonNull
    @Override
    public Result doWork() {
        // Your code to post a notification using NotificationCompat
        // (You can adapt the code provided earlier for NotificationWorker)
        return Result.success();
    }
}
