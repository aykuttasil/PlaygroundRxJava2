package com.aykuttasil.rxjava2examples

import android.os.Bundle
import android.provider.CallLog
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.aykuttasil.rxjava2examples.models.User
import com.google.android.material.snackbar.Snackbar
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.MaybeSource
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.ReplaySubject
import kotlinx.android.synthetic.main.content_main.*
import java.util.concurrent.Callable
import java.util.concurrent.Executors
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

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
        tryReplaySubject()
    }

    private fun tryReplaySubject() {
        val replaySubject = ReplaySubject.create<String>()
        replaySubject.observeOn(AndroidSchedulers.mainThread())

        val observer1 = object : Observer<String> {
            override fun onSubscribe(d: Disposable) {
                Log.i(TAG, "observer1 - onSubscribe")
            }

            override fun onNext(s: String) {
                Log.i(TAG, "observer1 - onNext: $s")
            }

            override fun onError(e: Throwable) {
                showSnackbar(e.message!!)
            }

            override fun onComplete() {
                Log.i(TAG, "observer1 - onComplete")
            }
        }

        val observer2 = object : Observer<String> {
            override fun onSubscribe(d: Disposable) {
                Log.i(TAG, "observer2 - onSubscribe")
            }

            override fun onNext(s: String) {
                Log.i(TAG, "observer2 - onNext: $s")
            }

            override fun onError(e: Throwable) {
                e.printStackTrace()
            }

            override fun onComplete() {
                Log.i(TAG, "observer2 - onComplete")
            }
        }


        replaySubject.subscribe(observer1)

        replaySubject.onNext("Merhaba")
        replaySubject.onNext("Aykut")

        replaySubject.subscribe(observer2)

        replaySubject.onNext("Napıyosun")
        replaySubject.onNext("İyi misin ? ")


    }

    private fun tryPublishSubject() {

        val publishSubject = PublishSubject.create<String>()
        publishSubject.observeOn(AndroidSchedulers.mainThread())

        val observer1 = object : Observer<String> {
            override fun onSubscribe(d: Disposable) {
                Log.i(TAG, "observer1 - onSubscribe")
            }

            override fun onNext(s: String) {
                Log.i(TAG, "observer1 - onNext: $s")
            }

            override fun onError(e: Throwable) {
                showSnackbar(e.message!!)
            }

            override fun onComplete() {
                Log.i(TAG, "observer1 - onComplete")
            }
        }

        val observer2 = object : Observer<String> {
            override fun onSubscribe(d: Disposable) {
                Log.i(TAG, "observer2 - onSubscribe")
            }

            override fun onNext(s: String) {
                Log.i(TAG, "observer2 - onNext: $s")
            }

            override fun onError(e: Throwable) {
                e.printStackTrace()
            }

            override fun onComplete() {
                Log.i(TAG, "observer2 - onComplete")
            }
        }


        publishSubject.subscribe(observer1)

        publishSubject.onNext("Merhaba")
        publishSubject.onNext("Aykut")

        publishSubject.subscribe(observer2)

        publishSubject.onNext("Napıyosun")
        publishSubject.onNext("İyi misin ? ")

    }

    /**
     * Not: observeOn(AndroidSchedulers.mainThread()) belirtilmezse onComplete UiThread de çalıştırılmaz.
     * Bu nedenle uygulama hata alır.
     */
    private fun tryFlowable() {
        Flowable.range(1, 10)
                .delay(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ success ->

                    Log.i(TAG, success.toString())
                    //showSnackbar(success);
                }, { error -> showTextView(error.message!!) }, {
                    // runOnUiThread(() -> showTextView("HATA"));
                    showTextView("completed")
                })
    }

    /**
     * Single ve Completable birleşimi gibi düşünebiliriz.
     * Sadece tek bir değer yayar.
     * Yayınlıcak değer yoksa onComplate çalıştırılır
     * Hata meydana gelirse onError çalıştırılır.
     */
    private fun tryMaybe() {
        Maybe.just(5)
                .flatMap(object : Function<Int, MaybeSource<Int>> {
                    override fun apply(t: Int): MaybeSource<Int> {
                        //return Maybe.error(new Exception("HATA"));
                        return Maybe.just(2)
                    }
                })
                .filter { deger -> deger > 3 }
                .map { it.toString() }
                //.defaultIfEmpty("3")
                .subscribe({ succ -> TextViewResp.text = succ }, { error -> TextViewResp.text = error.message },
                        { Snackbar.make(TextViewResp, "complated", Snackbar.LENGTH_SHORT).show() })

    }

    internal inner class Abc

    private fun deneme() {

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

    private fun executorServiceNewSingleThreadExecutor() {

        val executorService = Executors.newSingleThreadExecutor()

        for (a in 0..9999) {
            executorService.execute { Log.i(TAG, "ExecuterServicee: " + Thread.currentThread().name) }
        }

    }

    private fun executorServiceNewFixedThreadPool() {

        val executorService = Executors.newFixedThreadPool(1)
        val threadPoolExecutor = executorService as ThreadPoolExecutor
        threadPoolExecutor.corePoolSize = 2
        threadPoolExecutor.maximumPoolSize = 2

        for (a in 0..9999) {
            threadPoolExecutor.execute { Log.i(TAG, "ExecuterServicee: " + Thread.currentThread().name) }
        }
    }

    private fun executorServiceNewCachedThreadPool() {

        val executorService = Executors.newCachedThreadPool()

        for (a in 0..9999) {
            executorService.execute { Log.i(TAG, "ExecuterServicee: " + Thread.currentThread().name) }
        }
    }

    private fun executorServiceExecute() {
        val executorService = Executors.newCachedThreadPool()

        for (a in 0..39) {
            executorService.submit {
                //
                Log.i(TAG, "ExecuterService: " + Thread.currentThread().name)
            }
        }
    }

    private fun executorServiceSubmit() {

        val executorService = Executors.newFixedThreadPool(20)

        for (a in 0..39) {
            executorService.submit {
                //
                Log.i(TAG, "ExecuterService: " + Thread.currentThread().name)
            }
        }
    }

    private fun callable() {
        val callable = Callable {
            val user = User()
            user.ad = "Aykut"
            user.soyad = "Asil"
            user
        }
        TextViewResp.text = callable.call().ad
    }

    private fun runnable() {
        val runnable = Runnable { TextViewResp.text = "Runnable is run" }
        runnable.run()

        try {
            Executors.callable(runnable, User()).call()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun replaySubject() {

    }

    private fun fibonacci() {
        val mIntList = mutableListOf<Int>(2)
        mIntList[0] = 0
        mIntList[1] = 1

        val observable = Observable.fromArray(0)
                .repeat()
                .scan(
                        mIntList.toTypedArray(),
                        { list, _ ->
                            arrayOf(list[1], list[0] + list[1])

                            /*int a = list[0];
                            list[0] = list[1];
                            list[1] = a + list[1];
                            Log.i(TAG, "list 0: " + list[0]);
                            Log.i(TAG, "list 1: " + list[1]);
                            return list;
                            */
                        }
                ).map { list ->
                    list[1]
                }

        observable.take(10)
                .subscribe { success -> Log.i(TAG, "val: $success") }
    }

    private fun materialize() {
        Observable.range(2, 10)
                .materialize()
                .filter { integerNotification ->
                    Log.i(TAG, "filter: $integerNotification")
                    integerNotification.value != 2
                }
                .subscribe { success ->
                    Log.i(TAG, "val: $success")
                    if (success.isOnNext) {
                        Log.i(TAG, "onNext: " + success.value)
                    }
                }
    }

    private fun range() {
        Observable.range(3, 5).subscribeOn(Schedulers.io())
                .subscribe { success -> Log.i(TAG, "val: " + success!!.toString()) }
    }

    private fun scan() {
        Observable.fromArray(3, 5, 7)
                .scan(10, { val1, val2 ->
                    //
                    Log.i(TAG, "val1: $val1")
                    Log.i(TAG, "val2: $val2")
                    val1 + val2
                })
                .subscribeOn(Schedulers.io())
                .subscribe { success -> Log.i(TAG, "Sonuc:" + success!!.toString()) }
    }

    private fun reduce() {
        Observable.just(1, 3, 5)
                .reduce(10, { val1, val2 ->
                    Log.i(TAG, "val1: $val1")
                    Log.i(TAG, "val2: $val2")
                    val1 + val2
                })
                .subscribeOn(Schedulers.computation())
                .subscribe { success -> Log.i(TAG, "Sonuc:" + success!!.toString()) }
    }

    private fun writeGetLastOutgoingCall() {
        val `val` = CallLog.Calls.getLastOutgoingCall(this)
        TextViewResp.text = `val`
    }


    private fun showSnackbar(value: String) {
        Snackbar.make(TextViewResp, value, Snackbar.LENGTH_SHORT).show()
    }

    private fun showSnackbar(value: Int?) {
        Snackbar.make(TextViewResp, value.toString(), Snackbar.LENGTH_SHORT).show()
    }

    private fun showTextView(value: String) {
        TextViewResp.text = value
    }

}
