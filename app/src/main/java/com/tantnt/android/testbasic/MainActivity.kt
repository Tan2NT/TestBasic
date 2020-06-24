package com.tantnt.android.testbasic

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.work.*
import com.google.gson.Gson
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.internal.util.HalfSerializer.onNext
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {

    // Coroutine
    private var viewModelJob : Job = Job()
    private val coroutineScope : CoroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

       // testGson()

       //activityScope.launch {
           //setupWork()
       //}

        // RxJava
        testRxjava()

//        coroutineScope.launch {
//            val weatherRespone = WeatherApiNetwork.weatherApi.getCurrentWeatherByCityName("DaNang")
//            Log.i("TDebug", "weather: " + weatherRespone.toString())
//        }

        btnChangecolor.setOnClickListener {
            btnChangecolor.setBackgroundColor(Color.RED)
        }
    }


    // test Rxjava
    fun testRxjava() {
        // 1. Observable.from
//        Observable.fromArray("Apple", "Orange", "Banana")
//            .subscribe {
//                Log.i("TDebug", "$it")
//            }
//            .dispose()

        // 2. Observable.just
        val compositeDisposable = CompositeDisposable()

        val observale1 = Observable.just("Apple", "Orange", "Banana")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe (
                { value -> Log.i("TDebug", "$value") },
                { error -> Log.i("TDebug", "$error")},
                { Log.i("TDebug", "Completed!") }
            )
        compositeDisposable.add(observale1)
        compositeDisposable.clear()

        // 3.Observable.create
        val myList = listOf<String>("Tan", "Tri", "Thai")
        Observable.create<String> { emitter ->
            myList.forEach { kind ->
                if(kind == "") {
                    emitter.onError(Exception("There is no value to show"))
                }
                emitter.onNext(kind)
            }
            emitter.onComplete()
        }
            .subscribe { Log.i("TDebug", "Received $it") }
            .dispose()

        // 4. interval
        Observable.intervalRange(
            10L,
            5L,
            0L,
            2L,
            TimeUnit.SECONDS
        )
            .subscribe {  Log.i("TDebug", "Interval Received $it") }
            .dispose()
    }

    // test Gson
    fun testGson() {
        val student = Student("Tan", "Da Nang")

        // parse class into json string
        val jsonString = Gson().toJson(student)
        Log.i("TDebug", "student String: " + jsonString.toString())

        // jsonString to object
        val json1 = """
            { 
                "name": "Mark", 
                "address": "London"
             }
             """
        val student2 = Gson().fromJson(json1, Student::class.java)
        Log.i("TDebug", "student : " + student2.toString())
    }

    // worker
    class MyWork(appContext: Context, params: WorkerParameters): Worker(appContext, params) {
        override fun doWork(): Result {

            var i = 1
            while(i > 0) {
                Log.i("TDebug", "i = $i")
                i++
            }

            return Result.success()
        }
    }

    fun setupWork() {
        val constraint = Constraints.Builder()
                .setRequiresBatteryNotLow(true)
                .setRequiresCharging(true)
                .build()

        val request = OneTimeWorkRequestBuilder<MyWork>()
                //.setConstraints(constraint)
                .build()

        WorkManager.getInstance().enqueue(request)
    }

    private fun Loop() {
        var i = 1
        while(i > 0) {
            Log.i("TDebug", "i = $i")
            i++
        }
    }

    class MyLoop : Thread() {
        init {
            run()
        }

        override fun run() {
            Log.i("TDebug", "${Thread.currentThread()} has run")
            var i = 1
            while(i > 0) {
                Log.i("TDebug", "i = " + i.toString())
                i++
            }
        }
    }

}
