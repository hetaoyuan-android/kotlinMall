package com.yuan.baselibrary.ext

import android.view.View
import android.widget.Button
import android.widget.EditText
import com.kotlin.base.widgets.DefaultTextWatcher
import com.trello.rxlifecycle.LifecycleProvider
import com.yuan.baselibrary.data.protocol.BaseResp
import com.yuan.baselibrary.rx.BaseFunc
import com.yuan.baselibrary.rx.BaseFuncBoolean
import com.yuan.baselibrary.rx.BaseSubscriber
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

fun <T> Observable<T>.execute(subscriber: BaseSubscriber<T>, lifecycleProvider: LifecycleProvider<*>) {
    // 将观察者切换到主线程
    this.observeOn(AndroidSchedulers.mainThread())
        .compose(lifecycleProvider.bindToLifecycle())
        // 将被观察者切换到子线程
        .subscribeOn(Schedulers.io())
        // 创建观察者并订阅
        .subscribe(subscriber)
}

fun <T> Observable<BaseResp<T>>.convert() : Observable<T>{
    return this.flatMap(BaseFunc())
}

fun <T> Observable<BaseResp<T>>.convertBoolean() : Observable<Boolean>{
    return this.flatMap(BaseFuncBoolean())
}

fun View.onClick(listener:View.OnClickListener) {
    this.setOnClickListener(listener)
}

fun View.onClick(method: ()->Unit) {
    this.setOnClickListener { method() }
}

fun Button.enable(et:EditText, method: () -> Boolean) {
    val btn = this
    et.addTextChangedListener(object : DefaultTextWatcher(){
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            btn.isEnabled = method()
        }
    })
}