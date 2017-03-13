package com.aykuttasil.rxjava2examples;

import android.os.Bundle;
import android.provider.CallLog;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import com.aykuttasil.rxjava2examples.models.User;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.MaybeSource;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.ReplaySubject;

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


        //writeGetLastOutgoingCall();
        //reduce();
        //scan();
        //range();
        //materialize();
        //fibonacci();
        //replaySubject();
        //runnable();
        /*try {
            callable();
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        //executorServiceSubmit();
        //executorServiceExecute();
        //executorServiceNewCachedThreadPool();
        //executorServiceNewFixedThreadPool();
        //executorServiceNewSingleThreadExecutor();

        //deneme();


        //tryMaybe();
        //tryFlowable();

        //tryPublishSubject();
        tryReplaySubject();
    }

    private void tryReplaySubject() {

        ReplaySubject<String> replaySubject = ReplaySubject.create();
        replaySubject.observeOn(AndroidSchedulers.mainThread());

        Observer<String> observer1 = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.i(TAG, "observer1 - onSubscribe");
            }

            @Override
            public void onNext(String s) {
                Log.i(TAG, "observer1 - onNext: " + s);
            }

            @Override
            public void onError(Throwable e) {
                showSnackbar(e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.i(TAG, "observer1 - onComplete");
            }
        };

        Observer<String> observer2 = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.i(TAG, "observer2 - onSubscribe");
            }

            @Override
            public void onNext(String s) {
                Log.i(TAG, "observer2 - onNext: " + s);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onComplete() {
                Log.i(TAG, "observer2 - onComplete");
            }
        };


        replaySubject.subscribe(observer1);

        replaySubject.onNext("Merhaba");
        replaySubject.onNext("Aykut");

        replaySubject.subscribe(observer2);

        replaySubject.onNext("Napıyosun");
        replaySubject.onNext("İyi misin ? ");


    }

    private void tryPublishSubject() {

        PublishSubject<String> publishSubject = PublishSubject.create();
        publishSubject.observeOn(AndroidSchedulers.mainThread());

        Observer<String> observer1 = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.i(TAG, "observer1 - onSubscribe");
            }

            @Override
            public void onNext(String s) {
                Log.i(TAG, "observer1 - onNext: " + s);
            }

            @Override
            public void onError(Throwable e) {
                showSnackbar(e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.i(TAG, "observer1 - onComplete");
            }
        };

        Observer<String> observer2 = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.i(TAG, "observer2 - onSubscribe");
            }

            @Override
            public void onNext(String s) {
                Log.i(TAG, "observer2 - onNext: " + s);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onComplete() {
                Log.i(TAG, "observer2 - onComplete");
            }
        };


        publishSubject.subscribe(observer1);

        publishSubject.onNext("Merhaba");
        publishSubject.onNext("Aykut");

        publishSubject.subscribe(observer2);

        publishSubject.onNext("Napıyosun");
        publishSubject.onNext("İyi misin ? ");

    }

    /**
     * Not: observeOn(AndroidSchedulers.mainThread()) belirtilmezse onComplete UiThread de çalıştırılmaz.
     * Bu nedenle uygulama hata alır.
     */
    private void tryFlowable() {

        Flowable.range(1, 10)
                .delay(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(success -> {

                    Log.i(TAG, String.valueOf(success));
                    //showSnackbar(success);
                }, error -> {
                    showTextView(error.getMessage());
                }, () -> {
                    // runOnUiThread(() -> showTextView("HATA"));
                    showTextView("completed");
                });
    }

    /**
     * Single ve Completable birleşimi gibi düşünebiliriz.
     * Sadece tek bir değer yayar.
     * Yayınlıcak değer yoksa onComplate çalıştırılır
     * Hata meydana gelirse onError çalıştırılır.
     */
    private void tryMaybe() {

        Maybe.just(5)
                .flatMap(new Function<Integer, MaybeSource<Integer>>() {
                    @Override
                    public MaybeSource<Integer> apply(@NonNull Integer deger) throws Exception {
                        //return Maybe.error(new Exception("HATA"));
                        return Maybe.just(2);
                    }
                })
                .filter(deger -> deger > 3)
                .map(String::valueOf)
                //.defaultIfEmpty("3")
                .subscribe(succ -> {
                    mTextView.setText(succ);
                }, error -> {
                    mTextView.setText(error.getMessage());
                }, () -> {
                    Snackbar.make(mTextView, "complated", Snackbar.LENGTH_SHORT).show();
                });

    }

    class Abc {

    }

    private void deneme() {

//        Observable.range(1, 10)
//                .map(new Function<Integer, Integer>() {
//                    @Override
//                    public Integer apply(Integer deger) throws Exception {
//                        return deger;
//                    }
//                })
//                .map(new Function<Integer, String>() {
//                    @Override
//                    public String apply(Integer ınteger) throws Exception {
//                        return null;
//                    }
//                })
//                .subscribe(success -> {
//
//                });

    }

    private void executorServiceNewSingleThreadExecutor() {

        ExecutorService executorService = Executors.newSingleThreadExecutor();

        for (int a = 0; a < 10000; a++) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    Log.i(TAG, "ExecuterServicee: " + Thread.currentThread().getName());
                }
            });
        }

    }

    private void executorServiceNewFixedThreadPool() {

        ExecutorService executorService = Executors.newFixedThreadPool(1);
        ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) executorService;
        threadPoolExecutor.setCorePoolSize(2);
        threadPoolExecutor.setMaximumPoolSize(2);

        for (int a = 0; a < 10000; a++) {
            threadPoolExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    Log.i(TAG, "ExecuterServicee: " + Thread.currentThread().getName());
                }
            });
        }
    }

    private void executorServiceNewCachedThreadPool() {

        ExecutorService executorService = Executors.newCachedThreadPool();

        for (int a = 0; a < 10000; a++) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    Log.i(TAG, "ExecuterServicee: " + Thread.currentThread().getName());
                }
            });
        }
    }

    private void executorServiceExecute() {
        ExecutorService executorService = Executors.newCachedThreadPool();

        for (int a = 0; a < 40; a++) {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    //
                    Log.i(TAG, "ExecuterService: " + Thread.currentThread().getName());
                }
            });
        }
    }

    private void executorServiceSubmit() {

        ExecutorService executorService = Executors.newFixedThreadPool(20);

        for (int a = 0; a < 40; a++) {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    //
                    Log.i(TAG, "ExecuterService: " + Thread.currentThread().getName());
                }
            });
        }
    }

    private void callable() throws Exception {
        Callable<User> callable = () -> {
            User user = new User();
            user.setAd("Aykut");
            user.setSoyad("Asil");
            return user;
        };

        mTextView.setText(callable.call().getAd());
    }

    private void runnable() {
        Runnable runnable = () -> {
            mTextView.setText("Runnable is run");
        };
        runnable.run();

        try {
            Executors.callable(runnable, new User()).call();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void replaySubject() {

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


    private void showSnackbar(String value) {
        Snackbar.make(mTextView, value, Snackbar.LENGTH_SHORT).show();
    }

    private void showSnackbar(Integer value) {
        Snackbar.make(mTextView, String.valueOf(value), Snackbar.LENGTH_SHORT).show();
    }

    private void showTextView(String value) {
        mTextView.setText(value);
    }

}
