package com.yuan.baselibrary.ext

import com.yuan.baselibrary.rx.BaseSubscriber
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

fun <T> Observable<T>.execute(subscriber: BaseSubscriber<T>) {
    // 将观察者切换到主线程
    this.observeOn(AndroidSchedulers.mainThread())
        // 将被观察者切换到子线程
        .subscribeOn(Schedulers.io())
        // 创建观察者并订阅
        .subscribe(subscriber)
}