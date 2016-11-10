package com.aykuttasil.rxjava2examples;

import android.os.Bundle;
import android.provider.CallLog;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private final String TAG = MainActivity.class.getSimpleName();
    TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mTextView = (TextView) findViewById(R.id.TextViewResp);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());


        //writeGetLastOutgoingCall();
        //reduce();
        //scan();
        //range();
        //materialize();
        fibonacci();
    }

    private void fibonacci() {

        Integer[] mIntList = new Integer[2];
        mIntList[0] = 0;
        mIntList[1] = 1;

        Observable observable = Observable.fromArray(0)
                .repeat()
                .scan(mIntList, (list, valInt) -> {
                    return new Integer[]{list[1], list[0] + list[1]};

                    /*int a = list[0];
                    list[0] = list[1];
                    list[1] = a + list[1];
                    Log.i(TAG, "list 0: " + list[0]);
                    Log.i(TAG, "list 1: " + list[1]);
                    return list;
                    */
                }).map(list -> list[1]);


        observable.take(10)
                .subscribe(success -> {
                    Log.i(TAG, "val: " + success.toString());
                });
    }

    private void materialize() {
        Observable.range(2, 10)
                .materialize()
                .filter(integerNotification ->
                {
                    Log.i(TAG, "filter: " + integerNotification.toString());
                    return integerNotification.getValue() != 2;
                })
                .subscribe(success -> {
                    Log.i(TAG, "val: " + success.toString());
                    if (success.isOnNext()) {
                        Log.i(TAG, "onNext: " + success.getValue());
                    }
                });
    }

    private void range() {
        Observable.range(3, 5).subscribeOn(Schedulers.io())
                .subscribe(success -> {
                    Log.i(TAG, "val: " + success.toString());
                });
    }

    private void scan() {
        Observable.fromArray(3, 5, 7)
                .scan(10, (val1, val2) -> {
                    //
                    Log.i(TAG, "val1: " + val1.toString());
                    Log.i(TAG, "val2: " + val2.toString());
                    return val1 + val2;
                })
                .subscribeOn(Schedulers.io())
                .subscribe(success -> {
                    Log.i(TAG, "Sonuc:" + success.toString());
                });
    }

    private void reduce() {
        Observable.just(1, 3, 5)
                .reduce(10, (val1, val2) -> {
                    Log.i(TAG, "val1: " + val1.toString());
                    Log.i(TAG, "val2: " + val2.toString());
                    return val1 + val2;
                })
                .subscribeOn(Schedulers.computation())
                .subscribe(success -> {
                    Log.i(TAG, "Sonuc:" + success.toString());
                });
    }

    private void writeGetLastOutgoingCall() {
        String val = CallLog.Calls.getLastOutgoingCall(this);
        mTextView.setText(val);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
