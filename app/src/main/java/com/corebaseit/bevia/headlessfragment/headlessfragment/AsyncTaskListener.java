package com.corebaseit.bevia.headlessfragment.headlessfragment;

/**
 * Created by vbevia on 10/03/16.
 */

public interface AsyncTaskListener<Progress, Result> {
    void onPreExecute();
    void onProgressUpdate(Progress... progress);
    void onPostExecute(Result result);
    void onCancelled(Result result);
}

