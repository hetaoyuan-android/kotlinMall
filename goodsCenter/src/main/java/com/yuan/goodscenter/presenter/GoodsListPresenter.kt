package com.kotlin.goods.presenter

import com.kotlin.goods.data.protocol.Goods
import com.kotlin.goods.presenter.view.GoodsListView
import com.kotlin.goods.service.GoodsService
import com.yuan.baselibrary.ext.execute
import com.yuan.baselibrary.presenter.BasePresenter
import com.yuan.baselibrary.rx.BaseSubscriber
import javax.inject.Inject

/*
    商品列表 Presenter
 */
class GoodsListPresenter @Inject constructor() : BasePresenter<GoodsListView>() {

    @Inject
    lateinit var goodsService: GoodsService


    /*
        获取商品列表
     */
    fun getGoodsList(categoryId: Int, pageNo: Int) {
        mView.showLoading()
        goodsService.getGoodsList(categoryId,pageNo).execute(object : BaseSubscriber<MutableList<Goods>?>(mView) {
            override fun onNext(t: MutableList<Goods>?) {
                    mView.onGetGoodsListResult(t)
            }
        }, lifecycleProvider)

    }

    /*
        根据关键字 搜索商品
     */
    fun getGoodsListByKeyword(keyword: String, pageNo: Int) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        goodsService.getGoodsListByKeyword(keyword,pageNo).execute(object : BaseSubscriber<MutableList<Goods>?>(mView) {
            override fun onNext(t: MutableList<Goods>?) {
                mView.onGetGoodsListResult(t)
            }
        }, lifecycleProvider)

    }

}
