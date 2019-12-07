package com.yuan.baselibrary.rx

import com.yuan.baselibrary.presenter.view.BaseView
import rx.Subscriber

open class BaseSubscriber<T>(val baseView: BaseView): Subscriber<T>(){
    override fun onNext(t: T) {
    }

    override fun onCompleted() {
        baseView.hideLoading()
    }

    override fun onError(e: Throwable?) {
        baseView.hideLoading()
        if (e is BaseExcaption) {
            baseView.onError(e.msg)
        }
    }
}