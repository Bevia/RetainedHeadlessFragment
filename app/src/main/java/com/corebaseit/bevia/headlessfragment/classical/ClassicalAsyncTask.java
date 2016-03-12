package com.corebaseit.bevia.headlessfragment.classical;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;

import com.corebaseit.bevia.headlessfragment.Utils;

/**
 * Created by vbevia on 10/03/16.
 */

public class ClassicalAsyncTask extends AsyncTask<String,Integer,Integer> implements DialogInterface.OnCancelListener {

    private Context context;
    ProgressDialog progressDialog;

    //Arguments Constructor...
    ClassicalAsyncTask(Context context)
    {
        this.context = context;
    }

    protected void onPreExecute() {
        prepareProgressDialog();
    }

    protected void onProgressUpdate(Integer... progress) {
        if (progressDialog == null) {
            prepareProgressDialog();
        }
        progressDialog.setProgress(progress[0]);
    }

    @Override
    protected Integer doInBackground(String... params) {
        //Runs on a worker thread
        //May even be a pool if there are
        //more tasks.

        for (int i=0;i<1000;i++)
        {
            Utils.sleepForInSecs(1);
            publishProgress(i);
        }

        return null;
    }

    protected void onPostExecute(Integer result) {
        //Runs on the main ui thread
        progressDialog.cancel();
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        this.cancel(true);
    }

    private void prepareProgressDialog() {

        //Runs on the main ui thread
        //progressDialog = ProgressDialog.show(this.context, "Testing Progress Dialog", "In Progress...",true);
        progressDialog = new ProgressDialog(this.context);
        progressDialog.setTitle("Testing ProgressDialog");
        progressDialog.setMessage("In Progress...");
        progressDialog.setIndeterminate(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setMax(1000);
        progressDialog.show();
        //This lines make the progress dialog cancelable!
        progressDialog.setCancelable(true);
        progressDialog.setOnCancelListener(this);

    }
}
