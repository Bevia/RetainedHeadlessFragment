package com.corebaseit.bevia.headlessfragment.headlessfragment;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.corebaseit.bevia.headlessfragment.Utils;



/**
 * Created by vbevia on 10/03/16.
 */

public class AsyncTaskHeadlessFragment extends Fragment {

    private AsyncTaskListener<Integer,Integer> listener;
    private HeadlessFragmentAsyncTask headlessFragmentAsyncTask;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        headlessFragmentAsyncTask = new HeadlessFragmentAsyncTask();
        headlessFragmentAsyncTask.execute();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (AsyncTaskListener<Integer,Integer>)context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public void cancel() {
        headlessFragmentAsyncTask.cancel(false);
    }

    class HeadlessFragmentAsyncTask extends AsyncTask<String, Integer, Integer> {
        @Override
        protected void onPreExecute() {
            if (listener != null) listener.onPreExecute();
        }

        @Override
        protected Integer doInBackground(String... params) {

            for (int i=0;i<1000;i++) {

                Utils.sleepForInSecs(1);
                publishProgress(i);

                if (isCancelled())
                    break;
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            if (listener != null) listener.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Integer result) {
            if (listener != null) listener.onPostExecute(result);
        }

        @Override
        protected void onCancelled(Integer result) {
            if (listener != null) listener.onCancelled(result);
        }
    }
}
