package com.corebaseit.bevia.headlessfragment;

/**
 * Created by vbevia on 10/03/16.
 */

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.corebaseit.bevia.headlessfragment.classical.AsyncTaskCaller;
import com.corebaseit.bevia.headlessfragment.headlessfragment.AsyncTaskHeadlessFragment;
import com.corebaseit.bevia.headlessfragment.headlessfragment.AsyncTaskListener;

import java.math.BigInteger;

public class MainActivity extends AppCompatActivity implements AsyncTaskListener<Integer, BigInteger> {

    private AsyncTaskCaller asyncCaller;
    public static final String HEADLESS = "headlessAsyncTask";
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**

         Calling an AsyncTask the classical way...

         From Google:
         http://developer.android.com/intl/es/guide/topics/resources/runtime-changes.html

         "Some device configurations can change during runtime
         (such as screen orientation, keyboard availability, and language).
         When such a change occurs, Android restarts the running Activity
         (onDestroy() is called, followed by onCreate())."

         Notice how Objets are NOT retained during device rotation...tested!

         */

        //Using composition here ;) --> My MainActivity - has-a - AsyncTaskCaller!
        asyncCaller = new AsyncTaskCaller(this); //pass "this" object reference to constructor...
        //Instantiate the Button at runtime:
        Button classicalAsyncTaskButton = (Button) findViewById(R.id.button1);
        //Right here we will use an inner class!
        classicalAsyncTaskButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                asyncCaller.ClassicalAsyncTaskCaller(); //Call the method at this Class..
            }
        });

        /**

         Now, into the Headless Fragment...

         From Google:
         http://developer.android.com/intl/es/guide/topics/resources/runtime-changes.html

         "you can alleviate the burden of reinitializing your activity by retaining a
         Fragment when your activity is restarted due to a configuration change.
         This fragment can contain references to stateful objects that you want to retain.
         You can obtain the data object from the fragment when the activity starts
         again during runtime configuration changes"

         Notice how Objets are NOW retained during device rotation...tested!

         */
        //Instantiate the Button at runtime:
        Button headlessFragmentAsyncTaskButton = (Button) findViewById(R.id.button);
        //Right here we will use an inner class!
        headlessFragmentAsyncTaskButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

               //Use FragmentManager to add the fragment to this activity.
                FragmentManager mf = getSupportFragmentManager();
                AsyncTaskHeadlessFragment headlessAsyncTask = (AsyncTaskHeadlessFragment)
                        mf.findFragmentByTag("headlessAsyncTask");
                if (headlessAsyncTask == null) {
                    headlessAsyncTask = new AsyncTaskHeadlessFragment();
                    FragmentTransaction transaction = mf.beginTransaction();
                    transaction.add(headlessAsyncTask, HEADLESS);
                    transaction.commit();
                }
            }
        });
    }

    /**

     From the interface: AsyncTaskListener
     Wee need this interface ---> Because this Activity needs to implement callback
     methods defined in the interface to update stateful objects!

     */

    public void onPreExecute() {
        onProgressUpdate(0);
    }

    public void onProgressUpdate(Integer... progress) {
        if (progressDialog == null) {
            prepareProgressDialog();
        }
        progressDialog.setProgress(progress[0]);
    }

    public void onPostExecute(BigInteger result) {
        cleanUp();
    }

    public void onCancelled(BigInteger result) {
        cleanUp();
    }

    private void prepareProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Testing ProgressDialog");
        progressDialog.setMessage("In Progress...");
        progressDialog.setCancelable(true);
        progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface progressDialog) {
                AsyncTaskHeadlessFragment dialogRating = (AsyncTaskHeadlessFragment)
                        getSupportFragmentManager().findFragmentByTag(HEADLESS);
                dialogRating.cancel();
            }
        });
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setProgress(0);
        progressDialog.setMax(1000);
        progressDialog.show();
    }

    private void cleanUp() {
        progressDialog.dismiss();
        progressDialog = null;
        FragmentManager fm = getSupportFragmentManager();
        AsyncTaskHeadlessFragment dialogRating =
                (AsyncTaskHeadlessFragment) fm.findFragmentByTag(HEADLESS);
        fm.beginTransaction().remove(dialogRating).commit();
    }
}
