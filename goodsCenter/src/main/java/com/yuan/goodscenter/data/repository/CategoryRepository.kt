package com.kotlin.goods.data.repository

import com.kotlin.goods.data.api.CategoryApi
import com.kotlin.goods.data.protocol.Category
import com.kotlin.goods.data.protocol.GetCategoryReq
import com.yuan.baselibrary.data.net.RetrofitFactory
import com.yuan.baselibrary.data.protocol.BaseResp
import rx.Observable
import javax.inject.Inject

/*
    商品分类 数据层
 */
class CategoryRepository @Inject constructor(){
    /*
        获取商品分类
     */
    fun getCategory(parentId: Int): Observable<BaseResp<MutableList<Category>?>> {
        return RetrofitFactory.instance.create(CategoryApi::class.java).getCategory(GetCategoryReq(parentId))
    }

}
