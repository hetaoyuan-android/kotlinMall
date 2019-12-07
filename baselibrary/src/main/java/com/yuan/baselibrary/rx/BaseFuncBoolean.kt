package com.yuan.baselibrary.rx

import com.yuan.baselibrary.common.ResultCode
import com.yuan.baselibrary.data.protocol.BaseResp
import rx.Observable
import rx.functions.Func1

class BaseFuncBoolean<T>: Func1<BaseResp<T>, Observable<Boolean>> {
    override fun call(t: BaseResp<T>?): Observable<Boolean> {
        if (t != null) {
            if (t.status != ResultCode.SUCCESS) {
                return Observable.error(BaseExcaption(t.status, t.message))
            }
        }
        return Observable.just(true)
    }
}