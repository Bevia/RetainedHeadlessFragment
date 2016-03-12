package com.corebaseit.bevia.headlessfragment.classical;

import android.content.Context;

public class AsyncTaskCaller {

    protected Context mContext;

    public AsyncTaskCaller(Context mContext) {
        this.mContext = mContext; //context from MainActivity
    }

    //AsyncTask Call:
    public void ClassicalAsyncTaskCaller()
    {
        ClassicalAsyncTask classicalAsyncTask = new ClassicalAsyncTask(this.mContext);
        classicalAsyncTask.execute();
    }
}
