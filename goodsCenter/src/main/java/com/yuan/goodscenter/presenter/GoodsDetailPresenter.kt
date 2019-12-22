package com.kotlin.goods.presenter

import com.kotlin.base.utils.AppPrefsUtils
import com.kotlin.goods.common.GoodsConstant
import com.kotlin.goods.data.protocol.Goods
import com.kotlin.goods.presenter.view.GoodsDetailView
import com.kotlin.goods.service.CartService
import com.kotlin.goods.service.GoodsService
import com.yuan.baselibrary.ext.execute
import com.yuan.baselibrary.presenter.BasePresenter
import com.yuan.baselibrary.rx.BaseSubscriber
import javax.inject.Inject

/*
    商品详情 Presenter
 */
class GoodsDetailPresenter @Inject constructor() : BasePresenter<GoodsDetailView>() {

    @Inject
    lateinit var goodsService: GoodsService

    @Inject
    lateinit var cartService: CartService

    /*
        获取商品详情
     */
    fun getGoodsDetailList(goodsId: Int) {
        mView.showLoading()
        goodsService.getGoodsDetail(goodsId).execute(object : BaseSubscriber<Goods>(mView) {
            override fun onNext(t: Goods) {
                    mView.onGetGoodsDetailResult(t)
            }
        }, lifecycleProvider)

    }

    /*
        加入购物车
     */
    fun addCart(goodsId: Int, goodsDesc: String, goodsIcon: String, goodsPrice: Long,
                           goodsCount: Int, goodsSku: String) {
        mView.showLoading()
        cartService.addCart(goodsId,goodsDesc,goodsIcon,goodsPrice,
                goodsCount,goodsSku).execute(object : BaseSubscriber<Int>(mView) {
            override fun onNext(t: Int) {
                AppPrefsUtils.putInt(GoodsConstant.SP_CART_SIZE,t)
                mView.onAddCartResult(t)
            }
        }, lifecycleProvider)

    }

}
