package com.yuan.baselibrary.rx

import com.yuan.baselibrary.common.ResultCode
import com.yuan.baselibrary.data.protocol.BaseResp
import rx.Observable
import rx.functions.Func1

class BaseFunc<T>: Func1<BaseResp<T>, Observable<T>> {
    override fun call(t: BaseResp<T>?): Observable<T> {
            if (t!!.status != ResultCode.SUCCESS) {
                return Observable.error(BaseExcaption(t.status, t.message))

        }
        return Observable.just(t?.data)
    }
}